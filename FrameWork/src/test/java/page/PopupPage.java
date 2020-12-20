package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.ElementWaiting;

import java.util.List;
import java.util.stream.Collectors;

public class PopupPage {
    private WebDriver driver;

    private By popupLinkBy = By.xpath("//*[@id='ax-catalog']//li[1]/div/a[1]");
    private By additionalElementBy = By.id("arrFilter_217_3233089245-styler");
    private By infoModelColorBy = By.xpath("//*[@class='mf-product-info-column-2']//div[@class='view_popup-header']//span");
    private By toCartButtonBy = By.xpath("//a[@class='button-link black-tr-b btn-pink']");

    @FindBy(xpath="//*[@id='ax-catalog']//li[1]/div[1]")
    private WebElement item;

    @FindBy(xpath="//*[@id='mf-pr-table-atts']/div[1]/ul/li[contains(@class, 'selected')]")
    private WebElement selectedSize;

    @FindBy(xpath="//*[@class='mf-input-quantity']/div/input")
    private WebElement selectedQuantity;

    @FindBy(xpath="//a[@class='button-link black-tr-b btn-pink']")
    private WebElement toCartButton;

    public PopupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PopupPage openPopup() {
        ElementWaiting.waitForElementToBeClickableBy(driver, additionalElementBy);

        Actions builder = new Actions(driver);
        builder.moveToElement(item).perform();
        builder
                .moveToElement(ElementWaiting.waitForElementToBeClickableBy(driver, popupLinkBy))
                .click()
                .perform();
        return this;
    }

    public List<String> gatherInformation() {
        ElementWaiting.waitForElementToBeClickableBy(driver, toCartButtonBy);
        List<String> res = ElementWaiting
                .waitForElementsLocatedBy(driver, infoModelColorBy)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        res.add(selectedSize.getText());
        res.add(selectedQuantity.getAttribute("value"));

        toCartButton.click();
        return res;
    }
}
