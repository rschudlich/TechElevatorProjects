<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<c:url value="/css/style.css" var="styleCSS" />
<link rel="stylesheet" href="${styleCSS}" />
</head>
<body>

<section>
    <c:forEach items="${parks}" var="park">
        <div style="display: flex">
            <div class="parkpic">
                <c:url var="image" value="/img/parks/${park.parkCode}.jpg" />
                <c:url var="detailsPage" value="/parkDetails">
                    <c:param name="parkCode" value="${park.parkCode}" />
                    <c:param name="tempType" value='F' />
                </c:url>
                <a href="${detailsPage}"><img src="${image}" align="left"></a>
            </div>
            <div class="summary">
                <p>
                <h1>
                    <c:out value="${park.parkName}" />
                </h1>
                <br>
                </p>
                <p>
                    <c:out
                        value="A ${park.climate} climate with ${park.milesOfTrail} miles of trail at ${park.elevationInFeet} ft. elevation in ${park.state}" />
                </p>
            </div>
        </div>
    </c:forEach>
</section>
</body>
</html>