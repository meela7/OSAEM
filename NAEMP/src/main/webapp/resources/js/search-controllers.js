naempApp.controller('MapAllCtrl', [
		'$scope',
		'$routeParams',
		'SitesService',
		function($scope, $routeParams, SitesService) {
			var mapCenter = new google.maps.LatLng(36.1813, 127.3946);
			$scope.mapOptions = {
				center : mapCenter,
				zoom : 8,
				mapTypeId : google.maps.MapTypeId.MAP
			};

			var sites = SitesService.findall();

			// Markers should be added after map is loaded
			$scope.onMapIdle = function() {
				if ($scope.myMarkers === undefined) {
					var markers = [];
					for (var i = 0; i < sites.length; i++) {
						// alert(sites[i].siteName);
						var pos = new google.maps.LatLng(sites[i].latitude,
								sites[i].longitude);

						var marker = new google.maps.Marker({
							map : $scope.myMap,
							position : pos,
							title : sites[i].siteName
						});
						markers.push(marker);
					}
					// $scope.myMarkers = [marker, ];
					$scope.myMarkers = markers;
				}
			};

			$scope.openInfoWindow = function(e, selectedMarker) {
				e.preventDefault();
				google.maps.event.trigger(selectedMarker, 'click');
			};
			$scope.markerClicked = function(m) {
				alert(m.title);
			};
		} ]);

naempApp.controller('MapCtrl', [
		'$scope',
		'$routeParams',
		'SiteService',
		function($scope, $routeParams, SiteService) {
			var mapCenter = new google.maps.LatLng(36.1813, 127.3946);
			$scope.mapOptions = {
				center : mapCenter,
				zoom : 8,
				mapTypeId : google.maps.MapTypeId.MAP
			};

			var sites = SiteService.find({
				id : $routeParams.id
			});

			// Markers should be added after map is loaded
			$scope.onMapIdle = function() {
				if ($scope.myMarkers === undefined) {
					var markers = [];
					for (var i = 0; i < sites.length; i++) {
						// alert(sites[i].siteName);
						var pos = new google.maps.LatLng(sites[i].latitude,
								sites[i].longitude);

						var marker = new google.maps.Marker({
							map : $scope.myMap,
							position : pos,
							title : sites[i].siteName
						});
						markers.push(marker);
					}
					// $scope.myMarkers = [marker, ];
					$scope.myMarkers = markers;
				}
			};

			$scope.openInfoWindow = function(e, selectedMarker) {
				e.preventDefault();
				google.maps.event.trigger(selectedMarker, 'click');
			};
			$scope.markerClicked = function(m) {
				alert(m.title);
			};
		} ]);

naempApp.controller('FeatureSearchCtrl',
		[
				'$scope',
				'FeaturesService',
				'$location',
				function($scope, FeaturesService, $location) {

					$scope.features = FeaturesService.findall();

					// panination
					$scope.currentPage = 1, $scope.numPerPage = 10,
							$scope.maxSize = 10;

					$scope.searchFishByFeature = function() {
						// alert("called searchFishBySite" +$scope.year);
						var fIDs = [];
						for (var i = 0; i < $scope.searchResult.length; i++) {
							var feature = $scope.searchResult[i];
							// alert(river.riverID);
							fIDs.push(feature.featureID);
						}
						$location.path("/feature-fish/" + fIDs.toString());
					};
				} // alert("RiverSiteCtrl : " + $routeParams.id);
		]);

naempApp.controller('FishSearchCtrl', [
		'$scope',
		'$routeParams',
		'FishesService',
		'FishListFind',
		'$location',
		function($scope, $routeParams, FishesService, FishListFind, $location) {
			$scope.radioValue = 1;

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

			if ($routeParams.id == null) {
				$scope.collection = FishesService.findall();
			} else {
				$scope.collection = FishListFind.find({
					id : $routeParams.id
				});
				$scope.back = true;
			}
			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			$scope.searchFish = function() {
				var fIDs = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var fish = $scope.displayed[i];
					fIDs.push(fish.fishID);
				}
				$location.path("/fish-search/" + fIDs.toString());
			};
			$scope.searchByFish = function() {
				var fIDs = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var fish = $scope.displayed[i];
					fIDs.push(fish.fishID);
				}

				var timeType = $scope.radioValue;
				if (timeType == 1) {
					if (year == null || term == null)
						alert("Please select DateTime first!");
					else
						$location.path("/fish-value/term/" + year + "/" + term
								+ "/" + fIDs.toString());
				} else if (timeType == 2) {
					var start = $('#startTime').data('date');
					var end = $('#endTime').data('date');
					if (start == null || end == null)
						alert("Please select DateTime first!");
					else
						$location.path("/fish-value/" + start + "/" + end + "/"
								+ fIDs.toString());
				}

			};
		} ]);
