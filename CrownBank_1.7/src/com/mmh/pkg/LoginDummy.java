package com.mmh.pkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginDummy
 */
@WebServlet("/LoginDummy")
public class LoginDummy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginDummy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("Logging in...");
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		ServletContext sc = this.getServletContext();
		request.setAttribute("user", login);
		RequestDispatcher rd = sc.getRequestDispatcher("/userHome.jsp");
		
		if(login.equals("m") && password.equals("m")){
			rd = sc.getRequestDispatcher("/userHome.jsp");
	 	} else{
	 		request.setAttribute("failedLogin", "true");
	 		rd = sc.getRequestDispatcher("/login.jsp");
	 	}
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
