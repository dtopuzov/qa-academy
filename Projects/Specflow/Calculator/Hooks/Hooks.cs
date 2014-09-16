using ArtOfTest.WebAii.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TechTalk.SpecFlow;

namespace Calculator.Hooks
{
    [Binding]
    public class Hooks
    {
        public static Manager MyManager { get; set; }

        [BeforeTestRun]
        public static void BeforeTestRun()
        {
            MyManager = new Manager(false);
            MyManager.Settings.ExecutionDelay = 0;
            MyManager.Settings.Web.RecycleBrowser = true;
            MyManager.Start();            
        }

        [BeforeScenario]
        public void BeforeScenario()
        {
            MyManager.LaunchNewBrowser();
        }

        [AfterScenario]
        public void AfterScenario()
        {
        }

        [AfterTestRun]
        public static void AfterTestRun()
        {
            MyManager.Dispose();
        }
    }
}
