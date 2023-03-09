<%@page import="com.epfo.passbook2.common.util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="py-1" style="border-bottom: 1px solid #eeeeee; background-color: #fbfbfb;">            
    <div  class="container msewa">  
        <div class="row text-info" >
            <div class="col-md-8">
                <b>For EPF Balance enquiry</b> <%=util.msewa%>
            </div>

            <div class="col-md-4">
                <div class="float-end">
                    <b>Toll free No. </b> 1800
                </div>
            </div>
        </div>
    </div>
</div>


<div class="py-1">            

    <div class="container">
        <header>
            <div class="row">
                <div class="col-md-6">
                    <div class="logo">
                        <a href="/<%=util.war.warname%>" title="Home" rel="home" >
                            <img width="40px" class="national_emblem" src="<%=util.national_emblem%>" alt="national emblem">
                            <img width="80px" class="epfo_logo" src="<%=util.epfo_logo%>" alt="epfo">                                    
                        </a>                                
                        <p class="th1">कर्मचारी भविष्य निधि संगठन</p>
                        <p class="th2">Employees' Provident Fund Organisation</p>
                        <p class="th3">(श्रम एवं रोजगार मंत्रालय, भारत सरकार)</p>
                        <p class="th3">(Ministry of Labour & Employment, Govt. of India)</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="col-md-12 float-end">
                        <img style="height: 90px" class="swach float-end" src="<%=util.swach%>" alt="Swach Bharat">
                        <img height="105px" class="azadi float-end" src="<%=util.azadi%>" alt="Azadi ka Amrit Mahotsav">
                    </div>
                </div>
            </div>


        </header>
    </div>
</div>