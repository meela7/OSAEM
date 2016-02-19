/**
 * @File Name: controller.js
 * @Description: Angular JS Controllers
 * 
 * @author Meilan Jiang
 * @since 2016.02.15
 * @version 1.0
 * 
 * Copyright(c) 2016 by CILAB All right reserved.
 */

/**
 * Map Controllers
 */
naempApp.controller('SiteMapCtrl',[
		'$scope',
		'$http',
		function($scope, $http) {

			var addressPointsToMarkers = function(points) {
				return points.map(function(ap) {
					
					return {
						layer : "site data",
						lat : ap.latitude,
						lng : ap.longitude
					};
				});
			};

			angular.extend($scope,{
				center : {
					lat : 36.1813,
					lng : 127.3946,
					zoom : 8
				},
				events : {
					map : {
						enable : [ 'moveend',
								'popupopen' ],
						logic : 'emit'
					},
					marker : {
						enable : [],
						logic : 'emit'
					}
				},
				layers : {
					baselayers : {
						osm : {
							name : 'OpenStreetMap',
							type : 'xyz',
							url : 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
						},
						cycle : {
							name : 'OpenCycleMap',
							type : 'xyz',
							url : 'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png'

						}
					},
					overlays : {
						siteData : {
							name : 'siteData',
							type : 'markercluster',
							visible : true
						}
					}
				}
			});
			
			$http.get("sites").success(function(data) {
					
				var points = data.map(function(d) {
					return {
						layer : "siteData",
						lat : parseFloat(d.latitude),
						lng : parseFloat(d.longitude),
						message : '<div class="panel panel-default"><div class="panel-heading"> <h4>'
								+ d.siteName
								+ '</h4></div><div class="panel-body">'
								+ '<a href="#/site-detail/'
								+ d.siteID
								+ '" class="thumbnail"> <img src="'
								+ d.imageLink
								+ '" class="img-rounded" style="width:260px;height:180px"> <br/> <p>'
								+ d.river.midWatershed
								+ ', '
								+ d.river.subWatershed
								+ '</p></a></div>'
					};
				});

				console.log(points.length);

				angular.extend($scope, {
					markers : points
				});
			});
		} ]);
naempApp.controller('SiteValueMapCtrl',[
   		'$scope',
   		'$routeParams',
   		'ValueSearchService',
   		'$http',
   		function($scope, $routeParams, ValueSearchService, $http) {

   			angular.extend($scope,{
   				center : {
   					lat : 36.1813,
   					lng : 127.3946,
   					zoom : 8
   				},
   				events : {
   					map : {
   						enable : [ 'moveend',
   								'popupopen' ],
   						logic : 'emit'
   					},
   					marker : {
   						enable : [],
   						logic : 'emit'
   					}
   				},
   				layers : {
   					baselayers : {
   						osm : {
   							name : 'OpenStreetMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
   						},
   						cycle : {
   							name : 'OpenCycleMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png'

   						}
   					},
   					overlays : {
   						siteData : {
   							name : 'siteData',
   							type : 'markercluster',
   							visible : true
   						}
   					}
   				}
   			});
   			
   			var points = [];
   			$http.get("sites/list/" + $routeParams.id).success(function(data) {
				
				points = data.map(function(d) {
					return {
						layer : "siteData",
						id : parseInt(d.siteID),
						lat : parseFloat(d.latitude),
						lng : parseFloat(d.longitude),
						message : '<div class="panel panel-default"><div class="panel-heading"> <h4>'
								+ d.siteName + '</h4></div><div class="panel-body"> <a href="#/site-period/' + $routeParams.start + '/' + $routeParams.end + '/' + $routeParams.id 
									+ '" > '+ d.river.midWatershed + ', '+ d.river.subWatershed + '</a>'
//								+ addValue(d.siteID) +'</div></div>'
						};
				});
				
				angular.extend($scope, {
					markers : points
				});	
			});
   			
   			// Data Search 
   			var text = '{"join": ["on"], "dateTime" : ["' + $routeParams.start
					+ ', ' + $routeParams.end + '"],"siteID" : ['
					+ $routeParams.id + ']}';
			var dataObj = JSON.parse(text);
		
   			$http({
   		        method : "POST",
   		        url : "values",
   		        data: dataObj,
   		        headers: {'Content-Type': 'application/json'}
   		    }).success(function(data, status, headers, config) {
	   		 	if( data ) {
	   		 		console.log(data);
	   		 		for(var i = 0; i<points.length; i++){
	   		 			var msg = points[i].message;
	   		 			var point = points[i];
	   		 			var dateList = [];
		   		 		for(var j = 0; j < data.length; ++j){	  						
	  						 var value = data[j];
	  						if(value.siteID.siteID == point.id ){
	  							if(!inArray(value.dateTime, dateList)){
  									dateList.push(value.dateTime);
  									msg = msg + '<hr/><strong>' + value.dateTime.slice(0,10) + '</strong></br>';
  								}
	  							if (value.featureID.featureType == "Water"){	  								
	  								msg = msg + value.variableID.variableName + ':' + value.dataValue + '</br>';
	  								
	  							}
	  							if  (value.featureID.featureType == "Fish"){
	  								msg = msg + value.featureID.featureName + ':' + value.dataValue + '</br>';
	  							}
	  						}
	  					}
		   		 		points[i].message = msg + '</div></div>';
	   		 		}
	   		 		
	   		 		
	   		 		angular.extend($scope, {
	   		 			markers : points
	   		 		});	
	   			}
	   			else {
	   				/* 통신한 URL에서 데이터가 넘어오지 않았을 때 처리 */
	   				console.log("No Response Data");
	   			}
	   		 	
	   		 	
	   		})
	   		.error(function(data, status, headers, config) {
	   			/* 서버와의 연결이 정상적이지 않을 때 처리 */
	   			console.log(status);
	   		});
   			
   		} ]);
