<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/header.jsp" />

<html>
<title>Home</title>
</head>
<body>
	<div id="wrapper">
		<h1>Golf Tracker</h1>

		<div class="container">
			<c:url var="imgSrc" value="/img/treetops.jpg" /> 
			<img src="${imgSrc}" class="homeImg" alt="Treetops Resort in Gaylord, MI" title="Treetops Resort in Gaylord, MI"/><br> <br>			
		</div>
		<div class="textblock">
			<p> Welcome to Golf Tracker! This site will help you track your scores over the course of a season. 
			You can compete with the scores of your friends via private league leaderboards and set up matches with your friends!
			You can also search for courses based on a location and view the results on a map. </p>
		</div>
	</div>
</body>
</html>
<c:import url="/WEB-INF/jsp/footer.jsp" />