package com.jorge.unitary;

// JUnit imports
//import static org.junit.Assert.*;
//import org.junit.Test;

// TestNG imports
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jorge.util.NumberUtil;

public class NumberUtilTest {

	/**
	 * Unit testing with JUnit 4
	 * 
	 * A JUnit class is a normal Java class with some methods annotated with @Test .
	 * assertEquals(x, y) is a JUnit method that makes a test method fail if x is not equal to y .
	 * In the testAdd() method, we check whether the method works for different sets of data.
	 * 
	 * Run right-click somewhere in the class and choose Run As | JUnit Test
	 * 
	 */
	/*
	@Test
	public void testAdd() {
		assertEquals(NumberUtil.add(5, 3), 8);
		assertEquals(NumberUtil.add(1500, 32), 1532);
	}
	*/
	
	
	
	
	/**
	 * Unit testing with TestNG 6
	 * 
	 * Run right-click somewhere in the class and choose Run As | TestNG Test
	 * 
	 * It's possible to execute a test only if another test was successful (useful for integration testing):
	 *
	 *		@Test
	 *		public void connectToDatabase() {}
	 *	
	 *		@Test(dependsOnMethods = { "connectToDatabase" })
	 *		public void testMyFancySQLQuery() {
	 *			...
	 *		}
	 *
	 * Other advanced features are well explained in TestNG's documentation at
	 *	    http://testng.org/doc/documentation-main.html#parameters-dataproviders.
	 *
	 * For more reasons to choose TestNG over JUnit, refer to
	 * 	    http://kaczanowscy.pl/tomek/sites/default/files/testng_vs_junit.txt.slidy_.html.
	 * 
	 */
	@DataProvider
	public Object[][] values() {
		return new Object[][] {
			new Object[] { 1, 2, 3 },
			new Object[] { 4, 5, 9 },
			new Object[] { 3000, 2000, 5000 },
			new Object[] { 25, 50, 75 }
		};
	}
	
	@Test(dataProvider = "values")
	public void testAdd(int a, int b, int c) {
		System.out.println("NumberUtilTest");
		AssertJUnit.assertEquals(NumberUtil.add(a, b), c);
	}

}
