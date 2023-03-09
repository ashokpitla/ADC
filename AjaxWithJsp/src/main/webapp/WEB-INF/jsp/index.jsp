<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login Page</title>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
       <script type="text/javascript">
        
        function doAjaxPost() {
	      
	        var name = $('#name').val();
	        var password = $('#password').val();
	        
	        if(name=="" || password==""){
	        	alert('enter username/password');
	        }
	 
	        $.ajax({
		        type: "POST",
		        url: "/AjaxWithJsp/LoginPage",
		        data: "name=" + name + "&password=" + password,
		        success: function(response){
			     
			       // $('#info').html(response);
			     
		        },
	       		 error: function(e){
		      		  alert('Error: ' + e);
		        }
	        });
        }
        </script>
    </head>
   <body>
   <div class="container" style="width:500px;">
	<h3>Login Page</h3>
	<div class="alert alert-danger" data-toggle="alert">
  			<p class="close" data-dismiss="alert">&times;</p> 
  			${error }
  </div>
	
	<form method="post"	action="/AjaxWithJsp/login">
			<div class="form-control-group">
				<label>Username</label>
				<input type="text" class="form-control" name="username">
			</div>
			<div class="form-control-group">
				<label>Password</label>
				<input type="password" class="form-control" name="password">
			</div><br>
			<div class="form-control-group">
				<input type="submit" class="btn btn-primary" value="Login">&nbsp;&nbsp;&nbsp;
				<input type="reset" class="btn btn-danger" value="Clear">
			</div>
	</form>
</div>
</body>
</html>
  