# Appium Demos

## About

Sample mobile tests against native Android and iOS application.

Technologies:
- [Maven](https://maven.apache.org/) as build system
- [Junit](https://junit.org/junit4/) as unit testing framework
- [Appium](http://appium.io/) to interact with mobile devices/emulators/simulators

Application under test:
- [WebdriverIO Demo App for iOS and Android](https://github.com/webdriverio/native-demo-app/releases)

## Execute Tests

First please view config files located at `<ProjectRoot>/src/main/resources` and make sure you specify AVD image that is available on your machine.

Please also look at the top of `MobileTest` class where config is hardcoded and update if needed.

Notes:

    Hardcoding configs is bad practice, please do not use it in real world projects.

    
Run all tests in single class:
```
mvn -Dtest=tests.SmokeTests test
```
    
Run all tests in package:
```
mvn -Dtest=tests.* test
```
