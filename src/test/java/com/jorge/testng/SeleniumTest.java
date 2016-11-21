package com.jorge.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;

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
		ChromeDriverManager.getInstance().setup();
		/*
		FirefoxDriverManager.getInstance().setup();
		InternetExplorerDriverManager.getInstance().setup();
		OperaDriverManager.getInstance().setup();
		EdgeDriverManager.getInstance().setup();
		PhantomJsDriverManager.getInstance().setup();
		*/
		WebDriver driver = new ChromeDriver();
		
		
		
		
		//Common for both ways
		driver.get("http://www.learn-automation.com");
		
		Assert.assertTrue(driver.getTitle().contains("Selenium"));
		
		//Run project as Maven Test
	}

}
