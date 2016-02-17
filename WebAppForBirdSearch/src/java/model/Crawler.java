/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;

/**
 * This class uses external library to parse source website html file and get 
 * the bird type and dynamically generate the index page.
 * @author qiuyi
 */
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Crawler {
    private final List<String> rets;
    private final String url;
    
    /**
     * Constructor with the target url to be parsed.
     * @param url 
     */
    public Crawler(String url) {
        rets = new ArrayList<>();
        this.url = url;
    }
    
    /**
     * This method is to get all the bird names in the source website html file.
     * @return 
     *      A list that contains all the bird names.
     */
    public List<String> crawler() {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("select[name=\"pix\"] > option");
            for (Element element: elements) {
                rets.add(element.html());
            }
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rets;
        
    }
}
