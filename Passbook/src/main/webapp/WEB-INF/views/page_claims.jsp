<%@page import="com.epfo.passbook2.model.BarChart2"%>
<%@page import="com.epfo.passbook2.model.BarChart1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.epfo.passbook2.model.ClaimObj"%>
<%@page import="com.epfo.passbook2.model.Claims"%>
<%@page import="com.epfo.passbook2.model.UserModel"%>
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

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css">

    <jsp:include page="/WEB-INF/views/nav.jsp" >
        <jsp:param name="navtoken" value="claims" />
    </jsp:include>

    <%        //BarChart1 barChart1 = (BarChart1) request.getAttribute("barChart1");
        BarChart2 barChart2 = (BarChart2) request.getAttribute("barChart2");

        //System.out.println(barChart1.getData());
        ArrayList<ClaimObj.Pending> penList = new ArrayList();
        ArrayList<ClaimObj.Settled> sttList = new ArrayList();
        ArrayList<ClaimObj.Rejected> rejList = new ArrayList();

        ClaimObj claimObj = (ClaimObj) request.getAttribute("claimObj");

        Boolean isPenList = false;
        if (claimObj.getPenList() != null) {
            if (claimObj.getPenList().size() > 0) {
                isPenList = true;
            }
        }
        if (isPenList) {
            penList = claimObj.getPenList();
        }

        Boolean isSttList = false;
        if (claimObj.getSttList() != null) {
            if (claimObj.getSttList().size() > 0) {
                isSttList = true;
            }
        }
        if (isSttList) {
            sttList = claimObj.getSttList();
        }

        Boolean isRejList = false;
        if (claimObj.getRejList() != null) {
            if (claimObj.getRejList().size() > 0) {
                isRejList = true;
            }
        }
        if (isRejList) {
            rejList = claimObj.getRejList();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");


    %>
    <div class="container content">
        <section>
            <div class="container py-5">
                <div class="row">

                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> Claims Summary
                            </div>
                            <div class="card-body">      
                                <div class="row">
                                    <div style="padding: 10px">
                                        <table class="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col" class="text-end texth">Total Claims</th>
                                                    <th scope="col" class="text-end texth">Approved/Settled Claims</th>
                                                    <th scope="col" class="text-end texth">Rejected Claims</th>
                                                    <th scope="col" class="text-end texth">Pending/In-Process Claims</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td scope="row" class="text-end texth" style="vertical-align: middle">
                                                        <%=fun.formatN(claimObj.getTotalClaims())%>
                                                    </td>
                                                    <td class="text-end texth" style="vertical-align: middle">
                                                        <%=fun.formatN(claimObj.getSettled())%>
                                                        <hr class="hr">
                                                        <%=fun.formatN(claimObj.getSettledAmount())%>
                                                    </td>
                                                    <td class="text-end texth" style="vertical-align: middle">
                                                        <%=fun.formatN(claimObj.getRejected())%>
                                                    </td>
                                                    <td class="text-end texth" style="vertical-align: middle">
                                                        <%=fun.formatN(claimObj.getPending())%>                                                        
                                                    </td>
                                                </tr>                                            
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> Some Header
                            </div>
                            <div class="card-body">      
                                <div class="row">
                                    <canvas id="claims5_div">

                                    </canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <br />
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header cheader">
                                <img height="20px" src="resources/images/icons/basic-details.png"> Claims Details
                            </div>
                            <div class="card-body">      
                                <div class="row">

                                    <div id="tabs" class="ui-tabs ui-corner-all ui-widget ui-widget-content">
                                        <ul role="tablist" class="ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" >
                                            <li role="tab" tabindex="0" class="ui-tabs-tab ui-corner-top ui-state-default ui-tab" aria-controls="tabs-p" aria-labelledby="ui-id-1" aria-selected="true" aria-expanded="true">
                                                <a href="#tabs-p" tabindex="-1" class="ui-tabs-anchor" id="ui-id-1">Pending Claims [ <%=penList.size()%> ] </a>                                                    
                                            </li>
                                            <li role="tab" tabindex="0" class="ui-tabs-tab ui-corner-top ui-state-default ui-tab" aria-controls="tabs-s" aria-labelledby="ui-id-2" aria-selected="true" aria-expanded="true">
                                                <a href="#tabs-s" tabindex="-1" class="ui-tabs-anchor" id="ui-id-2">Settled Claims [ <%=sttList.size()%> ]</a>
                                            </li>
                                            <li role="tab" tabindex="0" class="ui-tabs-tab ui-corner-top ui-state-default ui-tab" aria-controls="tabs-r" aria-labelledby="ui-id-3" aria-selected="true" aria-expanded="true">
                                                <a href="#tabs-r" tabindex="-1" class="ui-tabs-anchor" id="ui-id-3">Rejected Claims [ <%=rejList.size()%> ]</a>
                                            </li>
                                        </ul>
                                        <div id="tabs-p" style="font-size: 13px;">                                           
                                            <%
                                                if (!isPenList) {
                                            %>
                                            <table id="table-cp" class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            No Claims pending..
                                                        </th>
                                                    </tr>
                                                </thead>
                                            </table>

                                            <% } else { %>
                                            <table id="table-cp" class="table table-bordered">
                                                <thead>
                                                    <tr style="vertical-align: middle" >
                                                        <th scope="col">#SR</th>
                                                        <th scope="col">CLAIM/TRACKING ID</th>
                                                        <th scope="col">RECEIPT DATE</th>
                                                        <th scope="col">MEMBER ID</th>
                                                        <th scope="col">FORM TYPE</th>
                                                        <th scope="col">CLAIM DESCRIPTION</th>
                                                        <th scope="col">OFFICE NAME</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%
                                                        int penSr = 1;
                                                        for (ClaimObj.Pending p : penList) {
                                                    %>
                                                    <tr style="vertical-align: middle" >
                                                        <td class="text-end"><%=penSr%></td>
                                                        <td><%=p.getTcId()%></td>
                                                        <td><%=(p.getReceiptDate() == null) ? "" : sdf.format(p.getReceiptDate())%> </td>
                                                        <td><%=p.getMemberId()%></td>
                                                        <td><%=p.getFormName()%></td>
                                                        <td><%=p.getFormDescription()%></td>
                                                        <td><%=p.getOfficeName()%></td>
                                                    </tr>   
                                                    <% penSr++;
                                                        }%>

                                                </tbody>
                                            </table>
                                            <% }%>
                                        </div>
                                        <div id="tabs-s">
                                            <div id="tabs-p" style="font-size: 13px;">                                           
                                                <%
                                                    if (!isSttList) {
                                                %>
                                                <table id="table-cp" class="table table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th>
                                                                No Claims Settled yet.
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                </table>

                                                <% } else { %>
                                                <table id="table-cp" class="table table-bordered">
                                                    <thead>
                                                        <tr style="vertical-align: middle" >
                                                            <th scope="col">#SR</th>
                                                            <th scope="col">CLAIM/TRACKING ID</th>
                                                            <th scope="col">RECEIPT DATE</th>
                                                            <th scope="col">MEMBER ID</th>
                                                            <th scope="col">FORM TYPE</th>
                                                            <th scope="col">CLAIM DESCRIPTION</th>
                                                            <th scope="col" class="text-end">TOTAL AMOUNT</th>
                                                            <th scope="col">APPROVED DATE</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                        <%
                                                            int sttSr = 1;
                                                            for (ClaimObj.Settled p : sttList) {
                                                        %>
                                                        <tr style="vertical-align: middle" >
                                                            <td class="text-end"><%=sttSr%></td>
                                                            <td><%=p.getTcId()%></td>
                                                            <td><%=(p.getReceiptDate() == null) ? "" : sdf.format(p.getReceiptDate())%> </td>
                                                            <td><%=p.getMemberId()%></td>
                                                            <td><%=p.getFormName()%></td>
                                                            <td><%=p.getFormDescription()%></td>
                                                            <td><%=util.formatN(p.getSettledAmount())%></td>
                                                            <td><%=(p.getApprovedDate() == null) ? "" : sdf.format(p.getApprovedDate())%> </td>
                                                        </tr>   

                                                        <% sttSr++;
                                                            }%>

                                                    </tbody>
                                                </table>
                                                <% }%>
                                            </div>
                                        </div>
                                        <div id="tabs-r">
                                            <div id="tabs-p" style="font-size: 13px;">                                           
                                                <%
                                                    if (!isRejList) {
                                                %>
                                                <table id="table-cp" class="table table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th>
                                                                No Claims rejected yet.
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                </table>

                                                <% } else { %>
                                                <table id="table-cp" class="table table-bordered">
                                                    <thead>
                                                        <tr style="vertical-align: middle" >
                                                            <th rowspan="2" scope="col">#SR</th>
                                                            <th scope="col">CLAIM/TRACKING ID</th>
                                                            <th scope="col">RECEIPT DATE</th>
                                                            <th scope="col">MEMBER ID</th>
                                                            <th scope="col">FORM TYPE</th>
                                                            <th scope="col">CLAIM DESCRIPTION</th>
                                                            <th scope="col">REJECT DATE</th>
                                                        </tr>
                                                        <tr style="vertical-align: middle" >                                                           
                                                            <th colspan="6" scope="col">REJECT REASON</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                        <%
                                                            int rejSr = 1;
                                                            for (ClaimObj.Rejected p : rejList) {
                                                        %>
                                                        <tr style="vertical-align: middle" >
                                                            <td rowspan="2"  class="text-end"><%=rejSr%></td>
                                                            <td><%=p.getTcId()%></td>
                                                            <td><%=(p.getReceiptDate() == null) ? "" : sdf.format(p.getReceiptDate())%> </td>
                                                            <td><%=p.getMemberId()%></td>
                                                            <td><%=p.getFormName()%></td>
                                                            <td><%=p.getFormDescription()%></td>
                                                            <td><%=p.getOfficeName()%></td>
                                                        </tr>   
                                                        <tr style="vertical-align: middle; color: lightcoral" >
                                                            <td colspan="6" ><%=p.getRejectReason()%></td>                                                            
                                                        </tr> 
                                                        <% rejSr++;
                                                            }%>

                                                    </tbody>
                                                </table>
                                                <% }%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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

<script language="javascript">

    $(function () {
        $("#tabs").tabs();
    });

</script>


<% if (barChart2 != null) {
%>
<script>
    const ctx = document.getElementById('claims5_div');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: <%=barChart2.getLabels()%>,
            datasets:
                    [
    <% for (BarChart2.DataSet ds : barChart2.getDataSetist()) {%>
                        {
                            label: '<%=ds.getLabel()%>',
                            backgroundColor: "<%=ds.getColor()%>",
                            data: <%=ds.getData()%>
                        },
    <% }%>
                    ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: '<%=barChart2.getText()%>'
                }
            },
            scales: {
                x: {
                    display: true,
                    label: "Something"
                }
            },
            maintainAspectRatio: false,
            legend: {position: 'bottom'}
        }
    });
</script>
<% }%>
