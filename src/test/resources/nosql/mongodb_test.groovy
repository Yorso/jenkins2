import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.DB
import com.mongodb.BasicDBObject

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
}

def service = new MongoService(databaseName: 'jenkins_test_db')
def coll = service.collection('user')

def data = [
    [firstName: 'Peter', lastName: 'Parker', age: '36'],
    [firstName: 'Homer', lastName: 'Simpson', age: '38']
].collect { it as BasicDBObject }

coll.insert(data)

coll.find().toArray().each {
    println it
}