package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.CoppiaXY;
import bean.Filtro;
import bean.Person;

import query.Queries;


/**
 * Servlet implementation class ShowGrafic
 */
@WebServlet("/ShowGraphic")
public class ShowGraphic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public ShowGraphic() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String ascissa= request.getParameter("ascissa");
		Queries db=new Queries();		
		MongoCollection<Document> collection = db.connection(); 		
		
		Filtro f= new Filtro();
		ArrayList<Integer> binary=new ArrayList<Integer>();
		ArrayList<String> bin=new ArrayList<String>();
		ArrayList<String> binA=new ArrayList<String>();
		Person v=new Person();
		Person a= new Person();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		String year=request.getParameter("year");
		String yearTo=request.getParameter("yearTo");
		f.setA(a);
		f.setV(v);
		//inserisco filtro inizio anno
		
		if(!year.contentEquals("")) {	
			binary.add(Integer.parseInt(year));
			//inserisco filtro fine anno
			binary.add(Integer.parseInt(yearTo));
			f.setRangeYears(binary);
		}else {
			ArrayList<String> y= db.findProperty(collection, "Year");
			binary.add(Integer.parseInt(y.get(0)));
			binary.add(Integer.parseInt(y.get(y.size()-1)));
			f.setRangeYears(binary);
		}
		//inserisci filtro stato
		f.setState(request.getParameter("state")); 
		//inserisci filtro mese
		f.setMounth(request.getParameter("month")); 		
		//inserisci range anni vittima
		bin.add(request.getParameter("ageVstart"));
		bin.add(request.getParameter("ageVend"));		
		f.getV().setRangePersonAge(bin);   
		//range anni assassino
		binA.add(request.getParameter("ageAstart"));
		binA.add(request.getParameter("ageAend"));
		f.getA().setRangePersonAge(binA);   
		//inserisci sesso della vittima
		f.getV().setPersonSex(request.getParameter("victimSex"));
		//inserisci sesso dell'assassino
		f.getA().setPersonSex(request.getParameter("perpetratorSex"));
		//inserisci razza dell'assassino
		f.getA().setPersonRazza(request.getParameter("perpetratorRace"));
		//inserisci razza della vittima
		f.getV().setPersonRazza(request.getParameter("victimRace"));
		//inserisci arma
		f.setWeapon(request.getParameter("weapon"));
		//inserisci relazione
		f.setRelationship(request.getParameter("relationship"));
				
		switch (ascissa) {
        case "Year":
            result=db.findForYear(f, collection);   
            break;
           
        case "Month":   
            result=    db.findForMonth(f, collection);
            break;
           
        case "State":
            result=    db.findForState(f, collection);
            break;
           
        case "City":   
            result=    db.findForCity(f, collection);
            break;
           
        case "Victim Sex":
            result=db.findForVictimSex(f, collection);
            break;
           
        case "Perpetrator Sex":
            result=db.findForPerpetratorSex(f, collection);
            break;
           
        case "Victim Age":
            result=db.findForVictimAge(f, collection);
            break;
           
        case "Perpetrator Age":
            result=db.findForPerpetratorAge(f, collection);
            break;
           
        case "Victim Race":
        	   result=db.findForVictimRace(f, collection);
            break;
           
        case "Perpetrator Race":
        	   result=db.findForPerpetratorRace(f, collection);
            break;
           
        case "Weapon":
        	   result=db.findForWeapon(f, collection);
            break;
           
        case "Relationship":
        	   result=db.findForRelationship(f, collection);
            break;
			
		default:
			break;
		}
		

		response.getWriter().append("[");

		int i;
		for(i = 0; i < result.size()-1; i++){

			response.getWriter().append("{\"x\":\""+result.get(i).getAscissa()+"\",\"y\":\""+result.get(i).getOrdinata()+"\"},");

		}

		response.getWriter().append("{\"x\":\""+result.get(i).getAscissa()+"\",\"y\":\""+result.get(i).getOrdinata()+"\"}");

		response.getWriter().append("]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
