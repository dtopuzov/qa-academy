package tests;

import base.MobileTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginDemo;

public class SmokeTests extends MobileTest {

    @Test
    void loginInvalidUser() {
        HomePage home = new HomePage(driver);
        LoginDemo login = home.openLoginDemo();
        login.login("Admin12345@gmail.com", "Admin12345");
        login.verifyAlert();
    }
}
