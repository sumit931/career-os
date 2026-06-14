package io.sumitnegi.careeros.runner;

import io.sumitnegi.careeros.automation.NaukriPlaywrightClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final NaukriPlaywrightClient naukriPlaywrightClient;

    @Override
    public void run(String... args){
        System.out.println("startup runner started functioning");
        naukriPlaywrightClient.launchBrowser();
    }
}
