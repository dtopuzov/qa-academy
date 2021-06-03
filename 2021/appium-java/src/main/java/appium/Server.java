package appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import settings.Settings;

import java.net.URL;

/**
 * Appium server utils.
 */
public class Server {
    private final Settings settings;
    private AppiumDriverLocalService service;

    public Server(Settings settings) {
        this.settings = settings;
    }

    public void start() {
        // Construct AppiumServiceBuilder
        String logLevel = settings.isDebug() ? "debug" : "warn";
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.LOG_LEVEL, logLevel)
                .withArgument(GeneralServerFlag.RELAXED_SECURITY);

        // Start Appium Server
        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
    }

    public URL getUrl() {
        return service.getUrl();
    }

    public void stop() {
        if (service != null) {
            service.stop();
        }
    }
}
