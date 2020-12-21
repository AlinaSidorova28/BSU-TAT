package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.TestDataReader;
import test.CustomConditions;

import java.util.concurrent.TimeUnit;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signInWithCredentials() {
        openTestedPage(TestDataReader.getTestData("testdata.homepage"));
        new RegistrationPage(driver)
                .openSignInForm()
                .signIn();
    }

    public void openTestedPage(String URL) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get(URL);
        new WebDriverWait(driver, 15)
                .until(CustomConditions.jQueryAjaxCompleted());
    }
}
