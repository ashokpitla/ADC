<%@page import="com.epfo.passbook2.model.UserData"%>
<%@page import="com.epfo.passbook2.model.UserModel"%>
<%@page import="com.epfo.passbook2.common.util"%>

<%
    //UserModel usermodel;
   UserData userData = (UserData) session.getAttribute(util.session.userData);

    String navtoken = request.getParameter("navtoken");
    String navhome = "", navprofile = "", navpassbook = "", navclaims = "", navservice = "";

    if (navtoken.equalsIgnoreCase("home")) {
        navhome = "nav-selected";
    } else if (navtoken.equalsIgnoreCase("profile")) {
        navprofile = "nav-selected";
    } else if (navtoken.equalsIgnoreCase("passbook")) {
        navpassbook = "nav-selected";
    } else if (navtoken.equalsIgnoreCase("claims")) {
        navclaims = "nav-selected";
    } else if (navtoken.equalsIgnoreCase("service")) {
        navservice = "nav-selected";
    }


%>

<nav id="navbar_top" class="navbar navbar-expand-lg navbar-dark bg-primary navbar-new" style="z-index: 999">
    <div class="container">

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"  aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="main_nav" style="font-size: 14px">

            <!--            <ul class="navbar-nav" style="margin-left: 5px;">
                            <li class="nav-item <%=navhome%>"><a class="nav-link" href="/<%=util.war.warname%>/home"> <i class="fa fa-home mx-1"></i> Home</a> </a></li>
                            <li class="nav-item <%=navprofile%>"><a class="nav-link" href="/<%=util.war.warname%>/profile"> <i class="fa fa-user mx-1"></i> Profile</a> </a></li>                    
                            <li class="nav-item <%=navpassbook%>"><a class="nav-link" href="/<%=util.war.warname%>/passbook"> <i class="fa fa-newspaper-o mx-1"></i> Passbook</a> </a></li>
                            <li class="nav-item <%=navclaims%>"><a class="nav-link" href="/<%=util.war.warname%>/claims"> <i class="fa fa-file-text-o mx-1"></i> Claims</a> </a></li>                    
                            <li class="nav-item <%=navservice%>"><a class="nav-link" href="/<%=util.war.warname%>/service-history"> Service History </a></li>
                        </ul>-->

            <ul class="navbar-nav ms-auto" >
                <span class="navbar-text" style="color: #ffecb5">
                    <%=userData.getName()%>
                </span>
                <li class="nav-item"><a id="logout" class="nav-link" href="#"> <i class="fa fa-sign-out mx-1"></i> Logout</a> </a></li>                    
            </ul>

        </div> 
    </div> 
</nav>