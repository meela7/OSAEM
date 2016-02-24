<script>
	function getValue(id) {
		var data;
		if (id == 'Site')
			data = {
				"siteID" : "int",
				"siteName" : "String",
				"riverID" : "int",
				"longitude" : "String",
				"latitude" : "String",
				"streamOrder" : "int",
				"streamGrade" : "String",
				"address" : "String",
				"standardStructure" : "String",
				"wqmn" : "String",
				"imageLink" : "String",
				"description" : "String"
			};
		else if (id == 'River')
			data = {
				"riverID" : "int",
				"riverName" : "String",
				"basin" : "String",
				"waterSystem" : "String",
				"midWatershed" : "String",
				"subWatershed" : "String",
				"classification" : "String",
				"imageLink" : "String",
				"description" : "String"
			};
		else if (id == 'Fish')
			data = {
				"fishID" : "int",
				"fishClass" : "String",
				"order" : "String",
				"family" : "String",
				"species" : "String",
				"scientificName" : "String",
				"toleranceGuild" : "String",
				"trophicGuild" : "String",
				"habitatGuild" : "String",
				"invasiveSpecies" : "String",
				"endemicSpecies" : "String",
				"endangeredSpecies" : "String",
				"naturalMonument" : "String",
				"imageLink" : "String",
				"description" : "String"
			};
		else if (id == 'Variable')
			data = {
				"variableID" : "int",
				"variableName" : "String",
				"valueType" : "String",
				"unitID" : "int",
				"description" : "String"
			};
		else if (id == 'Unit')
			data = {
				"unitID" : "int",
				"unitName" : "String",
				"unitNameLong" : "String"
			};
		else if (id == 'Source')
			data = {
				"sourceID" : "int",
				"institution" : "String",
				"investigator" : "String",
				"phone" : "String",
				"email" : "String",
				"description" : "String"
			};
		else if (id == 'Method')
			data = {
				"methodID" : "int",
				"methodName" : "String",
				"methodLink" : "String"
			};
		else if (id == 'Query')
			data = {
				"riverName" : ["a", "b"],
				"classification" : ["a"]
			};
		else if (id == 'Create')
			data = {
				"riverID" : "",
				"riverName" : "a",
				"midWatershed" : "b",
				"subWatershed" : "c",
				"classification" : "",
				"imageLink" : "",
				"description" : ""
			};
		else if (id == 'Update')
			data = {
				"riverID" : "1",
				"riverName" : "a",
				"basin" : "b",
				"waterSystem" : "c",
				"midWatershed" : "d'",
				"subWatershed" : "e'",
				"classification" : "f",
				"imageLink" : "",
				"description" : ""
			};
		var x = document.getElementById("collapse" + id);
		x.innerHTML = JSON.stringify(data, undefined, 3);
	}
