package io.careeros.coldemailer.service;

import io.careeros.coldemailer.dto.mapper.CampaignMapper;
import io.careeros.coldemailer.dto.mapper.FollowupMapper;
import io.careeros.coldemailer.dto.request.CreateCampaignRequest;
import io.careeros.coldemailer.dto.response.CampaignResponse;
import io.careeros.coldemailer.dto.response.FollowupResponse;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.entity.User;
import io.careeros.coldemailer.exception.UserNotFoundException;
import io.careeros.coldemailer.repository.CampaignRepository;
import io.careeros.coldemailer.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CampaignService {

  private final CampaignRepository campaignRepository;
  private final UserRepository userRepository;
  private final FollowupService followupService;

  @Transactional
  public CampaignResponse createWithFollowups(CreateCampaignRequest request) {
    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new UserNotFoundException(request.userId()));

    Campaign saved = campaignRepository.save(CampaignMapper.toEntity(request, user));

    List<FollowupResponse> followups = followupService.generateAndSave(saved.getId(), request.followupCount());
    return FollowupMapper.toCampaignResponse(saved, followups);
  }
}