naempApp.controller('FishValueCtrl', [
		'$scope',
		'$routeParams',
		'ValueByFish',
		'$location',
		function($scope, $routeParams, ValueByFish, $location) {

			var start = $routeParams.start;
			var end = $routeParams.end;

			$scope.collection = ValueByFish.find({
				'start' : $routeParams.start,
				'end' : $routeParams.end,
				'id' : $routeParams.id
			});
			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			$scope.viewInGraph = function() {
				$scope.chart1 = true;
				var survey = [];
				var series = [];

				fName = $scope.displayed[0].feature.featureName;
				var start = $routeParams.start.slice(0, 4);
				var end = $routeParams.end.slice(0, 4);
				for (var i = 0; i < end - start + 1; i++) {
					if (0 == end - start) {
						if ($routeParams.start.slice(5, 7) < 4
								&& $routeParams.end.slice(5, 7) > 10) {
							survey.push({
								"year" : Number(start) + i,
								"term" : 1
							});
							survey.push({
								"year" : Number(start) + i,
								"term" : 2
							});
						} else if ($routeParams.start.slice(5, 7) > 8) {
							survey.push({
								"year" : Number(start) + i,
								"term" : 2
							});
						} else if ($routeParams.end.slice(5, 7) < 8) {
							survey.push({
								"year" : Number(start) + i,
								"term" : 1
							});
						}
					} else if (i == 0) {
						if ($routeParams.start.slice(5, 7) < 9) {
							survey.push({
								"year" : Number(start) + i,
								"term" : 1
							});
							survey.push({
								"year" : Number(start) + i,
								"term" : 2
							});
						} else
							survey.push({
								"year" : Number(start) + i,
								"term" : 2
							});
					} else if (end - start == i) {
						if ($routeParams.end.slice(5, 7) < 4) {

						} else if ($routeParams.end.slice(5, 7) < 9)
							survey.push({
								"year" : Number(start) + i,
								"term" : 1
							});
						else
							survey.push({
								"year" : Number(start) + i,
								"term" : 2
							});
					} else {
						survey.push({
							"year" : Number(start) + i,
							"term" : 1
						});
						survey.push({
							"year" : Number(start) + i,
							"term" : 2
						});
					}
				}

				var sNames = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var value = $scope.displayed[i];
					if (!inArray(value.site.siteName, sNames))
						sNames.push(value.site.siteName);
				}

				var sumData = [];
				var siteNum = [];
				for (var k = 0; k < survey.length; k++) {
					var sum = 0;
					var stNum = 0;
					for (var i = 0; i < $scope.displayed.length; i++) {
						var value = $scope.displayed[i];
						if (value.surveyYear == survey[k].year
								&& value.surveyTerm == survey[k].term
								&& value.feature.featureName == fName
								&& value.dataValue != 0) {
							sum += value.dataValue;
							stNum += 1;
						}
					}
					sumData.push(sum);
					siteNum.push(stNum);
				}

				series.push({
					"type" : "spline",
					"name" : "Sum",
					"data" : sumData
				});
				series.push({
					"type" : "spline",
					"name" : "Sites #",
					"data" : siteNum
				});
				var name = '';
				for (var k = 0; k < sNames.length; k++) {
					var data = [];
					for (var j = 0; j < survey.length; j++) {

						for (var i = 0; i < $scope.displayed.length; i++) {
							var value = $scope.displayed[i];
							if (value.feature.featureName == fName
									&& value.site.siteName == sNames[k]
									&& value.surveyYear == survey[j].year
									&& value.surveyTerm == survey[j].term)
								data.push(value.dataValue);
						}
					}
					if (data.length > 0) {
						series.push({
							"type" : "column",
							"name" : sNames[k],
							"data" : data
						});
					}

				}

				$scope.highchartsNG = {
					exporting : {
						chartOptions : { // specific options for the exported
							// image
							plotOptions : {
								series : {
									dataLabels : {
										enabled : true
									}
								}
							}
						},
						fallbackToExportServer : false
					},
					chart : {
						height: 800
					},
					title : {
						text : fName
					},
					option : {
						height : 800
					},
					xAxis : {
						categories : survey,
						labels : {
							formatter : function() {
								return this.value.year + "/" + this.value.term;
							}
						},
						crosshair : true
					},
					yAxis : {
						title : {
							text : ''
						},
						min : 0,
						labels : {
							formatter : function() {
								return this.value;
							}
						}
					},
					// tooltip: {
					// headerFormat: '<span
					// style="font-size:10px">{series.name}</span><table>',
					// pointFormat: '<tr><td
					// style="color:{series.color};padding:0">{point.x}: </td>'
					// +
					// '<td style="padding:0"><b>{point.y:.1f}
					// mm</b></td></tr>',
					// footerFormat: '</table>',
					// shared: true,
					// useHTML: true
					// },
					plotOptions : {
						column : {
							pointPadding : 0.2,
							borderWidth : 0
						}
					},
					series : series
				};
			};
			$scope.viewInMap = function() {
				var fIDs = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var value = $scope.displayed[i];
					if (!inArray(value.feature.featureID, fIDs))
						fIDs.push(value.feature.featureID);
				}
				$location.path("/fish-map/" + start + "/" + end + "/"
						+ fIDs.toString());
			};

		} ]);
