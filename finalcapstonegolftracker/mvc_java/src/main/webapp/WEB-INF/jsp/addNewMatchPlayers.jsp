<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:import url="/WEB-INF/jsp/header.jsp" />

<title>Team Selection</title>
</head>
<body>
<div id="wrapper">
	<div class="row">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">	
	<h3>Input the scores for each player in the team</h3>
			
			<form method = "POST" action = "/capstone/users/${currentUser}/addNewMatchPlayers">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
	
			<c:set var = "count" value = "1"/>
			<div class="form-group">
				<label for="team1Id">Team 1: </label> <br>
				<c:forEach items="${team1}" var="golfer">
					<label for ="score${count}">${golfer.userName}</label>
					<input name="score${count}"/>
					<input type="hidden" id="user${count}" name="user${count}" value="${golfer.userName}">
					<c:set var = "count" value = "${count + 1}"/>
					<br>			
				</c:forEach>
			</div>
			<p>
			<div class="form-group">
				<label for="team2Id">Team 2: </label> <br>
				<c:forEach items="${team2}" var="golfer">
					<label for ="score${count}">${golfer.userName}</label>
					<input name="score${count}"/>
					<input type="hidden" id="user${count}" name="user${count}" value="${golfer.userName}">
					<c:set var = "count" value = "${count + 1}"/>
					<br>			
				</c:forEach>
			</div>
			
			<input type="hidden" id="teeTimeId" name="teeTimeId" value="${teeTimeId}">
			<input type="hidden" id="courseId" name="courseId" value="${courseId}">
			<button type="submit" class="btn btn-primary" id="btnSavePlayers">Submit</button>
			</form>
</div>
</div>
</div>
</body>
</html>