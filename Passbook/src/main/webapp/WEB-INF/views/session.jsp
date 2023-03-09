<%-- 
    Document   : session
    Created on : 12 Dec, 2022, 4:12:04 PM
    Author     : SANJAY-KUNJAM
--%>

<%@page import="com.epfo.passbook2.common.util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    $(document).ready(function () {
        window.location.href = '/<%=util.war.warname%>/login?reason=session_expired';
    });
</script>