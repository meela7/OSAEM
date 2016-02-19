<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>National Aquatic Ecological Monitoring Program API</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link rel="icon" type="image/png"
	href="resources/swagger/images/favicon-32x32.png" sizes="32x32" />
<link rel="icon" type="image/png"
	href="resources/images/favicon-16x16.png" sizes="16x16" />

<link href='resources/swagger/css/typography.css' media='screen'
	rel='stylesheet' type='text/css' />
<link href='resources/swagger/css/reset.css' media='screen'
	rel='stylesheet' type='text/css' />
<link href='resources/swagger/css/screen.css' media='screen'
	rel='stylesheet' type='text/css' />
<link href='resources/swagger/css/reset.css' media='print'
	rel='stylesheet' type='text/css' />
<link href='resources/swagger/css/print.css' media='print'
	rel='stylesheet' type='text/css' />
<script src='resources/swagger/lib/jquery-1.8.0.min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/jquery.slideto.min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/jquery.wiggle.min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/jquery.ba-bbq.min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/handlebars-2.0.0.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/underscore-min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/backbone-min.js'
	type='text/javascript'></script>
<script src='resources/swagger/swagger-ui.js' type='text/javascript'></script>
<script src='resources/swagger/lib/highlight.7.3.pack.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/jsoneditor.min.js'
	type='text/javascript'></script>
<script src='resources/swagger/lib/marked.js' type='text/javascript'></script>
<script src='resources/swagger/lib/swagger-oauth.js'
	type='text/javascript'></script>

<!-- Some basic translations -->
<!-- <script src='lang/translator.js' type='text/javascript'></script> -->
<!-- <script src='lang/ru.js' type='text/javascript'></script> -->
<!-- <script src='lang/en.js' type='text/javascript'></script> -->
<script type="text/javascript">
	$(function() {
		var url = window.location.search.match(/url=([^&]+)/);
		if (url && url.length > 1) {
			url = decodeURIComponent(url[1]);
		} else {
			url = "http://localhost:8080/naemp/v2/api-docs.json";
		}

		// Pre load translate...
		if (window.SwaggerTranslator) {
			window.SwaggerTranslator.translate();
		}
		window.swaggerUi = new SwaggerUi(
				{
					url : url,
					dom_id : "swagger-ui-container",
					supportedSubmitMethods : [ 'get', 'post', 'put', 'delete',
							'patch' ],
					onComplete : function(swaggerApi, swaggerUi) {
						if (typeof initOAuth == "function") {
							initOAuth({
								clientId : "your-client-id",
								clientSecret : "your-client-secret-if-required",
								realm : "your-realms",
								appName : "your-app-name",
								scopeSeparator : ",",
								additionalQueryStringParams : {}
							});
						}

						if (window.SwaggerTranslator) {
							window.SwaggerTranslator.translate();
						}

						$('pre code').each(function(i, e) {
							hljs.highlightBlock(e)
						});

						addApiKeyAuthorization();
					},
					onFailure : function(data) {
						log("Unable to Load SwaggerUI");
					},
					docExpansion : "none",
					jsonEditor : false,
					apisSorter : "alpha",
					defaultModelRendering : 'schema',
					showRequestHeaders : false
				});

		function addApiKeyAuthorization() {
			var key = encodeURIComponent($('#input_apiKey')[0].value);
			if (key && key.trim() != "") {
				var apiKeyAuth = new SwaggerClient.ApiKeyAuthorization(
						"api_key", key, "query");
				window.swaggerUi.api.clientAuthorizations.add("api_key",
						apiKeyAuth);
				log("added key " + key);
			}
		}

		$('#input_apiKey').change(addApiKeyAuthorization);

		// if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
		/*
		  var apiKey = "myApiKeyXXXX123456789";
		  $('#input_apiKey').val(apiKey);
		 */

		window.swaggerUi.load();

		function log() {
			if ('console' in window) {
				console.log.apply(console, arguments);
			}
		}
	});
</script>
</head>

<body>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h5>NAEMP Web Resources</h5>
			</div>
			<div class="panel-body">

				<table class="table table-condensed table-striped">
					<thead>
						<tr>
							<th colspan="2">Major data components</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Living organisms</td>
							<td>All the individual fish and microbial species are
								modeled as resources</td>
						</tr>
						<tr>
							<td>Sites and rivers</td>
							<td>The individual sites where ecological monitoring is
								carried out are also modeled as resources.The rivers are also
								handled as resources.</td>
						</tr>
						<tr>
							<td>Parameters and methods for observation</td>
							<td>The individual parameters are also treated as resources.
								Therefore, this design requires all the observation parameters
								to be explicitly defined. In the same way, the individual
								measurement and analysis methods are managed as resources.</td>
						</tr>
						<tr>
							<td>Observation data</td>
							<td>Each individual instance of observation is modeled as a
								resource.</td>
						</tr>
					</tbody>
				</table>
				
				<table class="table table-condensed table-striped">
					<thead>
						<tr>
							<th>The resources are grouped into two categories:</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><strong>Basic resources.</strong> A basic resource is a
								single instance of data entity (e.g., an individual fish
								species). Basic resources are logically analogous to a database
								record in relational database systems.</td>
						</tr>
						<tr>
							<td><strong>Collection resources.</strong> A collection
								resource is defined to be the set of all the basic resources of
								the same type. Therefore, all the basic resources are grouped
								into collection resources. Collection resources are logically
								analogous to database tables in relational database systems.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h5>NAEMP Web Resources URI Pattern</h5>
			</div>
			<div class="panel-body">
				<table class="table table-condensed table-striped">
					<thead>
						<tr>
							<th>Collection Resources</th>
							<th>URI Pattern</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Fish</td>
							<td>https://db.cilaboratory.org:8080/naemp/fishes/{identifier}</td>
						</tr>
						<tr>
							<td>Benthic Diatom</td>
							<td>https://db.cilaboratory.org:8080/naemp/diatoms/{identifier}</td>
						</tr>
						<tr>
							<td>Macro invertebrates</td>
							<td>https://db.cilaboratory.org:8080/naemp/invertebrates/{identifier}</td>
						</tr>
						<tr>
							<td>Site</td>
							<td>https://db.cilaboratory.org:8080/naemp/sites/{identifier}</td>
						</tr>
						<tr>
							<td>River</td>
							<td>https://db.cilaboratory.org:8080/naemp/rivers/{identifier}</td>
						</tr>
						<tr>
							<td>Variable</td>
							<td>https://db.cilaboratory.org:8080/naemp/variables/{identifier}</td>
						</tr>
						<tr>
							<td>Method</td>
							<td>https://db.cilaboratory.org:8080/naemp/methods/{identifier}</td>
						</tr>
						<tr>
							<td>Source</td>
							<td>https://db.cilaboratory.org:8080/naemp/sources/{identifier}</td>
						</tr>
						<tr>
							<td>Observation</td>
							<td>https://db.cilaboratory.org:8080/naemp/observations/{identifier}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- 
	<div id='header'>
	  <div class="swagger-ui-wrap">
	    <a id="logo" href="http://swagger.io">swagger</a>
	    <form id='api_selector'>
	      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
	      <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
	      <div class='input'><a id="explore" href="#" data-sw-translate>Explore</a></div>
	    </form>
	  </div>
	</div>
 	-->
	<div class="container swagger-section">
		<!-- 	<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>   -->
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>API Documentation</h4>
			</div>
			<div class="panel-body">
				<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
			</div>
		</div>
	</div>
</body>
</html>
