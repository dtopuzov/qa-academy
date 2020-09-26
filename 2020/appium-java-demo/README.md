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

First of all please view config files located at `<ProjectRoot>\src\main\resources` and make sure you specify AVD image that is available on your machine.

Please also look at the top of `MobileTest` class where config is hardcoded and update if needed.

Notes:

    Hardcoding configs is bad practice, please do not use it in real world projects.

Run all tests in single class:
```
gradlew.bat clean test --tests "tests.SmokeTests"
```
    
Run all tests in package:
```
gradlew.bat clean test --tests "tests.*"
```

Note that on Linux and macOS you should call `./gradlew` instead of `gradlew.bat`.