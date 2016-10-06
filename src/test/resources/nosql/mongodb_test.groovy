import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.DB
import com.mongodb.BasicDBObject

// Creating all referent to DB
class MongoService { 

    private MongoClient mongoClient 

    def host = "localhost" //your host name 
    def port = 27017 //your port. 
    def databaseName = 'jenkins_test_db'

    public MongoClient client() {
        mongoClient = mongoClient ?: new MongoClient(host, port) 

        return mongoClient
    } 

    public DBCollection collection(collectionName) { 
        DB db = client().getDB(databaseName)

        return db.getCollection(collectionName) 
    }
    
    public void dropDB(dbn){
    	DB db = client().getDB(dbn)
    	db.dropDatabase()
    }
    
}

// Deleting database
def service = new MongoService(databaseName: 'jenkins_test_db')
service.dropDB('jenkins_test_db');

// Creating database
service = new MongoService(databaseName: 'jenkins_test_db')

// Creating a collection in db
def coll = service.collection('user')

// Setting data
def data = [
    [firstName: 'Peter', lastName: 'Parker', age: '32'],
    [firstName: 'Homer', lastName: 'Simpson', age: '38']
].collect { it as BasicDBObject }

// Inserting data in collection
coll.insert(data)

// Displaying data collection
coll.find().toArray().each {
    println it
}

//Testing
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
      def expected = 'Joe1'
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