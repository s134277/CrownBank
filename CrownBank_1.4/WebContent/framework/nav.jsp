<%! 
	public boolean isLoggedIn = false; 
%>

<center>

<br>
<%if(isLoggedIn == false) {%>
<Form action = "login.jsp">
<input type = "Submit" value = "Login" />
</Form>
<%} else{ %>
<Form action = "home.jsp">
<input type = "Submit" value = "Logout" />
</Form>
<% } %>

<br>

<Form action = "createAcc.jsp">
<input type = "Submit" value = "Create Account" />
</Form>

<br>

<Form action = "home.jsp">
<input type = "Submit" value = "Home" />
</Form>

<br>

<img src="pictures/Logo.png" alt="Logo" width="140" height="140">
</center>