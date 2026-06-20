package io.careeros.coldemailer.service;

import io.careeros.coldemailer.dto.mapper.CampaignMapper;
import io.careeros.coldemailer.dto.mapper.FollowupMapper;
import io.careeros.coldemailer.dto.request.CreateCampaignRequest;
import io.careeros.coldemailer.dto.response.CampaignResponse;
import io.careeros.coldemailer.dto.response.FollowupResponse;
import io.careeros.coldemailer.dto.response.GmailSendResponse;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.entity.User;
import io.careeros.coldemailer.exception.FollowupGenerationException;
import io.careeros.coldemailer.exception.UserNotFoundException;
import io.careeros.coldemailer.enums.CampaignStatus;
import io.careeros.coldemailer.repository.CampaignRepository;
import io.careeros.coldemailer.repository.UserRepository;
import io.careeros.auth.oauth.service.GoogleOAuthService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampaignService {

  private final CampaignRepository campaignRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final GoogleOAuthService googleOAuthService;
  private final GmailService gmailService;
  private final FollowupService followupService;

  private void validateFollowupBodies(List<String> bodies, int expectedCount) {
    if (bodies == null || bodies.size() != expectedCount) {
      throw new FollowupGenerationException(
          "Expected " + expectedCount + " follow-up(s) from AI but got " + (bodies == null ? 0 : bodies.size())
      );
    }
    boolean hasBlank = bodies.stream().anyMatch(b -> b == null || b.isBlank());
    if (hasBlank) {
      throw new FollowupGenerationException("AI returned one or more blank follow-up bodies");
    }
  }

  public CampaignResponse createWithFollowups(CreateCampaignRequest request) {
    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new UserNotFoundException(request.userId()));

    String refreshToken = userService.getDecryptedRefreshToken(request.userId());
    String accessToken = googleOAuthService.refreshAccessToken(refreshToken).accessToken();

    // Gemini first — if this fails or returns bad output, no email is sent
    List<String> followupBodies = followupService.generateBodies(
        request.subject(), request.initialBody(), request.followupCount()
    );
    validateFollowupBodies(followupBodies, request.followupCount());

    GmailSendResponse emailResponse = gmailService.sendEmail(
        accessToken, user.getEmail(), request.recipientEmail(), request.subject(), request.initialBody()
    );
    String rfc2822MessageId = gmailService.fetchRfc2822MessageId(accessToken, emailResponse.id());

    // Email sent — persist the campaign immediately so we always have a record
    Campaign saved = campaignRepository.save(
        CampaignMapper.toEntity(request, user, emailResponse.threadId(), rfc2822MessageId, CampaignStatus.ACTIVE)
    );

    try {
      List<FollowupResponse> followups = followupService.save(
          saved, followupBodies, request.gapDays(), request.preferredHour()
      );
      return FollowupMapper.toCampaignResponse(saved, followups);
    } catch (Exception e) {
      saved.setStatus(CampaignStatus.FAILED);
      campaignRepository.save(saved);
      throw e;
    }
  }
}
