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

public class LinkTester {
	
	WebDriver driver;
	
	@Before
	public void setupTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	public void test() {
        driver.get("https://google.com");
        
        System.out.print(driver.getTitle()); // => "Google"

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));
        
        searchBox.sendKeys("Selenium");
        searchButton.click();
        
        searchBox = driver.findElement(By.name("q"));
        searchBox.getAttribute("value"); // => "Selenium"
	}
	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
