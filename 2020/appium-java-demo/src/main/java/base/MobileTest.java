package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileTest {
    protected static AppiumDriver driver;
    private static AppiumDriverLocalService service;

    @BeforeClass
    public void beforeAll() {
        startServer();
        startAndroid();
    }

    @BeforeMethod
    public void beforeTest() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @AfterMethod(alwaysRun = true)
    public void afterEach() {
        driver.closeApp();
    }


    @AfterClass
    public void afterAll() {
        driver.quit();
        service.stop();
    }

    private static void startServer() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error");

        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
    }

    private static void startIOS() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS.toString());
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.0");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        caps.setCapability(MobileCapabilityType.APP, getAppPath("iOS-Simulator-NativeDemoApp-0.2.1.app.zip"));
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60 * 5);
        URL url = service.getUrl();

        driver = new AppiumDriver<>(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private static void startAndroid() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID.toString());
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus5Api23");
        caps.setCapability(AndroidMobileCapabilityType.AVD, "Nexus5Api23");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability(MobileCapabilityType.APP, getAppPath("Android-NativeDemoApp-0.2.1.apk"));
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60 * 5);
        caps.setCapability(MobileCapabilityType.ORIENTATION, ScreenOrientation.PORTRAIT);
        URL url = service.getUrl();

        driver = new AppiumDriver<>(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private static String getAppPath(String appFile) {
        return System.getProperty("user.dir") + File.separator + "testapp" + File.separator + appFile;
    }
}
