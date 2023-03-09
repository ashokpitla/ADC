<%@page import="com.epfo.passbook2.common.Captcha"%>
<%@page import="com.epfo.passbook2.common.util"%>
<%@page import="com.epfo.passbook2.common.fun"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/head_outer.jsp" %>


<html lang="en">

    <%        String systemMessage = (String) request.getAttribute("systemMessage");
        String loadMessage = (String) request.getAttribute("loadMessage");
        Captcha captcha = (Captcha) request.getAttribute("captcha");

        System.out.println(captcha);

        String cap = "/" + util.war.warname + "/resources/images/refresh.png";

        String slide1 = "/" + util.war.warname + "/resources/images/slide/short-code-missed-call.jpg";
        String slide2 = "/" + util.war.warname + "/resources/images/slide/nidhinikat.jpg";

        if (systemMessage.isEmpty()) {
            systemMessage = loadMessage;
        }

        String pageError = (String) request.getAttribute("pageError");

        String _timeout = "";

        if (pageError != null && pageError.length() > 0) {
            _timeout = "<div class=\"alert alert-danger alert-dismissible show\" role=\"alert\"><strong> Error : " + pageError + " </strong><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button></div>";
        }


    %>

    <body>


        <%@include file="/WEB-INF/views/head_common.jsp" %>

        <div class="row" style="margin-left: 0; margin-right: 0">
            <div style="height: 48px" class="topheader" >

            </div> 
        </div>



        <div class="container content" style="max-width: 1290px; border-left: solid 1px #dee2e6 ;border-right: solid 1px #dee2e6;">
            <section>

                <div class="container py-1" >
                    <div class="row d-flex justify-content-center align-items-center">
                        <div class="col-xl-10">
                            <div id="div_page_msg" style="padding-left: 30px; padding-right: 30px; margin-top: 20px;">                        
                                <% if (!_timeout.isEmpty()) {%>
                                <%=_timeout%>
                                <% } %>
                            </div>
                        </div>
                    </div>


                </div>


                <div class="container py-1" style="min-height: 600px">
                    <div class="row d-flex justify-content-center align-items-center h-100 mt-5">
                        <div class="col-xl-11">

                            <div class="row g-0">

                                <div class="col-lg-4">

                                    <div class="card rounded-3 text-black">        
                                        <div class="card-body p-md-5 mx-md-4">


                                            <form>
                                                <p>Please login to your account</p>

                                                <div class="form-floating mb-4">
                                                    <input type="text" id="username" class="form-control"
                                                           maxlength="12"
                                                           placeholder="Phone number or email address" />
                                                    <label class="form-label" for="uan">UAN</label>
                                                </div>

                                                <div class="form-floating mb-4">
                                                    <input type="password" id="password" class="form-control" 
                                                           maxlength="100"
                                                           placeholder="Password" />
                                                    <label class="form-label" for="password">Password</label>
                                                </div>

                                                <% if (systemMessage.isEmpty()) {%>

                                                <div class="form-group mb-4">                                           
                                                    <img id="captcha_id" name="imgCaptcha" src="data:image/jpg;base64,<%=captcha.getImage64()%>">
                                                    <a href="#" > <img id="new_cap" width="20px" name="newcap" src="<%=cap%>"></a>
                                                </div>

                                                <div class="form-group mb-4">
                                                    <input id="captcha" class="form-control" name="captcha" value="11111" maxlength="5" type="text" style="width: 120px" >
                                                </div>

                                                <div class="text-center pt-1 pb-1">
                                                    <button id="login" class="btn btn-success btn-block fa-md mb-3"                                                             
                                                            style="width: 100%; height: 50px" type="button">
                                                        Sign in <i class="fa fa-sign-in" aria-hidden="true"></i></button>
                                                    <a class="text-muted" href="#!">Forgot password?</a>
                                                </div>

                                                <% }%>


                                            </form>

                                        </div>
                                    </div>


                                </div>

                                <div class="col-lg-7 rightpanel" style="margin-left: 80px">

                                    <div class="card ">
                                        <div class="card-header" style="background-color: #0dcaf0; color: white">
                                            Featured
                                        </div>
                                        <div class="card-body">
                                            <ul>
                                                <li>
                                                    1. This facility is to view the Member Passbook for the members registered on the Unified Member Portal.
                                                </li>
                                                <li>
                                                    2. Passbook will be available after 6 Hours of registration at Unified Member Portal.
                                                </li>
                                                <li>
                                                    3. Changes in the credentials at Unified Member Portal will be effective at this Portal after after 6 Hours.
                                                </li>
                                                <li>
                                                    4. Passbook will have the entries which has been reconciled at the EPFO field offices.
                                                </li>
                                                <li>
                                                    5. Passbook facility not be available for the Exempted Establishments Members.
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <br />
                                    <div class="row">

                                        <div id="logincarousel" class="carousel slide" data-bs-ride="carousel" data-interval="10000">

                                            <div class="carousel-indicators">
                                                <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
                                                <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
                                                <!--<button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>-->
                                            </div>

                                            <div class="carousel-inner">
                                                <div class="carousel-item active">
                                                    <img src="<%=slide1%>" alt="EPF" class="d-block" style="height: 240px;">
                                                </div>
                                                <div class="carousel-item">
                                                    <img src="<%=slide2%>" alt="EPF" class="d-block" style="height: 240px">
                                                </div>
                                                <!--                                                <div class="carousel-item">
                                                                                                    <img src="https://www.w3schools.com/bootstrap5/ny.jpg" alt="New York" class="d-block" style="width:100%">
                                                                                                </div>-->
                                            </div>

                                            <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
                                                <span class="carousel-control-prev-icon"></span>
                                            </button>
                                            <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
                                                <span class="carousel-control-next-icon"></span>
                                            </button>
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

