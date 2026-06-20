package io.careeros.coldemailer.service;

import io.careeros.coldemailer.dto.mapper.FollowupMapper;
import io.careeros.coldemailer.dto.response.FollowupResponse;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.exception.CampaignNotFoundException;
import io.careeros.coldemailer.repository.CampaignRepository;
import io.careeros.coldemailer.repository.FollowupRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowupService {

  private final CampaignRepository campaignRepository;
  private final FollowupRepository followupRepository;
  private final GeminiService geminiService;

  @Transactional
  public List<FollowupResponse> generateAndSave(UUID campaignId, int count, int gapDays, int preferredHour) {
    Campaign campaign = campaignRepository.findById(campaignId)
        .orElseThrow(() -> new CampaignNotFoundException(campaignId));

    List<String> bodies = geminiService.generateFollowups(campaign.getSubject(), campaign.getInitialBody(), count);

    AtomicInteger sequence = new AtomicInteger(1);
    return followupRepository.saveAll(
        bodies.stream()
            .map(body -> {
              int seq = sequence.getAndIncrement();
              LocalDateTime scheduledAt = LocalDateTime.now()
                  .plusDays((long) seq * gapDays)
                  .withHour(preferredHour)
                  .withMinute(0)
                  .withSecond(0)
                  .withNano(0);
              return FollowupMapper.toEntity(campaign, body, seq, scheduledAt);
            })
            .toList()
    ).stream().map(FollowupMapper::toResponse).toList();
  }
}
