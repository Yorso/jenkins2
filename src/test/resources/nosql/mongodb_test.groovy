import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.DB
import com.mongodb.BasicDBObject

class MongoService { 

    private MongoClient mongoClient 

    def host = "localhost" //your host name 
    def port = 27017 //your port. 
    def databaseName = 'jenkinsdb'

    public MongoClient client() {
        mongoClient = mongoClient ?: new MongoClient(host, port) 

        return mongoClient
    } 

    public DBCollection collection(collectionName) { 
        DB db = client().getDB(databaseName)

        return db.getCollection(collectionName) 
    }
    
    public void dropDatabase(dbn){
    	DB db = client().getDB(dbn)
    	db.remove()
    }
    
}

def service = new MongoService(databaseName: 'jenkinsdb')
db2.dropDatabase('jenkinsdb');

service = new MongoService(databaseName: 'jenkinsdb')
def coll = service.collection('user')

def data = [
    [firstName: 'Peter', lastName: 'Parker', age: '36'],
    [firstName: 'Homer', lastName: 'Simpson', age: '38']
].collect { it as BasicDBObject }

coll.insert(data)

coll.find().toArray().each {
    println it
}