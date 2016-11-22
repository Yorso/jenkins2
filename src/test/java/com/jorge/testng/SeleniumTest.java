package com.jorge.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.OperaDriverManager;

//USING TESTNG
public class SeleniumTest {

	@Test
	public SeleniumTest() {
		//Using driver files for each browser, downloaded in system file:
		//---------------------------------------------------------------
		//For Chrome
		//System.setProperty("webdriver.chrome.driver", "/opt/tomcat8/selenium/chromedriver");
		//WebDriver driver = new ChromeDriver();
		//For Firefox
		//System.setProperty("webdriver.gecko.driver", "/opt/tomcat8/selenium/geckodriver");
		//WebDriver driver = new FirefoxDriver();
		
		
		
		
		
		//Using Maven dependencies:
		//-------------------------
		/*Add this in pom.xml, under selenium-java dependency:
		    <dependency>
			   <groupId>io.github.bonigarcia</groupId>
			   <artifactId>webdrivermanager</artifactId>
			   <version>1.5.0</version>
			</dependency>
		 */
		//And then this:
		/*
		ChromeDriverManager.getInstance().setup();
		FirefoxDriverManager.getInstance().setup();
		InternetExplorerDriverManager.getInstance().setup();
		OperaDriverManager.getInstance().setup();
		EdgeDriverManager.getInstance().setup();
		PhantomJsDriverManager.getInstance().setup();
		*/
	    
		//System.setProperty("webdriver.chrome.driver", "/opt/tomcat8/selenium/chromedriver");
		//WebDriver driver = new ChromeDriver();
		//System.setProperty("webdriver.gecko.driver", "/opt/tomcat8/selenium/geckodriver");
		
		//Must be a browser installed on Master machine
		//FirefoxDriverManager.getInstance().setup();
		//WebDriver driver = new FirefoxDriver();
		OperaDriverManager.getInstance().setup();
		WebDriver driver = new OperaDriver();
		
		driver.manage().window().maximize();
		
		//Common for both ways
		driver.get("http://www.meneame.net");
		//Checking if title contains "Selenium" string
		Assert.assertTrue(driver.getTitle().contains("Menéame"));
		
		//Close browser
		//driver.close();
		//driver.quit();
		
		//You must run the project as Maven Test
	}

}
