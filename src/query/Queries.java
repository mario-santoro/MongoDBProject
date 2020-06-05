package query;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import bean.CoppiaXY;
import bean.Filtro;

public class Queries {
	//metodo che crea una connessione al Database NoSQL MongoDB, per usarla cambiare il nome del database
	//restituisce un oggetto che utilizzeremo come attributo per gli altri metodi per le query
	public MongoCollection<Document> connection(){		
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		MongoDatabase db = mongoClient.getDatabase("Omicidi");
		MongoCollection<Document> collection = db.getCollection("Omicidi");    	
		//collection.createIndex(Indexes.ascending("State"));
		return collection;
	}
	//metodo che trova tutti gli elementi (distinti) di un determinato filtro 
	public ArrayList<String> findProperty(MongoCollection<Document> collection, String filter){		
		ArrayList<String> result= new ArrayList<String>();
		MongoCursor<String> c = collection.distinct(filter, String.class).iterator();
		try {
			while (c.hasNext()) {
				// System.out.println(c.next());
				result.add(c.next());
			}
		} finally {
			c.close();
		}

		return result;

	}

	//metodo che restituisce tutte le città di uno stato
	public ArrayList<String> findCityOfState(MongoCollection<Document> collection, String stato){
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("State",stato);
		ArrayList<String> result= new ArrayList<String>();
		MongoCursor<String> c = collection.distinct("City", String.class).filter(searchQuery).iterator();
		try {
			while (c.hasNext()) {
				// System.out.println(c.next());
				result.add(c.next());
			}
		} finally {
			c.close();
		}

		return result;

	}
	//query con ascissa Anni
	public ArrayList<CoppiaXY> findForYear(Filtro f, MongoCollection<Document> collection){
		BasicDBObject searchQuery = new BasicDBObject();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();		
		if(f.getState()!=null && !f.getState().equals("")) {
			searchQuery.put("State",f.getState());
		}
		if(f.getMounth()!=null && !f.getMounth().equals("")) {
			searchQuery.put("Month",f.getMounth());
		}
		if(f.getRelationship()!=null && !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}
		if(f.getWeapon()!=null && !f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}
		if(f.getV().getPersonRazza()!=null && !f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}
		if(f.getV().getPersonSex()!=null && !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}
		if(f.getA().getPersonRazza()!=null && !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}
		if(f.getA().getPersonSex()!=null && !f.getA().getPersonSex().equals("")) {
			searchQuery.put("Perpetrator Sex",f.getA().getPersonSex());
		}
		if( !f.getV().getRangePersonAge().get(0).equals("")) {
		
				Bson condition = new Document("$gte", f.getV().getRangePersonAge().get(0)).append("$lte",f.getV().getRangePersonAge().get(1));

				searchQuery.put("Victim Age",condition);
		
		}
		if( !f.getA().getRangePersonAge().get(0).equals("")) {
		

				Bson condition = new Document("$gte", f.getA().getRangePersonAge().get(0)).append("$lte",f.getA().getRangePersonAge().get(1));

				searchQuery.put("Perpetrator Age",condition);
		
		}
		for(int i=f.getRangeYears().get(0); i<=f.getRangeYears().get(1); i++) {	

			searchQuery.put("Year",""+i);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(""+i,count);
				result.add(c);
				//System.out.println("Il numero delle vittime per anno, nell'anno: "+i+", nel mese: "+ f.getMounth()+", da anni: "+f.getV().getRangePersonAge().get(0)+",a anni: "+f.getV().getRangePersonAge().get(1)+" di sesso: "+f.getV().getPersonSex()+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}

	//query con ascissa Mesi
	public ArrayList<CoppiaXY> findForMonth(Filtro f, MongoCollection<Document> collection){ //sviluppo futuro aggiungere seconda barra con le morti di tutti gli anni dal 1980 al 2014
		String month[]= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		BasicDBObject searchQuery = new BasicDBObject();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		searchQuery.put("Year",""+f.getRangeYears().get(0));
		if(f.getState()!=null && !f.getState().equals("")) {
			searchQuery.put("State",f.getState());
		}
		if(f.getRelationship()!=null && !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}
		if(f.getWeapon()!=null && !f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}
		if(f.getV().getPersonRazza()!=null && !f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}
		if(f.getV().getPersonSex()!=null && !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}
		if(f.getA().getPersonRazza()!=null && !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}
		if(f.getA().getPersonSex()!=null && !f.getA().getPersonSex().equals("")) {
			searchQuery.put("Perpetrator Sex",f.getA().getPersonSex());
		}
		if( !f.getV().getRangePersonAge().get(0).equals("")) {
		
				Bson condition = new Document("$gte", f.getV().getRangePersonAge().get(0)).append("$lte",f.getV().getRangePersonAge().get(1));

				searchQuery.put("Victim Age",condition);
			
		}
		if( !f.getA().getRangePersonAge().get(0).equals("")) {			

				Bson condition = new Document("$gte", f.getA().getRangePersonAge().get(0)).append("$lte",f.getA().getRangePersonAge().get(1));
				searchQuery.put("Perpetrator Age",condition);
			
		}
		for(int i=0; i<12; i++) {	

			searchQuery.put("Month", month[i]);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(month[i],count);
				result.add(c);
				//System.out.println("Il numero delle vittime per mese, nell'anno: "+f.getRangeYears().get(0)+", nel mese: "+ month[i]+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}	
	//query con ascissa Stati
	public ArrayList<CoppiaXY> findForState(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = new BasicDBObject();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> stati= findProperty(collection,"State");
		if(f.getRangeYears().get(0)== f.getRangeYears().get(1)) {    			
			searchQuery.put("Year",""+f.getRangeYears().get(0));
		}else {
			Bson condition = new Document("$gte", ""+f.getRangeYears().get(0)).append("$lte",""+f.getRangeYears().get(1));
			searchQuery.put("Year",condition);
		}			
		if(f.getRelationship()!=null && !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}
		if(f.getWeapon()!=null && !f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}
		if(f.getV().getPersonRazza()!=null && !f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}
		if(f.getV().getPersonSex()!=null && !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}
		if(f.getA().getPersonRazza()!=null && !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}
		if(f.getA().getPersonSex()!=null && !f.getA().getPersonSex().equals("")) {
			searchQuery.put("Perpetrator Sex",f.getA().getPersonSex());
		}
		if(!f.getV().getRangePersonAge().get(0).equals("")) {
				Bson condition = new Document("$gte", f.getV().getRangePersonAge().get(0)).append("$lte",f.getV().getRangePersonAge().get(1));
				searchQuery.put("Victim Age",condition);			
		}
		if(!f.getA().getRangePersonAge().get(0).equals("")) {
				Bson condition = new Document("$gte", f.getA().getRangePersonAge().get(0)).append("$lte",f.getA().getRangePersonAge().get(1));
				searchQuery.put("Perpetrator Age",condition);
		}

		for(int i=0; i<stati.size(); i++) {	
			searchQuery.put("State",stati.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(stati.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nello stato: "+ stati.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}

	//query con ascissa Città di uno stato
	public ArrayList<CoppiaXY> findForCity(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = new BasicDBObject();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();

		searchQuery.put("State",f.getState());
		ArrayList<String> city= findCityOfState(collection, f.getState());

		if(f.getRangeYears().get(0).equals(f.getRangeYears().get(1))) {    			
			searchQuery.put("Year",""+f.getRangeYears().get(0));
		}else {
			Bson condition = new Document("$gte", ""+f.getRangeYears().get(0)).append("$lte",""+f.getRangeYears().get(1));
			searchQuery.put("Year",condition);
		}				
		if(f.getRelationship()!=null && !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}
		if(f.getWeapon()!=null && !f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}
		if(f.getV().getPersonRazza()!=null && !f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}
		if(f.getV().getPersonSex()!=null && !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}
		if(f.getA().getPersonRazza()!=null && !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}
		if(f.getA().getPersonSex()!=null && !f.getA().getPersonSex().equals("")) {
			searchQuery.put("Perpetrator Sex",f.getA().getPersonSex());
		}
		if(!f.getV().getRangePersonAge().get(0).equals("")) {
				Bson condition = new Document("$gte", f.getV().getRangePersonAge().get(0)).append("$lte",f.getV().getRangePersonAge().get(1));
				searchQuery.put("Victim Age",condition);
		}
		if( !f.getA().getRangePersonAge().get(0).equals("")) {
		
				Bson condition = new Document("$gte", f.getA().getRangePersonAge().get(0)).append("$lte",f.getA().getRangePersonAge().get(1));

				searchQuery.put("Perpetrator Age",condition);
			
		}
		for(int i=0; i<city.size(); i++) {	

			searchQuery.put("City",city.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(city.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nella città: "+ city.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}
}
