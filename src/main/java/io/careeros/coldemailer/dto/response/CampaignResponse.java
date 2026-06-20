package io.careeros.coldemailer.dto.response;

import io.careeros.coldemailer.enums.CampaignStatus;
import java.util.List;
import java.util.UUID;

public record CampaignResponse(
    UUID id,
    String recipientEmail,
    String subject,
    String initialBody,
    CampaignStatus status,
    List<FollowupResponse> followups
) {}
