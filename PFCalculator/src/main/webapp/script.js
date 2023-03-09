
			$(document).ready(function(){
								
				$("#cal").click(function(){
					
					var principle=$("#pamount").val();
					var principle1=$("#pamount").val();
					var rate=$("#iamount").val();
					var tenure=$("#tenure").val();
					var check="";
					var total;
					var interest;
					//get year
					const event = new Date();
					event.setMonth(parseInt(tenure));
					year1=event.getFullYear();
					
							       
					var radioValue = $("input[name='tenure']:checked").val();
					var t = document.getElementsByName('tenure');
					for(i = 0; i < t.length; i++) {
						if(t[i].checked)
						check= t[i].value;
					}
					if (principle=="") {
			            alert("Please Enter Principle Amount");
			            document.getElementById('pamount').focus();
			            return false;
			        }else if(rate==""){
			        	 alert("Please Enter interest Amount");
				            document.getElementById('iamount').focus();
				            return false;
			        }
			        else if(tenure==""){
			        	 alert("Please Enter tenure ");
				            document.getElementById('tenure').focus();
				            return false;
			        }
				     else if(!radioValue){
					 alert("Please select tenure ");
						 document.getElementByName('tenure').focus();
					 	 return false;
					 }
					
					   var outputHTML = "";
					    outputHTML += "<table class='table table-striped text-center'>";
				        outputHTML += "<tr>";
				        outputHTML += "<th  style='text-align:center;'>"+" "+"Year"+" "+"</th>";
				        outputHTML += "<th  style='background-color:DarkSlateBlue;text-align:center;'>"+" "+"Principle Amount<br>(A)"+" "+"</th>";
				        outputHTML += "<th  style='background-color:MediumSeaGreen;text-align:center;'>"+" "+"Interest Amount<br>(B)"+" "+"</th>";
				        outputHTML += "<th  style='background-color:Salmon;text-align:center;'>"+" "+"Total Amount<br>(A+B)"+" "+"</th>";
				        outputHTML += "</tr>";
				        
				   
				        
					   if(check=='month'){
				    	 for(var i=1;i<=tenure;i++){
				    		 interest=(parseFloat(principle)*parseFloat(rate)/100);
								total=interest+parseFloat(principle);
								
								
								principle=total;
								
				    	 }
						
					}
					
				     else if(check=='year'){
				    	 for(var i=1;i<=tenure;i++){
				    		 interest=(parseFloat(principle)*parseFloat(rate)*12/100);
								total=interest+parseFloat(principle);
								
								principle=total;
				    	 }
						
					}    
					     	
					var total1=parseInt(principle1)+parseInt(interest);
				    var amountArray = [year1,principle1, parseInt(interest), total1];
				       
				    // loop over our arrays and create our html string
				       
				        outputHTML += "<tr>";
					
					
					for (var i = 0; i <amountArray.length; i++) {
			              outputHTML += "<td style='text-align:center;'>" + amountArray[i] + " "+ "</td>";
		             }
			        outputHTML += "</tr>";
			        outputHTML += "</table>";
			        document.getElementById("output_div").innerHTML = outputHTML;
					
			   
			        // output our html
			   
										
					$("#amount").text(principle1);
					$("#earned").text(parseInt(interest));
					$("#total").text(parseInt(principle1)+parseInt(interest));
					
					
			//to show the pie chart of amount calculations
					
					var chart = new CanvasJS.Chart("chartContainer", {
						theme: "light2",
													
						data: [{
							type: "pie",
							showInLegend: true,
							legendText: "{label}",
							toolTipContent: "{label}: <strong>{y}</strong>",
							indexLabel: "{label} Rs. {y}",
							dataPoints: [
								{ label: "Principal Amount", y: parseFloat(principle1) },
								{ label: "Interest Payable", y: parseInt(interest) },
								{ label: "Total Payment", y: parseInt(principle1)+parseInt(interest) },
								
							]
						}]
					});
					 
					chart.render();
					
				// to show the column chart	
										 					 
					 var chart = new CanvasJS.Chart("chartContainer1", {
							theme: "light2",
														
							data: [{
								type: "stackedColumn",
								showInLegend: true,
								legendText: "{label}",
								toolTipContent: "{label}: <strong>{y}</strong>",
								indexLabel: "{label} Rs. {y}",
								dataPoints: [
									{ label: "Principal Amount", y: parseFloat(principle1) },
									{ label: "Interest Payable", y: parseInt(interest) },
									{ label: "Total Payment", y: parseInt(principle1)+parseInt(interest) },
									
								]
							}]
						});
						 
						chart.render();
					 
					 
					
				});
				
				
		//onchange function for selection year wise
				$("#select1").change(function() {
				var inputDate = document.getElementById( 'datepicker' ).value;
				var d = new Date( inputDate );
				var tenure=$("#tenure").val();
				year = d.getFullYear();
				
				month = d.getMonth();
				const monthNames = ["January", "February", "March", "April", "May", "June",
						  "July", "August", "September", "October", "November", "December"];
				const fnmonthNames = [ "April", "May", "June", "July", "August", "September", 
							"October", "November", "December","January", "February", "March"];
								
					var principle=$("#pamount").val();
					var principle1=$("#pamount").val();
					var rate=$("#iamount").val();
				
					var total;
					var interest;
					var radioValue = $("input[name='tenure']:checked").val();
					var t = document.getElementsByName('tenure');
					for(i = 0; i < t.length; i++) {
						if(t[i].checked)
						check= t[i].value;
					}
				
				
				selectElement = document.querySelector('#select1');
		        output = selectElement.value;
		          var outputHTML = "";
		          
		           // loop over our arrays and create our html string
				        outputHTML += "<table class='table table-striped text-center'>";
				        outputHTML += "<tr>";
				        outputHTML += "<th  style='text-align:center;'>"+" "+"Year"+" "+"</th>";
				        outputHTML += "<th  style='background-color:DarkSlateBlue;text-align:center;'>"+" "+"Principle Amount<br>(A)"+" "+"</th>";
				        outputHTML += "<th  style='background-color:MediumSeaGreen;text-align:center;'>"+" "+"Interest Amount<br>(B)"+" "+"</th>";
				        outputHTML += "<th  style='background-color:Salmon;text-align:center;'>"+" "+"Total Amount<br>(A+B)"+" "+"</th>";
				        outputHTML += "</tr>";
				       
				         if(output=='calendar'){
						   for(var i=0;i<=tenure;i++){
						   tenureYear=year+parseInt(i);
							interest=principle*rate/100;
							total=parseInt(interest)+parseInt(principle);
							
						   outputHTML += "<tr>";
				           outputHTML += "<td style='text-align:center;'>" + tenureYear + " "+ "</td>";
				           outputHTML += "<td style='text-align:center;'>" + principle + " "+ "</td>";
			               outputHTML += "<td style='text-align:center;'>" + interest + " "+ "</td>";
			               outputHTML += "<td style='text-align:center;'>" + total + " "+ "</td>";
			               outputHTML += "</tr>";
			              
			               for(var j=0;j<monthNames.length;j++){
							   var k=1;
							   interest1=principle*rate*k/100;
								total1=parseInt(interest1)+parseInt(principle);
							     outputHTML += "<tr >";
							     outputHTML += "<td>"+monthNames[j]+"</td>";
							     outputHTML += "<td>"+principle+"</td>";
								 outputHTML += "<td>"+interest1+"</td>";
								 outputHTML += "<td>"+total1+"</td>";
							     outputHTML += "</tr>";
							      principle=parseInt(principle)+parseInt(interest1);
							      k++;
							      total=total1;
							     
						   }
						 
			               principle=parseInt(principle)+parseInt(interest);
		              	  }
				  
				   outputHTML += "</table>";
				   document.getElementById("output_div").innerHTML = outputHTML;
			
				 
				   }
				   
			    if(output=='finacial'){
				 for(var i=0;i<=tenure;i++){
					tenureYear=year+parseInt(i);
					interest=principle*rate*12/100;
					total=parseInt(interest)+parseInt(principle);
					 outputHTML += "<tr>";
					 outputHTML += "<td style='text-align:center;'>" + ('FY'+tenureYear) + " "+ "</td>";
			         outputHTML += "<td style='text-align:center;'>" +principle  + " "+ "</td>";
			         outputHTML += "<td style='text-align:center;'>" + interest + " "+ "</td>";
			         outputHTML += "<td style='text-align:center;'>" +  total+ " "+ "</td>";
					  outputHTML += "</tr>";
					   for(var j=0;j<fnmonthNames.length;j++){
							   var k=1;
							   interest1=principle*rate*k/100;
								total1=parseInt(interest1)+parseInt(principle);
							     outputHTML += "<tr>";
							     outputHTML += "<td>"+fnmonthNames[j]+"</td>";
							        outputHTML += "<td>"+principle+"</td>";
									outputHTML += "<td>"+interest1+"</td>";
									outputHTML += "<td>"+total1+"</td>";

							     outputHTML += "</tr>";
							      principle=parseInt(principle)+parseInt(interest1);
							      k++;
							      total=total1;
							     
						   }
					  principle=parseInt(principle)+parseInt(interest);
				  }
				   outputHTML += "</table>";
				   document.getElementById("output_div").innerHTML = outputHTML;
			
			}
			//show the chart
			 var chart = new CanvasJS.Chart("chartContainer1", {
							theme: "light2",
														
							data: [{
								type: "stackedColumn",
								showInLegend: true,
								legendText: "{label}",
								toolTipContent: "{label}: <strong>{y}</strong>",
								indexLabel: "{label} Rs. {y}",
								dataPoints: [
									{ label: "Principal Amount", y: principle1 },
									{ label: "Interest Payable", y: parseInt(interest) },
									{ label: "Total Payment", y: parseInt(principle1)+parseInt(interest) },
									
								]
							}]
						});
						 
						chart.render();
				
			});
				
	});
			