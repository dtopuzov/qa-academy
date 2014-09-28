using ArtOfTest.WebAii.Controls.HtmlControls;
using System.Linq;

namespace ITeBooks
{
    public static class ResultsPage
    {
        public static HtmlTable ResultTable
        {
            get
            {
                return BAT.Browser.Find.ByXPath<HtmlTable>(@"/html/body/table/tbody/tr[2]/td/table");
            }
        }

        public static HtmlAnchor GetResult(string BookTitle) 
        {
            return ResultTable.Find.AllByTagName<HtmlAnchor>("a")
                .Where<HtmlAnchor>(a => a.InnerText == BookTitle)
                .First();
        }

        public static HtmlAnchor GetResult(int Index)
        {
            return ResultTable.Find.AllByTagName<HtmlAnchor>("a")[Index];
        }
    }
}
