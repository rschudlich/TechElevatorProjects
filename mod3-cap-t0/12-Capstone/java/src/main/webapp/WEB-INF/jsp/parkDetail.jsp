<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Park Details</title>
<c:url value="/css/detail.css" var="detailCSS" />
<link rel="stylesheet" href="${detailCSS}" />
</head>
<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />

	<c:url var="image" value="/img/parks/${myPark.parkCode}.jpg" />
	<c:url var="detailsPage" value="/parkDetails">
		<c:param name="parkCode" value="${myPark.parkCode}" />
		<c:param name="tempType" value='F' />
	</c:url>
	<c:url var="detailsPageCelcius" value="/parkDetails">
		<c:param name="parkCode" value="${myPark.parkCode}" />
		<c:param name="tempType" value='C' />
	</c:url>

	<div class="parkdetailpic">
		<img src="${image}" />
	</div>
	<h2>
		<c:out value="${myPark.parkName}, ${myPark.state}" />
	</h2>
	<div class="quote">
		<p>
			"${myPark.inspirationalQuote}" -
			<c:out value="${myPark.inspirationalQuoteSource}" />
		</p>
	</div>
	<div class="summaryinfo">
		<p>
			<c:out
				value="With ${myPark.acreage} acres at ${myPark.elevationInFeet}ft. of elevation, ${myPark.numberOfCampsites} campsites, and a ${myPark.climate} 
		climate, ${myPark.parkName}, founded in ${myPark.yearFounded} is a perennial favorite averaging ${myPark.annualVisitorCount}
		 visitors per year." />
			<br> <br>
			<c:out
				value="Be sure to look out for some of the ${myPark.numberOfAnimalSpecies} different animal species found within the park." />
		</p>
	</div>
	<div class="parkdesc">
		<p>
			<c:out value="${myPark.parkDescription}" />
		</p>

		<b>Entry Fee: $</b>
		<c:out value="${myPark.entryFee}" />
		<br>
		<div class="line"></div>
		<br>
	</div>

	<section>
		<h2 class="centered">
			<b><c:out value="WEATHER" /></b> <a href="${detailsPage}"> F<span>&#176;</span>
				/
			</a> <a href="${detailsPageCelcius}">C<span>&#176;</span></a>
		</h2>
		<c:set var="count" value="1" />
<div class="column">
		<c:forEach items="${weatherList}" var="weather">
			<div style="display: flex">
				<c:url var="image" value="/img/weather/${weather.forecast}.png" />
				<c:if test="${weather.forecast == 'partly cloudy'}">
					<c:set var="image"
						value="http://localhost:8080/m3-java-capstone/img/weather/partlyCloudy.png" />
				</c:if>
				
					<div class="row">
						<div id="weather${count}">
							<tr>
								<h3>
									<b><c:out value="${weather.day}" /></b>
								</h3>
							</tr>
							<tr>
								<img src="${image}" /><br>
							</tr>

							<tr>
								<c:out value="High: ${weather.high}${param.tempType}" />
								<span>&#176;</span>
								<br>
								<c:out value="Low: ${weather.low}${param.tempType}" />
								<span>&#176;</span>
							</tr>
							
							<tr>
								<c:if test="${weather.forecast == 'snow'}">
									<br>
									<b><c:out value="Make sure to pack snowshoes!" /></b>
								</c:if>
								<c:if test="${weather.forecast == 'rain'}">
									<br>
									<b><c:out
											value="Make sure to wear a raingear and waterproof shoes!" /></b>
								</c:if>
								<c:if test="${weather.forecast == 'thunderstorms'}">
									<br>
									<b><c:out
											value="Make sure to seek shelter and avoid hiking near or on exposed ridges!" /></b>
								</c:if>
								<c:if test="${weather.forecast == 'sunny'}">
									<br>
									<b><c:out value="Make sure to wear sunscreen!" /></b>
								</c:if>


								<c:if test="${param.tempType == 'F' }">
									<c:if test="${weather.high >= 75}">
										<br>
										<b><c:out
												value="Make sure to bring an extra gallon of water!" /></b>
									</c:if>
									<c:if test="${weather.high - weather.low >= 20}">
										<br>
										<b><c:out value="Make sure to wear breathable layers!" /></b>
									</c:if>
									<c:if test="${weather.low < 20}">
										<br>
										<b><c:out
												value="Make sure to be careful of the frigid temperatures!" /></b>
									</c:if>
								</c:if>

								<c:if test="${param.tempType == 'C'}">
									<c:if test="${weather.high >= 24}">
										<br>
										<b><c:out
												value="Make sure to bring an extra gallon of water!" /></b>
									</c:if>
									<c:if test="${weather.high - weather.low >= 11}">
										<br>
										<b><c:out value="Make sure to wear breathable layers!" /></b>
									</c:if>
									<c:if test="${weather.low < -7}">
										<br>
										<b><c:out
												value="Make sure to be careful of the frigid temperatures!" /></b>
									</c:if>
								</c:if>

							</tr>

					
					</div>
				</div>


				<br>
			</div>
			<c:if test="${count == 1}">
				<c:set var="count" value="${count + 1}" />
			</c:if>
		</c:forEach>
		<br>
	
</div>		
	</section>


	<div class="line2"></div>
</body>
</html>