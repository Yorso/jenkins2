class Example {
   static void main(String[] args) {
      Student mst = new Student();
      mst.name = "Joe";
      mst.ID = 1;
      println(mst.Display())
   } 
} 
 
public class Student {
   String name;
   int ID;
	
   String Display() {
      return name +ID;
   }  
}

class StudentTest extends GroovyTestCase {
   void testDisplay() {
      def stud = new Student(name : 'Joe', ID : '1')
      def expected = 'Joe49'
      assertToString(stud.Display(), expected)
   }
}

import groovy.util.GroovyTestSuite 
import junit.framework.Test 
import junit.textui.TestRunner 

class AllTests { 
   static Test suite() { 
      def allTests = new GroovyTestSuite() 
      allTests.addTestSuite(StudentTest.class) 
      //allTests.addTestSuite(EmployeeTest.class) 
      return allTests 
   } 
} 

TestRunner.run(AllTests.suite())