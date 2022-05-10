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

public class LinkTester {
	
	WebDriver driver;
	
	@Before
	public void setupTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void test() {
        driver.get("http://einlegesohlentest.de/einlegesohlen/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        List<WebElement> aLinks = driver.findElements(By.tagName("a"));
        System.out.println("Total links on the webpage: " + aLinks.size());
        
        for (int i = 0; i < aLinks.size(); i++) {
        	WebElement el = aLinks.get(i);
        	String url = el.getAttribute("href");
        	verifyLinks(url);
        }
	}
	
	public void verifyLinks(String linkURL) {
		try {
			URL url = new URL(linkURL);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.connect();
			
			if (con.getResponseCode() >= 400)
				System.out.println(linkURL + " - " + con.getResponseMessage() + " is a broken link");
			else System.out.println(linkURL + " - " + con.getResponseMessage());
		} catch (Exception e) {}
	}
	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
