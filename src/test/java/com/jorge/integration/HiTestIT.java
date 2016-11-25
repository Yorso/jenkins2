package com.jorge.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.runners.model.TestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jorge.util.Constants;
import com.jorge.util.TestRecorder;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

//INTEGRATION TESTNG
public class HiTestIT {

	private WebDriver driver;
	private StringBuilder url;
	private TestRecorder testRecorder;
	
	@BeforeTest
	public void startSeleniumServer() throws Exception {
		Properties mavenProps = new Properties();
		InputStream in = TestClass.class.getResourceAsStream("/maven.properties");
		try {
			mavenProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Getting port and project name from pom.xml <properties>...</properties>
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
				.append("/");
		
		
		
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
		
		
		//Starting recording test
		testRecorder = new TestRecorder();
		try {
			testRecorder.startRecording();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Test
	public void doTestIT() {
		System.out.println("URL HI: " + url.append(Constants.PAGE_HI).toString());
		driver.get(url.toString());
		Assert.assertTrue(driver.getPageSource().contains("Hi"));
		
		System.out.println("URL FORM: " + url.append("/../").append(Constants.PAGE_FORM).toString());
		driver.navigate().to(url.toString());
		driver.findElement(By.xpath(".//*[@id='firstName']")).sendKeys("Peter");
		
		//Selecting value in select
		driver.findElement(By.id("ageComb")).sendKeys("63");
		//driver.findElement(By.id("ageComb")).sendKeys(Keys.ARROW_DOWN); //Move one position down on select combo. 
																		  //For example, if you send key "63", ARROW_DOWN put select in "62"
		driver.findElement(By.id("ageComb")).sendKeys(Keys.ENTER);
		
		//It doesn't work in our example
		//Select selAge = new Select(driver.findElement(By.xpath(".//*[@id='ageComb']")));
		//selAge.selectByVisibleText(); 
		//selAge.selectByValue("32");
		//selAge.selectByIndex(27);

		//Clicking submit button
	    driver.findElement(By.xpath(".//*[@id='submit']")).click();
	    
	    //Create object of WebDriverWait class
	    WebDriverWait wait=new WebDriverWait(driver,20);
	    //Waiting for response sentence
	    WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='m']")));
	    
	    //If element found then we will use. In this example we just called isDisplayed method
 		boolean status = element.isDisplayed();
  
 		if (status) {
 			//If user was recorded in DB, sentences displays "correctly" word, else there was n error recording user in DB
 			Assert.assertTrue(driver.getPageSource().contains("correctly"));
 		} else {
 			//At this point, it means there was an error with the return of sentence
 			Assert.assertTrue(false);
 		}
		
	}
	
	@AfterTest
	public void closeBrowser() {
		//Stoping recording test
		try {
			testRecorder.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//Closing browser
			if (driver != null) {
	            driver.close();
	            driver.quit();
			}
		}
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
