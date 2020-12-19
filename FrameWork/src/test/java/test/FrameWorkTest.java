package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.CatalogPage;
import page.MainPage;
import page.PersonalPage;
import page.SearchPage;
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

    @Test (description = "test size sort in catalog")
    public void sortBySizeTest() {
        new MainPage(driver).openTestedPage(TestDataReader.getTestData("testdata.catalog"));
        Assert.assertTrue(
                new CatalogPage(driver)
                        .chooseFilter()
                        .collectItems("XXS")
        );
    }
}