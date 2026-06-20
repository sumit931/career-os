package io.careeros.coldemailer.dto.mapper;

import io.careeros.coldemailer.dto.request.CreateCampaignRequest;
import io.careeros.coldemailer.entity.Campaign;
import io.careeros.coldemailer.entity.User;
import io.careeros.coldemailer.enums.CampaignStatus;

public class CampaignMapper {

  private CampaignMapper() {}

  public static Campaign toEntity(CreateCampaignRequest request, User user, String gmailThreadId, String rootMessageId, CampaignStatus status) {
    Campaign campaign = new Campaign();
    campaign.setUser(user);
    campaign.setRecipientEmail(request.recipientEmail());
    campaign.setSubject(request.subject());
    campaign.setInitialBody(request.initialBody());
    campaign.setStatus(status);
    campaign.setGmailThreadId(gmailThreadId);
    campaign.setRootMessageId(rootMessageId);
    return campaign;
  }
}
