<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<c:import url="/WEB-INF/jsp/header.jsp" />


<style>

#leagueTrue {
	display: none;
}

</style>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/JavaScript">

//Incrementor Functions
	function increaseValue() {
		var value = parseInt(document.getElementById('number').value, 10);
		value = isNaN(value) ? 0 : value;
		value > 144 ? value = 144 : '';
		value++;
		document.getElementById('number').value = value;
	}

	function decreaseValue() {
		var value = parseInt(document.getElementById('number').value, 10);
		value = isNaN(value) ? 0 : value;
		value < 55 ? value = 55 : '';
		value--;
		document.getElementById('number').value = value;
	};

//Calendar Input
	$(document).ready(function(){
		var date_input=$('input[name="date"]');
		var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		var options={
		format: 'mm/dd/yyyy',
		container: container,
		todayHighlight: true,
		autoclose: true,
		maxDate: 'now',
		orientation: "left bottom"
		};
		date_input.datepicker(options);
		});

</script>


<html>
<title>Add Score</title>
</head>
<body>
<div id="wrapper">
	<div class="row">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">	
	<h3>Add a Score</h3>
	<c:url var = "newScoreSubmitVar" value = "/users/${currentUser}/addScore"/>

	<form name = 'myform' method="POST" action="${newScoreSubmitVar}" onsubmit = "return validate()">
	<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

			<div class="form-group">
				<label for="name">Course: </label> 
				<select name="name">
					<c:forEach items="${allCourses}" var="course">
						<option value="${course.name}">${course.name}</option>			
					</c:forEach>
				</select>
			</div>

			<div class="incrementButton">
				<label for="score">Score:</label> 
				<div class="value-button" id="decrease" onclick="decreaseValue()" value="Decrease Value">-</div>
					<input type="number" name="score" id="number" value="72" />
 				<div class="value-button" id="increase" onclick="increaseValue()" value="Increase Value">+</div>
			</div>
			
			<div class="form-group">
				<label class="control-label" for="date">Date</label>
				<input class="form-control" id="date" name="date" placeholder="MM/DD/YYY" type="text" data-date-end-date="0d"/>
			</div>
			<br>
						
		<button type="submit" class="btn btn-primary" id="btnSaveScore">Submit</button>
		</form>
		</div>
		</div>
	</div>		
</body>
</html>

<c:import url="/WEB-INF/jsp/footer.jsp" />