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
using ArtOfTest.WebAii.Silverlight.UI;

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Telerik.WebAii.Controls.Html;
using System.Threading;

namespace TTF_HelloWorld_Web
{
    /// <summary>
    /// Hello world web tests with Telerik Testing Framework.
    /// Using default [Setup / TearDown] templates that comes with TTF installation.
    /// Those tests are just to show how you can locate and interact with elements.
    /// </summary>
    [TestClass]
    public class WebTest : BaseTest
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

            // Pass in 'true' to recycle the browser between test methods
            Initialize(false, this.TestContext.TestLogsDir, new TestContextWriteLine(this.TestContext.WriteLine));

            // If you need to override any other settings coming from the
            // config section you can comment the 'Initialize' line above and instead
            // use the following:

            /*

            // This will get a new Settings object. If a configuration
            // section exists, then settings from that section will be
            // loaded

            Settings settings = GetSettings();

            // Override the settings you want. For example:
            settings.Web.DefaultBrowser = BrowserType.FireFox;

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

            // Shuts down WebAii manager and closes all browsers currently running
            // after each test. This call is ignored if recycleBrowser is set
            this.CleanUp();

            #endregion
        }

        //Use ClassCleanup to run code after all tests in a class have run
        [ClassCleanup()]
        public static void MyClassCleanup()
        {
            // This will shut down all browsers if
            // recycleBrowser is turned on. Else
            // will do nothing.
            ShutDown();
        }

        #endregion

        [TestMethod]
        public void AJAX_GridCombo_NavigateTo()
        {
            // Launch a browser instance
            Manager.LaunchNewBrowser(BrowserType.InternetExplorer);

            // Maximize Browser's window
            ActiveBrowser.Window.Maximize();
            
            // Navigate the active browser GridCombo demo of AJAX controls
            ActiveBrowser.NavigateTo("http://demos.telerik.com/aspnet-ajax/controls/examples/integration/gridcomboajax/defaultcs.aspx");
            ActiveBrowser.WaitUntilReady();

            // Verify page title
            Assert.AreEqual("ASP.NET ComboBox Demo - Grid Filtered by Combo", ActiveBrowser.PageTitle);
        }

        [TestMethod]
        public void AJAX_GridCombo_LocateElementsDemo()
        {
            AJAX_GridCombo_NavigateTo();

            // Locate Grid by Id
            var Grid = ActiveBrowser.Find.ById<HtmlDiv>("ctl00_ContentPlaceHolder1_OrdersGrid");
            Assert.AreEqual("ctl00_ContentPlaceHolder1_OrdersGrid", Grid.ID);

            // Locate Grid by Attributes
            Grid = ActiveBrowser.Find.ByAttributes<HtmlDiv>("class=RadGrid RadGrid_Silk");
            Assert.AreEqual("ctl00_ContentPlaceHolder1_OrdersGrid", Grid.ID);

            // Locate Grid by XPath
            Grid = ActiveBrowser.Find.ByXPath<HtmlDiv>("//*[@id='ctl00_ContentPlaceHolder1_OrdersGrid']");
            Assert.AreEqual("ctl00_ContentPlaceHolder1_OrdersGrid", Grid.ID);

            // This HtmlFindExpression will math controls with ID ends with "_OrdersGrid" AND class attribute contains Grid
            var FindExpression = new HtmlFindExpression("id=?_OrdersGrid", "class=~RadGrid");

            // Locate Grid by HtmlFindExpression
            Grid = ActiveBrowser.Find.ByExpression<HtmlDiv>(FindExpression);
            Assert.AreEqual("ctl00_ContentPlaceHolder1_OrdersGrid", Grid.ID);

            // Verify I see text "12 - 550 ml bottles" inside Grid control footer
            Assert.IsTrue(Grid.InnerText.Contains("12 - 550 ml bottles"));

            // Verify Grid has 10 rows and 6 columns
            var Table = Grid.Find.ByExpression<HtmlTable>(new HtmlFindExpression("tagname=table"));
            Assert.AreEqual(10, Table.Rows.Count);
            Assert.AreEqual(6, Table.ColumnCount);

            // Verify there are 8 pages with links in Grid footer
            var TableFooterLinks = Table.FootRows[0]
                .Find.ByAttributes<HtmlControl>("class=rgWrap rgNumPart")
                .Find.AllByTagName("a");
            Assert.AreEqual(8, TableFooterLinks.Count);

            // Verify selected value in ComboBox is "All"            
            var Combo = ActiveBrowser.Find.ByAttributes<RadComboBox>("class=RadComboBox RadComboBox_Silk");
            Assert.AreEqual("All", Combo.Text);
        }

        [TestMethod]
        public void AJAX_GridCombo_Sorting()
        {
            AJAX_GridCombo_NavigateTo();

            // Locate the grid (using build in translators for Telerik controls)
            var RadGrid = ActiveBrowser.Find.ById<RadGrid>("ctl00_ContentPlaceHolder1_OrdersGrid");

            // Find "Unit Price" header
            var UnitPriceColumnHeader = RadGrid.MasterTable.HeadRows[0].Find.ByContent<HtmlControl>("Unit Price", FindContentType.TextContent);
            
            // Click it to sort accending
            UnitPriceColumnHeader.Click();

            // Verify first result has Product Id = 33 and Unit Price = 2.50

            // This assert will fail beceause it takes some time until Grid is sorted.
            // Assert.AreEqual("33", RadGrid.MasterTable.Rows[0][0].InnerText);

            // DO NOT use Thread.Sleep()
            // Sometimes sorting might take > 2 sec and test will fail.
            // Even if 2 sec are enough, test will aways wait 2 sec even if sorting takes 0.1 sec (this will make test very slow).
            // Thread.Sleep(2000);

            // This is better:
            Wait.For<HtmlTableRow>(r => r[0].InnerText == "33" && r[3].InnerText == "2.50", RadGrid.MasterTable.Rows[0], 30000);
        }

        [TestMethod]
        public void AJAX_GridCombo_Filtering()
        {
            AJAX_GridCombo_NavigateTo();

            // Filter by "Shelley Burke"
            var Combo = ActiveBrowser.Find.ByAttributes<RadComboBox>("class=RadComboBox RadComboBox_Silk");
            Combo.SelectItemByText("Shelley Burke");

            // Verify there are 4 results
            var RadGrid = ActiveBrowser.Find.ById<RadGrid>("ctl00_ContentPlaceHolder1_OrdersGrid");
            Wait.For<GridTableView>(t => t.Rows.Count == 4, RadGrid.MasterTable, 30000);
        }

        [TestMethod]
        public void AJAX_GridCombo_Paging()
        {
            AJAX_GridCombo_NavigateTo();

            // Locate the grid
            var RadGrid = ActiveBrowser.Find.ById<RadGrid>("ctl00_ContentPlaceHolder1_OrdersGrid");

            // Find button for next page
            var NextButton = RadGrid.Find.ByAttributes<HtmlInputSubmit>("title=Next Page");
            NextButton.Click();

            // Verify I see "Jack's New England Clam Chowder" in first row
            Wait.For<GridTableView>(t => t.Rows[0].InnerText.Contains("Queso Cabrales"), RadGrid.MasterTable, 30000);
        }
    }
}
