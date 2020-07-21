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

import query.Queries;

/**
 * Servlet implementation class ShowFilter
 */
@WebServlet("/ShowFilter")
public class ShowFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filtro= request.getParameter("filtro");
	
		Queries db=new Queries();		
		MongoCollection<Document> collection = db.connection(); 		
		ArrayList<String> filtri=  db.findProperty(collection, filtro);
		response.getWriter().append("[");
		int i;
		for(i = 0; i < filtri.size()-1; i++){
			response.getWriter().append("{\"filtro\":\""+filtri.get(i)+"\"},");
		}
		response.getWriter().append("{\"filtro\":\""+filtri.get(i)+"\"}");
		response.getWriter().append("]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
