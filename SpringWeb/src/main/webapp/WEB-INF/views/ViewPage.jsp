<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Welcome to View Page</title>
</head> 
<body>
<h2>Welcome to Spring Security</h2><span><a href="logout"> Log out</a></span> 
<p> Hi ,  ${pageContext.request.userPrincipal.name}</p><br>

</body> 
</html>