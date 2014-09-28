using ArtOfTest.WebAii.Controls.HtmlControls;
using ArtOfTest.WebAii.Core;
using ArtOfTest.WebAii.ObjectModel;

namespace ITeBooks
{
    public static class DetailsPage
    {
        public static string Url
        {
            get
            {
                return BAT.Browser.Url;
            }
        }

        public static HtmlDiv BookInfo
        {
            get
            {
                return BAT.Browser.Find.ByAttributes<HtmlDiv>(@"itemtype=http://schema.org/Book");
            }
        }

        public static string BookTitle
        {
            get
            {
                return BookInfo.Find.ByExpression(new HtmlFindExpression("tagname=h1")).InnerText;
            }
        }

        public static string BookSubTitle
        {
            get
            {
                if (BookInfo.Find.ByExpression(new HtmlFindExpression("tagname=h3")) != null)
                {
                    return BookInfo.Find.ByExpression(new HtmlFindExpression("tagname=h3")).InnerText;
                }
                else 
                {
                    return string.Empty;
                }
            }
        }
    }
}
