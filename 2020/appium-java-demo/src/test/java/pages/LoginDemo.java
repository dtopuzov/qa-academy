package pages;

import base.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.testng.Assert;

@SuppressWarnings("unused")
public class LoginDemo extends MobilePage {

    @AndroidFindBy(accessibility = "input-email")
    @iOSXCUITFindBy(accessibility = "input-email")
    private MobileElement user;

    @AndroidFindBy(accessibility = "input-password")
    @iOSXCUITFindBy(accessibility = "input-password")
    private MobileElement pass;

    @AndroidFindBy(accessibility = "button-LOGIN")
    @iOSXCUITFindBy(accessibility = "button-LOGIN")
    private MobileElement login;

    LoginDemo(AppiumDriver driver) {
        super(driver);
    }

    public void login(String userName, String password) {
        driver.getKeyboard();
        user.sendKeys(userName);
        pass.sendKeys(password);
        login.click();
    }

    public void verifyAlertText(String text) {
        String message;
        String platformString = this.driver.getCapabilities().getCapability("platformName").toString();
        if (platformString.equals(Platform.ANDROID.toString())) {
            message = driver.findElement(By.id("android:id/message")).getText();
            driver.findElement(By.id("android:id/button1")).click();
        } else {
            message = driver.findElement(MobileBy.AccessibilityId(text)).getText();
            driver.findElement(MobileBy.AccessibilityId("OK")).click();
        }
        Assert.assertEquals(message, text, "Unexpected message.");
    }
}
