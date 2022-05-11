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
	
	String baseURLStr = "http://einlegesohlentest.de/einlegesohlen/";
	
	ArrayList<String> workingLinks;
	ArrayList<String> brokenLinks;
	
	
	@Before
	public void setupTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		workingLinks = new ArrayList<String>();
		brokenLinks = new ArrayList<String>();
	}

	@Test
	public void test() {
        driver.get(baseURLStr);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        List<WebElement> aLinks = driver.findElements(By.tagName("a"));
        System.out.println("Total links on the webpage: " + aLinks.size());
        
        for (int i = 0; i < aLinks.size(); i++) {
        	WebElement el = aLinks.get(i);
        	String url = el.getAttribute("href");
        	verifyLinks(url);
        }
        
        nextPage("http://einlegesohlentest.de/");
	}
	
	public void nextPage(String page) {
		driver.findElement(By.xpath("//a[@href='" + page + "']")).click();
	}
	
	public void verifyLinks(String linkURL) {
		try {
			URL url = new URL(linkURL);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.connect();
			
			if (con.getResponseCode() >= 400) {
				brokenLinks.add(linkURL);
				System.out.println(linkURL + " - " + con.getResponseMessage() + " is a broken link");
			} else {
				workingLinks.add(linkURL);
				System.out.println(linkURL + " - " + con.getResponseMessage());
			}
		} catch (Exception e) {}
	}
	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
