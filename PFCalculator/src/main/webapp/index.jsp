<html lag="en">
<head>
<title>EPF Calculator</title>
<link rel = "icon" href="images/pf.jpg">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
  <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
  <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <script src = "script.js"></script>
 <link rel="stylesheet" href="style.css" />

  	
</head>
<body>
	<div class="container" id="main">
	<h1>EPF Calculator</h1>
	
	<div style="border-style: groove;"><br><br><br>
		<div class="row">
		 <div class="col-sm-2"></div>
		 <div class="col-sm-4">
			<label>Principal Amount</label>
		 </div>
		 <div class="col-sm-4">
			  <div class="input-group">
			   	<input type="number" placeholder="enter amount" id="pamount" class="form-control"><span class="input-group-addon"><i class="fa fa-rupee" style="font-size:24px"></i></span>
			  </div>
		  </div>
		  <div class="col-sm-2"></div>
		  </div>
		  <div class="row">
			  <div class="col-sm-2"></div>
			  <div class="col-sm-4">
			  	<label>Interest Rate</label>
			  </div>
			   <div class="col-sm-4">
				 	<div class="input-group">
					 	<input type="number"  placeholder="enter interst" id="iamount" class="form-control"><span class="input-group-addon"><i class="fa fa-percent" style="font-size:23px"></i></span>
				 	</div>
			 	</div>
			 	<div class="col-sm-2"></div>
		 	</div>
		 	 <div class="row">
		 	 	<div class="col-sm-2"></div>
		 	 	<div class="col-sm-4">
		 	 		<label>Tenure Period</label>
		 	 	</div>
		 	 	<div class="col-sm-4">
			 			<div class="input-group">
				 	 		<input type="number" id="tenure"  placeholder="tenure"  class="form-control"><span class="input-group-addon">
				 	 		<input type="radio" id="t1" name="tenure" value="year" class="btn btn-success">&nbsp;Year &nbsp;&nbsp;
				 	 		<input type="radio" id="t2" name="tenure" value="month"class="btn btn-primary">&nbsp;Month</span>
							
					 	</div>
		 		</div>
		 			<div class="col-sm-2"></div>
			</div>	<br><br>
			 <div class="row">
		 	 	<div class="col-sm-3"></div>
		 	 	<div class="col-sm-6"><button id="cal" class="btn btn-primary">Calculate</button></div>
		 	 	<div class="col-sm-3"></div>
			</div>
			<br><br><br>
		</div>
		
			<div></div><br><br>
			<div class="row">
			<div class="col-sm-6" style="border-style: groove;">
			
					<br><br><br>
					
						<h4 style="color:DarkSlateBlue">Principal Amount</h4	><br>
						<b><p style="color:DarkSlateBlue"> &#8377<span id="amount">0</span></p></b>
						
						<br>				
					
						<h4 style="color:MediumSeaGreen">Total Interest Payable</h4><br>
						<b><p style="color:MediumSeaGreen"> &#8377<span id="earned">0</span></p></b>
					
					<br><br>					
					
						<h4 style="color:Salmon">Total Payment<br>(Principal + Interest)</h4>
						<b><p style="color:Salmon"> &#8377<span  id="total">0</span></p></b>
								
					</div>
					
					<div class="col-sm-6"><h4>Break-up of Total Amount</h4>
					<div id="chartContainer"></div>
					</div>
					
			</div>
		<hr style="height:10px;width:100%;background-color:DodgerBlue">
	<div class="row">
		<div class="col-sm-4"><label>Calculate showing Amount starting from</label></div>
		
		<div class="col-sm-4">
			<div class="input-group" >
				<input type = "date"  value="2023-01-01" class="form-control" id = "datepicker"><span class="input-group-addon"><i class="fa fa-calendar" style="font-size:22px" aria-hidden="true"></i></span>
			</div>
		</div>
		<div class="col-sm-4">
			<label>
			<select id="select1" class="form-control">
			<option value="calendar" >Calendar Year</option>
			<option value="finacial" selected>Financial Year</option>
			</select></label>
			</div>
	</div>
		
	<div id="chartContainer1"></div> <br>
	
	  	<div  id="output_div"></div>
	 
	 	
</div>
				
</body>
</html>