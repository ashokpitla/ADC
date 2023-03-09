<html>    
<head>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
<title>Home Page</title>    
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>    
<body> 
<div class="main">
<div class="container">
<div style="padding:20px;text-align:center;background-color: #668900;border-radius:10px;"><h3>You are successfully login</h3>
<div class="dropdown" style="float:right;">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"><p class="glyphicon glyphicon-user"></p>
   Hi, ${pageContext.request.userPrincipal.name}
    <span class="caret"></span></button>
    <ul class="dropdown-menu">
      <li><a href="logout">LogOut</a></li>
      
    </ul>
  </div>
  </div>
 <jsp:include page="dashboard.jsp"/>
  </div>
</div>
</body>    
</html>