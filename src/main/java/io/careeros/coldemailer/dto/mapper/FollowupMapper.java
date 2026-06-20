package io.careeros.coldemailer.dto.mapper;

import io.careeros.coldemailer.dto.response.CampaignResponse;
import io.careeros.coldemailer.dto.response.FollowupResponse;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.entity.Followup;
import io.careeros.coldemailer.enums.FollowupStatus;
import java.util.List;

public class FollowupMapper {

  private FollowupMapper() {}

  public static Followup toEntity(Campaign campaign, String body, int sequenceNumber) {
    Followup followup = new Followup();
    followup.setCampaign(campaign);
    followup.setBody(body);
    followup.setSequenceNumber(sequenceNumber);
    followup.setStatus(FollowupStatus.PENDING);
    return followup;
  }

  public static FollowupResponse toResponse(Followup followup) {
    return new FollowupResponse(
        followup.getId(),
        followup.getSequenceNumber(),
        followup.getBody(),
        followup.getStatus()
    );
  }

  public static CampaignResponse toCampaignResponse(Campaign campaign, List<FollowupResponse> followups) {
    return new CampaignResponse(
        campaign.getId(),
        campaign.getRecipientEmail(),
        campaign.getSubject(),
        campaign.getInitialBody(),
        campaign.getStatus(),
        followups
    );
  }
}
