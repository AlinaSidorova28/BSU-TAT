package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementInteraction {
    public static void clickOnSource(WebDriver driver, By by) {
        ElementWaiting
                .waitForElementToBeClickableBy(driver, by)
                .click();
    }
}
