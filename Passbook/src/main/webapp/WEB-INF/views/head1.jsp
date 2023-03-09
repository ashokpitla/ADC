
<%@page import="com.epfo.passbook2.common.util"%>
<%@page import="com.epfo.passbook2.common.fun"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">                
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="keywords" content="" />
    <meta name="description" content="" />  


    <% response.addHeader("X-Frame-Options", "DENY");
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Content-Type-Options", "nosniff");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");

        response.setHeader("SET-COOKIE", "JSESSIONID=" + session.getId() + "; HttpOnly; SameSite=Strict; Secure; ");
    %>

    <title></title>
    <link href="<c:url value="<%=util.jscss.bootstrap_css%>" />" rel="stylesheet">
    <!--<link href="<c:url value="<%=util.jscss.datatable_bootstrap_css%>" />" rel="stylesheet">-->
    <link href="<c:url value="<%=util.jscss.jquery_ui_css%>" />" rel="stylesheet">
    <link href="<c:url value="<%=util.jscss.fa_css%>" />" rel="stylesheet">
    <link href="<c:url value="<%=util.jscss.epfo_css%>" />" rel="stylesheet">
    <link href="<c:url value="<%=util.jscss.tab_css%>" />" rel="stylesheet">
    <link rel="shortcut icon" href="<%=util.jscss.favicon%>" />
</head>