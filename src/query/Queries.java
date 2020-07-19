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
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();		

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

	//query con ascissa Mesi solo per un anno specifico ma si potrebbe fa doppia barra con anni globali
	public ArrayList<CoppiaXY> findForMonth(Filtro f, MongoCollection<Document> collection){ //sviluppo futuro aggiungere seconda barra con le morti di tutti gli anni dal 1980 al 2014
		String month[]= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();				
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
		BasicDBObject searchQuery = controlli(f);;
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> stati= findProperty(collection,"State");


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
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> city= findCityOfState(collection, f.getState());

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

	private BasicDBObject controlli(Filtro f) {
		BasicDBObject searchQuery = new BasicDBObject();
		if( !f.getState().equals("")) {
			searchQuery.put("State",f.getState());
		}
		if(f.getRangeYears().get(0).equals(f.getRangeYears().get(1))) {    			
			searchQuery.put("Year",""+f.getRangeYears().get(0));
		}else {
			Bson condition = new Document("$gte", ""+f.getRangeYears().get(0)).append("$lte",""+f.getRangeYears().get(1));
			searchQuery.put("Year",condition);
		}				
		if( !f.getRelationship().equals("")) {
			searchQuery.put("Relationship",f.getRelationship());
		}
		if(!f.getWeapon().equals("")) {
			searchQuery.put("Weapon",f.getWeapon());
		}
		if(!f.getV().getPersonRazza().equals("")) {
			searchQuery.put("Victim Race",f.getV().getPersonRazza());
		}
		if( !f.getV().getPersonSex().equals("")) {
			searchQuery.put("Victim Sex",f.getV().getPersonSex());
		}
		if( !f.getA().getPersonRazza().equals("")) {
			searchQuery.put("Perpetrator Race",f.getA().getPersonRazza());
		}
		if( !f.getA().getPersonSex().equals("")) {
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
		if( !f.getMounth().equals("")) {
			searchQuery.put("Month",f.getMounth());
		}
		return searchQuery;
	}

	//ascissa eta vittima
	public ArrayList<CoppiaXY> findForVictimAge(Filtro f, MongoCollection<Document> collection ){
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();    
		int[][] eta= {{0,14},{15,20},{21,25},{26,34},{35,54},{55,64},{65,75},{76,84},{85,100}};
		for(int i = 0;i<9;i++) {
			
			Bson condition = new Document("$gte", ""+eta[i][0]).append("$lte",""+eta[i][1]);
			searchQuery.put("Victim Age",condition);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					System.out.println(count + " Conto i muort");
					count++;
					cursor.next();
				}
				
				CoppiaXY c= new CoppiaXY(eta[i][0]+ "-"+eta[i][1],count);
				result.add(c);
			} finally {
				cursor.close();
			}
		}
		return result;
	}

	//ascissa eta assassino
	public ArrayList<CoppiaXY> findForPerpetratorAge(Filtro f, MongoCollection<Document> collection ){
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();    
		int[][] eta= {{0,14},{15,20},{21,25},{26,34},{35,54},{55,64},{65,75},{76,84},{85,100}};
		for(int i = 0;i<9;i++) {
			Bson condition = new Document("$gte", ""+eta[i][0]).append("$lte",""+eta[i][1]);
			searchQuery.put("Perpetrator Age",condition);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
				}
			
				CoppiaXY c= new CoppiaXY(eta[i][0]+ "-"+eta[i][1],count);
				result.add(c);
			} finally {
				cursor.close();
			}
		}
		return result;
	}



	//ascissa sesso vittime
	public ArrayList<CoppiaXY> findForVictimSex(Filtro f, MongoCollection<Document> collection ){
		String sex[]= {"Male","Female","Unknown"};
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();        
		for(int i = 0;i<3;i++) {
			searchQuery.put("Victim Sex",sex[i]);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
				}
				CoppiaXY c= new CoppiaXY(sex[i],count);
				result.add(c);
			} finally {
				cursor.close();
			}
		}
		return result;
	}



	//ascissa sesso assassino
	public ArrayList<CoppiaXY> findForPerpetratorSex(Filtro f, MongoCollection<Document> collection ){
		String sex[]= {"Male","Female","Unknown"};
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();        
		for(int i = 0;i<3;i++) {
			searchQuery.put("Perpetrator Sex",sex[i]);
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
				}
				CoppiaXY c= new CoppiaXY(sex[i],count);
				result.add(c);
			} finally {
				cursor.close();
			}
		}
		return result;
	}
	
	public ArrayList<CoppiaXY> findForVictimRace(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();

		ArrayList<String> razzeVittime= findProperty(collection,"Victim Race");

	

		for(int i=0; i<razzeVittime.size(); i++) {	

			searchQuery.put("Victim Race",razzeVittime.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(razzeVittime.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nello stato: "+ stati.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}



	//Query con ascissa relazione vttima
	public ArrayList<CoppiaXY> findForRelationship(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> relationship= findProperty(collection,"Relationship");

		for(int i=0; i<relationship.size(); i++) {	

			searchQuery.put("Relationship",relationship.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(relationship.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nello stato: "+ stati.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}

	//Query con ascissa armi
	public ArrayList<CoppiaXY> findForWeapon(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		ArrayList<String> weapons= findProperty(collection,"Weapon");

		
		for(int i=0; i<weapons.size(); i++) {	

			searchQuery.put("Weapon",weapons.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(weapons.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nello stato: "+ stati.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}

	//ascissa razza assassino
	public ArrayList<CoppiaXY> findForPerpetratorRace(Filtro f, MongoCollection<Document> collection){ 
		BasicDBObject searchQuery = controlli(f);
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();

		ArrayList<String> razzeAssassini= findProperty(collection,"Perpetrator Race");

		
		for(int i=0; i<razzeAssassini.size(); i++) {	

			searchQuery.put("Perpetrator Race",razzeAssassini.get(i));
			MongoCursor<Document> cursor = collection.find(searchQuery).iterator();  
			int count=0;
			try {
				while (cursor.hasNext()) {
					count++;
					cursor.next();
					//System.out.println(cursor.next().toJson());

				}
				CoppiaXY c= new CoppiaXY(razzeAssassini.get(i),count);
				result.add(c);
				//System.out.println("Il numero delle vittime per stato, nell'anno: "+f.getRangeYears().get(0)+", nello stato: "+ stati.get(i)+" è: "+count);
			} finally {
				cursor.close();
			}
		}

		return result;
	}


}
