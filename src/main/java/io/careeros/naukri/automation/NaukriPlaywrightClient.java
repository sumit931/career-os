package io.careeros.naukri.automation;

import com.microsoft.playwright.*;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.repository.NaukriProfileUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NaukriPlaywrightClient {

    private final NaukriProfileUpdateRepository naukriProfileUpdateRepository;

    public void processBrowser(Browser browser, String email, String password, String resumeHeadline) throws  Exception{

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
                .fill(email);

        System.out.println("9. Email filled");

        System.out.println("10. Filling password");


        page.locator("input[placeholder='Enter your password']")
                .fill(password);

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
                .fill(resumeHeadline);
//            page.locator("#resumeHeadlineTxt")
//                    .fill("Software Developer with expertise in backend development, skilled in Java Spring,Node.js, MongoDB, REST APIs, and algorithms, enhancing productivity and engagement through innovative solutions.Software Development,Software Design,Software Debugging. Senior Software Engineer with expertise in Java, Spring Boot, Microservices, Kafka, Redis and distributed systems.");


        page.locator("button.btn-dark-ot[type='submit']")
                .click();


        Thread.sleep(1000);

        page.navigate("https://www.naukri.com/mnjuser/profile");

        Thread.sleep(1000);

        page.locator(".nI-gNb-drawer__icon").click();

        Thread.sleep(1000);


        page.getByText("Logout").click();


        Thread.sleep(5000);

    }


    public void launchBrowser() {
        System.out.println("1. Starting Playwright");

        try (Playwright playwright = Playwright.create()) {

            System.out.println("2. Creating browser");

            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setSlowMo(1000));

            List<NaukriProfileDetail> details  = naukriProfileUpdateRepository.findAll();
            for(NaukriProfileDetail profileDetail : details){
                String email = profileDetail.getEmail();
                String password = profileDetail.getPassword();
                String resumeHeadline = "";
                if(profileDetail.getChangeHeadline()==true){
                    profileDetail.setChangeHeadline(false);
                    resumeHeadline = profileDetail.getHeadline1();
                }
                else{
                    profileDetail.setChangeHeadline(true);
                    resumeHeadline = profileDetail.getHeadline2();
                }
                naukriProfileUpdateRepository.save(profileDetail);
                processBrowser(browser,email, password,resumeHeadline);

            }
//            String email = "sumitnegi9667@gmail.com";
//            String password = "@Swarup007";
//            String resumeHeadline = "Software Developer with expertise in backend development, skilled in Java Spring,Node.js, MongoDB, REST APIs, and algorithms, enhancing productivity and engagement through innovative solutions.Software Development,Software Design,Software Debugging.";


            System.out.println("12. Closing browser");

            browser.close();

            System.out.println("13. Browser closed successfully");

        } catch (Exception e) {
            System.out.println("ERROR OCCURRED");
            e.printStackTrace();
        }
    }
}
