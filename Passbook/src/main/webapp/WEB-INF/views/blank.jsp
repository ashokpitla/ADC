<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/head1.jsp" %>

<html lang="en">
    <body>
        <%@include file="/WEB-INF/views/head_common.jsp" %>

        <jsp:include page="/WEB-INF/views/nav.jsp" >
            <jsp:param name="navtoken" value="profile" />
        </jsp:include>
        <div class="container content">

            <section>
                <div class="container py-5">                   
                    <div class="row">

                    </div>
                </div>
            </section>

        </div>
        <%@include file="/WEB-INF/views/foot1_common.jsp" %>
    </body>
</html>

<%@include file="/WEB-INF/views/foot2.jsp" %>
