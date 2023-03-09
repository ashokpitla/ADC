   function expand(year) {
	   var rows = document.getElementsByClassName("child-row"+year);
	   if(rows != null) {
	   for (var i = 0; i < rows.length; i ++) {
	       if (rows[i].style.display === "none") {
	    	   rows[i].style.display = "table-row";
	  	   } else {
	  		 rows[i].style.display = "none";
	  	   }
	   }
	   }
} 

function calculate() {
	
	
	var loan_amount = parseFloat(document.getElementById('loan_amount').value.replace(",", ""));
	var loan_terms = parseFloat(document.getElementById('loan_terms').value.replace(",", ""));
	var interest_rate = parseFloat(document.getElementById('interest_rate').value.replace(",", ""));
	var formatter = new Intl.NumberFormat('en-US', {
	  style: 'currency',
	  currency: 'INR',
	});
	
	if (!isNaN(loan_amount) && !isNaN(loan_terms)&& !isNaN(interest_rate)) {

		var is_yearly = true;
		var terms_type = document.getElementsByName('loan_terms_year_month');
		var loan_terms_in_months;
		
		if(terms_type[0].checked)
		{
			is_yearly = true;
			loan_terms_in_months = loan_terms * 12;
		}
		if(terms_type[1].checked)
		{
			is_yearly = false;
			loan_terms_in_months = loan_terms;
		}
		
	$("#datepicker").datepicker().datepicker('setDate','today');
		
	  	var interest_paid;
		var amount = loan_amount;
		var principal_paid;
		var total_of_payment;
		var payment_date;
		var outputHTML="";
		  	outputHTML += "<table id='detail_table' class='table text-center detail'>";
	        outputHTML += "<tr >";
	        outputHTML += "<th  style='text-align:center;background-color:lightskyblue'>"+" "+"Year"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#b91d47;text-align:center;'>"+" "+"Principle Amount<br>(A)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#00aba9;text-align:center;'>"+" "+"Interest Amount<br>(B)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#2b5797;text-align:center;'>"+" "+"Total Amount<br>(A+B)"+" "+"</th>";
	        outputHTML += "</tr>";

		var date = new Date();		
		var first_payment_date = new Date(date.getFullYear(), date.getMonth(), 1);	
		var payment_date = first_payment_date; 
	selectElement = document.querySelector('#select1');
	year_type=selectElement.value;
	
				var temp_year;
			 	var chart_year=[];
			 	var chart_principle=[];
			 	var chart_interest=[];
			 	var chart_total=[];
				
	if(year_type=='calendar_year'){
			 //printing months
			 				 	
			 	for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount;
		             total_of_payment=principal_paid+interest_paid;
		           	// total_interest=interest_paid;					
		           	
		             if(temp_year === payment_date.getFullYear()) {
		          
                          outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 
		            		  	
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+ "<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+payment_date.getFullYear()+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
                          outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						
					 }
					 temp_year=payment_date.getFullYear();
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
					 
			        }
			        outputHTML += "</table>";
				 	document.getElementById('total_payment').innerHTML = formatter.format(total_of_payment);
					document.getElementById('monthly_payment').innerHTML = formatter.format(amount);
					document.getElementById('total_interest').innerHTML = formatter.format(interest_paid);
					
				 	document.getElementById("dvTable").innerHTML = outputHTML;
				 	
			        var xValues = ["principal", "Interest", "Total"];
								 var yValues = [amount, interest_paid, total_of_payment];
								 var barColors = ["#b91d47","#00aba9","#2b5797",];
								 
							 new Chart("pie_chart", {
								   type: "pie",
								   data: {
								     labels: xValues,
								     datasets: [{
								       backgroundColor: barColors,
								       data: yValues
								     }]
								   },
								  });
		 	
					   var principle_Data = {
							  label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							 barThickness: 60,
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							  barThickness: 60,
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							  barThickness: 60,
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
						new Chart("bar_chart", {
							  type: 'bar',
							  data: chartData,
							options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  // number (pixels) or 'flex'
							                maxBarThickness: 20 // number (pixels)
							            }]
							        }
							    }
							});
			}
		
				else if(year_type=='finacial_year'){
					
					
					
						var month_list= [ "April", "May", "June", "July", "August", "September", 
							"October", "November", "December","January", "February", "March"];
			
			 	
			 	for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount ;
		             total_of_payment=principal_paid+interest_paid;
		           	//earned_interest*=i;					
		               if(temp_year === payment_date.getFullYear()) {
		          
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+ "<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+('FY'+payment_date.getFullYear())+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  
						   chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
												  
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						  						
					 }
					 temp_year=payment_date.getFullYear();
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
					 
			        }
			     
			     
			     outputHTML += "</table>";
				 	document.getElementById('total_payment').innerHTML = formatter.format(total_of_payment);
					document.getElementById('monthly_payment').innerHTML = formatter.format(amount);
					document.getElementById('total_interest').innerHTML = formatter.format(interest_paid);
					
				 	document.getElementById("dvTable").innerHTML = outputHTML;
				 	
									 
					 			 var xValues = ["principal", "Interest", "Total"];
								 var yValues = [amount, interest_paid, total_of_payment];
								 var barColors = ["#b91d47","#00aba9","#2b5797",];
								 
							 new Chart("pie_chart", {
								   type: "pie",
								   data: {
								     labels: xValues,
								     datasets: [{
								       backgroundColor: barColors,
								       data: yValues
								     }]
								   },
								  });
		 	
					   var principle_Data = {
							  label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							 barThickness: 60,
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							  barThickness: 60,
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							  barThickness: 60,
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
						new Chart("bar_chart", {
							  type: 'bar',
							  data: chartData,
							options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  // number (pixels) or 'flex'
							                maxBarThickness: 20 // number (pixels)
							            }]
							        }
							    }
							});
			
			     }
			        
				}		
						
		} 


