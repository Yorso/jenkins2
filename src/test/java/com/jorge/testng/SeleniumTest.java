package com.jorge.testng;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

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
		//ChromeDriverManager.getInstance().setup();
		/*
		FirefoxDriverManager.getInstance().setup();
		InternetExplorerDriverManager.getInstance().setup();
		OperaDriverManager.getInstance().setup();
		EdgeDriverManager.getInstance().setup();
		PhantomJsDriverManager.getInstance().setup();
		*/
		//WebDriver driver = new ChromeDriver();
		
		//FirefoxDriverManager.getInstance().setup();
		//WebDriver driver = new FirefoxDriver();
		
		
		
		
		File pathToLinuxGecko = new File("/opt/tomcat8/selenium/geckodriver");
	    System.setProperty("webdriver.gecko.driver",pathToLinuxGecko.getPath());



	    DesiredCapabilities capabilities= DesiredCapabilities.firefox();
	    capabilities.setCapability("marionette", true);

	    WebDriver driver;
	    driver = new FirefoxDriver(capabilities);

	    driver.manage().window().maximize();

	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    
	    
		
		//Common for both ways
		driver.get("http://www.learn-automation.com");
		//Checking if title contains "Selenium" string
		Assert.assertTrue(driver.getTitle().contains("Selenium"));
		
		driver.close();
		//Close browser
		driver.quit();
		
		//Run project as Maven Test
	}

}
