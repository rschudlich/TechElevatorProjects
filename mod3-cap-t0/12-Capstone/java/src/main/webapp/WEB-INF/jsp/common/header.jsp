<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<c:url value="/css/header.css" var="headerCSS" />
<link rel="stylesheet" href="${headerCSS}" />
</head>
<body>

<c:url var="logo" value="/img/logo.png" />

<div class="logo">
    <img src="${logo}" alt="National Park Logo" width=400px/>
</div>
<div class="header">
    <c:url var="home" value="/" />
    <c:url var="survey" value="/survey" />
</div>


<ul>
    <li><a href="${home}">Home Page</a></li>
    <li><a href="${survey}">Survey</a></li>
</ul>
</body>
</html>