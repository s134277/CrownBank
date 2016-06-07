package com.mmh.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.Resource;
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
		out.println("With password: " + password);
		
		
		Connection con = null;
//		Properties properties = new Properties();
		
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		
//		properties.put("user", conUser);
//		properties.put("password", conPassword);
		
		try{
			con=ds1.getConnection(conUser,conPassword);
			response.getWriter().println("Connected to DB2 succesfully");
//			con.setClientInfo(properties);
//			out.println("Client info properties succesfully stored");
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
		 	out.println("Return from DB2: " + cstmt.getInt(3));
		} catch (SQLException e) {
			out.println("Login procedure call unsuccesful");
			e.printStackTrace();
		} 
//		
//		PrintWriter out = response.getWriter();
//		out.println("Logging in...");
//		out.println();
//		String login = request.getParameter("login");
//		String password = request.getParameter("password");
//		out.println(login);
//		out.println(password);
//		
//		
//		Connection con = null;
//		Properties properties = new Properties();
//		String url = "jdbc:db2://192.86.32.54:5040/"
//				+ "DALLASB:retrieveMessagesFromServerOnGetMessage=true;"
//				+ "emulateParameterMetaDataForZCalls=1;";
//				try {
//					Class.forName("com.ibm.db2.jcc.DB2Driver");
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//		
//		properties.put("user", "DTU06");
//		properties.put("password", "FAGP2016");
//		try {
//			con = DriverManager.getConnection(url, properties);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		CallableStatement cstmt;
//		try {
//			cstmt = con.prepareCall("{CALL \"DTUGRP03\".Verification(?,?,?)}");
//			cstmt.setString(1, login);
//		 	cstmt.setString(2, password);
//		 	cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
//		 	cstmt.execute();
//		 	out.println(cstmt.getInt(3));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
