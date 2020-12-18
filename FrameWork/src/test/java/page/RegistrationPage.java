package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.TestDataReader;
import test.CustomConditions;
import util.ElementWaiting;

import java.util.concurrent.TimeUnit;

public class RegistrationPage {
    private WebDriver driver;

    private final String URL = "https://markformelle.by/";
    public static final String TESTDATA_USER_EMAIL = TestDataReader.getTestData("testdata.user.email");
    public static final String TESTDATA_USER_PASSWORD = TestDataReader.getTestData("testdata.user.password");
    private By personalButtonBy = By.xpath("//*[@id='bx_basketFKauiI']/a" +
            "[@class='header-profile js-popup-modal-input']" +
            "[@href='javascript:void(0)']");
    private By loginInputBy = By.xpath("//input[@name='USER_LOGIN']");

    @FindBy(xpath="//input[@name='USER_PASSWORD']")
    private WebElement passwordInput;

    @FindBy(xpath="//input[@name='Login']")
    private WebElement loginButton;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RegistrationPage openTestedPage() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get(URL);
        new WebDriverWait(driver, 15)
                .until(CustomConditions.jQueryAjaxCompleted());
        return this;
    }

    public RegistrationPage openSignInForm() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='promo']")));

        ElementWaiting
                .waitForElementToBeClickableBy(driver, personalButtonBy)
                .click();
        return this;
    }

    public void signIn() {
        ElementWaiting
                .waitForElementToBeClickableBy(driver, loginInputBy)
                .sendKeys(TESTDATA_USER_EMAIL);
        passwordInput.sendKeys(TESTDATA_USER_PASSWORD);
        loginButton.click();
        new MainPage(driver).goToPage(By.xpath("//*[@id=\"bx_basketFKauiI\"]/a[@href='/personal/']"));
    }
}
