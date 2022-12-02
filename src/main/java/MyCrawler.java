import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.regex.Pattern;

/**
*@Auther z
*@Date 2018-09-11 16:15
*@Describe
*/
public class MyCrawler extends WebCrawler {
    //自定义过滤规则
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();// 得到小写的url
        return !FILTERS.matcher(href).matches() // 正则匹配，过滤掉我们不需要的后缀文件
                && href.startsWith("https://oureverydaylife.com/");// 只接受以“http://www.ics.uci.edu/”开头的url
    }
    //过滤掉config.xml中不满足相关配置的url--->保存在weburl中--->以下的方法用以相关页面的获取
    
    
    //获取页面的html数据信息,并且将获取文件保存在配置的文件目录中 
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();// 获取被过滤之后的url
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {// 判断是否是html数据
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();//// 强制类型转换，获取html数据对象
            String text = htmlParseData.getText();//获取页面纯文本（无html标签）
            String html = htmlParseData.getHtml();//获取页面Html
            Set<WebURL> links = htmlParseData.getOutgoingUrls();// 获取页面输出链接

            /**Jsoup解析html*/
            Document document=Jsoup.parse(html);
            /**选择p标签*/
            Elements h=document.select("h1");
            Elements author=document.select("cite");
            Element time=document.getElementById("last-update");
            Elements element=document.select("p");
            /**抽取p标签里的text，并保留换行换行，element.text()方法没保留换行*/
            String content=Jsoup.clean(element.toString(), "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));

//            System.out.println("纯文本长度: " + text.length());
//            System.out.println("html长度: " + html.length());
//            System.out.println("链接个数 " + links.size());
//            System.out.println(h.text());
//            System.out.println(content);
            
            //保存获取html文件到设定的文件路径下并且赋予文件名称
            sava.write("标题："+h.text());
            sava.write("作者: "+author.text());
            sava.write("时间: "+time.text());
            sava.write("内容: "+content);
            sava.write("" +
                    "" +
                    "" );
        }
    }


}
