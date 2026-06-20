package io.careeros.coldemailer.controller;

import io.careeros.coldemailer.dto.request.CreateCampaignRequest;
import io.careeros.coldemailer.dto.response.CampaignResponse;
import io.careeros.coldemailer.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {

  private final CampaignService campaignService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CampaignResponse createCampaign(@RequestBody CreateCampaignRequest request) {
    return campaignService.createWithFollowups(request);
  }
}
