package base;

import enums.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SuppressWarnings("unused")
public abstract class MobilePage {
    protected AppiumDriver<MobileElement> driver;
    protected WebDriverWait wait;

    public MobilePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Nullable
    private String getWebContext(AppiumDriver<?> driver) {
        await().atMost(30, SECONDS).until(() -> driver.getContextHandles().size() > 1);

        Set<String> handles = driver.getContextHandles();
        for (Object context : handles) {
            if (!String.valueOf(context).equals("NATIVE_APP")) {
                return String.valueOf(context);
            }
        }
        return null;
    }

    public void switchToWebContext() {
        String webContext = this.getWebContext(driver);
        if (webContext != null) {
            driver.context(webContext);
            await().atMost(30, SECONDS).until(() -> driver.getPageSource().contains("html"));
        } else {
            throw new RuntimeException("Failed to switch to WebView context.");
        }
    }

    public void switchToNativeContext() {
        driver.context("NATIVE_APP");
    }

    public MobileElement findElement(By locator, Duration timeout) {
        try {
            driver.manage().timeouts().implicitlyWait(0, SECONDS);
            return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        } finally {
            driver.manage().timeouts().implicitlyWait(30, SECONDS);
        }
    }

    public MobileElement findElement(By locator) {
        return findElement(locator, Duration.ofSeconds(30));
    }

    public MobileElement findByText(String text, boolean exactMatch) {
        By locator;
        String automation = driver.getCapabilities().getCapability("automationName").toString();
        if (automation.equalsIgnoreCase(AutomationName.ANDROID_UIAUTOMATOR2)) {
            if (exactMatch) {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")");
            } else {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
            }
        } else if (automation.equalsIgnoreCase(AutomationName.IOS_XCUI_TEST)) {
            if (exactMatch) {
                String exactPredicate = "name == \"" + text + "\" OR label == \"" + text + "\"";
                locator = MobileBy.iOSNsPredicateString(exactPredicate);
            } else {
                String containsPredicate = "name contains '" + text + "' OR label contains '" + text + "'";
                locator = MobileBy.iOSNsPredicateString(containsPredicate);
            }
        } else {
            throw new RuntimeException("Unsupported automation technology: " + automation);
        }
        return driver.findElement(locator);
    }

    public MobileElement findByText(String text) {
        return findByText(text, true);
    }

    public void swipeFromCenterToScreenEdge(Direction dir) {
        final int ANIMATION_TIME = 500; // ms
        final int PRESS_TIME = 200; // ms
        final int EDGE_BORDER = 10; // Ignore device edges

        // init screen variables
        Dimension dims = driver.manage().window().getSize();

        // init start point = center of screen
        PointOption<?> pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        PointOption<?> pointOptionEnd;
        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - EDGE_BORDER);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, EDGE_BORDER);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(EDGE_BORDER, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - EDGE_BORDER, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        new TouchAction<>(driver)
                .press(pointOptionStart)
                // a bit more reliable when we add small wait
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                .moveTo(pointOptionEnd)
                .release().perform();

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}