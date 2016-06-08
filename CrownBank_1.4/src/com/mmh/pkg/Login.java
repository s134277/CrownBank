package com.mmh.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;
       
 
	 
    public Login() {
        super();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("Logging in...");
		out.println();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		out.print("Logging in as user: " + login);
		out.println(". With password: " + password);
		
		
		Connection con = null;
		
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		
		try{
			con=ds1.getConnection(conUser,conPassword);
			out.println("Connected to DB2 succesfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		CallableStatement cstmt;
		try {
			out.println("Attempting to call the login procedure..");
			cstmt = con.prepareCall("{CALL \"DTUGRP03\".Verification(?,?,?)}");
			cstmt.setString(1, login);
		 	cstmt.setString(2, password);
		 	cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
		 	cstmt.execute();
		 	int result = cstmt.getInt(3);
		 	out.println("Return from DB2: " + result);
		 	
		 	ServletContext sc = this.getServletContext();
			request.setAttribute("user", login);
			RequestDispatcher rd = sc.getRequestDispatcher("/userHome.jsp");
			
			if(cstmt.getInt(3) == 1){
				rd = sc.getRequestDispatcher("/userHome.jsp");
		 	} else{
		 		request.setAttribute("failedLogin", "true");
		 		rd = sc.getRequestDispatcher("/login.jsp");
		 	}
			
			rd.forward(request, response);
		 	
		} catch (SQLException e) {
			out.println("Login procedure call unsuccesful");
			e.printStackTrace();
		} 
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
