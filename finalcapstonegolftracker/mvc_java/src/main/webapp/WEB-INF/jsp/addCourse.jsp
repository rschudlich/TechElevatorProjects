<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<meta charset="ISO-8859-1">
<head>
<title>Add Course Page</title>
</head>
<body>
<div id="wrapper">

	<c:url var="addCourseUrl" value="/users/${currentUser}/addCourse" />
	<form:form method="POST" action="${addCourseUrl}" modelAttribute="course">
	<div class="row">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		
		<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

			<div class="form-group">
				<label for="name">Name of Golf Course: </label> 
				<form:input path="name" placeHolder="Name"/>
  			<form:errors path="name" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="par">Par: </label> 
				<form:input path="par" placeHolder="Par"/>
				<form:errors path="par" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="slope">Slope: </label> 
				<form:input path="slope" placeHolder="Slope"/>
				<form:errors path="slope" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="rating">Rating: </label> 
				<form:input path="rating" placeHolder="Rating"/>
				<form:errors path="rating" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="address">Address: </label> 
				<form:input path="address" placeHolder="Address"/>
				<form:errors path="address" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="city">City: </label> 
				<form:input path="city" placeHolder="City"/>
				<form:errors path="city" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="state">State: </label> 
				<select name="state" id="state">
					<option value="  ">  </option>
					<option value="AL">AL</option>
					<option value="AK">AK</option>
					<option value="AR">AR</option>
					<option value="AZ">AZ</option>
					<option value="CA">CA</option>
					<option value="CO">CO</option>
					<option value="CT">CT</option>
					<option value="DE">DE</option>
					<option value="FL">FL</option>
					<option value="GA">GA</option>
					<option value="HI">HI</option>
					<option value="IA">IA</option>
					<option value="ID">ID</option>
					<option value="IL">IL</option>
					<option value="IN">IN</option>
					<option value="KS">KS</option>
					<option value="KY">KY</option>
					<option value="LA">LA</option>
					<option value="MA">MA</option>
					<option value="MD">MD</option>
					<option value="ME">ME</option>
					<option value="MI">MI</option>
					<option value="MN">MN</option>
					<option value="MO">MO</option>
					<option value="MS">MS</option>
					<option value="MT">MT</option>
					<option value="NC">NC</option>
					<option value="ND">ND</option>
					<option value="NE">NE</option>
					<option value="NH">NH</option>
					<option value="NJ">NJ</option>
					<option value="NM">NM</option>
					<option value="NV">NV</option>
					<option value="NY">NY</option>
					<option value="OH">OH</option>
					<option value="OK">OK</option>
					<option value="OR">OR</option>
					<option value="PA">PA</option>
					<option value="RI">RI</option>
					<option value="SC">SC</option>
					<option value="SD">SD</option>
					<option value="TN">TN</option>
					<option value="TX">TX</option>
					<option value="UT">UT</option>
					<option value="VA">VA</option>
					<option value="VT">VT</option>
					<option value="WA">WA</option>
					<option value="WI">WI</option>
					<option value="WV">WV</option>
					<option value="WY">WY</option>
				</select>
				<form:errors path="state" cssClass="error"/>
			</div>
			<div class="form-group">
				<label for="zip">Zip: </label> 
				<form:input path="zip" placeHolder="Zip"/>
				<form:errors path="zip" cssClass="error"/>
			</div>					
		<button type="submit" class="btn btn-primary">Submit</button>
			</div>
			</div>
		</form:form>
	<div class="col-sm-4"></div>
</div>	
</body>
</html>

<c:import url="/WEB-INF/jsp/footer.jsp" />