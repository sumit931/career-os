package io.careeros.naukri.automation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.repository.NaukriProfileUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaukriPlaywrightClient {

    private final NaukriProfileUpdateRepository naukriProfileUpdateRepository;

    public void processBrowser(Browser browser, String email, String password, String resumeHeadline) {

        log.info("Browser launched, creating new page");
        Page page = browser.newPage();

        page.navigate("https://www.naukri.com");
        page.locator("#login_Layer").waitFor();

        log.info("Navigated to Naukri — URL: {}, Title: {}", page.url(), page.title());

        page.locator("#login_Layer").click();
        log.debug("Login button clicked");

        page.locator("input[placeholder='Enter your active Email ID / Username']").waitFor();
        page.locator("input[placeholder='Enter your active Email ID / Username']").fill(email);
        page.locator("input[placeholder='Enter your password']").fill(password);
        log.debug("Credentials filled");

        page.locator(".loginButton").click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        log.info("Logged in successfully");

        page.navigate("https://www.naukri.com/mnjuser/profile");
        page.locator("#lazyResumeHead").waitFor();
        log.debug("Profile page loaded, lazyResumeHead count: {}", page.locator("#lazyResumeHead").count());

        page.locator("#lazyResumeHead").locator("text=editOneTheme").click();

        Locator headline = page.locator("#resumeHeadlineTxt");
        headline.waitFor();
        log.debug("Headline textarea — visible: {}, enabled: {}", headline.isVisible(), headline.isEnabled());

        headline.fill(resumeHeadline);

        page.locator("button.btn-dark-ot[type='submit']").click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        log.info("Resume headline updated");

        page.navigate("https://www.naukri.com/mnjuser/profile");
        page.locator(".nI-gNb-drawer__icon").waitFor();

        page.locator(".nI-gNb-drawer__icon").click();
        page.getByText("Logout").waitFor();
        page.getByText("Logout").click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        log.info("Logged out successfully");
    }


    public void launchBrowser() {
        log.info("Starting Playwright");

        try (Playwright playwright = Playwright.create()) {

            log.info("Launching browser");
            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setSlowMo(1000));

            List<NaukriProfileDetail> details = naukriProfileUpdateRepository.findAll();
            for (NaukriProfileDetail profileDetail : details) {
                String email = profileDetail.getEmail();
                String password = profileDetail.getPassword();
                String resumeHeadline;
                if (profileDetail.getChangeHeadline()) {
                    profileDetail.setChangeHeadline(false);
                    resumeHeadline = profileDetail.getHeadline1();
                } else {
                    profileDetail.setChangeHeadline(true);
                    resumeHeadline = profileDetail.getHeadline2();
                }
                naukriProfileUpdateRepository.save(profileDetail);
                processBrowser(browser, email, password, resumeHeadline);
            }

            log.info("Closing browser");
            browser.close();
            log.info("Browser closed successfully");

        } catch (Exception e) {
            log.error("Browser automation failed", e);
        }
    }
}
