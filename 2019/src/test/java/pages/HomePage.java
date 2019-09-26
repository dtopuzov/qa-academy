package pages;

import base.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

@SuppressWarnings("unused")
public class HomePage extends MobilePage {

    @AndroidFindBy(accessibility = "Login")
    @iOSXCUITFindBy(accessibility = "Login")
    private MobileElement loginScreenButton;

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    public LoginDemo openLoginDemo() {
        loginScreenButton.click();
        return new LoginDemo(driver);
    }
}