naempApp.controller('FishTermValueCtrl', [
		'$scope',
		'$routeParams',
		'TermValueByFish',
		'$location',
		function($scope, $routeParams, TermValueByFish, $location) {
			var year = $routeParams.year;
			var term = $routeParams.term;
			$scope.collection = TermValueByFish.find({
				year : $routeParams.year,
				term : $routeParams.term,
				id : $routeParams.id
			});
			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			$scope.viewInMap = function() {
				var sIDs = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var value = $scope.displayed[i];
					if (!inArray(value.site.siteID, sIDs))
						sIDs.push(value.site.siteID);
				}
				if (year == null || term == null)
					alert("Please select DateTime first!");
				else
					$location.path("/site-map/term/" + year + "/" + term + "/"
							+ sIDs.toString());
			};

			$scope.viewInGraph = function() {
				$scope.barChart = true;
				var fNames = [];
				var sNames = [];
				var series = [];

				for (var i = 0; i < $scope.displayed.length; i++) {
					var value = $scope.displayed[i];
					if (!inArray(value.site.siteName, sNames)) {
						sNames.push(value.site.siteName);
					}

					if (!inArray(value.feature.featureName, fNames)) {

						fNames.push(value.feature.featureName);
					}
				}
				alert(fNames.length);
				var sumList = [];
				var siteNum = [];
				for (var k = 0; k < fNames.length; k++) {
					var sum = 0;
					var stNum = 0;
					for (var i = 0; i < $scope.displayed.length; i++) {
						var value = $scope.displayed[i];
						if (value.feature.featureName == fNames[k]
								&& value.dataValue != 0) {
							sum += value.dataValue;
							stNum += 1;
						}
					}
					sumList.push(sum);
					siteNum.push(stNum);
				}
				series.push({
					"type" : "spline",
					"name" : "Sum",
					"data" : sumList
				});
				series.push({
					"type" : "spline",
					"name" : "Sites #",
					"data" : siteNum
				});
				var name = '';
				for (var k = 0; k < sNames.length; k++) {
					var data = [];
					for (var j = 0; j < fNames.length; j++) {
						var fval = 0;
						for (var i = 0; i < $scope.displayed.length; i++) {
							var value = $scope.displayed[i];
							if (value.site.siteName == sNames[k]
									&& value.feature.featureName == fNames[j])
								fval = value.dataValue;
						}
						data.push(fval);
					}
					series.push({
						"type" : "column",
						"name" : sNames[k],
						"data" : data
					});
				}

				$scope.highchartsNG = {
					exporting : {
						chartOptions : { // specific options for the exported
							// image
							plotOptions : {
								series : {
									dataLabels : {
										enabled : true
									}
								}
							}
						},
						fallbackToExportServer : false
					},
					chart : {
						height: 800
					},
					title : {
						text : term + "/" + year
					},					
					xAxis : {
						categories : fNames,
						crosshair : true

					},
					yAxis : {
						title : {
							text : 'Value'
						},
						min : 0,
						labels : {
							formatter : function() {
								return this.value;
							}
						}
					},
					// tooltip: {
					// headerFormat: '<b>{series.name}</b><br>',
					// pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
					// },
					plotOptions : {
						column : {
							pointPadding : 0.2,
							borderWidth : 0
						}
					},

					series : series
				};
			};
		} ]);
naempApp.controller('RiverSearchCtrl',
		[
				'$scope',
				'$routeParams',
				'RiversService',
				'RiverListFind',
				'$location',
				function($scope, $routeParams, RiversService, RiverListFind,
						$location) {

					if ($routeParams.id == null) {
						$scope.collection = RiversService.findall();
						$scope.query = 'ALL';
					} else {
						$scope.collection = RiverListFind.find({
							id : $routeParams.id
						});
						$scope.back = true;
						$scope.query = decodeURIComponent($routeParams.query);
					}

					$scope.itemsByPage = 15;
					$scope.displayed = [].concat($scope.collection);

					$scope.searchRiver = function() {
						var rIDs = [];
						for (var i = 0; i < $scope.displayed.length; i++) {
							var river = $scope.displayed[i];
							if (!inArray(river.riverID, rIDs)) {
								rIDs.push(river.riverID);
							}
						}
						$location.path("/river-search/" + rIDs.toString());
					};

					$scope.searchSiteByRiver = function() {

						var rIDs = [];
						for (var i = 0; i < $scope.displayed.length; i++) {
							var river = $scope.displayed[i];
							rIDs.push(river.riverID);
						}
						// alert("riverIDs: " +riverIDs);
						$location.path("/river-site/" + rIDs.toString());
						// set riverID to search site-list, need to add
						// controller for this.

					};
				} ]);

naempApp.controller('RiverSiteInfoCtrl', [
		'$scope',
		'$routeParams',
		'SiteInfoByRiver',
		'$location',
		function($scope, $routeParams, SiteInfoByRiver, $location) {
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
			$scope.collection = SiteInfoByRiver.find({
				id : $routeParams.id
			});

			$scope.itemsByPage = 15;
			$scope.displayed = [].concat($scope.collection);

			$scope.searchValue = function() {
				// alert("called searchFishBySite: " +$scope.year);
				var sIDs = [];
				for (var i = 0; i < $scope.displayed.length; i++) {
					var site = $scope.displayed[i];
					// alert(river.riverID);
					sIDs.push(site.siteID);
				}
				$location.path("/site-value/term/" + year + "/" + term + "/"
						+ sIDs.toString());
			}
		} ]);
