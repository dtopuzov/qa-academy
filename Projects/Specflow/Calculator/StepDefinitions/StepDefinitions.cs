using ArtOfTest.WebAii.Controls.HtmlControls;
using ArtOfTest.WebAii.Core;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TechTalk.SpecFlow;

namespace Calculator.StepDefinitions
{
    [Binding]
    public class StepDefinition
    {
        private Manager Manager { get; set; }
        private Browser Browser { get; set; }
        private HtmlControl Calc { get; set; }

        private StepDefinition()
        {
            Manager = Hooks.Hooks.MyManager;
            Browser = Manager.ActiveBrowser;
        }

        [Given(@"scientific calculator")]
        public void GivenScientificCalculator()
        {
            Browser.NavigateTo("http://web2.0calc.com/");
            Browser.WaitUntilReady();
            Browser.RefreshDomTree();

            Calc = Browser.Find.ByAttributes<HtmlDiv>("class=calccontainer");
        }

        [When(@"press (.*)")]
        public void WhenPress(string ButtonText)
        {
            int number;
            HtmlButton button;

            if (int.TryParse(ButtonText, out number))
            {
                // IDs of simple numbers are Btn1, Btn2, etc.
                button = Calc.Find.ById<HtmlButton>("Btn" + number);
            }
            else 
            {
                // Locate operation buttons by text content 
                var expression = new HtmlFindExpression("id=^Btn", "InnerText=~" + ButtonText);
                button = Calc.Find.AllByExpression<HtmlButton>(expression).FirstOrDefault();
            }

            button.MouseClick();
        }

        [Then(@"result is (.*)")]
        public void ThenResultIs(string Result)
        {
            Calc.Refresh();
            var actualResult = Calc.Find.ById<HtmlInputText>("input").Text;
            Assert.AreEqual(Result, actualResult, "Result is not correct.");
        }
   }
}