naempApp.controller('TermSiteValueMapCtrl',[
   		'$scope',
   		'$routeParams',
   		'ValueSearchService',
   		'$http',
   		function($scope, $routeParams, ValueSearchService, $http) {

   			angular.extend($scope,{
   				center : {
   					lat : 36.1813,
   					lng : 127.3946,
   					zoom : 8
   				},
   				events : {
   					map : {
   						enable : [ 'moveend',
   								'popupopen' ],
   						logic : 'emit'
   					},
   					marker : {
   						enable : [],
   						logic : 'emit'
   					}
   				},
   				layers : {
   					baselayers : {
   						osm : {
   							name : 'OpenStreetMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
   						},
   						cycle : {
   							name : 'OpenCycleMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png'

   						}
   					},
   					overlays : {
   						siteData : {
   							name : 'siteData',
   							type : 'markercluster',
   							visible : true
   						}
   					}
   				}
   			});
   			
   			var points = [];
   			$http.get("sites/list/" + $routeParams.id).success(function(data) {
				
				points = data.map(function(d) {
					return {
						layer : "siteData",
						id : parseInt(d.siteID),
						lat : parseFloat(d.latitude),
						lng : parseFloat(d.longitude),
						message : '<div class="panel panel-default"><div class="panel-heading"> <h4>'
								+ d.siteName + '</h4></div><div class="panel-body"> <a href="#/site-period/' + $routeParams.start + '/' + $routeParams.end + '/' + $routeParams.id 
									+ '" > '+ d.river.midWatershed + ', '+ d.river.subWatershed + '</a>'

						};
				});
				
				angular.extend($scope, {
					markers : points
				});	
			});
   			
   			// Data Search 
   			var text = '{"join": ["on"], "surveyYear" : ["' + $routeParams.year
					+ '"],  "surveyTerm" : ["' + $routeParams.term + '"],"siteID" : ['
					+ $routeParams.id + ']}';
			var dataObj = JSON.parse(text);
		
   			$http({
   		        method : "POST",
   		        url : "values",
   		        data: dataObj,
   		        headers: {'Content-Type': 'application/json'}
   		    }).success(function(data, status, headers, config) {
	   		 	if( data ) {
	   		 		console.log(data);
	   		 		for(var i = 0; i<points.length; i++){
	   		 			var msg = points[i].message + '<hr/> <strong>' + $routeParams.year + ' / ' + $routeParams.term + '</strong><br/> ';
	   		 			var point = points[i];
	   		 		
		   		 		for(var j = 0; j < data.length; ++j){	  						
	  						 var value = data[j];
	  						if(value.siteID.siteID == point.id ){
	  							
	  							if (value.featureID.featureType == "Water"){	  								
	  								msg = msg + value.variableID.variableName + ':' + value.dataValue + '</br>';
	  								
	  							}
	  							if  (value.featureID.featureType == "Fish"){
	  								msg = msg + value.featureID.featureName + ':' + value.dataValue + '</br>';
	  							}
	  						}
	  					}
		   		 		points[i].message = msg + '</div></div>';
	   		 		}
	   		 		
	   		 		
	   		 		angular.extend($scope, {
	   		 			markers : points
	   		 		});	
	   			}
	   			else {
	   				/* 통신한 URL에서 데이터가 넘어오지 않았을 때 처리 */
	   				console.log("No Response Data");
	   			}
	   		 	
	   		 	
	   		})
	   		.error(function(data, status, headers, config) {
	   			/* 서버와의 연결이 정상적이지 않을 때 처리 */
	   			console.log(status);
	   		});
   			
   		} ]);

