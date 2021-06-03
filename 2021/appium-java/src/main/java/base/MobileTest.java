package base;

import appium.Client;
import appium.Server;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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
import settings.Settings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MobileTest {

    protected static Settings settings;
    protected static AppiumDriver<MobileElement> driver;

    private static Client client;
    private static Server server;

    @BeforeClass
    public static void beforeAll() {
        settings = new Settings();
        server = new Server(settings);
        server.start();
        client = new Client(settings);
        client.start(server.getUrl());
        driver = client.getDriver();
    }

    @Before
    public void beforeTest() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void afterEach() {
    }

    @AfterClass
    public static void afterAll() {
        client.stop();
        server.stop();
    }
}
