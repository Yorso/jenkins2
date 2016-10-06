// Grab the jar
//@Grab('com.gmongo:gmongo:1.0')

//import com.gmongo.GMongo

def gmongo = new GMongo('localhost:27017')
def db = gmongo.getDB('tips')

db.so.drop()
db.so << [name: 'Windows XP']
db.so << [name: 'Windows 7']
db.so << [name: 'Windows Vista']
db.so << [name: 'Mac OS X v10.3 "Panther"']
db.so << [name: 'Mac OS X v10.4 "Tiger"']
db.so << [name: 'Mac OS X v10.5 "Leopard"']
db.so << [name: 'Mac OS X v10.6 "Snow Leopard"']
db.so << [name: 'Mac OS X v10.7 "Lion"']

// Mac OS Only
println "\nMac Os Only\n"
db.so.find([name: ~/^Mac/]).each { so -> println so}

// Print: 

// Mac OS Leopard, Snow Leopard
println "\nMac OS Leopard, Snow Leopard\n"
db.so.find([name: ~/Leopard/]).each { so -> println so}