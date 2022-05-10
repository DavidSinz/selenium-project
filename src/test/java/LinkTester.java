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
import java.util.Iterator;
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
        driver.get("http://einlegesohlentest.de");
        
        System.out.print(driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        String url = "";
        List<WebElement> allURLs = driver.findElements(By.tagName("a"));
        System.out.println("Total links on the webpage: " + allURLs.size());
        
        Iterator<WebElement> iterator = allURLs.iterator();
        while (iterator.hasNext()) {
        	url = iterator.next().getText();
        	System.out.println(url);
        }
	}
	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
