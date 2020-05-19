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
		System.out.println("we");
		
		
		Queries db=new Queries();
		
		MongoCollection<Document> collection = db.connection(); 
		Filtro f= new Filtro();
		ArrayList<Integer> binary=new ArrayList<Integer>();
		ArrayList<String> bin=new ArrayList<String>();
		Person v=new Person();
		Person a= new Person();
		f.setA(a);
		f.setV(v);



		//inserisco filtro inizio anno
		binary.add(1980);
		//inserisco filtro fine anno
		binary.add(2014);
		f.setRangeYears(binary);
		//inserisci filtro stato
		//f.setState("Alaska"); 
		//inserisci filtro mese
		//f.setMounth("June"); 
		//inserisci range iniziale anni vittima
		bin.add("22");
		//inserisci range finale anni vittima
		bin.add("27");
		f.getV().setRangePersonAge(bin);   
		//inserisci sesso della vittima
		//f.getV().setPersonSex("Female");

		ArrayList<CoppiaXY> result=	db.findForDate(f, collection);




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
