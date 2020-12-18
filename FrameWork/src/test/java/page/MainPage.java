package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import util.ElementWaiting;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signInWithCredentials() {
        new RegistrationPage(driver)
                .openTestedPage()
                .openSignInForm()
                .signIn();
    }

    public void goToPage(By by) {
        ElementWaiting
                .waitForElementToBeClickableBy(driver, by)
                .click();
    }
}
