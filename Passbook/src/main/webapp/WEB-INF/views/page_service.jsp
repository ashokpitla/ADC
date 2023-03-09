<%@page import="com.epfo.passbook2.model.ServiceSummary"%>
<%@page import="com.epfo.passbook2.model.BarChart1"%>
<%@page import="com.epfo.passbook2.model.ServicePage"%>
<%@page import="java.util.Collections"%>
<%@page import="com.epfo.passbook2.model.ServiceData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.epfo.passbook2.model.MemberData"%>
<%@page import="java.time.Period"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.epfo.passbook2.common.util"%>
<%@page import="com.epfo.passbook2.common.fun"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/head1.jsp" %>

<html lang="en">
    <body>
        <%@include file="/WEB-INF/views/head_common.jsp" %>

        <jsp:include page="/WEB-INF/views/nav.jsp" >
            <jsp:param name="navtoken" value="service" />
        </jsp:include>

        <%            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

            SimpleDateFormat fmth = new SimpleDateFormat("MMM yyyy");

            ArrayList<ServicePage> servicePageList = (ArrayList<ServicePage>) request.getAttribute("servicePageList");
            BarChart1 barChart1 = (BarChart1) request.getAttribute("barChart1");
            ServiceSummary serviceSummary = (ServiceSummary) request.getAttribute("serviceSummary");

            String firstDoj = "";

            if (serviceSummary.getDoj() != null) {
                firstDoj = formatter.format(serviceSummary.getDoj());
            }

        %>


        <div class="container content">
            <section>
                <div class="container py-5">                   

                    <div class="row">

                        <div class="col-lg-4">
                            <div class="card">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/basic-details.png"> Service Overview
                                </div>
                                <div class="card-body">                                   
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <p class="mb-0 bold texth">Total Experience</p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=serviceSummary.getExperience()%></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <p class="mb-0 bold texth">Date of Joining</p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=firstDoj%></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5 ">
                                            <p class="mb-0 bold texth">Total NCP Days
                                                <a tabindex="0" 
                                                   data-bs-toggle="popover" 
                                                   title="Help"
                                                   data-bs-trigger="focus"
                                                   data-bs-content="Help content here"
                                                   >
                                                    <img height="15px" src="resources/images/icons/help.png">
                                                </a>
                                            </p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=serviceSummary.getNcp()%></p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <br />

                            <div class="card">
                                <div class="card-header cheader">
                                    <!--<img height="20px" src="resources/images/icons/basic-details.png"> Service Overview-->
                                </div>
                                <div class="card-body">                                   
                                    <div class="row">
                                        <canvas id="service_div">

                                        </canvas>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="col-lg-8">
                            <div class="card">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/basic-details.png"> Service History
                                </div>
                                <div class="card-body"> 

                                    <%

                                        Boolean serFlag = false;

                                        if (servicePageList != null) {
                                            if (servicePageList.size() > 0) {
                                                serFlag = true;
                                            }
                                        }

                                        if (!serFlag) { %>
                                    <p>
                                        No service details available!
                                    </p>
                                    <% } else { %>


                                    <ul class="timeline">
                                        <%

                                            //for (MemberData md : memberDataList) {
                                            String balance = "";
                                            String claims = "";
                                            String transfer = "";

                                            //ArrayList<ServiceData> serList = md.getServiceList();
                                            int i = 1;
                                            int totalSer = servicePageList.size();

                                            for (ServicePage sh : servicePageList) {


                                        %>



                                        <li class="timeline-item mb-3">
                                            <p class="tlp <%=sh.getHeaderClass()%>" ><%=sh.getHeader()%></p>
                                            <p class="text-muted mb-2 bold"><%=sh.getEstName()%></p>
                                            <div class="row" >
                                                <div class="col-md-6" >
                                                    <div class="row" >
                                                        <div class="col-md-4" >
                                                            <p class="mb-0">Est Id</p>
                                                        </div>
                                                        <div class="col-md-8" >
                                                            <p class="mb-0 text-md-start"><%=sh.getEstId()%></p>
                                                        </div>
                                                    </div>
                                                    <div class="row" >
                                                        <div class="col-md-4" >
                                                            <p class="mb-0">Member Id</p>
                                                        </div>
                                                        <div class="col-md-8" >
                                                            <p class="mb-0 text-md-start"><%=sh.getMemberId()%></p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6" >
                                                    <div class="row" >
                                                        <div class="col-md-4" >
                                                            <p class="mb-0">Joining Date</p>
                                                        </div>
                                                        <div class="col-md-8" >
                                                            <p class="mb-0 text-md-start"><%=sh.getDoj() != null ? formatter.format(sh.getDoj()) : ""%></p>
                                                        </div>
                                                    </div>
                                                    <div class="row" >
                                                        <div class="col-md-4" >
                                                            <p class="mb-0">Exit Date</p>
                                                        </div>
                                                        <div class="col-md-8" >
                                                            <p class="mb-0 text-md-start"><%=sh.getDoe() != null ? formatter.format(sh.getDoe()) : ""%></p>
                                                        </div>
                                                    </div>
                                                    <div class="row" >
                                                        <div class="col-md-4" >
                                                            <p class="mb-0">Total Service</p>
                                                        </div>
                                                        <div class="col-md-8" >
                                                            <p class="mb-0 text-md-start"><%=sh.getTotalServiceString()%></p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <hr class="hr"/>

                                        <% } %>


                                    </ul>


                                    <% }%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                    </div>
                </div>
            </section>
        </div>
        <%@include file="/WEB-INF/views/foot1_common.jsp" %>
    </body>
</html>

<%@include file="/WEB-INF/views/foot2.jsp" %>

<% if (barChart1 != null) {

%>
<script>
    const ctx = document.getElementById('service_div');

    var h =<%=barChart1.getCount()%> * 75;

    $('#service_div').css('min-height', '200px');
    $('#service_div').css('height', h + 'px');


    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: <%=barChart1.getLabels()%>,
            datasets: [{
                    label: '<%=barChart1.getLabel()%>',
                    data: <%=barChart1.getData()%>,
                    borderWidth: 1,
                    backgroundColor: '<%=barChart1.getColor().toUpperCase()%>'
                }]
        },
        options: {
            indexAxis: 'y',
            elements: {
                bar: {
                    borderWidth: 2
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: '<%=barChart1.getText()%>'
                }
            }
        }
    });
</script>
<% }%>