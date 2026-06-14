package io.careeros.naukri.scheduler;

import io.careeros.naukri.automation.NaukriPlaywrightClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaukriUpdateScheduler {
    boolean check;
    private final NaukriPlaywrightClient playwrightClient;
    public void NuakriUpdateScheduler(){
        check = false;
    }
    @Scheduled(cron = "0 * * * * *")
    public void checkProfile() {
        System.out.println("check profile process started");
        if(check == false){
            System.out.println("It is working inside");
            check = true;
            playwrightClient.launchBrowser();
        }
        System.out.println("Profile updated");
    }
}