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

import bean.CoppiaXY;
import bean.Filtro;

public class Queries {

	public MongoCollection<Document> connection(){		
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		MongoDatabase db = mongoClient.getDatabase("Omicidi");
		MongoCollection<Document> collection = db.getCollection("Omicidi");
		//creazione dell'indice per anno, viene creato nel db;
		//collection.createIndex(Indexes.ascending("Year"));
		return collection;
	}

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

	public ArrayList<CoppiaXY> findForDate(Filtro f, MongoCollection<Document> collection){
	
		BasicDBObject searchQuery = new BasicDBObject();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> tmp= new ArrayList<String>();
		tmp.add("tutte");
		if(f.getState()!=null && !f.getState().equals("")) {
			searchQuery.put("State",f.getState());
		}else {
			f.setState("tutti");
		}
		if(f.getMounth()!=null && !f.getMounth().equals("")) {
			searchQuery.put("Month",f.getMounth());
		}else {
			f.setMounth("tutti");
		}
		if(f.getRelationship()!=null && !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}else {
			f.setRelationship("tutti");
		}
		if(f.getWeapon()!=null && !f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}else {
			f.setWeapon("tutte");
		}
		if(f.getV().getPersonRazza()!=null && !f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}else {
			f.getV().setPersonRazza("tutte");
		}
		if(f.getV().getPersonSex()!=null && !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}else {
			f.getV().setPersonSex("tutte");
		}
		if(f.getA().getPersonRazza()!=null && !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}else {
			f.getA().setPersonRazza("tutte");
		}
		if(f.getA().getPersonSex()!=null && !f.getA().getPersonSex().equals("")) {
			searchQuery.put("Perpetrator Sex",f.getA().getPersonSex());
		}else {
			f.getA().setPersonSex("tutte");
		}

		if(f.getV().getRangePersonAge()!=null && !f.getV().getRangePersonAge().get(0).equals("")) {
			if(f.getV().getRangePersonAge().get(0).equals(f.getV().getRangePersonAge().get(1))) {    			
				searchQuery.put("Victim Age",f.getV().getRangePersonAge().get(0));
			}else {

				Bson condition = new Document("$gte", f.getV().getRangePersonAge().get(0)).append("$lte",f.getV().getRangePersonAge().get(1));

				searchQuery.put("Victim Age",condition);
			}
		}else {
			f.getV().setRangePersonAge(tmp);
		}
		/*if(f.getA().getRangePersonAge()!=null && !f.getA().getRangePersonAge().get(0).equals("")) {
    		if(f.getA().getRangePersonAge().get(0).equals(f.getA().getRangePersonAge().get(1))) {    			
    			searchQuery.put("Perpetrator Age",f.getA().getRangePersonAge().get(0));
    		}else {

		    	Bson condition = new Document("$gt", f.getA().getRangePersonAge().get(0)).append("$lt",f.getA().getRangePersonAge().get(1));

    	    	searchQuery.put("Perpetrator Age",condition);
    		}
    	}else {
    		f.getA().setRangePersonAge(tmp);
    	}*/
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


}
