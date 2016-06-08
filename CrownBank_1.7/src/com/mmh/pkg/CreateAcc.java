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

@WebServlet("/CreateAcc")
public class CreateAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;   

    public CreateAcc() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/createAcc.jsp");
		int tel = -1;
		int post = -1;
		out.println("Logging in...");
		out.println();
		// Getting parameters from HTTP request and checking for simple validity: 
		// (advanced validity is a model check in the database)
		String name = request.getParameter("name");
		try{
			tel = Integer.parseInt(request.getParameter("tel"));
			request.setAttribute("badTelephoneNumber", "false");
		} catch(Exception e){
			request.setAttribute("badTelephoneNumber", "true");
		}
		
		try{
			post = Integer.parseInt(request.getParameter("post"));
			request.setAttribute("badPostalCode", "false");
		} catch(Exception e){
			request.setAttribute("badPostalCode", "true");
		}
		
		String userID = request.getParameter("userID");
		String password = request.getParameter("password1");
		String passwordConfirm = request.getParameter("password2");
		String isAdmin = request.getParameter("isAdmin");
		String currency = request.getParameter("currency");
		String type;
		if(isAdmin!=null){
			type = "Administrator";
		} else{
			type = "Client";
		}
		
		
		if(password.equals(passwordConfirm)){
			request.setAttribute("confirmedPassword", "true");
		} else{
			request.setAttribute("confirmedPassword", "false");
		}
	
		/** output for testing purposes: **/
		boolean admin = false;
		if(isAdmin!=null){
			admin = true;
		}
		
		out.println("Full name " + name);
		out.println(", with telephone number: " + tel);
		out.println("and postal code: " + post);
		out.println("User ID: " + userID);
		out.println(", with password: " + password);
		out.println("Is administrator: " + admin);
		out.println("Currency " + currency);
		/** **/
		
		//if validity is alright, connect to DB and request to create user:
		if(tel!=-1 && post!=-1 && request.getAttribute("confirmedPassword").equals("true")){
			
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
				cstmt = con.prepareCall("{CALL \"DTUGRP03\".CreateUserAccount(?,?,?,?,?,?,?,?,?)}");
				cstmt.setString(1, userID);
			 	cstmt.setString(2, name);
			 	cstmt.setInt(3, tel);
			 	cstmt.setString(4, password);
			 	cstmt.setString(5, type);
			 	cstmt.setInt(6, post);
			 	cstmt.setString(7, currency);
			 	cstmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			 	cstmt.registerOutParameter(9, java.sql.Types.INTEGER);
			 	cstmt.execute();
	
			 	boolean result = (cstmt.getInt(9)!=0);
			 	String status = cstmt.getString(8);
			 	
			 	if(result){
			 		out.println("Successfully registered");
			 		request.setAttribute("success", "true");
			 		request.setAttribute("status", null);
			 	} else{
			 		out.println("Unsuccesfully registered, with error: " + status);
			 		request.setAttribute("success", "false");
			 		request.setAttribute("status", status);
			 	}
			 	
			 	
			} catch (SQLException e) {
				out.println("Login procedure call unsuccesful");
				e.printStackTrace();
			} 
		} else{ //in case telephone/postal code could not be parsed as int or password 1 and 2 dont match.
			String status = "check that the password is the same in both input fields, and the telephone and postal code only contains integers";
			out.println("Unsuccesfully registered, with error: input is invalid, check that the password is the same in both input fields, and the telephone and postal code only contains integers");
	 		request.setAttribute("success", "false");
	 		request.setAttribute("status", status);
		}
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
