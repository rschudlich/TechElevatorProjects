<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Survey</title>
</head>
<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />

	<c:url var="surveySubmitUrl" value="/survey" />

	<h2>Vote Your Favorite Park</h2>

	<p>Thank you for visiting our website. We
			hope you enjoyed your time with us during your visit. Your response below will help us 
			find which park people prefer. Thank you.</p>

	<form modelAttribute="survey" action="${surveySubmitUrl}" method="POST">

		<table>
			<tr>
				<div class="form-group">
					<td style="font-weight: bold"><label
							for="parkCode">Select Favorite National Park:</label></td>
					<td><select name="parkCode" id="parkCode">
							<option value="CVNP">Cuyahoga Valley NationalPark</option>
							<option value="ENP">Everglades National Park</option>
							<option value="GCNP">Grand Canyon National Park</option>
							<option value="GNP">Glacier National Park</option>
							<option value="GSMNP">Great Smoky Mountains National
								Park</option>
							<option value="GTNP">Grand Teton National Park</option>
							<option value="MRNP">Mount Rainier National Park</option>
							<option value="RMNP">Rocky Mountain National Park</option>
							<option value="YNP">Yellowstone National Park</option>
							<option value="YNP2">Yosemite National Park</option>
					</select></td>
				</div>
			</tr>

			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

			<tr>
				<div class="form-group">
					<td style="font-weight: bold"><label
							for ="email">Your Email Address:</label></td>
					<td><input name="email" type = "text" class="form-control" /> <errors
							path="email" cssClass="error" /></td>
				</div>
			</tr>

			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

			<tr>
				<div class="form-group">
					<td style="font-weight: bold"><label
							for="state">Your State of Residence:</label></td>
					<td><select name="state" id="state">
							<option value="AL">Alabama</option>
							<option value="AK">Alaska</option>
							<option value="AZ">Arizona</option>
							<option value="AR">Arkansas</option>
							<option value="CA">California</option>
							<option value="CO">Colorado</option>
							<option value="CT">Connecticut</option>
							<option value="DE">Delaware</option>
							<option value="DC">District Of Columbia</option>
							<option value="FL">Florida</option>
							<option value="GA">Georgia</option>
							<option value="HI">Hawaii</option>
							<option value="ID">Idaho</option>
							<option value="IL">Illinois</option>
							<option value="IN">Indiana</option>
							<option value="IA">Iowa</option>
							<option value="KS">Kansas</option>
							<option value="KY">Kentucky</option>
							<option value="LA">Louisiana</option>
							<option value="ME">Maine</option>
							<option value="MD">Maryland</option>
							<option value="MA">Massachusetts</option>
							<option value="MI">Michigan</option>
							<option value="MN">Minnesota</option>
							<option value="MS">Mississippi</option>
							<option value="MO">Missouri</option>
							<option value="MT">Montana</option>
							<option value="NE">Nebraska</option>
							<option value="NV">Nevada</option>
							<option value="NH">New Hampshire</option>
							<option value="NJ">New Jersey</option>
							<option value="NM">New Mexico</option>
							<option value="NY">New York</option>
							<option value="NC">North Carolina</option>
							<option value="ND">North Dakota</option>
							<option value="OH">Ohio</option>
							<option value="OK">Oklahoma</option>
							<option value="OR">Oregon</option>
							<option value="PA">Pennsylvania</option>
							<option value="RI">Rhode Island</option>
							<option value="SC">South Carolina</option>
							<option value="SD">South Dakota</option>
							<option value="TN">Tennessee</option>
							<option value="TX">Texas</option>
							<option value="UT">Utah</option>
							<option value="VT">Vermont</option>
							<option value="VA">Virginia</option>
							<option value="WA">Washington</option>
							<option value="WV">West Virginia</option>
							<option value="WI">Wisconsin</option>
							<option value="WY">Wyoming</option>
					</select></td>
				</div>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

			<tr>
				<div class="form-group">
					<td style="font-weight: bold"><label
							for="activityLevel">Choose Your Activity Level:</label></td>
					<td><select name="activityLevel" id="activityLevel">
							<option value="inactive">I am inactive</option>
							<option value="sedentary">I am sedentary</option>
							<option value="active">I am active</option>
							<option value="extremely active">I am extremely active</option>

					</select></td>
				</div>
			</tr>

			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

			<tr>
				<td></td>

				<td><input type="submit" value="submit" class="btn btn-primary" /></td>
			<tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

		</table>
		<form>

			<c:import url="/WEB-INF/jsp/common/footer.jsp" />
</body>

</html>