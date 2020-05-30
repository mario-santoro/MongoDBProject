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
		Person v=new Person();
		Person a= new Person();
		ArrayList<CoppiaXY> result= new ArrayList<CoppiaXY>();
		
		f.setA(a);
		f.setV(v);
		//inserisco filtro inizio anno
		binary.add(1990);
		//inserisco filtro fine anno
		binary.add(1990);
		f.setRangeYears(binary);
		//inserisci filtro stato
		f.setState("California"); 
		//inserisci filtro mese
		//f.setMounth("June"); 
		//inserisci range iniziale anni vittima
		bin.add("");
		//inserisci range finale anni vittima
		bin.add("");
		f.getV().setRangePersonAge(bin);   
		f.getA().setRangePersonAge(bin);   
		//inserisci sesso della vittima
		//f.getV().setPersonSex("Male");
		switch (ascissa) {
		case "Year": 
				 result=db.findForYear(f, collection);	
			break;
		case "Month":	
				result=	db.findForMonth(f, collection);
			break;
		case "State":
			result=	db.findForState(f, collection);
			break;
		case "City":	
			result=	db.findForCity(f, collection);
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
