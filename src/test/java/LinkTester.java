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
	
	public class Node {
		
		private String value = "";
		private List<Node> linkedNodes = null;
		
		public Node(String value) {
			this.value = value;
			linkedNodes = new ArrayList<Node>();
		}
		
		public void addLinkedNode(Node node) {
			boolean nodeIsNew = true;
			for (Node n : linkedNodes)
				if (n.getValue().equals(node.getValue()))
					nodeIsNew = false;
			if (nodeIsNew) linkedNodes.add(node);
		}
		
		public String[] getLinkedNodes() {
			String[] result = new String[linkedNodes.size()];
			for (int i = 0; i < result.length; i++) 
				result[i] = linkedNodes.get(i).getValue();
			return result;
		}
		
		public String getValue() { return value; }
	}
	
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
        	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            List<WebElement> aLinks = driver.findElements(By.tagName("a"));
            
            for (int i = 0; i < aLinks.size(); i++) {
            	WebElement el = aLinks.get(i);
            	String linkURL = el.getAttribute("href");
            	verifyLinks(linkURL);
            }
            
            url = openLinks.get(0);
            openLinks.remove(0);
            completedLinks.add(url);
            
        } while (openLinks.size() > 0);
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
				if (!openLinks.contains(linkURL) && linkURL.indexOf("einlegesohlentest") != -1) {
					openLinks.add(linkURL);
				}
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
