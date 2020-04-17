<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAU2WLjSJwad6UxAVzMZP9GGfuNRjqmF-4"></script>
<script>

var map;
function initialize() {
	
	var mapOptions = {
		zoom : 6.5,
		center : new google.maps.LatLng(44.5, -86)
	};
	var map = new google.maps.Map(document.getElementById('map-canvas'),
			mapOptions);
		
	<c:forEach var="course" items="${allCourses}">
		var myLatLng = {lat: ${course.latitude}, lng: ${course.longitude}};
		var name = "${course.name}"
			var image = {
				url: 'https://upload.wikimedia.org/wikipedia/commons/b/bc/Map_symbol_golf_course_02.png',
				scaledSize: new google.maps.Size(30,30)
		};	
    	var marker = new google.maps.Marker({
    		position: myLatLng,
    		map: map,
    		title: name,
    		icon: image
   	});

    </c:forEach>
    
    
};
google.maps.event.addDomListener(window, 'load', initialize);
</script>
<input type="hidden" name="request" value="${request}"/>

<html>
<title>Course Search</title>
<body>
	<div id="wrapper">
		<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />

<div id="weather" style="width: 100%;">
  <iframe style="display: block;
  " src="https://cdnres.willyweather.com/widget/loadView.html?id=122053" 
  width=100% 
  height="92" 
  frameborder="0" 
  scrolling="no">
  </iframe><a style="text-indent: -9999em;
  height: 92px;float: right;
  position: relative;
  margin: -92px 0 0 0;
  z-index: 1;
  width: 20px" href="https://www.willyweather.com/mi/wayne-county/detroit.html" rel="nofollow">willyweather</a>
  </div>

<!-- Search Form -->
	<div id="courseSearchForm">	
		<c:url value='/courseSearchResults' var='courseSearchVar'>
		<c:param name='request' value="${request}"/>
		</c:url>
		<c:if test = "${not empty currentUser}">
			<c:url value = '/users/${currentUser}/courseSearchResults' var = "courseSearchVar"/>
		</c:if>
		<div id="courseSearchFields">
			<h1>Search for Courses</h1>
			<br><br>
			<form method="GET" action="${courseSearchVar}">
				<div class="form-group pull-right">
					<label for="name">Course Name:</label> 
					<input type="text" name="searchName" placeHolder="Course Name" id="name" />
				</div>
				<div class="row">
					<div class="col-md-12 bs-linebreak"></div>
				</div>
				<div class="form-group pull-right">
					<label for="city">City:</label> 
					<input type="text" name="searchCity" placeholder="City" id="city" />
				</div>
				<div class="row">
					<div class="col-md-12 bs-linebreak"></div>
				</div>
			<button type="submit" class="btn btn-primary pull-right">Search</button>	
			</form>
				<div class="row">
					<div class="col-md-12 bs-linebreak"></div>
				</div>
			<c:if test = "${role == 'Admin'}">
				<button type="submit" class="btn btn-primary pull-right">
					<c:url var="addCourseHref" value="/users/${currentUser}/addCourse"/>
					<a href="${addCourseHref}">+ Add a new Course</a>
				</button>
			</c:if>		
			<br><br><br><br>
			<c:if test = "${not empty currentUser}">
				<c:url var="largeMapHref" value="/users/${currentUser}/map"/>		
			</c:if>
			<c:if test="${empty currentUser}">
				<c:url var="largeMapHref" value="/map"/>
			</c:if>
			<div class="pull-right" id="mapLink">
				<a href="${largeMapHref}">Click here to view an enlarged map</a>
			</div>
		</div>

<!-- Course Map -->
	
		<div id="map-canvas"></div>
	</div>
	<br><br>

<!-- Course Table -->

		<table class="courseTable">
			<tr>
				<th align="left">Name</th>
				<th align="left">Address</th>
				<th align="left">City</th>
				<th align="left">State</th>
				<th align="left">Par</th>
				<th align="left">Slope</th>
				<th align="left">Rating</th> 
			</tr>		
		<c:forEach items="${allCourses}" var="course">
			<tr>
				<td><c:out value="${course.name}" /></td>
				<td><c:out value="${course.address}" /></td>
				<td><c:out value="${course.city}" /></td>
				<td><c:out value="${course.state}" /></td>
				<td><c:out value="${course.par}" /></td>
				<td><c:out value="${course.slope}" /></td>
				<td><c:out value="${course.rating}" /></td>
			<tr>
		</c:forEach>
	</table>
	<br><br>
	
<!-- Closing Tags -->
		
</div>
</body>
</html>
<c:import url="/WEB-INF/jsp/footer.jsp" />