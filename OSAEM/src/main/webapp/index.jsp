<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1"))
		response.setHeader("Cache-Control", "no-cache");
%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="naempApp">
<head>
<!-- Responsive Design -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title> OSAEM - Open, Sharable and Extensible Data Management System </title>

<link href="resources/css/naemp.css" rel="stylesheet">
<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<script	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.5/angular.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.5/angular-route.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.5/angular-resource.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.5/angular-cookies.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.5/angular-animate.min.js"></script>

<!-- UI BootStrap -->
<script src="https://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>

<!-- Angular - Leaflet Directive -->
<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.5/leaflet.css">
<script src="http://cdn.leafletjs.com/leaflet-0.7.5/leaflet.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-leaflet-directive/0.10.0/angular-leaflet-directive.js"></script>

<!-- Leaflet Marker Cluster -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.Default.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/leaflet.markercluster.js"></script>

<!-- DatePicker -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	
<!-- Smart Table -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/angular-smart-table/2.1.7/smart-table.js"></script>

<script type="text/javascript">
	// Dropdown Menu Fade    
	jQuery(document).ready(function() {
		$(".dropdown").hover(function() {
			$('.dropdown-menu', this).stop().fadeIn("fast");
		}, function() {
			$('.dropdown-menu', this).stop().fadeOut("fast");
		});
	});

	// Needle in Haystack
	function inArray(needle, haystack) {
		var count = haystack.length;
		for (var i = 0; i < count; i++) {
			if (haystack[i] === needle) {
				return true;
			}
		}
		return false;
	};
</script>
</head>
<body>

	<div class="navbar-wrapper">
		<div class="container">
			<nav class="navbar navbar-inverse"> <!-- and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavBar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> NAEMP </a>
			</div>
			<div class="collapse navbar-collapse" id="myNavBar">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="" class="dropdown-toggle"
						data-toggle="dropdown"><span class="glyphicon glyphicon-list"></span>
							Program Data Management </a>
						<ul class="dropdown-menu inverse">
							<li><a href="#site-list"> Site</a></li>
							<li><a href="#river-list"> River</a></li>
							<li><a href="#fish-list"> Fish</a></li>
							<li><a href="#variable-list"> Variable</a></li>
							<li><a href="#unit-list"> Unit</a></li>
							<li><a href="#source-list"> Source</a></li>
							<li><a href="#method-list"> Method</a></li>
						</ul></li>

					<li class="dropdown"><a href="" class="dropdown-toggle"
						data-toggle="dropdown"> <span
							class="glyphicon glyphicon-search"></span> Observation Data
							Search
					</a>
						<ul class="dropdown-menu inverse">
							<li><a href="#search-site"> Search By Site</a></li>
							<li><a href="#search-fish"> Search By Species</a></li>
						</ul></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span>
							Sign Up</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Login</a></li>
				</ul>
			</div>
			</nav>
		</div>
	</div>
	<div class="container">
		<div ng-view></div>
	</div>
	<script src="resources/js/app.js"></script>
	<script src="resources/js/controller.js"></script>
	<script src="resources/js/service.js"></script>
	<script src="resources/js/filter.js"></script>
	<script src="resources/js/directive.js"></script>
</body>
</html>