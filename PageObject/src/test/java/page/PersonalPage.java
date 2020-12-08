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
import java.util.regex.Pattern;

public class PersonalPage {
    private WebDriver driver;

    private By newPasswordBy = By.xpath("//input[@name='NEW_PASSWORD']");
    private By passwordConfirmBy = By.xpath("//input[@name='NEW_PASSWORD_CONFIRM']");

    @FindBy(xpath="//input[@name='save']")
    private WebElement saveButton;

    public PersonalPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String changePassword(String password, String confirm) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Element was not found")
                .until(ExpectedConditions.elementToBeClickable(newPasswordBy))
                .sendKeys(password);

        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .withMessage("Element was not found")
                .until(ExpectedConditions.elementToBeClickable(passwordConfirmBy))
                .sendKeys(confirm);

        saveButton.click();
        WebElement res = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//font[string-length(text()) > 0]")));
        return res.getAttribute("innerText");
    }
}
