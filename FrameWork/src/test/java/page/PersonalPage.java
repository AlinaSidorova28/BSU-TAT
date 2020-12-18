package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ElementWaiting;

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
        new ElementWaiting()
                .waitForElementToBeClickableBy(driver, newPasswordBy)
                .sendKeys(password);

        new ElementWaiting()
                .waitForElementToBeClickableBy(driver, passwordConfirmBy)
                .sendKeys(confirm);

        saveButton.click();
        WebElement res = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//font[string-length(text()) > 0]")));
        return res.getAttribute("innerText");
    }
}
