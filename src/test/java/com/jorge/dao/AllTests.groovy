package com.jorge.dao;

import groovy.util.GroovyTestSuite; 
import junit.framework.Test; 
import junit.textui.TestRunner; 

public class AllTests { 
   static Test suite() { 
      GroovyTestSuite allTests = new GroovyTestSuite(); 
      allTests.addTestSuite(StudentTest.class); 
      //allTests.addTestSuite(EmployeeTest.class); 
      return allTests; 
   } 
} 

TestRunner.run(AllTests.suite());