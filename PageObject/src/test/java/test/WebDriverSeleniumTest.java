package test;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.PersonalPage;
import page.RegistrationPage;

public class WebDriverSeleniumTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.setProxy(null);
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }

    @Test (description = "test successful change of password", invocationCount = 3)
    public void changePasswordTestSuccess() {
        new RegistrationPage(driver)
                .openTestedPage()
                .openSignInForm()
                .signIn("alina28java@gmail.com", "123456");


        String actual = new PersonalPage(driver)
                .changePassword("123456", "123456");

        Assert.assertEquals(actual, "Изменения сохранены");
    }

    @Test (description = "test change of password with invalid data", invocationCount = 3)
    public void changePasswordWithInvalidDataTest() {
        new RegistrationPage(driver)
                .openTestedPage()
                .openSignInForm()
                .signIn("alina28java@gmail.com", "123456");

        String actual = new PersonalPage(driver)
                .changePassword("123", "123456");

        Assert.assertEquals(
                actual,
                "Пароль должен быть не менее 6 символов длиной.\nНеверное подтверждение пароля.\n"
        );
    }
}