function changeCalender(){
	var loan_amount = parseFloat(document.getElementById('loan_amount').value.replace(",", ""));
	var loan_terms = parseFloat(document.getElementById('loan_terms').value.replace(",", ""));
	var interest_rate = parseFloat(document.getElementById('interest_rate').value.replace(",", ""));
	var amount = loan_amount;
	var formatter = new Intl.NumberFormat('en-US', {
	  style: 'currency',
	  currency: 'INR',
	});
	
	if (!isNaN(loan_amount) && !isNaN(loan_terms)&& !isNaN(interest_rate)) {

		var is_yearly = true;
		var terms_type = document.getElementsByName('loan_terms_year_month');
		var loan_terms_in_months;
		
		if(terms_type[0].checked)
		{
			is_yearly = true;
			loan_terms_in_months = loan_terms * 12;
		}
		if(terms_type[1].checked)
		{
			is_yearly = false;
			loan_terms_in_months = loan_terms;
		}
		
   		var outputHTML="";
		  	outputHTML += "<table class='table text-center'>";
	        outputHTML += "<tr >";
	        outputHTML += "<th  style='text-align:center;background-color:lightskyblue'>"+" "+"Year"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#b91d47;text-align:center;'>"+" "+"Principle Amount<br>(A)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#00aba9;text-align:center;'>"+" "+"Interest Amount<br>(B)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#2b5797;text-align:center;'>"+" "+"Total Amount<br>(A+B)"+" "+"</th>";
	        outputHTML += "</tr>";
	        
		$("#datepicker").datepicker();
		var pick_date=$("#datepicker").datepicker('getDate');
		
		selectElement = document.querySelector('#select1');
		year_type=selectElement.value;
		
			if(year_type=='calendar_year'){
		
			 //printing months
			 	var temp_year;
			 	var total_of_payment;
			 	var chart_year=[];
			 	var chart_principle=[];
			 	var chart_interest=[];
			 	var chart_total=[];
			 		var first_payment_date = new Date(pick_date.getFullYear(), pick_date.getMonth(), pick_date.getDate());	
					var payment_date = first_payment_date; 
			 	for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount;
		             total_of_payment=principal_paid+interest_paid;
		           	// total_interest=interest_paid;					
		           	
		             if(temp_year === payment_date.getFullYear()) {
		          
                          outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 
		            		  	
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+ "<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+payment_date.getFullYear()+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  
						  chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
                          outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						
					 }
					 temp_year=payment_date.getFullYear();
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
					 
			        }
			        var xValues = ["principal", "Interest", "Total"];
								 var yValues = [amount, interest_paid, total_of_payment];
								 var barColors = ["#b91d47","#00aba9","#2b5797",];
								 
							 new Chart("pie_chart", {
								   type: "pie",
								   data: {
								     labels: xValues,
								     datasets: [{
								       backgroundColor: barColors,
								       data: yValues
								     }]
								   },
								  });
		 	
					   var principle_Data = {
							  label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							 barThickness: 60,
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							  barThickness: 60,
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							  barThickness: 60,
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
						new Chart("bar_chart", {
							  type: 'bar',
							  data: chartData,
							options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  // number (pixels) or 'flex'
							                maxBarThickness: 20 // number (pixels)
							            }]
							        }
							    }
							});
			
				}				
				
				
				else if(year_type=='finacial_year'){
						var month_list= [ "April", "May", "June", "July", "August", "September", 
							"October", "November", "December","January", "February", "March"];
							
			 //printing months
			 	var temp_year;
			 		var first_payment_date = new Date(pick_date.getFullYear(), pick_date.getMonth(), pick_date.getDate());	
					var payment_date = first_payment_date; 
			 	for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount ;
		             total_of_payment=principal_paid+interest_paid;
		           	//earned_interest*=i;					
		               if(temp_year === payment_date.getFullYear()) {
		          
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+ "<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+('FY'+payment_date.getFullYear())+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  
						   chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
												  
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						  						
					 }
					 temp_year=payment_date.getFullYear();
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
			        }
			         outputHTML += "</table>";
				 	document.getElementById('total_payment').innerHTML = formatter.format(total_of_payment);
					document.getElementById('monthly_payment').innerHTML = formatter.format(amount);
					document.getElementById('total_interest').innerHTML = formatter.format(interest_paid);
					
					 document.getElementById("dvTable").innerHTML = outputHTML;
			 
		 var xValues = ["principal", "Interest", "Total"];
		 var yValues = [principal_paid, interest_paid, total_of_payment];
		 var barColors = [
		   "#b91d47",
		   "#00aba9",
		   "#2b5797",
		  ];

		 new Chart("pie_chart", {
		   type: "pie",
		   data: {
		     labels: xValues,
		     datasets: [{
		       backgroundColor: barColors,
		       data: yValues
		     }]
		   },
		   options: {
		     title: {
		      
		       text: "Break-up of Total Amount", display: true,
		     }
		   }
		 });		 		 
					   var principle_Data = {
							  label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							 barThickness: 60,
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							  barThickness: 60,
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							  barThickness: 60,
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
						new Chart("bar_chart", {
							  type: 'bar',
							  data: chartData,
							options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  // number (pixels) or 'flex'
							                maxBarThickness: 20 // number (pixels)
							            }]
							        }
							    }
							});
						}	
				}
				 		
		} 
	

