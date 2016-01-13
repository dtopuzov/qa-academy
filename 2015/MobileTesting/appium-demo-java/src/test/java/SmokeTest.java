import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;

public class SmokeTest {
    private static AndroidDriver<MobileElement> driver;
    private static AppiumDriverLocalService service;

    @BeforeClass
    public static void beforeClass() throws Exception {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(AndroidServerFlag.AVD, "Emulator-Api21-Default")
                .withArgument(AndroidServerFlag.AVD_ARGS, "-scale 0.5")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "warn");
        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
    }

    @Before
    public void setup() throws Exception {
        if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");

        File appDir = new File("../testapp");
        File app = new File(appDir, "android-rottentomatoes-demo-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private static MobileElement listView() {
        return driver.findElement(By.className("android.widget.ListView"));
    }

    private static List<MobileElement> listViewItems() {
        return driver.findElements(By.xpath("//android.widget.ListView/*"));
    }

    private static MobileElement score() {
        return driver.findElementById("com.codepath.example.rottentomatoes:id/tvAudienceScore");
    }

    private static void homeLoaded() {
        Assert.assertNotNull("Home page not loaded.", listView());
    }

    private static void detailsLoaded() {
        Assert.assertNotNull("Details page not loaded.", score());
    }

    enum Directions {Up, Down}

    ;

    private static void swipe(Directions direction) {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        int startX = width / 2;
        int startY = height / 2;
        int endX = width / 2;
        int endY = height / 2;

        if (direction == Directions.Down) {
            startY = ((Double) (height * 0.75)).intValue();
            endY = ((Double) (height * 0.25)).intValue();
        } else if (direction == Directions.Up) {
            startY = ((Double) (height * 0.25)).intValue();
            endY = ((Double) (height * 0.75)).intValue();
        }

        driver.swipe(startX, startY, endX, endY, 1000);
    }

    @Test
    public void masterDetailNavigation() {
        homeLoaded();
        for (int i = 0; i < 3; i++) {
            listViewItems().get(i).tap(1, 500);
            detailsLoaded();
            driver.navigate().back();
            homeLoaded();
        }
    }

    @Test
    public void swipeUpAndDown() {
        homeLoaded();
        swipe(Directions.Down);
        swipe(Directions.Up);
        swipe(Directions.Down);
    }

    @Test
    public void runAppInBackground() {
        homeLoaded();
        listViewItems().get(1).tap(1, 500);
        detailsLoaded();
        driver.runAppInBackground(10);
        detailsLoaded();
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
        service.stop();
    }
}
