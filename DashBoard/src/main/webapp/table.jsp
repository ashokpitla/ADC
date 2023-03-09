<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<Html>  
<head>   
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>  
Registration Page  
</title>  
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>  
</head>  
<body>  
<div id="main" class="container">
<a href="index.jsp" class="btn btn-primary"><i class='fas fa-arrow-alt-circle-left' style='font-size:24px'></i></a>
<div>
<h2 style="text-align: center"><b>Application Table</b></h2>
</div>

<%

try{
	Class.forName("oracle.jdbc.Driver.OracleDriver");
	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.50:1521/Passbook","mis","mis");  
	Statement stmt=con.createStatement();  
	ResultSet rs=stmt.executeQuery("select * from  ");  
	out.print("<table>");
	out.print("<thead>");
	out.print("<tr>");
	out.print("<th>Sno</th>"+"<th>First</th>");
	out.print("</tr>");
	out.print("</thead>");
	
	while(rs.next()){  
		out.print("<tr>"+"<th>"+rs.getInt(1)+"</th>"+"<th>"+rs.getString(2)+"</th>"+"</tr");
	}
	out.print("</table>");
}
	catch(Exception e){
		System.out.println(e);
		}  
	 
	
%>

<div style="border-radius: 20px;width: 400px;margin-left:350px">
<div style="margin-left: 20px">

  
</div>
</div>
<form name="registration" class="form-group">
	<label>Sno</label>
	<input type="number" id="sno" class="form-group"><br>
	<label>First Name</label>
	<input type="text" id="fname" class="form-group"><br>
	<label>Last name</label>
	<input type="text" id="lname" class="form-group"><br>
	<label>Father Name</label>
	<input type="text" id="father" class="form-group"><br>
	<label>Age</label>
	<input type="number" id="age" class="form-group"><br>
	<label>Select Course</label>
	<select id="course" class="form-group">
	<option value="BTECH">BTECH
	<option value="MTECH">MTECH
	<option value="MCA">MCA
	<option value="BCA">BCA
	</select><br>
	<label>Mobile</label>
	<input type="tel" id="mobile" class="form-group"><br>
	<button type="button" onclick="myFunction()">click to save</button>
	<input type="reset" value="clear">

</form>
</div>

</body>  
</html>  