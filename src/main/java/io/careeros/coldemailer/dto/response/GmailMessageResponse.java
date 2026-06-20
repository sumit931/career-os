package io.careeros.coldemailer.dto.response;

import java.util.List;

public record GmailMessageResponse(String id, String threadId, Payload payload) {

  public record Payload(List<Header> headers) {}

  public record Header(String name, String value) {}

  public String rfc2822MessageId() {
    return payload.headers().stream()
        .filter(h -> "Message-ID".equalsIgnoreCase(h.name()))
        .map(Header::value)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Message-ID header not found in Gmail response"));
  }
}