$(document).ready(function(){
$("#select1").change(function() {
	var loan_amount = parseFloat(document.getElementById('loan_amount').value.replace(",", ""));
	var loan_terms = parseFloat(document.getElementById('loan_terms').value.replace(",", ""));
	var interest_rate = parseFloat(document.getElementById('interest_rate').value.replace(",", ""));
	var amount = loan_amount;
	var formatter = new Intl.NumberFormat('en-US', {
	  style: 'currency',
	  currency: 'INR',
	});
	
	if (!isNaN(loan_amount) && !isNaN(loan_terms)&& !isNaN(interest_rate)) {

		var is_yearly = true;
		var terms_type = document.getElementsByName('loan_terms_year_month');
		var loan_terms_in_months;
		
		if(terms_type[0].checked)
		{
			is_yearly = true;
			loan_terms_in_months = loan_terms * 12;
		}
		if(terms_type[1].checked)
		{
			is_yearly = false;
			loan_terms_in_months = loan_terms;
		}
		$("#datepicker").datepicker();
		var pick_date=$("#datepicker").datepicker('getDate');
		var outputHTML="";
		  	outputHTML += "<table class='table text-center'>";
	        outputHTML += "<tr >";
	        outputHTML += "<th  style='text-align:center;background-color:lightskyblue'>"+" "+"Year"+" "+"</th>";
	         outputHTML += "<th  style='background-color:#b91d47;text-align:center;'>"+" "+"Principle Amount<br>(A)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#00aba9;text-align:center;'>"+" "+"Interest Amount<br>(B)"+" "+"</th>";
	        outputHTML += "<th  style='background-color:#2b5797;text-align:center;'>"+" "+"Total Amount<br>(A+B)"+" "+"</th>";
	        outputHTML += "</tr>";
		var first_payment_date = new Date(pick_date.getFullYear(), pick_date.getMonth(), pick_date.getDate());
		var payment_date = first_payment_date; 
		
		selectElement = document.querySelector('#select1');
		year_type=selectElement.value;
		
	if(year_type=='calendar_year'){
	
			 //printing months
			 	var temp_year;
			 	var chart_year=[];
			 	var chart_principle=[];
			 	var chart_interest=[];
			 	var chart_total=[];
			 	 for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount;
		             total_of_payment=principal_paid+interest_paid;
		           	// total_interest=interest_paid;					
		             if(temp_year === payment_date.getFullYear()) {
		          
                          outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 		            		  	
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+"<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+ payment_date.getFullYear()+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  
						  chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + payment_date.toLocaleString('default', { month: 'long' })+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						
					 }
					 temp_year=payment_date.getFullYear();
					 		   
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
			        }
				 	 outputHTML += "</table>";
				 	 document.getElementById('total_payment').innerHTML = formatter.format(total_of_payment);
					document.getElementById('monthly_payment').innerHTML = formatter.format(amount);
					document.getElementById('total_interest').innerHTML = formatter.format(interest_paid);
					 document.getElementById("dvTable").innerHTML = outputHTML;
					
			
							 
		 var xValues = ["principal", "Interest", "Total"];
		 var yValues = [principal_paid, interest_paid, total_of_payment];
		 var barColors = [
		   "#b91d47",
		   "#00aba9",
		   "#2b5797",
		  ];

		new Chart("pie_chart", {
		   type: "pie",
		   data: {
		     labels: xValues,
		     datasets: [{
		       backgroundColor: barColors,
		       data: yValues
		     }]
		   },
		   options: {
		     title: {
		      
		       text: "Break-up of Total Amount", display: true,
		     }
		   }
		 });

					   var principle_Data = {
							  label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							 barThickness: 30,
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							  barThickness: 30,
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							  barThickness: 30,
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
					new Chart("bar_chart", {
							  type: 'bar',
							  data: chartData,
							options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  // number (pixels) or 'flex'
							                maxBarThickness: 20 // number (pixels)
							            }]
							        }
							    }
							});
							
						

		} 
	
	else if(year_type=='finacial_year'){
			
		var month_list= [ "April", "May", "June", "July", "August", "September", 
							"October", "November", "December","January", "February", "March"];
							
			 //printing months
			 	var temp_year;
			 	var chart_year=[];
			 	var chart_principle=[];
			 	var chart_interest=[];
			 	var chart_total=[];
			 	for (var i = 1; i <= loan_terms_in_months; i++)
			        {
					 interest_paid = amount * interest_rate*i/100;
		             principal_paid = amount ;
		             total_of_payment=principal_paid+interest_paid;
		           	//earned_interest*=i;					
		               if(temp_year === payment_date.getFullYear()) {
		          
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
					 
		            		  	
					 } else {
						  outputHTML +="<tr bgcolor= 'gold' class='parent' title='Click to expand/collapse' onclick='expand("+payment_date.getFullYear()+")'  style='cursor:pointer'>";
						  outputHTML +="<td style='text-align:center;'>"+ "<img src='js/arrow-down-up.svg'/>&nbsp&nbsp"+('FY'+payment_date.getFullYear())+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(principal_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(interest_paid)+" "+"</td>";
						  outputHTML +="<td style='text-align:center;'>"+ formatter.format(total_of_payment)+" "+"</td>"; 
						  outputHTML += "</tr>";
						  
						  chart_year.push(payment_date.getFullYear());
						  chart_principle.push(amount);
						  chart_interest.push(interest_paid);
						  chart_total.push(total_of_payment);
						  
                         outputHTML += "<tr style='display:none;' class='child-row"+payment_date.getFullYear()+"'>";
			             outputHTML += "<td style='text-align:center;'>" + month_list[first_payment_date.getMonth(i)]+" "+ "</td>";
						 outputHTML += "<td style='text-align:center;'>" + formatter.format(principal_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(interest_paid) + " "+ "</td>";
					     outputHTML += "<td style='text-align:center;'>" + formatter.format(total_of_payment) + " "+ "</td>";
					     outputHTML += "</tr>";
						  
					 }
					 temp_year=payment_date.getFullYear();
					 
										
					 payment_date = new Date(first_payment_date.setMonth(first_payment_date.getMonth()+1));
			        }
				 	 outputHTML += "</table>";
				 	 document.getElementById('total_payment').innerHTML = formatter.format(total_of_payment);
					document.getElementById('monthly_payment').innerHTML = formatter.format(amount);
					document.getElementById('total_interest').innerHTML = formatter.format(interest_paid);
					 document.getElementById("dvTable").innerHTML = outputHTML;
				
 
		 var xValues = ["principal", "Interest", "Total"];
		 var yValues = [principal_paid, interest_paid, total_of_payment];
		 var barColors = [
		   "#b91d47",
		   "#00aba9",
		   "#2b5797",
		  ];

		new Chart("pie_chart", {
		   type: "pie",
		   data: {
		     labels: xValues,
		     datasets: [{
		       backgroundColor: barColors,
		       data: yValues
		     }]
		   },
		   options: {
		     title: {
		      
		       text: "Break-up of Total Amount", display: true,
		     }
		   }
		 });
		 
		   				var principle_Data = {
			   				 label: 'Principle amount',
							  data: chart_principle,
							  backgroundColor: '#b91d47',
							};
							 
							var interest_Data = {
							  label: 'Interest Earned',
							  data: chart_interest,
							  backgroundColor: '#00aba9',
							 };
							var total_Data = {
							  label: 'Total Earned',
							  data: chart_total,
							  backgroundColor: '#2b5797',
							};
							 
							var chartData= {
							  labels: chart_year,
							  datasets: [principle_Data, interest_Data,total_Data]
							};
							
						new Chart("bar_chart", {
							 type: 'bar',
							  data: chartData,
								options: {
							        scales: {
							            xAxes: [{
							                barThickness: 30,  
							                maxBarThickness: 20
							            }]
							        }
							    }
							});
		} 
	
 }
});
});