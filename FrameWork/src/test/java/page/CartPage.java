package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.TestDataReader;
import util.ElementInteraction;
import util.ElementWaiting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebElement testedItem;

    private By infoModelBy = By.xpath(".//*[@data-column-property-code='PROPERTY_ARTNUMBER_VALUE']");
    private By infoColorBy = By.xpath(".//span[@class='props-name']");
    private By infoSizeBy = By.xpath(".//td//div[@class='jq-selectbox__select-text']");
    private By itemsBy = By.xpath("//*[@data-entity='basket-item']");
    private By deleteButtonBy = By.xpath("//span[@class='basket-item-actions-remove'][@data-entity='basket-item-delete']");
    private By additionalElementBy = By.xpath("//*[@data-entity='basket-coupon-input']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<List<String>> gatherInformation() {
        ElementWaiting.waitForElementToBeClickableBy(driver, additionalElementBy);
        List<WebElement> cartItems = ElementWaiting.waitForElementsLocatedBy(driver, itemsBy);
        List<List<String>> cartItemsInfo = new ArrayList<>();
        for (WebElement e : cartItems) {
            cartItemsInfo.add(Arrays.asList(
                    e.findElement(infoModelBy).getText(),
                    e.findElement(infoColorBy).getText().toLowerCase(),
                    e.findElement(infoSizeBy).getText()
            ));
        }

        return cartItemsInfo;
    }

    public CartPage deleteItem() {
        ElementWaiting.waitForElementToBeClickableBy(driver, additionalElementBy);
        List<WebElement> items = ElementWaiting
                .waitForElementsLocatedBy(driver, itemsBy);
        int size = items.size();
        testedItem = items.get(0);
        ElementInteraction.clickOnSource(driver, deleteButtonBy);
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.numberOfElementsToBeLessThan(itemsBy, size));
        return this;
    }

    public boolean checkIfDeleted() {
        return !driver.findElements(itemsBy)
                .contains(testedItem);
    }
}
