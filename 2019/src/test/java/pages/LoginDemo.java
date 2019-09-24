package pages;

import base.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginDemo extends MobilePage {

    @AndroidFindBy(id = "username")
    @iOSXCUITFindBy(id = "username")
    private MobileElement user;

    @AndroidFindBy(id = "password")
    @iOSXCUITFindBy(id = "password")
    private MobileElement pass;

    @AndroidFindBy(id = "loginBtn")
    @iOSXCUITFindBy(id = "loginBtn")
    private MobileElement login;

    public LoginDemo(AppiumDriver driver) {
        super(driver);
    }

    public void login(String userName, String password) {
        driver.getKeyboard();
        user.sendKeys(userName);
        pass.sendKeys(password);
        login.click();
    }

    public void verifyAlert() {
        String message = driver.findElement(By.id("message")).getText();
        driver.findElement(By.id("button1")).click();
        Assert.assertEquals("Invalid login credentials, please try again", message);
    }
}