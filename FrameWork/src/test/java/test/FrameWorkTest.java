package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.MainPage;
import page.PersonalPage;

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
}