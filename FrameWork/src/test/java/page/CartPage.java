package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import service.TestDataReader;
import util.ElementWaiting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    private By infoModelBy = By.xpath(".//*[@data-column-property-code='PROPERTY_ARTNUMBER_VALUE']");
    private By infoColorBy = By.xpath(".//span[@class='props-name']");
    private By infoSizeBy = By.xpath(".//td//div[@class='jq-selectbox__select-text']");
    private By infoAmountBy = By.xpath(".//input[@class='basket-item-amount-filed']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CartPage openCartPage() {
        driver.get(TestDataReader.getTestData("testdata.cart"));
        return this;
    }

    public List<List<String>> gatherInformation() {
        ElementWaiting.waitForElementToBeClickableBy(driver, By.xpath("//*[@data-entity='basket-coupon-input']"));
        List<WebElement> cartItems = ElementWaiting.waitForElementsLocatedBy(driver, By.xpath("//*[@data-entity='basket-item']"));
        List<List<String>> cartItemsInfo = new ArrayList<>();
        for (WebElement e : cartItems) {
            cartItemsInfo.add(Arrays.asList(
                    e.findElement(infoModelBy).getText(),
                    e.findElement(infoColorBy).getText().toLowerCase(),
                    e.findElement(infoSizeBy).getText(),
                    e.findElement(infoAmountBy).getAttribute("value")
            ));
        }
        return cartItemsInfo;
    }
}
