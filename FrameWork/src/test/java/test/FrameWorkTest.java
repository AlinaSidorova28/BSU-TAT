package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.*;
import service.TestDataReader;

import java.util.List;

public class FrameWorkTest extends CustomConditions {
    @Test (description = "test successful change of password")
    public void changePasswordTestSuccess() {
        new MainPage(driver).signInWithCredentials();
        String actual = new PersonalPage(driver)
                .changePassword("123456", "123456");
        Assert.assertEquals("Изменения сохранены", actual);
    }

    @Test (description = "test change of password with invalid data")
    public void changePasswordWithInvalidDataTest() {
        new MainPage(driver).signInWithCredentials();
        String actual = new PersonalPage(driver)
                .changePassword("123", "123456");
        Assert.assertEquals(
                "Пароль должен быть не менее 6 символов длиной.\nНеверное подтверждение пароля.\n",
                actual
        );
    }

    @Test (description = "test search results to match the correct query")
    public void commonSearchTermResultsMatchCorrectQuery() {
        new MainPage(driver).openTestedPage(TestDataReader.getTestData("testdata.homepage"));
        String correctQuery = "колготки";
        List<String> searchResults =  new SearchPage(driver)
                .openSearchForm()
                .inputSearchData(correctQuery)
                .gatherResults();

        Assert.assertTrue(searchResults.stream().allMatch(s -> s.contains(correctQuery)));
    }

    @Test (description = "test size XXS filter in catalog")
    public void filterBySizeTest() {
        new MainPage(driver).openTestedPage(TestDataReader.getTestData("testdata.catalog"));
        Assert.assertTrue(new CatalogPage(driver)
                .chooseFilter()
                .checkSizes("XXS"));
    }

    @Test (description = "test sort by price increasing in catalog")
    public void sortByPriceTest() {
        new MainPage(driver).openTestedPage(TestDataReader.getTestData("testdata.catalog.small"));
        Assert.assertTrue(
                new CatalogPage(driver)
                        .chooseSort()
                        .checkSort()
        );
    }


    @Test (description = "test adding item to cart")
    public void addToCartTest() {
        MainPage page = new MainPage(driver);
        page.signInWithCredentials();
//        page.openTestedPage(TestDataReader.getTestData("testdata.catalog"));
        driver.get(TestDataReader.getTestData("testdata.catalog"));
        List<String> itemInfo = new PopupPage(driver)
                .openPopup()
                .gatherInformation();
        List<List<String>> cartItems = new CartPage(driver)
                .openCartPage()
                .gatherInformation();
        Assert.assertTrue(cartItems.stream().anyMatch(i -> i.equals(itemInfo)));
        Assert.assertTrue(cartItems.stream().anyMatch(i -> i.equals(itemInfo)));
    }

    @Test (description = "test removing item from cart", dependsOnMethods="addToCartTest")
    public void removeFromCartTest() {
        new MainPage(driver).signInWithCredentials();
        Assert.assertTrue(
                new CartPage(driver)
                        .openCartPage()
                        .deleteItem()
                        .checkIfDeleted()
        );
    }
}