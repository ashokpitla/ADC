<%@page import="com.epfo.passbook2.model.UserData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/head1.jsp" %>

<html lang="en">

    <style>

    </style>

    <body>
        <%@include file="/WEB-INF/views/head_common.jsp" %>

        <jsp:include page="/WEB-INF/views/nav2.jsp" >
            <jsp:param name="navtoken" value="get_data" />
        </jsp:include>

        <%            UserData userData1 = (UserData) session.getAttribute(util.session.userData);
        %>

        <div class="container content" style="border-top: solid 1px #eeeeee; padding-top: 50px">

            <section>
                <div class="container py-5">                   
                    <div class="row" style="text-align: center">
                        <div id="overlay">
                            <div>
                                <i class="fa fa-spinner fa-spin fa-3x" ></i>
                            </div>
                            <div id="progstat"></div>
                            <div id="progress"></div>
                        </div>
                        <div id="err" >
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

    var c = 0;
    var tot = 3;
    var ok = 0;

    var ok = true;
    var ovrl = id("overlay");
    var prog = id("progress");
    var stat = id("progstat");
    //var img = document.images;

    var perc = "0%";
    prog.style.width = perc;
    stat.innerHTML = "Loading " + perc;

    $(document).ready(function () {

        var url;
        var _status, _html, _message;
        var d = {};

        // Get Member List - 1
        d["username"] = <%=userData1.getUan()%>;
        url = '/<%=util.war.warname%>/passbook/api/ajax/get-uan-mid';

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: url,
            data: d,
            dataType: 'json',
            timeout: 100000,
            //async: false,
            success: function (data) {
                var result = data;
                //console.log('MID ' + JSON.stringify(result));
                _status = result.status;
                _message = result.message;

                c++;
                perc = Math.round(100 * c / tot) + "%";
                prog.style.width = perc;
                stat.innerHTML = "Loading " + perc;

                if (_status === 1) {
                    //ok = '1';
                    page_error(_message);
                    return;
                }
                //ok = ok + 1;
                clm();
            },
            error: function (x, t, m) {
                //ok = '1';
                if (m.length > 0)
                {
                    page_error();
                } else {
                    page_error('un-specified error');
                }
                return;
            }});
        // Get Member List - 1

    });

    function id(v) {
        return document.getElementById(v);
    }


    function doneLoading() {
        ovrl.style.opacity = 0;
        setTimeout(function () {
            ovrl.style.display = "none";
        }, 1200);
        window.location.replace('home');
    }

    function page_error(m)
    {
        var h = '<div class="alert alert-warning alert-dismissible fade show" role="alert"><strong>' + m + '</div>';
        var ovrl = id("overlay");
        ovrl.style.display = "none";
        if ($('#err').length) {
            $('#err').html(h);
        }

    }

    function clm() {

        var url;
        var _status, _html, _message;
        var d = {};

        // Get Claims Data - 2
        d = {};
        d["username"] = <%=userData1.getUan()%>;
        url = '/<%=util.war.warname%>/passbook/api/ajax/get-uan-claims';

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: url,
            data: d,
            dataType: 'json',
            timeout: 100000,
            //async: false,
            success: function (data) {
                var result = data;
                console.log('CLM ' + JSON.stringify(result));
                _status = result.status;
                _message = result.message;

                c++;
                perc = Math.round(100 * c / tot) + "%";
                prog.style.width = perc;
                stat.innerHTML = "Loading " + perc;

                if (_status === 1) {
                    page_error(_message);
                    return;
                }
                psb();
            },
            error: function (x, t, m) {
                //ok = '1';
                if (m.length > 0)
                {
                    page_error();
                } else {
                    page_error('un-specified error');
                }
                return;
            }});
        // Get Claims Data - 2

    }


    function psb() {

        var url;
        var _status, _html, _message;
        var d = {};

        // Get Claims Data - 2
        d = {};
        d["username"] = <%=userData1.getUan()%>;
        url = '/<%=util.war.warname%>/passbook/api/ajax/get-uan-passbook';

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: url,
            data: d,
            dataType: 'json',
            timeout: 100000,
            //async: false,
            success: function (data) {
                var result = data;
                console.log('PSB ' + JSON.stringify(result));
                _status = result.status;
                _message = result.message;

                c++;
                perc = Math.round(100 * c / tot) + "%";
                prog.style.width = perc;
                stat.innerHTML = "Loading " + perc;

                if (_status === 1) {
                    page_error(_message);
                    return;
                }
                doneLoading();
            },
            error: function (x, t, m) {
                //ok = '1';
                if (m.length > 0)
                {
                    page_error();
                } else {
                    page_error('un-specified error');
                }
                return;
            }});
        // Get Claims Data - 2

    }


</script>