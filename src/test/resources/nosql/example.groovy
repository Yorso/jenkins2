@Grab(group='com.gmongo', module='gmongo', version='0.9.3') 
import com.gmongo.GMongo 

// Instantiate gmongo.GMongo object 
// It defaults to localhost, but you can pass connection settings through the constructor 
def mongo = new GMongo() 

// Get a reference to the db
//def db = mongo.getDB("physicianfinder")