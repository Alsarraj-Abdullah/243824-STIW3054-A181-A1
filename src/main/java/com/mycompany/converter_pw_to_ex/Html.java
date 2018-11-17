
package com.mycompany.converter_pw_to_ex;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Html {
    
    Connection.Response response = null;
    
    /**
     * Extract the table from the page
     * 
     * @return Element Object
     */
    public Element getTable(){
        
        Element table = null;
        Document doc = getDoc();
        
        try {

            table = doc.getElementsByClass("wikitable").get(1);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return table;
    }

    /**
     * Check the page and load it
     * 
     * @param url
     * @return integer number
     */
    public int checkPage(String url){
        int statusCode = 0;
        try {
           response = Jsoup.connect(url).execute();
           statusCode = response.statusCode();
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
        return statusCode;
    }
   
    /**
     * The page is returned as a document
     * 
     * @return Document Object
     */
    private Document getDoc(){
        try {
            return response.parse();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
}
