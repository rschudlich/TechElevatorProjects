<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:import url="/WEB-INF/jsp/header.jsp" />


<html>
<title>Course Succesfully Added</title>
</head>
<body>
<div id="wrapper">
		<div class="textblock">
			<br>
			<p><em>You have successfully added a Course!</em></p>
			<button type="submit" class="btn btn-primary">
				<c:url var="dashboardHref" value="/users/${currentUser}/dashboard"/>
				<a href="${dashboardHref}">Go to My Dashboard</a>
			</button>
			
			<button type="submit" class="btn btn-primary">
				<c:url var="myLeaguesHref" value="/users/${currentUser}/myLeagues"/>
				<a href="${myLeaguesHref}">Go to My Leagues</a>
			</button>
			<br><br>
		</div>
		<c:url var="imgSrc" value="/img/oaklandhillssouthpjkoenig.jpg" /> 
		<img src="${imgSrc}" class="localImg" alt="Oakland Hills Country Club: South (photo by Patrick Koenig)" title="Oakland Hills Country Club: South (photo by Patrick Koenig)"/>
	<br><br><br>
</div>
</body>
</html>

<c:import url="/WEB-INF/jsp/footer.jsp" />