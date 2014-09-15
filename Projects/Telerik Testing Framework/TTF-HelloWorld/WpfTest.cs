using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using ArtOfTest.WebAii.Controls.HtmlControls;
using ArtOfTest.WebAii.Controls.HtmlControls.HtmlAsserts;
using ArtOfTest.WebAii.Core;
using ArtOfTest.WebAii.ObjectModel;
using ArtOfTest.WebAii.TestAttributes;
using ArtOfTest.WebAii.TestTemplates;
using ArtOfTest.WebAii.Win32.Dialogs;

using ArtOfTest.WebAii.Silverlight;
using ArtOfTest.WebAii.Controls.Xaml.Wpf;
using ArtOfTest.WebAii.Wpf;

using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.IO;
using System.Threading;

namespace TTF_HelloWorld_WPF
{
    /// <summary>
    /// Hello world WPF test with Telerik Testing Framework.
    /// Using default [Setup / TearDown] templates that comes with TTF installation.
    /// Those tests are just to show how you can locate and interact with elements.
    /// 
    /// Note: Plase copy TestApp\Calculator.exe in C:\Temp\TestApp\Calculator.exe
    /// </summary>
    [TestClass]
    public class WpfTest : BaseWpfTest
    {

        #region [Setup / TearDown]

        private TestContext testContextInstance = null;
        /// <summary>
        ///Gets or sets the VS test context which provides
        ///information about and functionality for the
        ///current test run.
        ///</summary>
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


        //Use ClassInitialize to run code before running the first test in the class
        [ClassInitialize()]
        public static void MyClassInitialize(TestContext testContext)
        {
        }


        // Use TestInitialize to run code before running each test
        [TestInitialize()]
        public void MyTestInitialize()
        {
            #region WebAii Initialization

            // Initializes WebAii manager to be used by the test case.
            // If a WebAii configuration section exists, settings will be
            // loaded from it. Otherwise, will create a default settings
            // object with system defaults.
            //
            // Note: We are passing in a delegate to the VisualStudio
            // testContext.WriteLine() method in addition to the Visual Studio
            // TestLogs directory as our log location. This way any logging
            // done from WebAii (i.e. Manager.Log.WriteLine()) is
            // automatically logged to the VisualStudio test log and
            // the WebAii log file is placed in the same location as VS logs.
            //
            // If you do not care about unifying the log, then you can simply
            // initialize the test by calling Initialize() with no parameters;
            // that will cause the log location to be picked up from the config
            // file if it exists or will use the default system settings (C:\WebAiiLog\)
            // You can also use Initialize(LogLocation) to set a specific log
            // location for this test.

            Initialize(this.TestContext.TestLogsDir, new TestContextWriteLine(this.TestContext.WriteLine));

            // If you need to override any other settings coming from the
            // config section you can comment the 'Initialize' line above and instead
            // use the following:

            /*

            // This will get a new Settings object. If a configuration
            // section exists, then settings from that section will be
            // loaded

            Settings settings = GetSettings();

            // Override the settings you want. For example:
            settings.WaitCheckInterval = 10000;

            // Now call Initialize again with your updated settings object
            Initialize(settings, new TestContextWriteLine(this.TestContext.WriteLine));

            */

            // Set the current test method. This is needed for WebAii to discover
            // its custom TestAttributes set on methods and classes.
            // This method should always exist in [TestInitialize()] method.
            SetTestMethod(this, (string)TestContext.Properties["TestName"]);

            #endregion

            //
            // Place any additional initialization here
            //

        }

        // Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {

            //
            // Place any additional cleanup here
            //

            #region WebAii CleanUp

            // Shuts down WebAii manager and closes all applications currently running
            // after each test.
            this.CleanUp();

            #endregion
        }

        //Use ClassCleanup to run code after all tests in a class have run
        [ClassCleanup()]
        public static void MyClassCleanup()
        {
            // This will shut down all applications
            ShutDown();
        }

        #endregion

        [TestMethod]
        public void WpfCalc_Demo_LocateElements()
        {
            var CalcPath = @"c:\Temp\TestApp\Calculator.exe";

            var CalculatorApp = Manager.LaunchNewApplication(CalcPath);
            var Calc = CalculatorApp.WaitForWindow("WPF Calculator");

            // Find element by name and verify properties
            var ButtonOne = Calc.Find.ByName<Button>("one");
            Assert.AreEqual("one", ButtonOne.Name);
            Assert.AreEqual("1", ButtonOne.Text);
            Assert.AreEqual(40, ButtonOne.Height);
            Assert.AreEqual(true, ButtonOne.IsEnabled);
            Assert.AreEqual(true, ButtonOne.IsVisible);

            // Find all buttons in the calculator
            var AllButtons = Calc.Find.AllByType<Button>();
            Assert.AreEqual(19, AllButtons.Count);

            // Get the one with text Enter (using Linq)
            var EnterButton = AllButtons.Where(b => b.Text == "Enter").FirstOrDefault();
            Assert.AreEqual("enter", EnterButton.Name);

            // Locate CE button with XamlFindExpression
            var CEButton = Calc.Find.ByExpression(new XamlFindExpression("Name=clear", "XamlTag=Button"));
            Assert.AreEqual("CE", CEButton.Text);

            // Find all buttons containing "c" in name
            var CButtonsByName = Calc.Find.AllByExpression(new XamlFindExpression("Name=~c", "XamlTag=Button"));
            Assert.AreEqual(3, CButtonsByName.Count);        
        }

        [TestMethod]
        public void WpfCalc_Demo_PerformActions()
        {
            // Start the calculator
            var CalcPath = @"c:\Temp\TestApp\Calculator.exe";
            var CalculatorApp = Manager.LaunchNewApplication(CalcPath);
            var Calc = CalculatorApp.WaitForWindow("WPF Calculator");

            // Default Click action
            Calc.Find.ByName<Button>("one").User.Click();

            // Click action with specified click type and coordinates
            Calc.Find.ByName<Button>("add").User.Click(MouseClickType.LeftClick, 10, 10, ArtOfTest.Common.OffsetReference.TopLeftCorner);

            // Hover over FrameworkElement (this is just to show we can hover over element)
            Calc.Find.ByName<Button>("one").User.HoverOver(-3, -3, ArtOfTest.Common.OffsetReference.AbsoluteCenter);
            
            // Hover over and click via Desktop class
            var OneButton = Calc.Find.ByName<Button>("one");
            var OneButtonRectangle = OneButton.GetScreenRectangle();
            Manager.Desktop.Mouse.HoverOver(OneButtonRectangle.GetCenterPoint());
            Manager.Desktop.Mouse.Click(MouseClickType.LeftClick, OneButtonRectangle.GetCenterPoint());

            // Drag WPF Calculator window
            var CalcWindowRectangle = Calc.Window.Rectangle;
            Calc.Desktop.Mouse.DragDrop(CalcWindowRectangle.Left + 50, CalcWindowRectangle.Top + 5, CalcWindowRectangle.Left + 150, CalcWindowRectangle.Top + 105, 10, 250);

            // Click Enter via setting the focus on "enter" button and hit Enter key on the keyboard
            Calc.Find.ByName<Button>("enter").SetFocus();
            Desktop.KeyBoard.KeyPress(System.Windows.Forms.Keys.Enter);

            // Verify result is correct
            var result = Calc.Find.ByName<TextBox>("textBox1").Text;
            Assert.AreEqual("2", result);
        }
    }
}
