<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
section {
clear: both;
display: block;
width: 80%;
margin: 15px auto;
vertical-align: top;
text-align: center;

background-color: #FFF;
border-radius: 7px;
padding: 5px 15px 15px 15px;
}
h3 {
	font-size: 15px;
	color: #008000;
	font-family: sans-serif;
	margin-left: 50px;
	
}

h1 {
	font-size: 45px;
	font-family: sans-serif;
	
}

h2 {
	font-size: 20px;
}
.line {
	width : 100%;
	border-bottom: 2px solid black;
	position : absolute;
}
td > img {
	margin-top: 10px;
}
</style>
<meta charset="ISO-8859-1">
<title>Favorite Parks</title>
<c:url value="/css/surveyResults.css" var="detailCSS" />
<link rel="stylesheet" href="${detailCSS}" />
</head>
<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />
	
	<section id="main-content">
	<h1>All Survey Results</h1>
	<table>
		<c:set var="count" value="1" />
		<c:forEach items="${surveyResults}" var="park">
			
			<tr>
				<td><c:url var="image" value="/img/parks/${park.parkCode}.jpg" />
				<img src="${image}" height="150" /></td>
				</td>
			
				<td valign = "top" td style="text-align:left"><h2 >Rank ${count}: ${park.parkName}</h2>
					<h3><c:out value="Favorites: ${park.count}" /></h3></td>
				
			</tr>
			<tr>
			
			
		<c:set var="count" value="${count + 1}" />
		<tr class="line"></tr>
		</c:forEach>
		
	</table>
	
	</section>
</body>
</html>