<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Golf App</title>
<c:url var="bootstrapCss" value="/css/bootstrap.min.css" />
<c:url var="siteCss" value="/css/site.css" />

<c:url var="jQueryJs" value="/js/jquery.min.js" />
<c:url var="jqValidateJs" value="/js/jquery.validate.min.js" />
<c:url var="jqvAddMethJs" value="/js/additional-methods.min.js" />
<c:url var="jqTimeagoJs" value="/js/jquery.timeago.js" />
<c:url var="popperJs" value="/js/popper.min.js" />
<c:url var="bootstrapJs" value="/js/bootstrap.min.js" />

<link rel="stylesheet" type="text/css" href="${bootstrapCss}">
<link rel="stylesheet" type="text/css" href="${siteCss}">

<script src="${jQueryJs}"></script>
<script src="${jqValidateJs}"></script>
<script src="${jqvAddMethJs}"></script>
<script src="${jqTimeagoJs}"></script>
<script src="${popperJs}"></script>
<script src="${bootstrapJs}"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("time.timeago").timeago();

		$("#logoutLink").click(function(event) {
			$("#logoutForm").submit();
		});

		var pathname = window.location.pathname;
		$("nav a[href='" + pathname + "']").parent().addClass("active");

	});
</script>
</head>


<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">

		<a class="navbar-brand" href="#"> 
			<c:url var="homePageHref" value="/" />
			<c:if test = "${not empty currentUser}">
					<c:url var = 'homePageHref' value = '/users/${currentUser}/'/>
			</c:if>
			<c:url var="imgSrc" value="/img/gtlogogreen.png" /> 
			<a href="${homePageHref}"><img src="${imgSrc}" class="img-fluid" style="height: 65px;" /></a>
		</a>
		
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">

				<c:if test = "${not empty currentUser}">
					<c:url var = 'homePageHref' value = '/users/${currentUser}/'/>
				</c:if>

				<c:if test="${not empty currentUser}">
					<c:url var="dashboardHref" value="/users/${currentUser}" />

					<c:url var="dashboardHref" value="/users/${currentUser}/dashboard" />
					<li class="nav-item"><a class="nav-link" href="${dashboardHref}">My Dashboard</a></li>

					<c:url var="myLeaguesHref" value="/users/${currentUser}/myLeagues" />
					<li class="nav-item"><a class="nav-link" href="${myLeaguesHref}">My Leagues</a></li>

					<c:url var="courseSearchHref" value="/users/${currentUser}/courseSearch" />
					<li class="nav-item"><a class="nav-link" href="${courseSearchHref}">Course Search</a></li>
				</c:if>
			</ul>	

			<ul class="navbar-nav ml-auto">
				<c:if test="${empty currentUser}">
					<li class="nav-item"><a class="nav-link" href="${homePageHref}">Home</a></li>
					<c:url var="courseSearchHref" value="/courseSearch" />
					<li class="nav-item"><a class="nav-link" href="${courseSearchHref}">Course Search</a></li>
						
					<c:url var="newUserHref" value="/users/new" />
					<li class="nav-item"><a class="nav-link" href="${newUserHref}">Sign Up</a></li>
						
					<c:url var="loginHref" value="/login" />
					<li class="nav-item"><a class="nav-link" href="${loginHref}">Log In</a></li>
				</c:if>
			</ul>
		</div>			
		
		<ul class="dropdown pull-right">
			<c:if test="${not empty currentUser}">				
				<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
   					<c:out value="Welcome, ${currentUser} "/>
  					<span class="caret"></span>
 	 			</button>
  				<div class="dropdown-menu dropdown-menu-right">
  				
  					<li class="nav-item">Current Role: ${role}</li>
  					
					<c:url var="changePasswordHref" value="/users/${currentUser}/changePassword" />
					<li class="nav-item"><a class="nav-link" href="${changePasswordHref}">Change Password</a></li>

					<c:if test = "${role == 'Admin'}">
						<c:url var='adminFunctions' value = '/users/${currentUser}/adminFunctions'>
							<c:param name = 'role' value = '${role}'/>
						</c:url>
						<li class = 'nav-item'><a class = 'nav-link' href = "${adminFunctions}">Admin Functions</a>
					</c:if>
				
					<c:url var="logoutAction" value="/logout" />
						<form id="logoutForm" action="${logoutAction}" method="POST">
							<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
						</form>
					<li class="nav-item"><a id="logoutLink" href="#">Log Out</a></li>
				</div>
			</c:if>
		</ul>	

	</nav>								
</body>
