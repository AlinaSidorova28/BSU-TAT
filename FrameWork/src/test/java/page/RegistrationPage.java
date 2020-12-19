package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.TestDataReader;
import util.ElementInteraction;
import util.ElementWaiting;

public class RegistrationPage {
    private WebDriver driver;

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
        ElementInteraction.clickOnSource(driver, By.xpath("//*[@id=\"bx_basketFKauiI\"]/a[@href='/personal/']"));
    }
}