</script>
<div class="panel panel-info">
	<div class="panel-heading">NAEMP RESTful API Document</div>
	<div class="panel-body">

		<table class="table table-condensed table-striped" style="width: 90%;"
			align="center">
			<thead>
				<tr>
					<th>Collection Resources</th>
					<th>URL</th>
					<th>Instance Resource URL Pattern</th>
					<th id="accordion">Representation Scheme</th>
				</tr>
			</thead>
			<tbody>

				<!-- <tr>
						<td>Benthic Diatom</td>
						<td>https://db.cilaboratory.org:8080/naemp/diatoms</td>
						<td>/diatoms/{identifier}</td>
						<td></td>
					</tr>
					<tr>
						<td>Macro invertebrates</td>
						<td>https://db.cilaboratory.org:8080/naemp/invertebrates</td>
						<td>/invertebrates/{identifier}</td>
						<td></td>
					</tr> -->
				<tr>
					<td>Site</td>
					<td>https://db.cilaboratory.org:8080/naemp/sites</td>
					<td>/sites/{identifier}</td>
					<td>
						<div id="Site" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseSite"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Site
							Presentation Scheme
						</div> <pre id="collapseSite" class="collapse">			 
						</pre>
					</td>
				</tr>
				<tr>
					<td>River</td>
					<td>https://db.cilaboratory.org:8080/naemp/rivers</td>
					<td>/rivers/{identifier}</td>
					<td>
						<div id="River" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseRiver"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> River
							Presentation Scheme
						</div> <pre id="collapseRiver" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Fish</td>
					<td>https://db.cilaboratory.org:8080/naemp/fishes</td>
					<td>/fishes/{identifier}</td>
					<td>
						<div id="Fish" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseFish"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Fish
							Presentation Scheme
						</div> <pre id="collapseFish" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Variable</td>
					<td>https://db.cilaboratory.org:8080/naemp/variables</td>
					<td>/variables/{identifier}</td>
					<td>
						<div id="Variable" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseVariable"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Variable
							Presentation Scheme
						</div> <pre id="collapseVariable" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Unit</td>
					<td>https://db.cilaboratory.org:8080/naemp/units</td>
					<td>/units/{identifier}</td>
					<td>
						<div id="Unit" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseUnit"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Unit
							Presentation Scheme
						</div> <pre id="collapseUnit" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Source</td>
					<td>https://db.cilaboratory.org:8080/naemp/sources</td>
					<td>/sources/{identifier}</td>
					<td>
						<div id="Source" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseSource"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Source
							Presentation Scheme
						</div> <pre id="collapseSource" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Method</td>
					<td>https://db.cilaboratory.org:8080/naemp/methods</td>
					<td>/methods/{identifier}</td>
					<td>
						<div id="Method" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseMethod"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> Method
							Presentation Scheme
						</div> <pre id="collapseMethod" class="collapse">							 
						</pre>
					</td>
				</tr>
			</tbody>
		</table>

		<table class="table table-condensed table-striped" style="width: 90%;"
			align="center">
			<thead>
				<tr>
					<th>Resources </th>
					<th>Services</th>
					<th>HTTP_METHOD</th>
					<th>Request URL</th>
					<th>Request Body</th>
					<th>Response Body</th>
					<th>Example</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Collection Resource </td>
					<td>READ</td>
					<td>GET</td>
					<td>URI_BASE/{collection_resource}</td>
					<td>N/A</td>
					<td>All instance resources</td>
					<td>https://db.cilaboratory.org:8080/naemp/rivers</td>
				</tr>
				<tr>
					<td></td>
					<td>QUERY</td>
					<td>GET</td>
					<td>URI_BASE/{collection_resource}?parameter1={&}parameter1={&}parameter2=...</td>
					<td><span></span>N/A</td>
					<td>List of instance resources</td>
					<td>https://db.cilaboratory.org:8080/naemp/rivers</td>
				</tr>
				<tr>
					<td></td>
					<td>QUERY</td>
					<td>POST</td>
					<td>URI_BASE/{collection_resource}</td>
					<td>Some fields from the Representation Scheme</td>
					<td>List of instance resources</td>
					<td>
						<div id="Query" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseQuery"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> https://db.cilaboratory.org:8080/naemp/rivers
						</div> <pre id="collapseQuery" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td>Instance Resource </td>
					<td>CREATE</td>
					<td>POST</td>
					<td>URI_BASE/{collection_resource}/new</td>
					<td>Required fields from the Representation Scheme</td>
					<td>Identifier of the created instance resource</td>
					<td>
						<div id="Create" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseCreate"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> https://db.cilaboratory.org:8080/naemp/rivers/new
						</div> <pre id="collapseCreate" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>READ</td>
					<td>GET</td>
					<td>URI_BASE/{collection_resource}/{identifier}</td>
					<td>N/A</td>
					<td>The instance resource</td>
					<td>https://db.cilaboratory.org:8080/naemp/rivers/1</td>
				</tr>
				<tr>
					<td></td>
					<td>UPDATE</td>
					<td>PUT</td>
					<td>URI_BASE/{collection_resource}/{identifier}</td>
					<td>All fields from the Representation Scheme</td>
					<td>The instance resource</td>
					<td>
						<div id="Update" class="btn btn-defult" data-toggle="collapse"
							data-parent="#accordion" href="#collapseUpdate"
							onclick="getValue(id)">
							<span class="glyphicon glyphicon-collapse-down"></span> https://db.cilaboratory.org:8080/naemp/rivers/1
						</div> <pre id="collapseUpdate" class="collapse">							 
						</pre>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>DELETE</td>
					<td>DELETE</td>
					<td>URI_BASE/{collection_resource}/{identifier}</td>
					<td>N/A</td>
					<td>N/A</td>
					<td>https://db.cilaboratory.org:8080/naemp/rivers/1</td>
				</tr>

			</tbody>
		</table>
		
		<!-- <table class="table table-condensed table-striped">
			<thead>
				<tr>
					<th colspan="2">Major data components</th>

				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Living organisms</td>
					<td>All the individual fish and microbial species are modeled
						as resources</td>
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
						Therefore, this design requires all the observation parameters to
						be explicitly defined. In the same way, the individual measurement
						and analysis methods are managed as resources.</td>
				</tr>
				<tr>
					<td>Observation data</td>
					<td>Each individual instance of observation is modeled as a
						resource.</td>
				</tr>
			</tbody>
		</table> -->

		<!-- <table class="table table-condensed table-striped">
			<thead>
				<tr>
					<th>The resources are grouped into two categories:</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><strong>Basic resources.</strong> A basic resource is a
						single instance of data entity (e.g., an individual fish species).
						Basic resources are logically analogous to a database record in
						relational database systems.</td>
				</tr>
				<tr>
					<td><strong>Collection resources.</strong> A collection
						resource is defined to be the set of all the basic resources of
						the same type. Therefore, all the basic resources are grouped into
						collection resources. Collection resources are logically analogous
						to database tables in relational database systems.</td>
				</tr>
			</tbody>
		</table> -->
	</div>
</div>

