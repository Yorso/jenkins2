import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.DB
import com.mongodb.BasicDBObject
import com.gmongo.GMongo

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

assert 1 == coll.count(name: 'Homer')

// Displaying data collection
coll.find().toArray().each {
    println it
}

coll.remove(age: '32')
assert 0 == coll.count(age: '32')

// Displaying data collection
coll.find().toArray().each {
    println it
}

// Another example

// Instantiate a com.gmongo.GMongo object instead of com.mongodb.Mongo
// The same constructors and methods are available here
def mongo = new GMongo()

// Get a db reference in the old fashion way
def db = mongo.getDB("jenkins_test_db")

// Collections can be accessed as a db property (like the javascript API)
assert db.myCollection instanceof com.mongodb.DBCollection
// They also can be accessed with array notation 
assert db['my.collection'] instanceof com.mongodb.DBCollection

// Insert a document
db.languages.insert([name: 'Groovy'])
// A less verbose way to do it
db.languages.insert(name: 'Ruby')
// Yet another way
db.languages << [name: 'Python']

// Insert a list of documents
db.languages << [[name: 'Javascript', type: 'prototyped'], [name: 'Ioke', type: 'prototyped']]

def statics = ['Java', 'C', 'VB']

statics.each {
    db.languages << [name: it, type: 'static']
}

// Finding the first document
def lang = db.languages.findOne()
assert lang.name == 'Groovy'
// Set a new property
lang.site = 'http://groovy.codehaus.org/'
// Save the new version
db.languages.save lang

assert db.languages.findOne(name: 'Groovy').site == 'http://groovy.codehaus.org/'

// Counting the number of documents in the collection
assert db.languages.find(type: 'static').count() == 3

// Another way to count
assert db.languages.count(type: 'prototyped') == 2

// Updating a document using the '$set' operator
db.languages.update([name: 'Python'], [$set: [paradigms: ['object-oriented', 'functional', 'imperative']]])

assert 3 == db.languages.findOne(name: 'Python').paradigms.size()

// Using upsert
db.languages.update([name: 'Haskel'], [$set: [paradigms: ['functional']]], true)

assert db.languages.findOne(name: 'Haskel')

// Removing some documents
db.languages.remove(type: 'prototyped')
assert 0 == db.languages.count(type: 'prototyped')

// Removing all documents
db.languages.remove([:])
assert 0 == db.languages.count()

// To ensure complete consistency in a session use DB#inRequest
// It is analogous to user DB#requestStarted and DB#requestDone
db.inRequest {
    db.languages.insert(name: 'Objective-C')
    assert 1 == db.languages.count(name: 'Objective-C')
}

db.languages.find().toArray().each {
    println it
}
