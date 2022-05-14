import static org.junit.Assert.*;

import java.time.Duration;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class LinkTester {
	
	WebDriver driver;
	
	String baseURLStr = "http://einlegesohlentest.de/";
	
	ArrayList<String> openLinks;
	ArrayList<String> completedLinks;
	ArrayList<String> brokenLinks;
	
	
	@Before
	public void setupTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		openLinks = new ArrayList<String>();
		completedLinks = new ArrayList<String>();
		brokenLinks = new ArrayList<String>();
	}

	@Test
	public void test() {
        
		String url = baseURLStr;
		
        do {
        	driver.get(url);
        	
            List<WebElement> linkElements = driver.findElements(By.tagName("a"));
            
            for (WebElement linkEl : linkElements) {
            	String linkURL = linkEl.getAttribute("href");
            	if (linkIsValid(linkURL)) {
            		if (!openLinks.contains(linkURL)
            				&& linkURL.indexOf("einlegesohlentest") != -1
            				&& !completedLinks.contains(linkURL))
    					openLinks.add(linkURL);
            	}
            	else brokenLinks.add(linkURL);
            }
            
            url = openLinks.get(0);
            openLinks.remove(0);
            completedLinks.add(url);
            
            //for (String s : openLinks) System.out.println();
            System.out.println("Length of open links: " + openLinks.size());
            System.out.println("Length of completed links: " + completedLinks.size() + "\n");
            
        } while (openLinks.size() > 0);
	}
	
	
	// check if the link URL is valid
	public boolean linkIsValid(String linkURL) {
		try {
			URL url = new URL(linkURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.connect();
			if (con.getResponseCode() >= 400) 
				return false;
			else return true;
		} catch (Exception e) { return false; }
	}
	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
