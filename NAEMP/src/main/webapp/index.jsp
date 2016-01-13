
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1"))
		response.setHeader("Cache-Control", "no-cache");
%>
<%@ page contentType="text/html; Charset=UTF-8"%>

<html ng-app="naempApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Data Model Management</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">

<link
	href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.14.30/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="//code.angularjs.org/1.3.1/angular.min.js"></script>
<script src="//code.angularjs.org/1.3.1/angular-route.min.js"></script>
<script src="//code.angularjs.org/1.3.1/angular-resource.min.js"></script>
<script src="//code.angularjs.org/1.3.1/angular-cookies.min.js"></script>
<script
	src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.13.1.js"></script>
<script
	src="//www.flocations.com/static/vendor/angular-ui/event/event.js"></script>
<!-- <script	src="//www.flocations.com/static/vendor/angular-ui/map/ui-map.min.js"></script> -->
<script
	src="//rawgit.com/allenhwkim/angularjs-google-maps/master/build/scripts/ng-map.min.js"></script>
<script
	src="//maps.googleapis.com/maps/api/js?v=3.exp&language=en&libraries=weather,visualization,panoramio"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.3/moment.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.14.30/js/bootstrap-datetimepicker.min.js"></script>
<!-- <script src="//angular-ui.github.io/ui-router/release/angular-ui-router.min.js"></script> -->
<!-- Smart Table Script -->
<script
	src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/3.10.0/lodash.js"></script>
<script src="//code.highcharts.com/highcharts.js"></script>
<script
	src="//rawgit.com/pablojim/highcharts-ng/master/dist/highcharts-ng.js"></script>
<script src="//code.highcharts.com/modules/exporting.js"></script>
<!--<script src="//code.highcharts.com/maps/highmaps.js"></script>
<script src="//code.highcharts.com/mapdata/countries/kr/kr-all.js"></script> -->

<style type="text/css">
body .container {
	width: 100%;
}

.navbar {
	background: #4676B6 !important;
}

.navbar-inverse {
	height: 100px;
	padding-top: 20px
}

.navbar-brand {
	font-weight: 800;
	font-size: 28px;
}

.navbar-inverse a {
	color: #ccc !important;
}

.navbar-inverse a:hover {
	background: #4676B6 !important;
}

.navbar-nav a {
	font-weight: 500;
	font-size: 20px;
}

.dropdown-menu {
	background: #4676B6;
}

.dropdown-menu.inverse {
	background: #4676B6;
}

.dropdown-menu.inverse a {
	font-weight: 300;
	font-size: 18px;
	color: #ccc;
}

.panel-heading {
	height: 50px;
}

/* CUSTOMIZE THE NAVBAR
	-------------------------------------------------- */

/* Special class on .container surrounding .navbar, used for positioning it into place. */
.navbar-wrapper {
	position: relative;
	z-index: 15;
}

.navbar-wrapper .container {
	position: relative;
	top: 10px;
	left: 0;
	width: 100%;
}

</style>
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
			<nav class="navbar navbar-inverse">
				<!-- and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"> NAEMP </a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span class="glyphicon glyphicon-list"></span>
								Program Management <b class="caret"></b></a>
							<ul class="dropdown-menu inverse">
								<li><a href="#site-list"> Site</a></li>
								<li><a href="#river-list"> River</a></li>
			<!-- 				<li><a href="#siteCode-list"> Site Code</a></li>  -->
			<!--				<li><a href="#feature-list"> Feature</a></li> -->
								<li><a href="#fish-list"> Fish</a></li>
								<li><a href="#variable-list"> Variable</a></li>
								<li><a href="#unit-list"> Unit</a></li>
								<li><a href="#source-list"> Source</a></li>
								<li><a href="#method-list"> Method</a></li>
							</ul></li>

						<li class="dropdown"><a href="#site-search" class="dropdown-toggle"
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-search"></span> Observation Data
								Search <b class="caret"></b></a>
							<ul class="dropdown-menu inverse">
								<!-- 							<li><a href="#river-search"> by River </a></li> -->
								<li><a href="#site-search"> Location </a></li>
								<li><a href="#fish-search"> Taxonomy </a></li>
							</ul></li>

	<!-- 					<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-cog"></span> Assessment <b
								class="caret"></b></a>
							<ul class="dropdown-menu inverse">
								<li><a href="#"> FAI </a></li>
								<li><a href="#"> BMI </a></li>
								<li><a href="#"> TDI </a></li>
								<li><a href="#"> HRI </a></li>
								<li><a href="#"> RVI </a></li>							
							</ul></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <span
								class="glyphicon glyphicon-blackboard"></span> Report <b
								class="caret"></b></a>
							<ul class="dropdown-menu inverse">
								<li><a href="#"> Change </a></li>
								<li><a href="#"> Restoration </a></li>
								<li><a href="#"> Monitoring </a></li>							
							</ul></li>
	 -->
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
		<!-- /.container -->
	</div>
	<!-- /.navbar-wrapper -->
	<div class="container">
		<div ng-model="searchRequest"></div>
		<div ng-view></div>

	</div>
	<script src="resources/js/app.js"></script>
	<script src="resources/js/model-controllers.js"></script>
	<script src="resources/js/search-controllers.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="resources/js/directives.js"></script>
	<script src="resources/js/filters.js"></script>
	<script src="resources/js/smart-table.js"></script>

</body>
</html>