naempApp.controller('FishValueMapCtrl',[
   		'$scope',
   		'$routeParams',
   		'ValueSearchService',
   		'$http',
   		function($scope, $routeParams, ValueSearchService, $http) {

   			angular.extend($scope,{
   				center : {
   					lat : 36.1813,
   					lng : 127.3946,
   					zoom : 8
   				},
   				events : {
   					map : {
   						enable : [ 'moveend',
   								'popupopen' ],
   						logic : 'emit'
   					},
   					marker : {
   						enable : [],
   						logic : 'emit'
   					}
   				},
   				layers : {
   					baselayers : {
   						osm : {
   							name : 'OpenStreetMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
   						},
   						cycle : {
   							name : 'OpenCycleMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png'

   						}
   					},
   					overlays : {
   						siteData : {
   							name : 'siteData',
   							type : 'markercluster',
   							visible : true
   						}
   					}
   				}
   			});
   			
   			// Data Search 
   			var text = '{"join": ["on"], "dateTime" : ["' + $routeParams.start
					+ ', ' + $routeParams.end + '"],"featureID" : ['
					+ $routeParams.id + ']}';
			var dataObj = JSON.parse(text);
		
			var points = [];
   			$http({
   		        method : "POST",
   		        url : "values",
   		        data: dataObj,
   		        headers: {'Content-Type': 'application/json'}
   		    }).success(function(data, status, headers, config) {
	   		 	if( data ) {
		   		 	var sIDs = [];
	   		 		var siteList = [];
	   		 		for(var i = 0; i < data.length; ++i){	  						
  						var value = data[i];
  						if(!inArray(value.siteID.siteID, sIDs) && value.featureID.featureType == "Fish"){
  							sIDs.push(value.siteID.siteID);
  							siteList.push(value.siteID);
  						}
	   		 		}
	   		 		console.log(siteList.length);
		   		 	points = siteList.map(function(d) {
						return {
							layer : "siteData",
							id : parseInt(d.siteID),
							lat : parseFloat(d.latitude),
							lng : parseFloat(d.longitude),
							message : '<div class="panel panel-default"><div class="panel-heading"> <h4>'
									+ d.siteName + '</h4></div><div class="panel-body"> <a href="#/fish-period/' + $routeParams.start + '/' + $routeParams.end + '/' + $routeParams.id 
										+ '" > '+ d.river.midWatershed + ', '+ d.river.subWatershed + '</a>'
	//                                								+ addValue(d.siteID) +'</div></div>'
							};
					});
		   		 	
		   		 	for(var i = 0; i<points.length; i++){
	   		 			var msg = points[i].message;
	   		 			var point = points[i];
	   		 			var dateList = [];
		   		 		for(var j = 0; j < data.length; ++j){	  						
	  						 var value = data[j];
	  						if(value.siteID.siteID == point.id ){
	  							if(!inArray(value.dateTime, dateList)){
									dateList.push(value.dateTime);
									msg = msg + '<hr/><strong>' + value.dateTime.slice(0,10) + '</strong></br>';
								}
	  							if (value.featureID.featureType == "Fish"){	  								
	  								msg = msg + value.featureID.featureName + ':' + value.dataValue + '</br>';	  								
	  							}
	  							
	  							
	  						}
	  					}
		   		 		points[i].message = msg + '</div></div>';
	   		 		}
		   		 
	   		 		angular.extend($scope, {
	   		 			markers : points
	   		 		});	
	   			}
	   			else {
	   				/* 통신한 URL에서 데이터가 넘어오지 않았을 때 처리 */
	   				console.log("No Response Data");
	   			}
	   		 	
	   		 	
	   		})
	   		.error(function(data, status, headers, config) {
	   			/* 서버와의 연결이 정상적이지 않을 때 처리 */
	   			console.log(status);
	   		});
   			
   		} ]);

