package io.careeros.naukri.automation;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Component;

@Component
public class NaukriPlaywrightClient {

    public void launchBrowser() {
        System.out.println("1. Starting Playwright");

        try (Playwright playwright = Playwright.create()) {

            System.out.println("2. Creating browser");

            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setSlowMo(1000));

            System.out.println("3. Browser launched");

            Page page = browser.newPage();

            System.out.println("4. New page created");

            page.navigate("https://www.naukri.com");

            System.out.println("5. Navigated to Naukri");
            System.out.println("Current URL = " + page.url());
            System.out.println("Page Title = " + page.title());

            Thread.sleep(2000);

            System.out.println("6. Trying to click login button");

            page.locator("#login_Layer").click();

            System.out.println("7. Login button clicked");

            System.out.println("8. Filling email");

            page.locator("input[placeholder='Enter your active Email ID / Username']")
                    .fill("sumitnegi9667@gmail.com");

            System.out.println("9. Email filled");

            System.out.println("10. Filling password");


            page.locator("input[placeholder='Enter your password']")
                    .fill("@Swarup007");

            System.out.println("11. Password filled");



            page.locator(".loginButton").click();

            Thread.sleep(2000);

            page.navigate("https://www.naukri.com/mnjuser/profile");

            Thread.sleep(2000);

            System.out.println(
                    page.locator("#lazyResumeHead").count()
            );

            page.locator("#lazyResumeHead").locator("text=editOneTheme").click();

            Thread.sleep(2000);

            // Fill textarea

            Locator headline = page.locator("#resumeHeadlineTxt");

            System.out.println("Count = " + headline.count());
            System.out.println("Visible = " + headline.isVisible());
            System.out.println("Enabled = " + headline.isEnabled());

            page.locator("#resumeHeadlineTxt")
                    .fill("Testing ho rhi h bhut bhayankar");
//            page.locator("#resumeHeadlineTxt")
//                    .fill("Software Developer with expertise in backend development, skilled in Java Spring,Node.js, MongoDB, REST APIs, and algorithms, enhancing productivity and engagement through innovative solutions.Software Development,Software Design,Software Debugging. Senior Software Engineer with expertise in Java, Spring Boot, Microservices, Kafka, Redis and distributed systems.");

            page.locator("button.btn-dark-ot[type='submit']")
                    .click();

            Thread.sleep(5000);

            System.out.println("12. Closing browser");

            browser.close();

            System.out.println("13. Browser closed successfully");

        } catch (Exception e) {
            System.out.println("ERROR OCCURRED");
            e.printStackTrace();
        }
    }
}
