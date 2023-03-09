<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.epfo.passbook2.model.Balance"%>
<%@page import="com.epfo.passbook2.model.BarChart2"%>
<%@page import="java.util.ArrayList"%>
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

        <jsp:include page="/WEB-INF/views/nav.jsp" >
            <jsp:param name="navtoken" value="passbook" />
        </jsp:include>

        <%            Balance balance = (Balance) request.getAttribute("balance");
            BarChart2 barChart2 = (BarChart2) request.getAttribute("barChart2");
            boolean yearListFlag = true;
            ArrayList<String> yearList = (ArrayList<String>) request.getAttribute("yearList");

            if (yearList == null) {
                yearListFlag = false;
            }
            if (yearList.isEmpty()) {
                yearListFlag = false;
            }

            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM-yyyy");


        %>
        <div class="container content">
            <section>
                <div class="container py-5">
                    <div class="row">

                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/basic-details.png"> Passbook Overview
                                </div>
                                <div class="card-body">      
                                    <div class="row" style="padding: 10px">
                                        <table class="table table-bordered table-1">
                                            <thead>
                                                <tr>
                                                    <th scope="col" class="text-end texth">Total Balance</th>
                                                    <th scope="col" class="text-end texth">Employee Contribution</th>
                                                    <th scope="col" class="text-end texth">Employer Contribution</th>
                                                    <th scope="col" class="text-end texth">
                                                        Interest Earned
                                                        <a tabindex="0" 
                                                           data-bs-toggle="popover" 
                                                           title="Help"
                                                           data-bs-trigger="focus"
                                                           data-bs-content="Help content here"
                                                           >
                                                            <img height="15px" src="resources/images/icons/help.png">
                                                        </a>
                                                    </th>
                                                    <th scope="col" class="text-end texth">Transfer-Ins/VDR</th>
                                                    <th scope="col" class="text-end texth">Pension Contribution</th>
                                                    <th scope="col" class="text-end texth">Total PF Withdrawal</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getTotalBalance())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getEeShare())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getErShare())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getInterest())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getVdr())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getPenShare())%>
                                                    </td>
                                                    <td scope="row" class="text-end bold" style="vertical-align: middle">
                                                        <%=util.formatN(balance.getWith())%>
                                                    </td>
                                                </tr>  
                                                <tr>
                                                    <td colspan="6">
                                                        <i class="fa fa-info-circle"></i> 
                                                        Last Contribution made by for the month of <%=(balance.getLastWm() == null) ? "N/A" : sdf2.format(balance.getLastWm())%>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <br />
                                        <div style="min-height: 300px;margin-top: 20px;">
                                            <canvas id="pb_div_1">

                                            </canvas>
                                        </div>

                                    </div>


                                    <div class="row" style="padding: 10px; margin-top: 20px;">

                                        <%  if (yearListFlag) {
                                                int sr = 0;
                                                String active = "";
                                        %>


                                        <div class="col-md-12 v-tab-container">

                                            <table style="width: 100%">
                                                <tr>
                                                    <td style="width: 120px">
                                                        <div class="v-tab-menu">
                                                            <div class="list-group">
                                                                <% for (String yr : yearList) {
                                                                        active = "";
                                                                %>

                                                                <% if (sr == 0) {
                                                                        active = "active";
                                                                    }
                                                                %>
                                                                <a href="#" data-year="<%=yr%>"  class="list-group-item text-center <%=active%>">
                                                                    <%=yr%>
                                                                </a>

                                                                <% sr++;
                                                                    } %>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td style="vertical-align: top">

                                                        <div class="v-tab">

                                                            <%
                                                                sr = 0;
                                                                active = "";
                                                            %>

                                                            <% for (String yr : yearList) {
                                                                    active = "";
                                                            %>

                                                            <% if (sr == 0) {
                                                                    active = "active";
                                                                }
                                                            %>
                                                            <div class="v-tab-content <%=active%>">
                                                                <p>
                                                                    <%=yr%>
                                                                </p>
                                                            </div>

                                                            <% sr++;
                                                                } %>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>







                                        </div>


                                        <% }%>





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

<script>

    $(document).ready(function () {
        $("div.v-tab-menu>div.list-group>a").click(function (e) {
            e.preventDefault();
            $(this).siblings('a.active').removeClass("active");
            $(this).addClass("active");
            var index = $(this).index();
            $("div.v-tab>div.v-tab-content").removeClass("active");
            $("div.v-tab>div.v-tab-content").eq(index).addClass("active");
            //console.log($(this));

            var data = {};
            data["year"] = $(this).data().year;
            var url = '/<%=util.war.warname%>/passbook/api/ajax/get-passbook-data';
            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                url: url,
                data: data,
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    var result = data;
                    console.log(result);
                    if (result.status !== undefined) {
                        if (result.status === 0) {
                            //$("div.v-tab>div.v-tab-content").eq(index).html(JSON.stringify(result));
                            //console.log($("div.v-tab>div.v-tab-content").eq(index).html());
                        }
                        if (result.status === 1) {
                        }
                    } else {
                    }
                },
                error: function (x, t, m) {
                    if (t === "timeout") {
                    } else {
                        if (m.length > 0)
                        {
                            //var err = '<div class="alert alert-warning alert-dismissible fade show" role="alert">' + m + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';
                            //$('#div_page_msg').html(err);
                        }
                    }
                }
            });
        });
    });</script>


<% if (barChart2 != null) {
%>
<script>
    const ctx = document.getElementById('pb_div_1');
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
                    stacked: true,
                    display: true,
                    label: "Something"
                },
                y: {stacked: true}
            },
            maintainAspectRatio: false,
            legend: {position: 'bottom'}
        }
    });
</script>
<% }%>
