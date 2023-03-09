<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:url value="/login" var="loginUrl"/>  
<html>
<head>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="main"><br>
<div class="container">
<div style="padding:20px;text-align:center;background-color: #668900;border-radius:10px;"><h3>Welcome to Login Page</h3></div><br>
<form action="${loginUrl}" method="post">         
    <c:if test="${param.error != null}">          
        <div class="alert alert-danger" data-toggle="alert">  
           Invalid username and password.
           <p class="close" data-dismiss="alert">&times; </p>
        </div>  
    </c:if>  
    <c:if test="${param.logout != null}"> 
      <div class="alert alert-danger" data-toggle="alert">  
             You have been logged out.  
           <p class="close" data-dismiss="alert">&times; </p>
        </div>          
       
    </c:if>  
    <div class="form-group">
        <label for="username">Username</label>  
        <input type="text" id="username" class="form-control" name="username"/>      
</div>
  <div class="form-group">
        <label for="password">Password</label>  
        <input type="password" class="form-control" id="password" name="password"/>      
    </div>
     <div class="form-group">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>  
    <input type="submit" class="btn btn-primary" value="Log in" onClick="doAjaxPost()"> &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="reset" value="Clear" class="btn btn-danger">
    </div>
</form> 
</div>
</div>
 <script type="text/javascript">
        
        function doAjaxPost() {
	      
	        var username = $('#username').val();
	        var password = $('#password').val();
	        
	        $.ajax({
		        type: "POST",
		        url: "/SpringSecurity/login",
		        data: "username=" + username + "&password=" + password,
		        success: function(response){
			     
			       console.log(response);
			     
		        },
	       		 error: function(e){
		      		 console.log('Error: ' + e);
		        }
	        });
        }
        </script>
</body>
</html>