package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.CustomConditions;
import util.ElementWaiting;

import java.util.concurrent.TimeUnit;

public class MainPage {
    private WebDriver driver;
    private final String URL = "https://markformelle.by/";

    public MainPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signInWithCredentials() {
        openTestedPage();
        new RegistrationPage(driver)
                .openSignInForm()
                .signIn();
    }

    public void openTestedPage() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get(URL);
        new WebDriverWait(driver, 15)
                .until(CustomConditions.jQueryAjaxCompleted());
    }

    public void goToPage(By by) {
        ElementWaiting
                .waitForElementToBeClickableBy(driver, by)
                .click();
    }
}