naempApp.controller('SiteSearchCtrl',
		[
				'$scope',
				'$routeParams',
				'SiteInfosService',
				'SiteInfoListFind',
				'$location',
				function($scope, $routeParams, SiteInfosService,
						SiteInfoListFind, $location) {
					var year = "";
					var term = "";

					if ($routeParams.id == null) {
						$scope.collection = SiteInfosService.findall();
						$scope.query = 'ALL';
					} else {
						$scope.collection = SiteInfoListFind.find({
							id : $routeParams.id
						});
						$scope.back = true;
						$scope.query = decodeURIComponent($routeParams.query);
					}

					// $scope.years = SurveyYear.find();
					$scope.years = [ 2008, 2009, 2010, 2011, 2012, 2013 ];
					$scope.ddlYear = "Year";
					$scope.terms = [ 1, 2 ];
					$scope.ddlTerm = "Term";

					// default 설정
					$scope.radioValue = 1;

					$scope.getYear = function() {
						year = $scope.ddlYear;
					};

					$scope.getTerm = function() {
						term = $scope.ddlTerm;
					};

					$scope.itemsByPage = 15;
					$scope.displayed = [].concat($scope.collection);

					$scope.searchSite = function() {
						var sIDs = [];
						for (var i = 0; i < $scope.displayed.length; i++) {
							var site = $scope.displayed[i];
							sIDs.push(site.siteID);
						}
						// var query = "ID-" + $scope.idSearch;
						// var query = "Site-" + $scope.siteSearch;
						// var query = "River-" + $scope.riverSearch;
						var query = "Basin";

						$location.path("/site-search/" + sIDs.toString() + "/"
								+ query);
					};
					$scope.searchValue = function() {
						var sIDs = [];
						for (var i = 0; i < $scope.displayed.length; i++) {
							var site = $scope.displayed[i];
							sIDs.push(site.siteID);
						}

						var timeType = $scope.radioValue;
						if (timeType == 1) {
							$location.path("/site-value/term/" + year + "/"
									+ term + "/" + sIDs.toString());
						} else if (timeType == 2) {
							var start = $('#startTime').data('date');
							var end = $('#endTime').data('date');

							$location.path("/site-value/" + start + "/" + end
									+ "/" + sIDs.toString());
						}
					};
					$scope.viewInMap = function() {
						var sIds = [];
						for (var i = 0; i < $scope.displayed.length; i++) {
							var site = $scope.displayed[i];
							sIds.push(site.siteID);
						}
						$location.path("/site-map/" + sIds.toString());
					};
				} ]);
naempApp
		.controller(
				'MapTermValueCtrl',
				[
						'$scope',
						'$routeParams',
						'TermValueBySite',
						function($scope, $routeParams, TermValueBySite) {
							$scope.$on('mapInitialized', function(event, map) {
								$scope.objMapa = map;
							});

							var values = TermValueBySite.find({
								year : $routeParams.year,
								term : $routeParams.term,
								id : $routeParams.id
							});

							$scope.positions = function() {
								var sites = [];
								for (var i = 0; i < values.length; i++) {
									if (!inArray(values[i].site, sites))
										sites.push(values[i].site);
								}
								return sites;
							};

							$scope.showInfoWindow = function(event, pos) {
								var infowindow = new google.maps.InfoWindow();
								var position = new google.maps.LatLng(
										pos.latitude, pos.longitude);
								var individualNum = 0;
								var content = '<div class="panel panel-default"><div class="panel-heading"> <h4>'
										+ pos.siteName
										+ '</h4></div><div class="panel-body">';
								var fishNum = 0;
								var wqNum = 0;
								var fishContent = '';
								var waterContent = '';
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										fishContent = fishContent + "<p>"
												+ value.feature.featureName
												+ ": " + value.dataValue
												+ "</p>";
										fishNum++;
										individualNum += value.dataValue;
									} else if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Water") {
										waterContent = waterContent
												+ "<p>"
												+ value.variable.variableName.term
												+ ": " + value.dataValue
												+ "</p>";
										wqNum++;
									}
								}
								fishContent = '<p><strong> 총 종수:  ' + fishNum
										+ ', 총 개체수: ' + individualNum
										+ ' </strong></p>' + fishContent;
								if (fishNum != 0 && wqNum != 0)
									content = content
											+ '<table class="table table-bordered table-striped"><thead><tr><th>Fish</th><th>수질</th></tr><thead>'
											+ '<tbody><tr><td>'
											+ fishContent
											+ '</td>'
											+ '<td>'
											+ waterContent
											+ '</td></tr></tbody></table></div>';
								else
									content = content + fishContent
											+ waterContent + "</div>";

								infowindow.setContent(content);

								infowindow.setPosition(position);
								infowindow.open($scope.objMapa);
							};

							$scope.getRadius = function(pos) {
								var individualNum = 0;
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										individualNum += value.dataValue;
									}
								}
								return individualNum;
							};
						
						} ]);
