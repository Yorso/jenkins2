package com.jorge.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.runners.model.TestClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jorge.util.Constants;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

//INTEGRATION TESTNG
public class HiTestIT {

	private WebDriver driver;
	private StringBuilder url;
	
	@BeforeTest
	public void startSeleniumServer() throws Exception {
		Properties mavenProps = new Properties();
		InputStream in = TestClass.class.getResourceAsStream("/maven.properties");
		try {
			mavenProps.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String port = mavenProps.getProperty(Constants.CARGO_PORT);
		String projectName = mavenProps.getProperty(Constants.PROJECT_NAME);
		  
		url = new StringBuilder()
				.append(Constants.HTTP_PROTOCOL)
				.append("://")
				.append(Constants.SERVER)
				.append(":")
				.append(port)
				.append("/")
				.append(projectName)
				.append("/")
				.append(Constants.PAGE);
		
		
		
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
		
		//IMPORTANT: Must be a browser installed on Master machine
		FirefoxDriverManager.getInstance().setup();
		driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
	}
	
	@Test
	public void doTestIT() {
		System.out.println("URL FINAL: " + url.toString());
		
		driver.get(url.toString());
		
		Assert.assertTrue(driver.getPageSource().contains("Hi"));
		
	}
	
	@AfterTest
	public void closeBrowser() {
		//Close browser
		//driver.close();
		driver.quit();
	}

	/*private Selenium selenium; 
	private SeleniumServer seleniumServer;
	
	/*@BeforeSuite
	public void startSeleniumServer() throws Exception {
		
	  seleniumServer = new SeleniumServer(null);
	  seleniumServer.boot();
	}*/
	
	/*@AfterSuite
	public void stopSeleniumServer() throws Exception {
	  seleniumServer.stop();
	}*/
	/*
	@BeforeTest
	public void openBrowser() {
	  selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080");
	  selenium.start();
	  selenium.open("http://localhost:8080/jenkins2/hi");
	  selenium.waitForPageToLoad("5000");
	  assertTrue(selenium.getBodyText().contains("Hi"));//getTitle(), "My WebWork Application");
	}

	@AfterTest
	public void closeBrowser() {
	  selenium.stop();
	}

	@Test
	public void indexPageShouldComeUp() {
	 //
	}

	@Test
	public void converterPageShouldComeUp() {
	 // ..
	}
*/
}
