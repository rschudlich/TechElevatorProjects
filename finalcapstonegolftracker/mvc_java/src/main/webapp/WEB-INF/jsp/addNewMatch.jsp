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
	<h3>Select the teams playing in this match</h3>
	
			<form method = "POST" action = "/capstone/users/${currentUser}/addNewMatch">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

				<div class="form-group">
					<label for="team1Id">Team 1: </label> 
					<select name="team1Id">
						<c:forEach items="${teamsInLeague}" var="team1">
							<option value="${team1.teamId}">${team1.name}</option>			
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label for="team2Id">Team 2: </label> 
					<select name="team2Id">
						<c:forEach items="${teamsInLeague}" var="team2">
							<option value="${team2.teamId}">${team2.name}</option>			
						</c:forEach>
					</select>
				</div>
				<button type="submit" class="btn btn-primary" id="btnSaveTeams">Submit</button>
			
			</form>	
</div>
</div>
</div>
</body>
</html>