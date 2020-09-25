# Appium Demos

## About

Sample mobile tests against native Android and iOS application.

Technologies:
- [Gradle](https://gradle.org/) as build system
- [TestNG](https://testng.org/doc/) as unit testing framework
- [Appium](http://appium.io/) to interact with mobile devices/emulators/simulators

Application under test:
- [WebdriverIO Demo App for iOS and Android](https://github.com/webdriverio/native-demo-app/releases)

## Execute Tests

Run all tests in single class:
```
gradlew.bat clean test --tests "tests.SmokeTests"
```
    
Run all tests in package:
```
gradlew.bat clean test --tests "tests.*"
```

Note that on Linux and macOS you should call `./gradlew` instead of `gradlew.bat`.