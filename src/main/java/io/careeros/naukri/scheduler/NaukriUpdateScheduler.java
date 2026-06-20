package io.careeros.naukri.scheduler;

import io.careeros.naukri.automation.NaukriPlaywrightClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaukriUpdateScheduler {
    private final NaukriPlaywrightClient playwrightClient;

//    @Scheduled(cron = "0 * * * * *")
    public void checkProfile() {
        System.out.println("check profile process started");
        playwrightClient.launchBrowser();
        System.out.println("Profile updated");
    }
}