naempApp
		.controller(
				'MapSiteCtrl',
				[
						'$scope',
						'$routeParams',
						'ValueBySite',
						function($scope, $routeParams, ValueBySite) {
							$scope.$on('mapInitialized', function(event, map) {
								$scope.objMapa = map;
							});

							var values = ValueBySite.find({
								start : $routeParams.start,
								end : $routeParams.end,
								id : $routeParams.id
							});

							$scope.positions = function() {
								var sites = [];
								for (var i = 0; i < values.length; i++) {
									if (!inArray(values[i].site, sites))
										sites.push(values[i].site);
								}
								return sites;
							};

							$scope.showInfoWindow = function(event, pos) {
								var infowindow = new google.maps.InfoWindow();
								var position = new google.maps.LatLng(
										pos.latitude, pos.longitude);
								var individualNum = 0;
								var content = '<div class="panel panel-default"><div class="panel-heading"> <h4>'
										+ pos.siteName
										+ '</h4></div><div class="panel-body">';
								var fishNum = 0;
								var wqNum = 0;
								var fishContent = '';
								var waterContent = '';
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										fishContent = fishContent + "<p>"
												+ value.feature.featureName
												+ ": " + value.dataValue
												+ "</p>";
										fishNum++;
										individualNum += value.dataValue;
									} else if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Water") {
										waterContent = waterContent
												+ "<p>"
												+ value.variable.variableName.term
												+ ": " + value.dataValue
												+ "</p>";
										wqNum++;
									}
								}
								fishContent = '<p><strong> 총 종수:  ' + fishNum
										+ ', 총 개체수: ' + individualNum
										+ ' </strong></p>' + fishContent;
								if (fishNum != 0 && wqNum != 0)
									content = content
											+ '<table class="table table-bordered table-striped"><thead><tr><th>Fish</th><th>수질</th></tr><thead>'
											+ '<tbody><tr><td>'
											+ fishContent
											+ '</td>'
											+ '<td>'
											+ waterContent
											+ '</td></tr></tbody></table></div>';
								else
									content = content + fishContent
											+ waterContent + "</div>";

								infowindow.setContent(content);

								infowindow.setPosition(position);
								infowindow.open($scope.objMapa);
							};

							$scope.getRadius = function(pos) {
								var individualNum = 0;
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										individualNum += value.dataValue;
									}
								}
								return individualNum;
							};
							$scope.addMarker = function(event) {
								var ll = event.latLng;
								$scope.positions.push({
									lat : ll.lat(),
									lng : ll.lng()
								});
							}
							$scope.deleteMarkers = function() {
								$scope.positions = [];
							};
							$scope.showMarkers = function() {

								for ( var key in $scope.map.markers) {
									$scope.map.markers[key].setMap($scope.map);
								}
								;
							};
							$scope.hideMarkers = function() {
								for ( var key in $scope.map.markers) {
									$scope.map.markers[key].setMap(null);
								}
								;
							};
						} ]);
naempApp
		.controller(
				'MapValueCtrl',
				[
						'$scope',
						'$routeParams',
						'SiteListFind',
						'ValueByFish',
						function($scope, $routeParams, SiteListFind,
								ValueByFish) {

							$scope.query = $routeParams.start + " ~ "
									+ $routeParams.end + " 조사결과";
							$scope.$on('mapInitialized', function(event, map) {
								$scope.objMapa = map;
							});
							// var sites = SiteListFind.find({
							// id : $routeParams.id
							// });

							var values = ValueByFish.find({
								start : $routeParams.start,
								end : $routeParams.end,
								id : $routeParams.id
							});

							$scope.positions = function() {
								var sites = [];
								for (var i = 0; i < values.length; i++) {
									if (!inArray(values[i].site, sites))
										sites.push(values[i].site);
								}
								return sites;
							};

							$scope.showInfoWindow = function(event, pos) {
								var infowindow = new google.maps.InfoWindow();
								var position = new google.maps.LatLng(
										pos.latitude, pos.longitude);
								var individualNum = 0;
								var content = '<div class="panel panel-default"><div class="panel-heading"> <h4>'
										+ pos.siteName
										+ '</h4></div><div class="panel-body">';
								var fishNum = 0;
								var wqNum = 0;
								var fishContent = '';
								var waterContent = '';
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										fishContent = fishContent + "<p>"
												+ value.feature.featureName
												+ ": " + value.dataValue
												+ "</p>";
										fishNum++;
										individualNum += value.dataValue;
									} else if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Water") {
										waterContent = waterContent
												+ "<p>"
												+ value.variable.variableName.term
												+ ": " + value.dataValue
												+ "</p>";
										wqNum++;
									}
								}
								fishContent = '<p><strong> 총 종수:  ' + fishNum
										+ ', 총 개체수: ' + individualNum
										+ ' </strong></p>' + fishContent;
								if (fishNum != 0 && wqNum != 0)
									content = content
											+ '<table class="table table-bordered table-striped"><thead><tr><th>Fish</th><th>수질</th></tr><thead>'
											+ '<tbody><tr><td>'
											+ fishContent
											+ '</td>'
											+ '<td>'
											+ waterContent
											+ '</td></tr></tbody></table></div>';
								else
									content = content + fishContent
											+ waterContent + "</div>";

								infowindow.setContent(content);

								infowindow.setPosition(position);
								infowindow.open($scope.objMapa);
							};

							$scope.getRadius = function(pos) {
								var individualNum = 0;
								for (var i = 0; i < values.length; i++) {
									var value = values[i];
									if (value.site.siteName == pos.siteName
											&& value.feature.featureType == "Fish") {
										individualNum += value.dataValue;
									}
								}
								return individualNum;
							};
							$scope.addMarker = function(event) {
								var ll = event.latLng;
								$scope.positions.push({
									lat : ll.lat(),
									lng : ll.lng()
								});
							};
							$scope.deleteMarkers = function() {
								$scope.positions = [];
							};
							$scope.showMarkers = function() {

								for ( var key in $scope.map.markers) {
									$scope.map.markers[key].setMap($scope.map);
								}
								;
							};
							$scope.hideMarkers = function() {
								for ( var key in $scope.map.markers) {
									$scope.map.markers[key].setMap(null);
								}
								;
							};
						} ]);

