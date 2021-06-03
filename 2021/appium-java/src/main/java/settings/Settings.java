package settings;

import org.openqa.selenium.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

public class Settings {

    private final Properties properties;

    public Settings() {
        String config = System.getProperty("config");
        if (config != null) {
            properties = loadProperties(config);
        } else {
            throw new RuntimeException("Config not specified!");
        }
    }

    public String getDeviceName() {
        return properties.getProperty("deviceName");
    }

    public Platform getPlatform() {
        String platformString = properties.getProperty("platform");
        if (platformString.toLowerCase().contains("android")) {
            return Platform.ANDROID;
        } else if (platformString.toLowerCase().contains("ios")) {
            return Platform.IOS;
        } else {
            throw new RuntimeException(String.format("Unknown platform: %s", platformString));
        }
    }

    public double getPlatformVersion() {
        String platformVersionString = properties.getProperty("platformVersion");
        return Double.parseDouble(platformVersionString);
    }

    public String getUdid() {
        return properties.getProperty("udid");
    }

    public String getAvdName() {
        return properties.getProperty("avdName");
    }

    public String getAppPath() {
        String appPath = properties.getProperty("appPath");
        if (appPath != null && !appPath.toLowerCase().contains("http")) {
            appPath = System.getProperty("user.dir") + File.separator + "testapp" + File.separator + appPath;
            File f = new File(appPath);
            if (!f.exists() || f.isDirectory()) {
                throw new RuntimeException(String.format("Invalid appPath: %s", appPath));
            }
        }
        return appPath;
    }

    public String getAppPackage() {
        return properties.getProperty("appPackage");
    }

    public String getAppActivity() {
        return properties.getProperty("appActivity");
    }

    public String getBrowserType() {
        return properties.getProperty("browser");
    }

    public String getChromeDriverVersion() {
        return properties.getProperty("chromeDriverVersion");
    }

    public boolean isDebug() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
    }

    private Properties loadProperties(String fileName) {
        ClassLoader classLoader = Settings.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName + ".properties");
        if (resource != null) {
            Properties properties = new Properties();
            try {
                properties.load(resource);
            } catch (IOException e) {
                String message = String.format("Fail to load properties from %s file.", fileName + ".properties");
                throw new RuntimeException(message);
            }
            return properties;
        } else {
            String message = String.format("Specified config file does not exist: %s", fileName + ".properties");
            throw new RuntimeException(message);
        }
    }
}