naempApp.controller('TermFishValueMapCtrl',[
   		'$scope',
   		'$routeParams',
   		'ValueSearchService',
   		'$http',
   		function($scope, $routeParams, ValueSearchService, $http) {

   			angular.extend($scope,{
   				center : {
   					lat : 36.1813,
   					lng : 127.3946,
   					zoom : 8
   				},
   				events : {
   					map : {
   						enable : [ 'moveend',
   								'popupopen' ],
   						logic : 'emit'
   					},
   					marker : {
   						enable : [],
   						logic : 'emit'
   					}
   				},
   				layers : {
   					baselayers : {
   						osm : {
   							name : 'OpenStreetMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
   						},
   						cycle : {
   							name : 'OpenCycleMap',
   							type : 'xyz',
   							url : 'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png'

   						}
   					},
   					overlays : {
   						siteData : {
   							name : 'siteData',
   							type : 'markercluster',
   							visible : true
   						}
   					}
   				}
   			});
   			
   			// Data Search 
   			var text = '{"join": ["on"], "surveyYear" : ["' + $routeParams.year
					+ '"], "surveyTerm" : ["' + $routeParams.term + '"],"featureID" : ['
					+ $routeParams.id + ']}';
			var dataObj = JSON.parse(text);
		
			var points = [];
   			$http({
   		        method : "POST",
   		        url : "values",
   		        data: dataObj,
   		        headers: {'Content-Type': 'application/json'}
   		    }).success(function(data, status, headers, config) {
	   		 	if( data ) {
		   		 	var sIDs = [];
	   		 		var siteList = [];
	   		 		for(var i = 0; i < data.length; i++){	  						
  						var value = data[i];
  						if(!inArray(value.siteID.siteID, sIDs) && value.featureID.featureType == "Fish"){
  							sIDs.push(value.siteID.siteID);
  							siteList.push(value.siteID);
  						}
	   		 		}
	   		 		console.log(siteList.length);
		   		 	points = siteList.map(function(d) {
						return {
							layer : "siteData",
							id : parseInt(d.siteID),
							lat : parseFloat(d.latitude),
							lng : parseFloat(d.longitude),
							message : '<div class="panel panel-default"><div class="panel-heading"> <h4>'
									+ d.siteName + '</h4></div><div class="panel-body"> <a href="#/fish-period/' + $routeParams.start + '/' + $routeParams.end + '/' + $routeParams.id 
										+ '" > '+ d.river.midWatershed + ', '+ d.river.subWatershed + '</a>'
	//                                								+ addValue(d.siteID) +'</div></div>'
							};
					});
		   		 	
		   		 	for(var i = 0; i<points.length; i++){
	   		 			var msg = points[i].message + '<hr/> <strong>' + $routeParams.year + ' / ' + $routeParams.term + '</strong><br/> ';
	   		 			var point = points[i];
		   		 		for(var j = 0; j < data.length; j++){	  						
	  						 var value = data[j];
	  						if(value.siteID.siteID == point.id ){
	  							
	  							if (value.featureID.featureType == "Fish"){	  								
	  								msg = msg + value.featureID.featureName + ':' + value.dataValue + '</br>';	  								
	  							}
	  							
	  							
	  						}
	  					}
		   		 		points[i].message = msg + '</div></div>';
	   		 		}
		   		 
	   		 		angular.extend($scope, {
	   		 			markers : points
	   		 		});	
	   			}
	   			else {
	   				/* 통신한 URL에서 데이터가 넘어오지 않았을 때 처리 */
	   				console.log("No Response Data");
	   			}
	   		 	
	   		 	
	   		})
	   		.error(function(data, status, headers, config) {
	   			/* 서버와의 연결이 정상적이지 않을 때 처리 */
	   			console.log(status);
	   		});
   			
   		} ]);	

/*
 * Search Controller by Site
 */
naempApp.controller('SearchBySiteCtrl', [
		'$scope',
		'$routeParams',
		'SiteCollectionService',
		'$location',
		function($scope, $routeParams, SiteCollectionService, $location) {
			var year = "";
			$scope.years = [ 2008, 2009, 2010, 2011, 2012, 2013 ];
			$scope.getYear = function() {
				year = $scope.ddlYear;
			};

			var term = "";
			$scope.terms = [ 1, 2 ];
			$scope.getTerm = function() {
				term = $scope.ddlTerm;
			};

			$scope.collection = SiteCollectionService.find();

			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			$scope.searchValue = function() {
				var sIDs = [];
				for (i = 0; i < $scope.displayed.length; i++) {
					var site = $scope.displayed[i];
					sIDs.push(site.siteID);
				}

				var searchType = $scope.radioValue;
				if (searchType == 1) {
					$location.path("/site-term/" + year + "/" + term + "/"
							+ sIDs.toString());
				} else if (searchType == 2) {
					var start = $('#startTime').data('date');
					var end = $('#endTime').data('date');

					$location.path("/site-period/" + start + "/" + end
							+ "/" + sIDs.toString());
				}
			};

		} ]);

naempApp.controller('ValueSearchBySiteCtrl', [
		'$scope',
		'$routeParams',
		'ValueSearchService',
		'$location',
		function($scope, $routeParams, ValueSearchService, $location) {

			var text = '{"join": ["on"], "dateTime" : ["' + $routeParams.start
					+ ', ' + $routeParams.end + '"],"siteID" : ['
					+ $routeParams.id + ']}';
			var obj = JSON.parse(text);
			$scope.collection = ValueSearchService.query(obj);

			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);
			
			$scope.viewInGraph = function() {
				
			};
			
			$scope.viewInMap = function() {
				
				$location.path("/site-period/map/" + $routeParams.start + "/" + $routeParams.end + "/" + $routeParams.id );
			};
		} ]);
