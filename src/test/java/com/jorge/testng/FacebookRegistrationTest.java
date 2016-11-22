package com.jorge.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;

//import io.github.bonigarcia.wdm.ChromeDriverManager;

public class FacebookRegistrationTest {

	@Test
	public FacebookRegistrationTest() {
		ChromeDriverManager.getInstance().setup();
		WebDriver driver = new ChromeDriver();
		
		//Maximize window browser
		driver.manage().window().maximize();
		
		Assert.assertTrue(driver.getTitle().contains("Menéame"));
		
		//Going to Facebook
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
		
		//Close browser
		driver.quit();
	}

}
