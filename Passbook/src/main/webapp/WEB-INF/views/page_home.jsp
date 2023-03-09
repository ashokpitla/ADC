<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.epfo.passbook2.model.ServicePage"%>
<%@page import="com.epfo.passbook2.model.UserData"%>
<%@page import="com.epfo.passbook2.model.Balance"%>
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

    <link rel="stylesheet" type="text/css" href="resources/css/load.css">

    <jsp:include page="/WEB-INF/views/nav.jsp" >
        <jsp:param name="navtoken" value="home" />
    </jsp:include>


    <%        //UserData userData1 = (UserData) session.getAttribute(util.session.userData);
        Balance balance = (Balance) request.getAttribute("balance");
        ServicePage servicePage = (ServicePage) request.getAttribute("servicePage");

        Boolean serData = true;
        if (servicePage == null) {
            serData = false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");


    %>


    <div class="container content">
        <section>
            <div class="container py-5">   

                <div class="row">

                    <div class="col-lg-7">
                        <div class="card mb-4">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> EPF Contribution Summary
                            </div>

                            <div class="card-body cbody10">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row" style="font-size: 15px; font-weight: 700;">
                                            <div class="col-sm-9">
                                                <p class="mb-0">Total Available Balance</p>
                                            </div>
                                            <div class="col-sm-3">
                                                <p class="mb-0 float-end"><%=fun.formatN(balance.getTotalBalance())%></p>
                                            </div>
                                        </div>
                                        <hr class="hr1" style="margin-bottom: 20px">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <!--<div class="chart-container" style="position: relative; height:40vh; width:50vw">-->
                                        <canvas id="balance_div"></canvas>
                                        <!--</div>-->  
                                    </div>
                                    <div class="col-md-7">
                                        <div class="row">
                                            <div class="col-sm-9">
                                                <p class="mb-0">Employee Contribution</p>
                                            </div>
                                            <div class="col-sm-3">
                                                <p class="mb-0 float-end"><%=fun.formatN(balance.getEeShare())%></p>
                                            </div>
                                        </div>
                                        <hr class="hr1">
                                        <div class="row">
                                            <div class="col-sm-9">
                                                <p class="mb-0">Employer Contribution</p>
                                            </div>
                                            <div class="col-sm-3">
                                                <p class="mb-0 float-end"><%=fun.formatN(balance.getErShare())%></p>
                                            </div>
                                        </div>
                                        <hr class="hr1">
                                        <div class="row">
                                            <div class="col-sm-9">
                                                <p class="mb-0">Interest Earned 
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
                                            <div class="col-sm-3">
                                                <p class="mb-0 float-end"><%=fun.formatN(balance.getInterest())%></p>
                                            </div>
                                        </div>
                                        <hr class="hr1">
                                    </div>
                                </div>     
                            </div>

                        </div>
                    </div>

                    <div class="col-lg-5">
                        <div class="card mb-4">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> Your Requests
                            </div>

                            <div class="card-body cbody10">
                                <p>
                                    No request pending
                                </p>
                                <!--<a tabindex="0" class="btn btn-lg btn-danger" role="button" data-bs-toggle="popover" data-bs-trigger="focus" title="Dismissible popover" data-bs-content="And here's some amazing content. It's very engaging. Right?">Dismissible popover</a>-->
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-lg-7">
                        <div class="card mb-4">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> Current Establishment Details
                            </div>

                            <div class="card-body cbody10">
                                <div class="row">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0 bold texth">Est. Name</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=(servicePage.getEstName() == null) ? "N/A" : servicePage.getEstName()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0 bold texth">Est. Id</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=(servicePage.getEstId() == null) ? "N/A" : servicePage.getEstId()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">

                                    <div class="row">
                                        <div class="col-sm-3 texth">
                                            <p class="mb-0 bold">Member Id</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=(servicePage.getMemberId() == null) ? "N/A" : servicePage.getMemberId()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">

                                    <div class="row">
                                        <div class="col-sm-3 texth">
                                            <p class="mb-0 bold">Date of Joining</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=(servicePage.getDoj() == null) ? "N/A" : sdf.format(servicePage.getDoj())%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">

                                    <div class="row">
                                        <div class="col-sm-3 texth">
                                            <p class="mb-0 bold">Experience</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=(servicePage.getTotalServiceString() == null) ? "N/A" : servicePage.getTotalServiceString()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                </div>
                                <a href="/<%=util.war.warname%>/service-history">View Past Service History</a>
                            </div>


                        </div>
                    </div>
                </div>
        </section>
    </div>
    <%@include file="/WEB-INF/views/foot1_common.jsp" %>
</body>
</html>

<%@include file="/WEB-INF/views/foot2.jsp" %>

<script>
    const ctx = document.getElementById('balance_div');

    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Employee Contribution', 'Employer Contribution', 'Interest Earned'],
            datasets: [{
                    label: '',
                    data: [125000, 100000, 36000],
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: ''
                }
            }
        }
    });
</script>