naempApp.controller('TermValueSearchBySiteCtrl', [
		'$scope',
		'$routeParams',
		'ValueSearchService',
		'$location',
		function($scope, $routeParams, ValueSearchService, $location) {
			$scope.barChart = false;
			var text = '{"join": ["on"], "surveyYear" : [' + $routeParams.year
					+ '], "surveyTerm" : [' + $routeParams.term
					+ '], "siteID" : [' + $routeParams.id + ']}';
			var obj = JSON.parse(text);
			$scope.collection = ValueSearchService.query(obj);

			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			
//	 		var data = [
//				[
//				 {
//				     "category": [
//				         {
//				             "label": "7/18/2014 9:30:01 AM"
//				         },
//				         {
//				             "label": "7/18/2014 9:40:00 AM"
//				         },
//				         {
//				             "label": "7/18/2014 9:50:00 AM"
//				         }
//				     ]
//				 },
//				 null,
//				 null
//				],
//				[
//				 null,
//				 {
//				     "seriesname": "Free_Memory",
//				     "data": [
//				         {
//				             "value": "6632"
//				         },
//				         {
//				             "value": "5136"
//				         },
//				         {
//				             "value": "6376"
//				         }
//				     ]
//				 },
//				 {
//				     "seriesname": "Page_Life_Exp",
//				     "data": [
//				         {
//				             "value": "48859"
//				         },
//				         {
//				             "value": "49458"
//				         },
//				         {
//				             "value": "50057"
//				         }
//				     ]
//				 }
//				]
//			];
			$scope.categories = [{
			     "category": [
		         {
		             "label": "7/18/2014 9:30:01 AM"
		         }]}];
			$scope.dataset = [{ "seriesname": "Free_Memory",
				     "data": [
				         {
				             "value": "6632"
				         }]}];
					
			$scope.viewInGraph = function() {
				$scope.barChart = true;
				// setting bar chart
				var sIDs = [];
		 		var siteList = [];
		 		var fvNames = [];
		 		var categoryText = '[{"category": [';
		 		for(var i = 0; i < $scope.displayed.length; i++){	  						
					var value = $scope.displayed[i];
					if(!inArray(value.siteID.siteID, sIDs)){
						sIDs.push(value.siteID.siteID);
						siteList.push(value.siteID);
					}
					if(value.featureID.featureType == "Water"){
	 					if(!inArray(value.variableID.variableName, fvNames))
	 						fvNames.push(value.variableID.variableName);
	 				}else if(value.featureID.featureType == "Fish"){
	 					if(!inArray(value.featureID.featureName, fvNames))
	 						fvNames.push(value.featureID.featureName);
	 				}
		 		}
		 		
		 		for(var i = 0; i<siteList.length; i++){
//		 			$scope.categories.category[i].label = siteList[i].siteName;
		 			categoryText += '{"label": "' + siteList[i].siteName + '"}';
		 			if(i == siteList.length-1)
		 				categoryText += ']}]';
		 			else
		 				categoryText += ',';
		 				
		 		}
				
				var datasetText = '[';
		 		
		 			for(var k = 0; k<fvNames.length; k++){
//		 				$scope.dataset[k].seriesName = fvNames[k];
		 				datasetText = datasetText + '{ "seriesname": "' + fvNames[k] + '", "data": ['
				 		
			 			for(var i = 0; i<sIDs.length; i++){
			 				for(var j = 0; j < $scope.displayed.length; ++j){
					 			var value = $scope.displayed[j];
					 			if(value.siteID.siteID == sIDs[i] ){
					 				if(value.variableID.variableName == fvNames[k] || value.featureID.featureName == fvNames[k]){
	//				 					$scope.dataset.data[k].value = value.dataValue;
					 					datasetText += '{"value": ' + value.dataValue + '},';
					 				}
					 			}
				 			}
				 		}
		 				var temp = datasetText.substring(0,datasetText.length-1);
		 				datasetText = temp + ']},';
		 			}
			 	
		 		temp = datasetText.substring(0,datasetText.length-1);
		 		datasetText = temp + ']';
		 		
		 		$scope.categories = JSON.parse(categoryText);
		 		$scope.dataset = JSON.parse(datasetText);
		 		
			}
			
			$scope.viewInMap = function() {				
				$location.path("/site-term/map/" + $routeParams.year + "/" + $routeParams.term + "/" + $routeParams.id );
			}
			
		} ]);

/*
 * Search By Fish
 */
