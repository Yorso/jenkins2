	package com.jorge.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jorge.config.AppConfig;
import com.jorge.model.User;

/**
 * Mixing Spring's application context and Unit testing with transactions examples in one class.
 * 
 * With TestNG we need two class (AppContextDAOTestNGTest.java and UserDAOTestNGTest.java) because 
 * application context example class extends from AbstractTestNGSpringContextTests and Unit testing 
 * with transactions example class extends from AbstractTransactionalTestNGSpringContextTests.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // Executes the test with the Spring runner instead of the default JUnit runner. A runner is a class that runs a JUnit test
@ContextConfiguration(classes = {AppConfig.class}) // Loads the Spring configuration class and makes the class's beans available
@WebAppConfiguration // Prevents exceptions from being raised. Without it, @EnableWebMvc (in theSpring configuration) would raise the "A ServletContext is required to configure default servlet handling" exception
@Transactional // Necessary in JUnit to testing transactions (for testAdd() method. If there wasn't this method it wouldn't be necessary for testListUsers() method)
public class UserAndAppContextDAOJUnitTest {

	@Autowired
	private UserDAO userDAO;
	
	
	/**
	 * Unit testing with JUnit 4 using Spring's application context
	 * 
	 * JUnit tests are run outside Spring; Spring is not initialized before the tests are run. To be able to use
	 * the beans defined in the configuration files and dependency injection, some bootstrapping code needs
	 * to be added to the test class
	 * 
	 * You can choose to use a separate Spring configuration class to run your tests:
	 * 		@ContextConfiguration(classes = {AppTestConfig.class})
	 * 
	 * You can also use the Spring main configuration class in combination with a test-specific configuration class:
	 * 		@ContextConfiguration(classes = {AppConfig.class, AppTestConfig.class})
	 * 
	 * The order in which the classes are declared matters. In this example, beans from AppConfig can be
	 * overridden in AppTestConfig . For example, you could choose to override a MySQL datasource by
	 * an in-memory database datasource for your tests.
	 *
	 */
	// This method doesn't need @Transactional annotation at the beginning of this class
	@Test
	public void testListUsers() {
		List<User> users = userDAO.findAll();
		
		for(User user : users){
			System.out.println("Name: " + user.getFirstName());
			System.out.println("Age: " + user.getAge());
		}
	}
	
	
	
	/**
	 * Unit testing with transactions
	 * 
	 * To test a DAO class, for example, you will need to perform database queries that won't be persisted.
	 * For example, to test the DAO method to add a user, you want to make sure that the user is actually
	 * created in the database, but you don't want that test user to remain in the database. Transactions help
	 * you to do this with minimum effort.
	 *
	 *Each test method of the class will automatically:
	 *		Start a new transaction
	 *		Execute as normal
	 *		Rollback the transaction (so any modifications to a database will be reverted)
	 *
	 */
	// This method needs @Transactional annotation  at the beginning of this class
	@Test
	public void testAdd() {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();	
		
		for(int i=0; i<4; i++){
			user = new User("Name" + String.valueOf(i + 1), 30 + i + 1);
			listUser.add(user);
		}
		
		for(User u : listUser){
			System.out.println("Checking user: " + u.getFirstName() + " " + u.getAge());
			userDAO.add(u);
		}
	}
}
