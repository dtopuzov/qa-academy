package tests;

import base.MobileTest;
import org.junit.Test;
import pages.HomePage;
import pages.LoginDemo;

public class SmokeTests extends MobileTest {

    @Test
    public void loginValidUser() {
        HomePage home = new HomePage(driver);
        LoginDemo loginDemo = home.openLoginDemo();
        loginDemo.login("Admin12345@gmail.com", "Admin12345");
        loginDemo.verifyAlertText("You are logged in!");
    }
}