naempApp.controller('SearchByFishCtrl', [
 		'$scope',
 		'$routeParams',
 		'FishCollectionService',
 		'$location',
 		function($scope, $routeParams, FishCollectionService, $location) {
 			var year = "";
 			$scope.years = [ 2008, 2009, 2010, 2011, 2012, 2013 ];
 			$scope.getYear = function() {
 				year = $scope.ddlYear;
 			};

 			var term = "";
 			$scope.terms = [ 1, 2 ];
 			$scope.getTerm = function() {
 				term = $scope.ddlTerm;
 			};

 			$scope.collection = FishCollectionService.find();

 			$scope.itemsByPage = 15;
 			$scope.displayed = [].concat($scope.collection);

 			$scope.searchValue = function() {
 				var fIDs = [];
 				for (var i = 0; i < $scope.displayed.length; i++) {
 					var fish = $scope.displayed[i];
 					fIDs.push(fish.fishID);
 				}

 				var searchType = $scope.radioValue;
 				if (searchType == 1) {
 					$location.path("/fish-term/" + year + "/" + term + "/"
 							+ fIDs.toString());
 				} else if (searchType == 2) {
 					var start = $('#startTime').data('date');
 					var end = $('#endTime').data('date');

 					$location.path("/fish-period/" + start + "/" + end + "/"
 							+ fIDs.toString());
 				}
 			};

 		} ]);
 naempApp.controller('ValueSearchByFishCtrl', [
 		'$scope',
 		'$routeParams',
 		'ValueSearchService',
 		'$location',
 		function($scope, $routeParams, ValueSearchService, $location) {

 			var text = '{"join": ["on"], "dateTime" : ["' + $routeParams.start
 					+ ', ' + $routeParams.end + '"],"featureID" : ['
 					+ $routeParams.id + ']}';
 			var obj = JSON.parse(text);
 			$scope.collection = ValueSearchService.query(obj);

 			$scope.itemsByPage = 15;
 			$scope.displayed = [].concat($scope.collection);
			
 			$scope.viewInMap = function() {
				
				$location.path("/fish-period/map/" + $routeParams.start + "/" + $routeParams.end + "/" + $routeParams.id );
			};
 		} ]);
 naempApp.controller('TermValueSearchByFishCtrl', [
 		'$scope',
 		'$routeParams',
 		'ValueSearchService',
 		'$location',
 		function($scope, $routeParams, ValueSearchService, $location) {

 			var text = '{"join": ["on"], "surveyYear" : [' + $routeParams.year
 					+ '],' + '"surveyTerm" : [' + $routeParams.term
 					+ '], "featureID" : [' + $routeParams.id + ']}';
 			var obj = JSON.parse(text);
 			$scope.collection = ValueSearchService.query(obj);

 			$scope.itemsByPage = 15;
 			$scope.displayed = [].concat($scope.collection);
 			
 			$scope.viewInMap = function() {
				
				$location.path("/fish-term/map/" + $routeParams.year + "/" + $routeParams.term + "/" + $routeParams.id );
			};
 		} ]);

/**
 * Program Data Management
 * 
 * Source Controller
 */
naempApp.controller('SourceListCtrl',
		[
				'$scope',
				'SourceCollectionService',
				'SourceService',
				'$location',
				function($scope, SourceCollectionService, SourceService,
						$location) {

					$scope.cancel = function() {
						$scope.search = "";
					};
					// callback for ng-click 'editSource':
					$scope.editSource = function(sourceID) {
						$location.path('/source-detail/' + sourceID);
					};
					// callback for ng-click 'deleteSource':
					$scope.deleteSource = function(sourceID) {
						SourceService.remove(sourceID);
						$location.path('/source-list');
					};
					// callback for ng-click 'createSource':
					$scope.createSource = function() {
						$location.path('/source-creation');
					};
					$scope.sources = SourceCollectionService.find();
					// panination
					$scope.currentPage = 1, $scope.numPerPage = 10,
							$scope.maxSize = 10;

				} ]);
naempApp.controller('SourceDetailCtrl', [ '$scope', '$routeParams',
		'SourceService', '$location',
		function($scope, $routeParams, SourceService, $location) {
			// callback for ng-click 'updateSource':
			$scope.updateSource = function() {
				SourceService.update($scope.source);
				$location.path('/source-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/source-list');
			};
			$scope.source = SourceService.find({
				id : $routeParams.id
			});

		} ]);
naempApp.controller('SourceCreationCtrl', [ '$scope', 'NewSourceService',
		'$location', function($scope, NewSourceService, $location) {
			// callback for ng-click 'createNewSource':
			$scope.createSource = function() {
				NewSourceService.create($scope.source);
				$location.path('/source-list');
			};
			$scope.cancel = function() {
				$location.path('/source-list');
			};
		} ]);

/**
 * Site Controller
 */
