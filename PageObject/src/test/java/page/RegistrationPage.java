package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.CustomConditions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RegistrationPage {
    private WebDriver driver;

    private final String URL = "https://markformelle.by/";
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

        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Element was not found")
                .until(ExpectedConditions.elementToBeClickable(personalButtonBy))
                .click();
        return this;
    }

    public void signIn(String email, String password) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Element was not found")
                .until(ExpectedConditions.presenceOfElementLocated(loginInputBy))
                .sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
        this.goToPersonalPage();
    }

    private void goToPersonalPage() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"bx_basketFKauiI\"]/a[@href='/personal/']")))
                .click();
    }
}
