using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Appium.Android;
using System.Reflection;
using System.IO;

namespace AppiumDemo
{
    [TestClass]
    public class AppiumDemo
    {
        private static AppiumDriver<AppiumWebElement> driver;

        private TestContext testContextInstance;

        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        [ClassInitialize]
        public static void ClassInit(TestContext context)
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.SetCapability("deviceName", "Android Emulator");
            capabilities.SetCapability("platformName", "Android");
            capabilities.SetCapability("app", "c:\\Git\\testapp\\android-rottentomatoes-demo-debug.apk");
            driver = new AndroidDriver<AppiumWebElement>(new Uri("http://127.0.0.1:4723/wd/hub"), capabilities);
        }

        [ClassCleanup]
        public static void ClassCleanup()
        {
            driver.Quit();
        }

        [TestInitialize]
        public void TestInit()
        {
        }

        [TestCleanup]
        public void TestCleanup()
        {
        }

        [TestMethod]
        public void SimpleTest()
        {
            // Tap first 3 list view items and navigate back
            
            var listView = driver.FindElementByClassName("android.widget.ListView"); 
            var listViewItems = driver.FindElementsByXPath("//android.widget.ListView/android.widget.RelativeLayout");
            for (int i = 0; i < 3; i++)
            {
                listViewItems[i].Click(); // Use click because Tap does not work (Appium Server 1.4.16)
                var score = driver.FindElementById("com.codepath.example.rottentomatoes:id/tvAudienceScore");
                Console.WriteLine("Score: " + score.Text);
                driver.Navigate().Back();
                listView = driver.FindElementByClassName("android.widget.ListView");
            }
        }
    }
}
