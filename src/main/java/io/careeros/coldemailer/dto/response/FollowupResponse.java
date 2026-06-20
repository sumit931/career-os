package io.careeros.coldemailer.dto.response;

import io.careeros.coldemailer.enums.FollowupStatus;
import java.util.UUID;

public record FollowupResponse(
    UUID id,
    int sequenceNumber,
    String body,
    FollowupStatus status
) {}