naempApp
		.controller(
				'SiteValueCtrl',
				[
						'$scope',
						'$routeParams',
						'ValueBySite',
						'$location',
						function($scope, $routeParams, ValueBySite, $location) {

							var start = $routeParams.start;
							var end = $routeParams.end;

							$scope.collection = ValueBySite.find({
								start : $routeParams.start,
								end : $routeParams.end,
								id : $routeParams.id
							});
							$scope.itemsByPage = 15;
							$scope.displayed = [].concat($scope.collection);

							$scope.viewInGraph = function() {
								$scope.chart1 = true;

								var survey = [];
								var series = [];
								var fNames = [];
								var sName = $scope.displayed[0].site.siteName;
								for (var i = 0; i < $scope.displayed.length; i++) {
									var value = $scope.displayed[i];
									if (!inArray(value.feature.featureName,
											fNames)
											&& value.feature.featureType == 'Fish')
										fNames.push(value.feature.featureName);
								}
								var wNames = [];
								for (var i = 0; i < $scope.displayed.length; i++) {
									var value = $scope.displayed[i];
									if (!inArray(
											value.variable.variableName.term,
											wNames)
											&& value.feature.featureType == 'Water')
										wNames
												.push(value.variable.variableName.term);
								}
								var start = $routeParams.start.slice(0, 4)
								var end = $routeParams.end.slice(0, 4);

								for (var i = 0; i < end - start + 1; i++) {
									if (end - start == 0) {
										survey.push({
											"year" : Number(start) + i,
											"term" : 1
										});
										survey.push({
											"year" : Number(start) + i,
											"term" : 2
										});
									} else if (i == 0) {
										if ($routeParams.start.slice(5, 7) < 8) {
											survey.push({
												"year" : Number(start) + i,
												"term" : 1
											});
											survey.push({
												"year" : Number(start) + i,
												"term" : 2
											});
										} else
											survey.push({
												"year" : Number(start) + i,
												"term" : 2
											});
									} else if (i == end - start) {
										if ($routeParams.end.slice(5, 7) < 4) {

										} else if ($routeParams.end.slice(5, 7) < 8)
											survey.push({
												"year" : Number(start) + i,
												"term" : 1
											});
										else
											survey.push({
												"year" : Number(start) + i,
												"term" : 2
											});
									} else {
										survey.push({
											"year" : Number(start) + i,
											"term" : 1
										});
										survey.push({
											"year" : Number(start) + i,
											"term" : 2
										});
									}
								}

								var sumData = [];
								var speciesNum = [];
								for (var k = 0; k < survey.length; k++) {
									var sum = 0;
									var num = 0;
									for (var i = 0; i < $scope.displayed.length; i++) {
										var value = $scope.displayed[i];
										if (value.surveyYear == survey[k].year
												&& value.surveyTerm == survey[k].term
												&& value.site.siteName == sName
												&& value.feature.featureType == "Fish") {
											sum += value.dataValue;
											num++;
										}
									}
									sumData.push(sum);
									speciesNum.push(num);
								}

								series.push({
									"type" : "spline",
									"name" : "Sum",
									"data" : sumData
								});
								series.push({
									"type" : "spline",
									"name" : "Species #",
									"data" : speciesNum
								});
								var name = '';

								for (var k = 0; k < fNames.length; k++) {
									var fData = [];
									for (var j = 0; j < survey.length; j++) {
										var vval = 0;
										for (var i = 0; i < $scope.displayed.length; i++) {
											var value = $scope.displayed[i];
											if (value.site.siteName == sName
													&& value.feature.featureName == fNames[k]
													&& value.surveyYear == survey[j].year
													&& value.surveyTerm == survey[j].term)
												vval = value.dataValue;
										}
										fData.push(vval); // category마다 모든
										// site이름을 보여준다.
										// even tough there
										// is no fish
										// caught.
									}
									if (fData.length > 0) {

										series.push({
											"type" : "column",
											"name" : fNames[k],
											"data" : fData
										});
									}

								}
								for (var k = 0; k < wNames.length; k++) {
									var wData = [];
									for (var j = 0; j < survey.length; j++) {
										var val = 0;
										for (var i = 0; i < $scope.displayed.length; i++) {
											var value = $scope.displayed[i];
											if (value.site.siteName == sName
													&& value.variable.variableName.term == wNames[k]
													&& value.surveyYear == survey[j].year
													&& value.surveyTerm == survey[j].term)
												val = value.dataValue;
										}
										wData.push(val);
									}
									if (wData.length > 0) {

										series.push({
											"type" : "column",
											"name" : wNames[k],
											"data" : wData
										});
									}

								}
								// series.push( {"type": "spline","name": "총
								// 개체수", "data": sumData});
								// series.push( {"type": "spline","name": "총
								// 종수", "data": speciesNum});
								$scope.highchartsNG = {
									exporting : {
										chartOptions : { // specific options
											// for the exported
											// image
											plotOptions : {
												series : {
													dataLabels : {
														enabled : true
													}
												}
											}
										},
										fallbackToExportServer : false
									},
									chart : {
										height: 800
									},
									title : {
										text : sName
									},
									xAxis : {
										categories : survey,
										labels : {
											formatter : function() {
												return this.value.year + "/"
														+ this.value.term;
											}
										},
										crosshair : true
									},
									yAxis : {
										title : {
											text : ''
										},
										min : 0,
										labels : {
											formatter : function() {
												return this.value;
											}
										}
									},
									// tooltip: {
									// headerFormat: '<b>{series.name}</b><br>',
									// pointFormat: '{point.x}: {point.y}'
									// },
									// tooltip: {
									// headerFormat: '<span
									// style="font-size:10px">{point.key}</span><table>',
									// pointFormat: '<tr><td
									// style="color:{series.color};padding:0">{series.name}:
									// </td>' +
									// '<td style="padding:0"><b>{point.y:.1f}
									// mm</b></td></tr>',
									// footerFormat: '</table>',
									// shared: true,
									// useHTML: true
									// },
									plotOptions : {
										column : {
											pointPadding : 0.2,
											borderWidth : 0
										}
									},
									series : series
								};
							};
							/*
							 * change visualization to map
							 */
							$scope.viewInMap = function() {
								// alert("called searchFishBySite"
								// +$scope.year);
								var sIds = [];
								for (var i = 0; i < $scope.displayed.length; i++) {
									var value = $scope.displayed[i];

									if (!inArray(value.site.siteID, sIds))
										sIds.push(value.site.siteID);
								}

								// $location.path("/site-map/" +
								// sIds.toString());
								$location.path("/site-map/" + start + "/" + end
										+ "/" + sIds.toString());
							};

						} ]);
