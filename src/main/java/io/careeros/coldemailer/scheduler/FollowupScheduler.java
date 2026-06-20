package io.careeros.coldemailer.scheduler;

import io.careeros.auth.oauth.service.GoogleOAuthService;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.entity.Followup;
import io.careeros.coldemailer.enums.FollowupStatus;
import io.careeros.coldemailer.repository.FollowupRepository;
import io.careeros.coldemailer.service.EncryptionService;
import io.careeros.coldemailer.service.GmailService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowupScheduler {

  private final FollowupRepository followupRepository;
  private final EncryptionService encryptionService;
  private final GoogleOAuthService googleOAuthService;
  private final GmailService gmailService;

  @Scheduled(fixedDelay = 60000)
  @Transactional
  public void sendDueFollowups() {
    List<Followup> due = followupRepository.findDueFollowups(FollowupStatus.PENDING, LocalDateTime.now());
    if (due.isEmpty()) return;

    log.info("Found {} due follow-up(s) to send", due.size());

    for (Followup followup : due) {
      followup.setStatus(FollowupStatus.PROCESSING);
      followupRepository.save(followup);
      sendFollowup(followup);
    }
  }

  private void sendFollowup(Followup followup) {
    Campaign campaign = followup.getCampaign();
    try {
      String refreshToken = encryptionService.decrypt(campaign.getUser().getEncryptedRefreshToken());
      String accessToken = googleOAuthService.refreshAccessToken(refreshToken).accessToken();

      gmailService.sendFollowup(
          accessToken,
          campaign.getUser().getEmail(),
          campaign.getRecipientEmail(),
          campaign.getSubject(),
          followup.getBody(),
          campaign.getGmailThreadId(),
          campaign.getRootMessageId()
      );

      followup.setStatus(FollowupStatus.SENT);
      log.info("Sent follow-up #{} for campaign {}", followup.getSequenceNumber(), campaign.getId());
    } catch (Exception e) {
      followup.setStatus(FollowupStatus.FAILED);
      log.error("Failed to send follow-up #{} for campaign {}: {}", followup.getSequenceNumber(), campaign.getId(), e.getMessage());
    }
    followupRepository.save(followup);
  }
}
