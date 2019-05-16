package com.spritehealth.gcpapplication.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.spritehealth.gcpapplication.factory.DatastoreFactory;

/**
 * Servlet implementation class LoginService
 */
@WebServlet(description = "This manages login behaviour of application", urlPatterns = { "/LoginService" })
public class LoginService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatastoreOptions options;
	Datastore datastore;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {

		datastore = DatastoreOptions.getDefaultInstance().getService();
		
		
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("psw");

		try { 
			KeyFactory keyFactory = datastore.newKeyFactory().setKind("Users");
			Entity Checkuser = datastore.get(keyFactory.newKey(username));
			if((Checkuser.getString("Username")).equals(username) 
					&& (Checkuser.getString("Password")).equals(password)){

				PrintWriter out = response.getWriter(); response.setContentType("text/html");
				out.println("<html>"); 
				out.println("<body>"); 
				out.println("<br/>");
				out.println("Login Success!!</br>");
				out.println("</body>");
				out.println("</html>");

			}
			else {
				throw new Exception();
			}
		} 
		catch (Exception e) {

			e.printStackTrace();

			PrintWriter out = response.getWriter(); response.setContentType("text/html");
			out.println("<html>"); 
			out.println("<body>"); 
			out.println("<br/>");
			out.println("Incorrect Username/Password.Please try again");
			out.println("<a href='index.jsp'> Click here to Login Again </a>"); 
			out.println("</body>");
			out.println("</html>");
		}

	}

}
