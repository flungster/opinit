package services.pinning;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Pinning {

    public static List<String> fetchImageLinks(String url) {
	List<String> imageLinks = new ArrayList<String>();
	String hostname = null;
	try {
	    URL pinUrl = new URL(url);
	    hostname = pinUrl.getHost();
	    if (hostname != null) {
		/*
		 * Strip out the subdomain if there is one
		 */
		int lastDot = hostname.lastIndexOf('.');
		if (-1 != lastDot) {
		    String partialHostname = hostname.substring(0, lastDot);
		    int secondLastDot = partialHostname.lastIndexOf('.');
      
		    if (-1 != secondLastDot) {
			hostname = hostname.substring(secondLastDot + 1, hostname.length());
		    }  
		} 
	    } 
	} catch (MalformedURLException e) {
	    hostname = null;
	}
	System.out.println("** PINNING HOST: " + hostname);
	if (hostname != null) {
	    try {
		Document doc = Jsoup.connect(url).get();
		
		Elements imgs = doc.select("img[src*=" + hostname + "]");
	    
		for (Element imgSrc : imgs) {
		    imageLinks.add(imgSrc.attr("abs:src"));
		}
	    } catch (IOException e) {
		// TBD - log error
		System.out.println("IOException");
	    } 
	}
	System.out.println("RETURNING");
	return imageLinks;
    }

}