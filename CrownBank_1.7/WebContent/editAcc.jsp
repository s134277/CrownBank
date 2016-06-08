<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WEBBANK</title>
<link rel="stylesheet" type="text/css" href="styles/myStyle.css">
</head>
<body>
<div id="header">
		<img src="pictures/CrownBankLogo2.png" alt="Logo" width="800" height="140">
	</div>

	<div id="nav">
		<%@include file="framework/nav.jsp" %>
	</div>

	<div id="section">
      	<h1>Edit account</h1>
      	Please enter new data in the desired fields:
      	<form method="post" action="CreateAcc">
        	<p><input type="text" name="name" value="" placeholder="Full name"></p>
        	<p><input type="text" name="tel" value="" placeholder="Telephone number"></p>
        	<p><input type="text" name="post" value="" placeholder="Postal number"></p>
        	<p><input type="text" name="userID" value="" placeholder="Desired username"></p>
       		<p><input type="password" name="password1" value="" placeholder="Password"></p>
       		<p><input type="password" name="password2" value="" placeholder="Repeat password"></p>
       		<p><input type="checkbox" name="isAdmin" value="isAdmin" >Administrator (default: regular user)</p>
       		Your desired default display currency:
       		<select name="currency">
    			<option value="USD">USD</option>
    			<option value="DKK">DKK</option>
    			<option value="GBP">GBP</option>
  			</select><p>
  			(Default is USD, you can always change your currency)
 
        <p class="submit"><input type="submit" name="commit" value="Create Account"></p>
      </form>
    </div>
<%if(request.getAttribute("success")!=null){
if(request.getAttribute("success").equals("false")){ %>

<font color = red> Something went wrong while processing you request.. <br> 
Error: <tr><%= request.getAttribute("status") %></tr> 
</font> 


<% } else{ %>

<font color = blue> Your account has succesfully been created! <br> 
</font> 

<% } }%>
<div id="footer">
Copyright © Michael Romer and Jesper Douglas
</div>

</body>
</html>