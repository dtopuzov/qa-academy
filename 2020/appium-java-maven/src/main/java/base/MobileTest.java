package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MobileTest {

    private static final String CONFIG = "android.emulator.properties";
    private static AppiumDriverLocalService service;
    protected static AppiumDriver driver;

    @BeforeClass
    public static void beforeAll() {
        startServer();
        startClient();
    }

    @Before
    public void beforeTest() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void afterEach() {
        driver.closeApp();
    }


    @AfterClass
    public static void afterAll() {
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

    private static void startClient() {
        InputStream inputStream = MobileTest.class.getClassLoader().getResourceAsStream(CONFIG);
        Properties config = new Properties();
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, config.getProperty("platform"));
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("deviceName"));
        caps.setCapability(MobileCapabilityType.APP, getAppPath(config.getProperty("testApp")));
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        if (config.getProperty("platformVersion") != null) {
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("platformVersion"));
        }
        if (config.getProperty("avdName") != null) {
            caps.setCapability(AndroidMobileCapabilityType.AVD, config.getProperty("avdName"));
        }
        if (config.getProperty("udid") != null) {
            caps.setCapability(MobileCapabilityType.UDID, config.getProperty("udid"));
        }

        URL url = service.getUrl();
        driver = new AppiumDriver<>(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private static String getAppPath(String appFile) {
        return System.getProperty("user.dir") + File.separator + "testapp" + File.separator + appFile;
    }
}
