package com.jorge.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jorge.model.Post;
import com.jorge.model.User;

@Repository //Necessary to use DAO and Spring. It allows the UserDAO class to be automatically discovered and instantiated as a bean
@Transactional // Necessary to reverting incomplete database modifications using transactions.
			   // @Transactional will enclose each DAO method in a BEGIN...COMMIT SQL block. So if there's an
			   // error (a runtime exception), any modification made by the DAO method to the database will be rolled back.
public class UserDAO {
	
	@Autowired // Dependency injection
	private JdbcTemplate jdbcTemplate; // This field will be initialized automatically by Spring via dependency injection with
									   // the JdbcTemplate bean defined previously in AppConfig.java

	/**********
	 *  CRUD  *
	 **********/
	
	/**
	 * Saving an object
	 * 
	 * Save an object in the database; a row will be added to
	 * the corresponding database table
	 *
	 * Define an SQL insert query with question marks as placeholders for the actual row values. Use the
	 * update() method to execute the query using the actual values from the object
	 * 
	 */
	public void add(User user) {
		String sql = "insert into user (first_name, age) values (?, ?)";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge());
	}
	
	/**
	 * Retrieving an object
	 * 
	 * We create a DAO method to retrieve a database row, which we will use to create an object
	 * 
	 * Add a DAO method which will perform an SQL select query and use a UserMapper object to generate a User object
     *
	 */
	public User findById(Long id) {
		String sql = "select * from user where id=?";
		User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
		return user;
	}
	
	/**
	 * If the database column names match the names of the object attributes, there's no need to define a
	 * custom RowMapper interface, just use a ParameterizedBeanPropertyRowMapper class
	 * 
	 */
	/*
	public User findById(Long id) {String sql = "select * from user where id=?";
		User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return user;
	}
	*/
	
	/**
	 * Retrieve database rows and create a list of objects from them.
	 * 
	 * The query() method uses RowMapper to generate objects from the returned database rows.
	 * We used a ParameterizedBeanPropertyRowMapper class assuming that the database table columns
	 * match the object attributes; however, as in the previous recipe, a custom RowMapper interface can be used
	 * 
	 * Perform an SQL select query and generate a list of objects from the result using RowMapper
	 * 
	 */
	/*
	public List<User> findAll() {
		String sql = "select * from user";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return userList;
	}
	*/
	// TODO: test this method
	public List<User> findAll() {
		String sql = "select * from user";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		return userList;
	}
	
	/**
	 * Retrieving a list of objects with their dependencies.
	 * 
	 * We will add a DAO method to generate, from an SQL query joining several tables, a
     * list of objects with their dependencies. We will retrieve a list of User objects along with their Post
     * objects (blog posts written by these users)
     * 
     * Add a DAO method performing an SQL select statement with left join and using
	 * ResultSetExtractor to generate a list of objects
	 * 
	 */
	public List<User> findAllObjects() {
		String sql = "select u.id, u.first_name, u.age, p.id as p_id, p.title as p_title, p.date as p_date from user u left join post p on p.user_id = u.id	order by u.id asc, p.date desc";
		return jdbcTemplate.query(sql, new UserWithPosts());
	}
	
	/**
	 * Updating an object
	 * 
	 * We will add a DAO method to update an existing row in the database with an object's fields
	 * 
	 * Use an SQL update query and execute it using the update() method
	 * 
	 */
	public void update(User user) {
		String sql = "update user set first_name=?, age=? where id=?";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge(), user.getId());
	}
	
	// It's convenient to also have a save() method that will create the database row if it doesn't exist:
	public void save(User user) {
		if (user.getId() == null) {
			add(user);
		}
		else {
			update(user);
		}
	}
	
	/**
	 * Deleting an object
	 * 
	 * We will add a DAO method to delete an existing row from the database
	 * 
	 * Use an SQL delete query and execute it using the update() method
	 * 
	 */
	public void delete(User user) {
		String sql = "delete from user where id=?";
		jdbcTemplate.update(sql, user.getId());
	}
	
	/**
	 * Finding the number of results for an SQL query
	 * 
	 * We will add a DAO method to quickly get the number of results for an SQL query
	 * without actually loading the rows in the memory
	 * 
	 * Use an SQL count(*) function and get the value directly using the queryForObject() method with
     * a second argument specifying Long as the returned type
     * 
	 */
	public long countMinorUsers() {
		String sql = "select count(*) from age < 18";
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
	
	/**
	 * Saving a list of objects at once
	 * 
	 * We will add a DAO method to save a list of objects to the database efficiently
	 * 
	 * Use the batchUpdate() method that takes an SQL insert query and a list of values as parameters
	 * 
	 */
	public void add(List<User> userList) {
		String sql = "insert into user (first_name, age) values (?, ?)";
		List<Object[]> userRows = new ArrayList<Object[]>();
		
		for (User user : userList) {
			userRows.add(new Object[] {user.getFirstName(), user.getAge()});
		}
		
		jdbcTemplate.batchUpdate(sql, userRows);
	}
	
	
	
	
	/********************
	 *  INLINE CLASSES  *
	 ********************/
	
	/**
	 * This is an inline class implementing RowMapper. This class defines how to
	 * generate an User object from a database row
	 *
	 */
	private class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet row, int rowNum) throws SQLException {
			User user = new User();
			user.setId(row.getLong("id"));
			user.setFirstName(row.getString("first_name"));
			user.setAge(row.getInt("age"));
			return user;
		}
	}
	
	/**
	 * This is an inline class. 
	 * Add an inline class implementing ResultSetExtractor
	 *
	 */
	private class UserWithPosts implements ResultSetExtractor<List<User>> {
		public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, User> userMap = new ConcurrentHashMap<Long, User>();
			User u = null;
			
			while (rs.next()) {
				// user already in map?
				Long id = rs.getLong("id");
				u = userMap.get(id);
				// if not, add it
				if(u == null) {
					u = new User();
					u.setId(id);
					u.setFirstName(rs.getString("first_name"));
					u.setAge(rs.getInt("age"));
					userMap.put(id, u);
				}
				// create post if there's one
				Long postId = rs.getLong("p_id");
				if (postId > 0) {
					System.out.println("add post id=" + postId);
					Post p = new Post();
					p.setId(postId);
					p.setTitle(rs.getString("p_title"));
					p.setDate(rs.getDate("p_date"));
					p.setUser(u);
					u.getPosts().add(p);
				}
			}
		
			return new LinkedList<User>(userMap.values());
		}
	}
	
}
