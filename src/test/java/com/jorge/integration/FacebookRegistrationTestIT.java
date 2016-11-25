package com.jorge.integration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jorge.util.TestRecorder;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class FacebookRegistrationTestIT {

	private WebDriver driver;
	private TestRecorder testRecorder;
	
	
	@BeforeTest
	public void startUp(){
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		try {
			testRecorder = new TestRecorder();
			testRecorder.startRecording();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Test
	public void testFacebook() {
		
		driver.get("http://www.facebook.com");
		
		//Putting name for user
		driver.findElement(By.xpath(".//*[@id='u_0_1']")).sendKeys("Name of user");
		
		//Checking male radio button
		driver.findElement(By.xpath(".//*[@id='u_0_i']")).click(); //click() also is valid to check checkboxes, buttons, links...
		
		//Changing selects
		//Month, selected by text
		Select selMonth = new Select(driver.findElement(By.xpath(".//*[@id='month']")));
		selMonth.selectByVisibleText("Feb"); 
		//Day, selected by value
		Select selDay = new Select(driver.findElement(By.xpath(".//*[@id='day']")));
		selDay.selectByValue("16");
		//Year, selcted by index
		Select selYear = new Select(driver.findElement(By.xpath(".//*[@id='year']")));
		selYear.selectByIndex(6);
		
		//Clicking Sign in button
		driver.findElement(By.xpath(".//*[@id='u_0_e']")).click();
		
		//Now, we are going to click on a link, changing page
		driver.findElement(By.xpath(".//*[@id='reg_pages_msg']/a")).click();//Inside the div tag called "reg_pages_msg", we say we want to click the link (/a)
		//And then, we are going back to the registration page
		driver.navigate().back();
		//driver.navigate().to(url);
		//driver.navigate().refresh();
		
	}
	
	@AfterTest
	public void cleanUp(){
		try {
			testRecorder.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (driver != null) {
	            driver.close();
	            driver.quit();
			}
		}
	}
	
}
