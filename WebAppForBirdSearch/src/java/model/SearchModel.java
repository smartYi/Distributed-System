package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author Yi
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/ 
 * and then screen scraping the HTML that is returned in order to fabricate an image URL.
 */
public class SearchModel {
    private String pictureTag;
    
    public List doBirdSearch(String searchTag, boolean mobile) {
        List<HashMap<String, String>> rets = new ArrayList<>();
        pictureTag = searchTag;
        
        String searchUrl = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + pictureTag;
        
        try {
            Document doc = Jsoup.connect(searchUrl).get();
            //Get all bird images url.
            Elements elements = doc.select("div.pull-left");
            for (Element element : elements) {
                
                String src = element.select("img").attr("src");
                    if (src != null && src.length() > 0) {
                    int questionMark = src.indexOf("?");
                    int numberMark = src.indexOf("&");
                    //Set the image size according the user agent.
                    String imgSize = mobile ? "max_w=250" : "max_w=500";
                    String prefix = src.substring(0, questionMark + 1);
                    String suffix = src.substring(numberMark);
                    //Use the new generated url to be displayed.
                    src = prefix + imgSize + suffix;
                    //Get the photographer name.
                    String author = element.select("a[title=\"View more pictures by this photographer\"]").html();
                    HashMap<String, String> cur = new HashMap<>();
                    cur.put("src", src);
                    cur.put("author", author);
                    rets.add(cur);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SearchModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rets;
    }
}