<%@include file="/WEB-INF/views/foot_outer.jsp" %>



<script type="text/javascript">


    $(document).ready(function () {

        $('#username').on('keypress', function (e) {
            if (e.which === 13) {
                checkLogin($('#login'));
            }
        });
        $('#password').on('keypress', function (e) {
            if (e.which === 13) {
                checkLogin($('#login'));
            }
        });
        $('#captcha').on('keypress', function (e) {
            if (e.which === 13) {
                checkLogin($('#login'));
            }
        });
        $("#login").click(function () {
            checkLogin($('#login'));
        });

        $("#new_cap").click(function () {
            getcap();
        });

    });

    (function ($) {
        $.fn.button = function (action) {
            if (action === 'loading' && this.data('loading-text')) {
                this.data('original-text', this.html()).html(this.data('loading-text')).prop('disabled', true);
            }
            if (action === 'reset' && this.data('original-text')) {
                this.html(this.data('original-text')).prop('disabled', false);
            }
        };
    }(jQuery));

    function checkLogin(btn) {
        var $this = btn;
        //var $this = $(this);

        $("#login").html('Sign in <i class="fa fa-spinner fa-spin" aria-hidden="true">');

        $('#div_page_msg').html('');
        if (chkDigit() === false)
        {
            return;
        }
        $this.button('loading');
        var data = {};
        data["username"] = $("#username").val();
        data["password"] = $("#password1").val();
        data["answer"] = $("#captcha").val();

        var url = '/<%=util.war.warname%>/passbook/api/ajax/checkLogin';
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: url,
            data: data,
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                $this.button('reset');
                var result = data;

                console.log(result);

                if (result.status !== undefined) {
                    if (result.status === 0) {
                        //$("#login").html('Sign in <i class="fa fa-sign-in" aria-hidden="true">');
                        setTimeout("page_Redirect()", 50);
                    }
                    if (result.status === 1) {
                        //$this.button('reset');
                        $("#login").html('Sign in <i class="fa fa-sign-in" aria-hidden="true">');
                        var err = '<div class="alert alert-warning alert-dismissible fade show" role="alert">' + result.message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';
                        $('#div_page_msg').html(err);
                        getcap();
                    }
                } else {
                    //$this.button('reset');
                    $("#login").html('Sign in <i class="fa fa-sign-in" aria-hidden="true">');
                    var err = '<div class="alert alert-warning alert-dismissible fade show" role="alert">Invalid output<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';
                    $('#div_page_msg').html(err);
                    getcap();
                }
            },
            error: function (x, t, m) {
                //$this.button('reset');
                $("#login").html('Sign in <i class="fa fa-sign-in" aria-hidden="true">');
                if (t === "timeout") {
                } else {
                    if (m.length > 0)
                    {
                        var err = '<div class="alert alert-warning alert-dismissible fade show" role="alert">' + m + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';
                        $('#div_page_msg').html(err);
                        getcap();
                    }
                }
            }
        });
    }


    function page_Redirect(url)
    {
        window.location.replace('getting_data');
    }


</script>


<script type="text/javascript">

    function getcap() {
        var url = '/<%=util.war.warname%>/passbook/ajax/get-new-captcha';
        var _status, _html;
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: url,
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                var result = data;
                _status = result.status;
                _html = result.html;
                if (_status === 0) {
                    $('#captcha_id').attr('src', '');
                    $('#captcha_id').attr('src', 'data:image/jpg;base64,' + _html);
                }
            },
            error: function (x, t, m) {
            }});
    }



    function passEnDec()
    {
        var pass = $("#password").val();
        var passwordHash = CryptoJS.MD5(pass);
        var fhashValue = hex_sha512("kr9rk" + passwordHash.toString() + "kr9rk");
        $("#password1").val(fhashValue);



    }


    function reset()
    {
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        //document.getElementById("captcha").value = "";
        document.getElementById('username').focus();
        return false;
    }


    function chkDigit()
    {
        var uan = document.getElementById("username").value;
        var pass = document.getElementById("password").value;
        var captcha = document.getElementById("captcha").value;


        if (uan.length === 0)
        {
            alert("Please Enter UAN");
            document.getElementById('username').focus();
            return false;
        } else if (pass.length === 0) {
            alert("Please Enter password");
            document.getElementById('password').focus();
            return false;
        } else if (uan.length !== 12)
        {
            alert("Please Enter Valid UAN");
            document.getElementById('uan').focus();
            return false;
        } else if (captcha.length === 0)
        {
            alert("Please Enter Captcha");
            document.getElementById('captcha').focus();
            return false;
        } else if (captcha.length === 0)
        {
            alert("Please Enter Captcha code");
            document.getElementById('captcha').value = "";
            document.getElementById('captcha').focus();
            return false;
        } else
            passEnDec();
        return true;
    }

    function validNumOnly(enteredKey)
    {
        if (!(event.keyCode >= 48 && event.keyCode <= 57))
        {
            alert("UAN Contains Only Numeric Value");
            document.getElementById('username').focus();
            return false;
        }
    }



</script>


