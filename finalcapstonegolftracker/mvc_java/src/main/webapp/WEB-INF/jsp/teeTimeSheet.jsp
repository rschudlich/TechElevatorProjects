<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:import url="/WEB-INF/jsp/header.jsp" />

<style>
.dangerDiv {
	text-align: center;
	background-color: #113015;
	color: #B2972C;
	font-size: 20px;
  	animation-name: flash;
  	animation-delay: -2s;
	animation-duration: 5s;
	animation-iteration-count: 2;
	margin-top: 10px;
}
</style>

<html>
<title>Schedule a Tee Time</title>
</head>

<body>
<div id="wrapper">
	<div class="row">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">	
	
	<c:url var = "submitTeeTime" value = "/users/${currentUser}/teeTimeSheet"/>
	<form method="POST" action="${submitTeeTime}">
	<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
	
	<c:set var ="message" value = ""/>
	<c:forEach items ="${bookings}" var= "dates">
		<c:if test = "${dates == date}">
			<c:set var="message" value = "You already have a tee time this day."/>
		</c:if>
	</c:forEach>
	<div class="dangerDiv">
		<c:out value = "${message}"/>
	</div>
	
	<h3>Choose a tee time below: </h3>
		<div class="form-group">
			<label for="times">Available Times: </label> 
			<select name="times">
				<c:forEach items="${availableTimes}" var="time">
					<c:set var = "timeUnformatted" value = "${time}"/>
					<c:set var = "timeFormatted" value = "${fn:substring(timeUnformatted, 11, 16)}"/>
					<option value="${time}">${timeFormatted}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group">
			<label for="golfers">How Many Golfers in your Party?</label> 
				<select name="golfers">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>			
				</select>
		</div>
		
		<input type="hidden" id="course" name="course" value="${course}">
		<button type="submit" class="btn btn-primary" id="btnSaveScore">Submit</button>
	</div>
	</div>
</div>
		
</body>
</html>