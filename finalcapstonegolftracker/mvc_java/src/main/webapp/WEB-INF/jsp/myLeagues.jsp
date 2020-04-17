<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/header.jsp" />


<style>

#addLeagueForm {
	display: none;
}

#addMatchForm {
	display: none;
}

#addPlayersForm{
	display: none;
}

</style>


<script type="text/JavaScript">
	function addLeague() {
		var addLeagueForm = document.getElementById('addLeagueForm');
		var displaySetting = addLeagueForm.style.display;
		if (displaySetting == 'none') {
			document.getElementById('addLeagueForm').style.display = 'block';
		} else {
			document.getElementById('addLeagueForm').style.display = 'none';
		}
	}
	
	function addMatch() {
		var addMatchForm = document.getElementById('addMatchForm');
		var displaySetting = addMatchForm.style.display;
		if (displaySetting == 'none') {
			document.getElementById('addMatchForm').style.display = 'block';
		} else {
			document.getElementById('addMatchForm').style.display = 'none';
		}
	}
	
	function addPlayers() {
		var addPlayersForm = document.getElementById('addPlayersForm');
		var displaySetting = addPlayersForm.style.display;
		if (displaySetting == 'none') {
			document.getElementById('addPlayersForm').style.display = 'block';
		} else {
			document.getElementById('addPlayersForm').style.display = 'none';
		}
	}
	
	function showSelectedLeague() {
		var selectedLeague = document.getElementById('radioLeague');
		console.log(selectedLeague);
	}
	
</script>


<html>
<title>My Leagues</title>

<body>
<div id="wrapper">
	<h1>Welcome, ${currentUser}!</h1>
	<div class="myLeaguesGrid">
	
<!-- Side Navbar -->

<div class="sidenav">
	<h1 class = "header_title">View My Leagues</h1>
		<c:forEach items="${allLeagues}" var="league">
			<c:url var = "myName" value = "/users/${currentUser}/myLeagues">
				<c:param name = "leagueName" value = "${league.name}"/>
			</c:url>
			<li>				
				<a href = "${myName}"><label for="league">${league.name}</label></a><br>
			</li>
		</c:forEach>
		<br>
	
	<!-- Create League Form -->


	<button type="button" class="btn btn-primary" id="addLeagueBtn" onclick="addLeague()">+ Create a League</button>
		<div id="addLeagueForm" >
			<br>
			<c:url var = "addLeagueUrl" value = "/users/${currentUser}/addLeague"/>
			<form method="POST" action="${addLeagueUrl}">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				<div class="form-group">
					<label for="leagueName">League Name:</label> 
					<input type="text" name="name" placeHolder="League Name"/>
				</div>
				<div class="form-group">
					<label for="users">Add Members: </label> 
					<p>Press control or command to select multiple users.</p>
					<select name="users" multiple>
						<c:forEach items="${allUsers}" var="user">
							<option value="${user.userName}">${user.userName}</option>			
						</c:forEach>
					</select>
				</div>
				<button type="submit" class="btn btn-primary" id="btnSaveLeague">Submit</button>
			</form>
			</div>
		<br><br>
		
		<!-- Create Match Form -->
		<c:if test = "${role == 'Admin' or role == 'League Admin'}">
		
		<button type="button" class="btn btn-primary" id="addMatchBtn" onclick="addMatch()">+ Add a Match</button>
			<div id="addMatchForm" >
			<form method = "POST" action = "/capstone/users/${currentUser}/addMatch">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				<br>
				<div class="form-group">
					<label for="leagueId">League: </label> 
					<select name=leagueId>
						<c:forEach items="${ownedLeagues}" var="league">
							<option value="${league.leagueId}">${league.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="courseName">Course: </label> 
					<select name="courseName">
						<c:forEach items="${allCourses}" var="course">
							<option value="${course.name}">${course.name}</option>			
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="date">Date: </label>
					<input name="date" placeHolder="Date (mm/dd/yyyy)"/>
				</div>
				
				<input type="hidden" id="numGolfers" name="numGolfers" value="4">		
				<button type="submit" class="btn btn-primary" id="btnSaveScore">Submit</button>
				</form>
			</div>				
		</c:if>
	</div>

<!-- League Leaderboard -->
	
<div class="recentScores">
	<h1 class = "header_title">${leagueName} Leaderboard</h1>
	<div id="selectedLeague"></div>

	<!-- Add Players to League Form -->

	<c:if test = "${role == 'Admin' or role == 'League Admin'}">
		<button type="button" class="btn btn-primary" id="addPlayersBtn" onclick="addPlayers()">+ Add Players to this League</button>
			<div id="addPlayersForm" >
			<c:url var = "addPlayersUrl" value = "/users/${currentUser}/addPlayers">
			<c:param name = "leagueNames" value = "Bushwood"/>
			</c:url>
			<form method = "POST" action = "${addPlayersUrl}">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				<br>
					<input type="hidden" name="leagueName">${leagueName}
				<div class="form-group">
					<label for="users">Add Members: </label> 
					<p>Press control or command to select multiple users.</p>
					<select name="users" multiple>
						<c:forEach items="${allUsers}" var="user">
							<option value="${user.userName}">${user.userName}</option>			
						</c:forEach>
					</select>
				</div>
				<button type="submit" class="btn btn-primary" id="btnSaveLeague">Submit</button>
			</form>
			</div>
		<br><br>	
	</c:if>
		
	<!-- League Leaderboard -->

		<hr>
		<table class="scores">
			<tr>
				<th align="left">Ranking</th>
				<th align="left">Name</th>
				<th align="left">Point Total</th>
			</tr>
			<c:if test = "${not empty teams}">
				<c:set var = 'count' value = '1'/>
				<c:forEach items = "${teams}" var = "team">
					<tr>
						<td>${count}</td>
						<td>${team.name}</td>
						<td>${team.points}</td>
					</tr>
				<c:set var = 'count' value = '${count + 1}'/>
				</c:forEach>
			</c:if>
		</table>
      </div>	

<!-- Matches -->    
	
	<div class="matches">
		<h1 class = "header_title">League Roster</h1>
		<hr>
		<table class="scores">
			<tr>
				<th align = "left">Team Name</th>
				<th align = "left">Player 1</th>
				<th align = "left">Player 2</th>
			</tr>
		<c:forEach items = "${teams}" var = "team">
					<tr>
						<td>${team.name}: </td>
						<td>${team.player1}</td>
						<td>${team.player2}</td>
					</tr>
		</c:forEach>
		</table>
</div>	

<!-- Closing Tags -->

</div>
</div>
</body>
</html>

<c:import url="/WEB-INF/jsp/footer.jsp" />