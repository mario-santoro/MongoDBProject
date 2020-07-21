
import bean.Filtro;
import bean.Person;
import query.Queries;

import java.util.ArrayList;

import java.util.Scanner;


public class DBtest {

	public static void main(String[] args) {

		Scanner in= new Scanner(System.in);
		Queries db=new Queries();
		//MongoCollection<Document> collection = db.connection(); 
		///////riempire le select///////////////////
	//	ArrayList<String> tmp= db.findProperty(collection, "Weapon");
		/*for(String a: tmp) {
    		System.out.println(a);
    	}
		 */


		/////fare query parametriche////////////	

		System.out.println("Puoi cercare il numero delle vittime, inserisci ordine della ricerca");
		System.out.println("Per: Anno, mese, stati, relazioni, età, razza");
		System.out.println("Inserisci ordine");
		String ordinamento=in.nextLine(); 
		Filtro f= new Filtro();
		ArrayList<Integer> binary=new ArrayList<Integer>();
		ArrayList<String> bin=new ArrayList<String>();
		Person v=new Person();
		Person a= new Person();
		f.setA(a);
		f.setV(v);

		if(ordinamento.contentEquals("mese")) {

			System.out.println("inserisci filtri");
			System.out.println("anno inizio (min 1980)");
			binary.add(Integer.parseInt(in.nextLine()));
			System.out.println("anno fine (max 2014)");
			binary.add(Integer.parseInt(in.nextLine()));
			f.setRangeYears(binary);
			//System.out.println("Inserisci stato");
			//f.setState(in.nextLine()); 
		//	System.out.println("Inserisci mese");
			//f.setMounth(in.nextLine()); 
			System.out.println("Inserisci anni della vittima");
			bin.add(in.nextLine());
			System.out.println("Inserisci anni della vittima");
			bin.add(in.nextLine());
			f.getV().setRangePersonAge(bin);   

			System.out.println("Inserisci sesso vittima");
			f.getV().setPersonSex(in.nextLine());

			
			//db.findForDate(f);

		}


	}
}