package jsoupdemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSRuleList;
import javax.xml.transform.Source;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleDeclaration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Driver;
import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.Statement;
import java.sql.ResultSet;  


public class JsoupD {
    protected static JsoupD oParser;
    static String css;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    Connection con=DriverManager.getConnection(  
                    "jdbc:mysql://localhost:3306","root","MysqlProject");

		String webPage = "http://www.iitb.ac.in/";
        String html= Jsoup.connect(webPage).get().html();
        System.out.println(html);

        oParser= new JsoupD();
//        css= "/css/bo-icomoon/style.css";

        Document document = Jsoup.connect(webPage).get();
        Elements links = document.select("link[href]");

        for (Element link : links) 
        {
            System.out.println("link : " + link.attr("href"));
            System.out.println("text : " + link.text());
            
            if(link.attr("href").equals("css/main.css"))
            {
                String cssText=Jsoup.connect(css).get().wholeText();
                System.out.println(Jsoup.connect(css).get().wholeText());
            }
        }
        
        InputSource source = new InputSource(
                new StringReader(
                		Jsoup.connect(css).get().wholeText()));
            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            ArrayList<String> CssList=new ArrayList<String>();
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                CssList.add(rule.getCssText());
                System.out.println(rule.getCssText());
            }
            int f=1;
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select CSSProperty from properties");  
            for(String css: CssList)
            {
            	while(rs.next())
                {
            	if(css.contains(rs.getString("CSSProperty")))
            	      {
            		System.out.println("Contains property");
            		System.out.println(css);
                        f=0;
                       }
                }
                
            }
            
            if(f==1)
            {
            	System.out.println("NO Property");
            }
          
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
        
	}

}
