<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:import url="/WEB-INF/jsp/header.jsp" />


<html>
<title>Match Succesfully Added</title>
</head>
<body>
<div id="wrapper">
		<div class="textblock">
			<br>
			<p><em>You have successfully added a Match!</em></p>
			<button type="submit" class="btn btn-primary">
				<c:url var="dashboardHref" value="/users/${currentUser}/dashboard"/>
				<a href="${dashboardHref}">Go to My Dashboard</a>
			</button>
			
			<button type="submit" class="btn btn-primary">
				<c:url var="myLeaguesHref" value="/users/${currentUser}/myLeagues"/>
				<a href="${myLeaguesHref}">Back to My Leagues</a>
			</button>
			<br><br>
		</div>
		<c:url var="imgSrc" value="/img/boynehighlandspjkoenig.jpg" /> 
		<img src="${imgSrc}" class="localImg" alt="Boyne Highlands: Arthur Hills (photo by Patrick Koenig)" title="Boyne Highlands: Arthur Hills (photo by Patrick Koenig)"/><br> <br>			
	<br><br><br>
</div>
</body>
</html>

<c:import url="/WEB-INF/jsp/footer.jsp" />