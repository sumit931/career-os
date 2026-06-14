package io.careeros.careeros.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NaukriUpdateScheduler {
    boolean check;
    public void NuakriUpdateScheduler(){
        check = false;
    }
    @Scheduled(cron = "0 * * * * *")
    public void checkProfile() {
//        System.out.println("check profile process started");
//        NaukriPlaywrightClient playwrightClient = new NaukriPlaywrightClient();
//        if(check == false){
//            System.out.println("It is working inside");
//            check = true;
//            playwrightClient.launchBrowser();
//        }
//        System.out.println("Profile updated");
    }
}