naempApp
		.controller('SiteListCtrl',
				[
						'$scope',
						'SiteCollectionService',
						'SiteService',
						'$location',
						function($scope, SiteCollectionService, SiteService,
								$location) {

							// callback for ng-click 'editSite':
							$scope.editSite = function(siteID) {
								$location.path('/site-detail/' + siteID);
							};
							// callback for ng-click 'deleteSite':
							$scope.deleteSite = function(siteID) {
								SiteService.remove({
									id : siteID
								});
								$location.path('/site-list');
							};
							// callback for ng-click 'createSite':
							$scope.createSite = function() {
								$location.path('/site-creation');
							};

							$scope.cancel = function() {
								$scope.search = "";
							};

							$scope.sites = SiteCollectionService.find();

							// panination
							$scope.currentPage = 1, $scope.numPerPage = 10,
									$scope.maxSize = 10;

						} ]);

naempApp.controller('SiteDetailCtrl', [ '$scope', '$routeParams',
		'SiteService', '$location',
		function($scope, $routeParams, SiteService, $location) {
			// callback for ng-click 'updateSite':
			$scope.updateSite = function() {
				SiteService.update($scope.site, function() {
					id: site.siteID
				});
				$location.path('/site-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/site-list');
			};
			$scope.site = SiteService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('SiteCreationCtrl', [ '$scope', 'NewSiteService',
		'$location', function($scope, NewSiteService, $location) {
			// callback for ng-click 'createSite':
			$scope.createSite = function() {
				NewSiteService.create($scope.site);
				$location.path('/site-list');
			};
			$scope.cancel = function() {
				$location.path('/site-list');
			};
		} ]);

/**
 * River Controller
 */
naempApp.controller('RiverListCtrl',
		[
				'$scope',
				'RiverCollectionService',
				'RiverService',
				'$location',
				function($scope, RiverCollectionService, RiverService,
						$location) {

					// callback for ng-click 'editRiver':
					$scope.editRiver = function(riverID) {
						$location.path('/river-detail/' + riverID);
					};
					// callback for ng-click 'deleteRiver':
					$scope.deleteRiver = function(riverID) {
						RiverService.remove(riverID);
						$location.path('/river-list');
					};
					// callback for ng-click 'createRiver':
					$scope.createRiver = function() {
						$location.path('/river-creation');
					};

					$scope.cancel = function() {

						$scope.search = "";
					};

					$scope.rivers = RiverCollectionService.find();

					// panination
					$scope.currentPage = 1, $scope.numPerPage = 10,
							$scope.maxSize = 10;

				} ]);

naempApp.controller('RiverDetailCtrl', [ '$scope', '$routeParams',
		'RiverService', '$location',
		function($scope, $routeParams, RiverService, $location) {
			// callback for ng-click 'updateRiver':
			$scope.updateRiver = function() {
				RiverService.update($scope.river);
				$location.path('/river-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/river-list');
			};
			$scope.river = RiverService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('RiverCreationCtrl', [ '$scope', 'NewRiverService',
		'$location', function($scope, NewRiverService, $location) {
			// callback for ng-click 'createRiver':
			$scope.createRiver = function() {
				NewRiverService.create($scope.river);
				$location.path('/river-list');
			};
			$scope.cancel = function() {
				$location.path('/river-list');
			};
		} ]);

/**
 * Fishes
 */
naempApp
		.controller('FishListCtrl',
				[
						'$scope',
						'FishCollectionService',
						'FishService',
						'$location',
						function($scope, FishCollectionService, FishService,
								$location) {
							$scope.cancel = function() {
								$scope.search = "";
							};
							// callback for ng-click 'editFish':
							$scope.editFish = function(fishID) {
								$location.path('/fish-detail/' + fishID);
							};
							// callback for ng-click 'deleteFish':
							$scope.deleteFish = function(fishID) {
								FishService.remove(fishID);
								$location.path('/fish-list');
							};
							// callback for ng-click 'createFish':
							$scope.createFish = function() {
								$location.path('/fish-creation');
							};
							$scope.fishes = FishCollectionService.find();

							// panination
							$scope.currentPage = 1, $scope.numPerPage = 10,
									$scope.maxSize = 10;

						} ]);
naempApp.controller('FishDetailCtrl', [ '$scope', '$routeParams',
		'FishService', '$location',
		function($scope, $routeParams, FishService, $location) {
			// callback for ng-click 'updateFish':
			$scope.updateFish = function() {
				FishService.update($scope.fish);
				$location.path('/fish-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/fish-list');
			};
			$scope.fish = FishService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('FishCreationCtrl', [ '$scope', 'NewFishService',
		'$location', function($scope, NewFishService, $location) {
			// callback for ng-click 'createNewFish':
			$scope.createFish = function() {
				NewFishService.create($scope.fish);
				$location.path('/fish-list');
			};
			$scope.cancel = function() {
				$location.path('/fish-list');
			};
		} ]);

/**
 * Methods
 */

naempApp.controller('MethodListCtrl',
		[
				'$scope',
				'MethodCollectionService',
				'MethodService',
				'$location',
				function($scope, MethodCollectionService, MethodService,
						$location) {
					$scope.cancel = function() {
						$scope.search = "";
					}
					// callback for ng-click 'editMethod':
					$scope.editMethod = function(methodID) {
						$location.path('/method-detail/' + methodID);
					};

					// callback for ng-click 'deleteMethod':
					$scope.deleteMethod = function(methodID) {
						MethodService.remove(methodID);
						$location.path('/method-list');
					};
					// callback for ng-click 'createMethod':
					$scope.createMethod = function() {
						$location.path('/method-creation');
					};
					$scope.methods = MethodCollectionService.find();

					// panination
					$scope.currentPage = 1, $scope.numPerPage = 10,
							$scope.maxSize = 10;

				} ]);
naempApp.controller('MethodDetailCtrl', [ '$scope', '$routeParams',
		'MethodService', '$location',
		function($scope, $routeParams, MethodService, $location) {
			// callback for ng-click 'updateFish':
			$scope.updateMethod = function() {
				MethodService.update($scope.method);
				$location.path('/method-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/method-list');
			};
			$scope.method = MethodService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('MethodCreationCtrl', [ '$scope', 'NewMethodService',
		'$location', function($scope, NewMethodService, $location) {
			// callback for ng-click 'createNewFish':
			$scope.createMethod = function() {
				NewMethodService.create($scope.method);
				$location.path('/method-list');
			};
			$scope.cancel = function() {
				$location.path('/method-list');
			};
		} ]);

/**
 * Variables
 */
naempApp.controller('VariableListCtrl',
		[
				'$scope',
				'VariableCollectionService',
				'VariableService',
				'$location',
				function($scope, VariableCollectionService, VariableService,
						$location) {
					$scope.cancel = function() {
						$scope.search = "";
					}
					// callback for ng-click 'editVariable':
					$scope.editVariable = function(variableID) {
						$location.path('/variable-detail/' + variableID);
					};

					// callback for ng-click 'deleteVariable':
					$scope.deleteVariable = function(variableID) {
						VariableService.remove(variableID);
						$location.path('/variable-list');
					};
					// callback for ng-click 'createVariable':
					$scope.createVariable = function() {
						$location.path('/variable-creation');
					};
					$scope.variables = VariableCollectionService.find();

					// panination
					$scope.currentPage = 1, $scope.numPerPage = 10,
							$scope.maxSize = 10;

				} ]);
naempApp.controller('VariableDetailCtrl', [ '$scope', '$routeParams',
		'VariableService', '$location',
		function($scope, $routeParams, VariableService, $location) {
			// callback for ng-click 'updateFeaturee':
			$scope.updateVariable = function() {
				VariableService.update($scope.variable);
				$location.path('/variable-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/variable-list');
			};
			$scope.variable = VariableService.find({
				id : $routeParams.id
			});

		} ]);
naempApp.controller('VariableCreationCtrl', [ '$scope', 'NewVariableService',
		'$location', function($scope, NewVariableService, $location) {
			// callback for ng-click 'createNewFeature':
			$scope.createVariable = function() {
				NewVariableService.create($scope.variable);
				$location.path('/variable-list');
			};
			$scope.cancel = function() {
				$location.path('/variable-list');
			};
		} ]);

/**
 * Units
 */

naempApp
		.controller('UnitListCtrl',
				[
						'$scope',
						'UnitCollectionService',
						'UnitService',
						'$location',
						function($scope, UnitCollectionService, UnitService,
								$location) {
							$scope.cancel = function() {
								$scope.search = "";
							}
							// callback for ng-click 'editUnit':
							$scope.editUnit = function(unitID) {
								$location.path('/unit-detail/' + unitID);
							};

							// callback for ng-click 'deleteUnit':
							$scope.deleteUnit = function(unitID) {
								UnitService.del(unitID);
								$location.path('/unit-list');
							};
							// callback for ng-click 'createUnit':
							$scope.createUnit = function() {
								$location.path('/unit-creation');
							};
							$scope.units = UnitCollectionService.find();

							// panination
							$scope.currentPage = 1, $scope.numPerPage = 10,
									$scope.maxSize = 10;

						} ]);
naempApp.controller('UnitDetailCtrl', [ '$scope', '$routeParams',
		'UnitService', '$location',
		function($scope, $routeParams, UnitService, $location) {
			// callback for ng-click 'updateFeaturee':
			$scope.updateUnit = function() {
				UnitService.update($scope.unit);
				$location.path('/unit-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/unit-list');
			};
			$scope.unit = UnitService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('UnitCreationCtrl', [ '$scope', 'NewUnitService',
		'$location', function($scope, NewUnitService, $location) {
			// callback for ng-click 'createNewFeature':
			$scope.createUnit = function() {
				NewUnitService.create($scope.unit);
				$location.path('/unit-list');
			};
			$scope.cancel = function() {
				$location.path('/unit-list');
			};
		} ]);
