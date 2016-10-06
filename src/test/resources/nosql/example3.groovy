@Grab('org.mongodb:mongodb-driver:3.2.2')

import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.DB
import com.mongodb.BasicDBObject

class MongoService { 
    private MongoClient mongoClient 

    def host = "localhost" //your host name 
    def port = 27017 //your port no. 
    def databaseName = 'test'

    public MongoClient client() {
        mongoClient = mongoClient ?: new MongoClient(host, port) 

        return mongoClient
    } 

    public DBCollection collection(collectionName) { 
        DB db = client().getDB(databaseName)

        return db.getCollection(collectionName) 
    }
}

def service = new MongoService(databaseName: 'db')
def foo = service.collection('foo')

def data = [
    [firstName: 'Jane', lastName: 'Doe'],
    [firstName: 'Elvis', lastName: 'Presley']
].collect { it as BasicDBObject }

foo.insert(data)

foo.find().toArray().each {
    println it
}