(function(ng) {
	naempApp
			.controller(
					'SiteTermValueCtrl',
					[
							'$scope',
							'$routeParams',
							'TermValueBySite',
							'$location',
							function($scope, $routeParams, TermValueBySite,
									$location) {

								var year = $routeParams.year;
								var term = $routeParams.term;

								$scope.collection = TermValueBySite.find({
									year : $routeParams.year,
									term : $routeParams.term,
									id : $routeParams.id
								});
								$scope.itemsByPage = 15;
								$scope.displayed = [].concat($scope.collection);

								/*
								 * View in Graph
								 */
								$scope.viewInGraph = function() {
									$scope.barChart = true;
									var fNames = [];
									var vNames = [];
									var sNames = [];
									var series = [];

									for (var i = 0; i < $scope.displayed.length; i++) {
										var value = $scope.displayed[i];
										if (!inArray(value.site.siteName,
												sNames)) {
											sNames.push(value.site.siteName);
										}
									}

									for (var i = 0; i < $scope.displayed.length; i++) {
										var value = $scope.displayed[i];
										if (!inArray(value.feature.featureName,
												fNames)
												&& value.feature.featureType == "Fish") {

											fNames
													.push(value.feature.featureName);
										}
									}

									for (var i = 0; i < $scope.displayed.length; i++) {
										var value = $scope.displayed[i];
										if (!inArray(
												value.variable.variableName.term,
												vNames)
												&& value.feature.featureType == "Water") {

											vNames
													.push(value.variable.variableName.term);
										}
									}

									var average = [];
									for (var k = 0; k < sNames.length; k++) {
										var sum = 0;
										for (var i = 0; i < $scope.displayed.length; i++) {
											var value = $scope.displayed[i];
											if (value.site.siteName == sNames[k]
													&& value.feature.featureType == "Fish") {
												sum += value.dataValue;
											}
										}
										average.push(sum);
									}
									var name = '';
									for (var j = 0; j < fNames.length; j++) {
										var data = [];
										for (var k = 0; k < sNames.length; k++) {
											var fval = 0;
											for (var i = 0; i < $scope.displayed.length; i++) {
												var value = $scope.displayed[i];
												if (value.site.siteName == sNames[k]
														&& value.feature.featureName == fNames[j])
													fval = value.dataValue;

											}
											data.push(fval);
										}
										series.push({
											"type" : "column",
											"name" : fNames[j],
											"data" : data
										});
									}

									for (var j = 0; j < vNames.length; j++) {
										var vdata = [];
										for (var k = 0; k < sNames.length; k++) {
											var val = 0;
											for (var i = 0; i < $scope.displayed.length; i++) {
												var value = $scope.displayed[i];
												if (value.site.siteName == sNames[k]
														&& value.variable.variableName.term == vNames[j])
													val = value.dataValue;

											}
											vdata.push(val);
										}
										series.push({
											"type" : "column",
											"name" : vNames[j],
											"data" : vdata
										});
									}
									// series.push( {"type": "spline","name":
									// "Sum", "data": average});
									$scope.highchartsNG = {										
										chart : {
											zoomType: 'xy',
											height: 800
										},
										title : {
											text : term + "/" + year
										},
										xAxis : {
											categories : sNames,
											crosshair : true

										},
										yAxis : {
											title : {
												text : ''
											},
											min : 0,
											labels : {
												formatter : function() {
													return this.value;
												}
											}
										},
										tooltip: {
								            shared: true
								        },
										series : series
									};
								};

								/*
								 * View in Map
								 */
								$scope.viewInMap = function() {

									var sIds = [];
									for (var i = 0; i < $scope.displayed.length; i++) {
										var value = $scope.displayed[i];

										if (!inArray(value.site.siteID, sIds))
											sIds.push(value.site.siteID);
									}

									$location.path("/site-map/term/" + year
											+ "/" + term + "/"
											+ sIds.toString());
								};
							} ]);

	naempApp.controller('Value4FishCtrl', [ '$scope', '$routeParams',
			'TermSpatialDataBySite', '$location',
			function($scope, $routeParams, TermSpatialDataBySite, $location) {
				$scope.barChart = false;
				$scope.time1 = $routeParams.year;
				$scope.time2 = $routeParams.term;

				var spatialData = TermSpatialDataBySite.find({
					year : $routeParams.year,
					term : $routeParams.term,
					id : $routeParams.id
				});
				$scope.itemsByPage = 15;
				$scope.collection = spatialData;
				$scope.displayed = [].concat($scope.collection);

				$scope.swapChartType = function() {
					if ($scope.highchartsNG.options.chart.type === 'line') {
						$scope.highchartsNG.options.chart.type = 'bar'
					} else {
						$scope.highchartsNG.options.chart.type = 'line'
					}
				};
				$scope.getSumVal = function(row) {
					var sum = 0;
					for (var i = 0; i < row.valueSet.length; i++) {
						sum = sum + Number(row.valueSet[i].data);
					}
					$scope.sum = sum;
					return sum;

				};

				$scope.showInBar = function(siteName) {
					$scope.barChart = true;
					var values = [];
					var predicates = spatialData[0].nameList;
					for (var i = 0; i < spatialData.length; i++) {
						var e = spatialData[i];
						if (e.siteName == siteName) {
							for (var j = 0; j < e.valueSet.length; j++) {
								values.push(e.valueSet[j].data);

							}//        			

						}
					}
					$scope.highchartsNG = {
						exporting : {
							chartOptions : { // specific options for the
								// exported image
								plotOptions : {
									series : {
										dataLabels : {
											enabled : true
										}
									}
								}
							},
							fallbackToExportServer : false
						},
						chart : {
							height: 800
						},
						options : {
							chart : {
								type : 'column',
								width : 1000
							}
						},
						xAxis : {
							categories : predicates
						},
						series : [ {
							name : siteName,
							data : values
						} ],
						title : {
							text : siteName
						}
					};
				};

			} ]);

	naempApp.controller('SiteFindCtrl', [
			'$scope',
			'$routeParams',
			'SiteListFind',
			'SurveyYear',
			function($scope, $routeParams, SiteListFind, SurveyYear) {
				var year = "";
				$scope.years = SurveyYear.find();
				var term = "";
				$scope.terms = [ 1, 2 ];

				$scope.getYear = function() {

					year = $scope.ddlYear;
				};

				$scope.getTerm = function() {
					term = $scope.ddlTerm;
				};
				$scope.cancel = function() {
					$scope.search = "";
				};
				$scope.sites = SiteListFind.find({
					id : $routeParams.id
				});

				// panination
				$scope.currentPage = 1, $scope.numPerPage = 10,
						$scope.maxSize = 10;

				$scope.searchBySite = function() {
					// alert("called searchFishBySite: " +$scope.year);
					var sIDs = [];

					for (var i = 0; i < $scope.searchResult.length; i++) {
						var site = $scope.searchResult[i];
						// alert(river.riverID);
						sIDs.push(site.siteID);
					}
					;
					$location.path("/site-value/term" + year + "/" + term + "/"
							+ sIDs.toString());
				}
			} ]);

	naempApp.controller('RiverFindCtrl', [
			'$scope',
			'$routeParams',
			'RiverListFind',
			'$location',
			function($scope, $routeParams, RiverListFind, $location) {

				$scope.rivers = RiverListFind.find({
					id : $routeParams.id
				});
				$scope.cancel = function() {
					$scope.search = "";
				};
				// panination
				$scope.currentPage = 1, $scope.numPerPage = 10,
						$scope.maxSize = 10;

				$scope.searchSiteByRiver = function() {

					var riverIDs = [];
					for (var i = 0; i < $scope.searchResult.length; i++) {
						var river = $scope.searchResult[i];
						// alert(river.riverID);
						riverIDs.push(river.riverID);
					}
					// set riverID to search site-list, need to add
					// controller for this.

				};
			} // alert("RiverSiteCtrl : " + $routeParams.id);
	]);
})(angular);