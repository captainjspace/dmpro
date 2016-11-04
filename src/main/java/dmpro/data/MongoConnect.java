/**
 * 
 */
package dmpro.data;

import java.util.Collection;

import org.bson.Document;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 3, 2016
 */
public class MongoConnect {
	public static void main (String [] args) {
		MongoClient mongoClient = new MongoClient();
		Collection<DB> dbs = mongoClient.getUsedDatabases();
		for (DB d: dbs) System.out.println(d.getName());
		
		mongoClient.getUsedDatabases().stream().forEach(p -> System.out.println(p.getName()));
		
		MongoDatabase db = mongoClient.getDatabase("reddragon");
		
		MongoIterable<String> mcols = db.listCollectionNames();	
		for (String s: mcols) System.out.println(s);
		
		MongoCollection<Document> docs = db.getCollection("spells");
		
		for (Document doc : docs.find()) {
			System.out.println(doc.getString("spellName"));
		}
				
		
	}
}
