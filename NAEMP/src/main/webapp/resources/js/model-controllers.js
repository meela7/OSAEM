/**
 * Data Model Management
 * 
 * 
 * Source Controller
 */
naempApp.controller('SourceListCtrl', [ '$scope', 'SourcesService', 'SourcesService', '$location',
		function($scope, SourcesService, SourcesService, $location) {
	
			$scope.cancel = function(){
				$scope.search = "";
			};
			// callback for ng-click 'editSource':
			$scope.editSource = function(sourceID) {
				$location.path('/source-detail/' + sourceID);
			};
			// callback for ng-click 'deleteSource':
			$scope.deleteSource = function(source) {
				SourceService.del(source);
				$location.path('/source-list');
			};
			// callback for ng-click 'createSource':
			$scope.createSource = function() {
				$location.path('/source-creation');
			};
			$scope.sources = SourcesService.findall();
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
naempApp.controller('SourceCreationCtrl', [ '$scope', 'SourceService','$location', 
    function($scope, SourceService, $location) {
	// callback for ng-click 'createNewSource':
	$scope.createSource = function() {
		SourceService.create($scope.source);
		$location.path('/source-list');
	};
	$scope.cancel = function() {
		$location.path('/source-list');
	};
} ]);

/**
 * Site Controller
 */
naempApp.controller('SiteListCtrl', ['$scope','SitesService', 'SiteService','$location',
	function($scope, SitesService, SiteService,$location) {
	
	// callback for ng-click 'editSite':
	$scope.editSite = function(siteID) {
		$location.path('/site-detail/' + siteID);
	};
	// callback for ng-click 'deleteSite':
	$scope.deleteSite = function(site) {
		SiteService.del(site);
		$location.path('/site-list');
	};
	// callback for ng-click 'createSite':
	$scope.createSite = function() {
		$location.path('/site-creation');
	};

	$scope.cancel = function () {
		$scope.search = "";
	};
	
	$scope.sites = SitesService.findall();

	// panination
	$scope.currentPage = 1, $scope.numPerPage = 10,
			$scope.maxSize = 10;

	$scope.$watch('currentPage + numPerPage',function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;
		$scope.filteredSites = $scope.sites
				.slice(begin, end);
	});
}]);

naempApp.controller('SiteDetailCtrl', [ '$scope', '$routeParams','SiteService', '$location',
	function($scope, $routeParams, SiteService, $location) {
		// callback for ng-click 'updateSite':
		$scope.updateSite = function() {
			SiteService.update($scope.site, function(){
				id : $routeParams.id
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
naempApp.controller('SiteCreationCtrl', [ '$scope', 'SiteService', '$location',
	function($scope, SiteCreate, $location) {
	// callback for ng-click 'createSite':
		$scope.createSite = function() {
			SiteService.create($scope.site);
			$location.path('/site-list');
		};
		$scope.cancel = function() {
			$location.path('/site-list');
		};
} ]);

/**
 * River Controller
 */
naempApp.controller('RiverListCtrl',['$scope','RiversService','RiverService','$location',
	function($scope, RiversService, RiverService, $location) {

		// callback for ng-click 'editRiver':
		$scope.editRiver = function (riverID) {
			$location.path('/river-detail/' + riverID);
		};
		// callback for ng-click 'deleteRiver':
		$scope.deleteRiver = function (river) {
			RiverService.del(river);
			$location.path('/river-list');
		};
		// callback for ng-click 'createRiver':
		$scope.createRiver = function () {
			$location.path('/river-creation');
		};

		$scope.cancel = function () {
			
			$scope.search = "";
		};

		$scope.rivers = RiversService.findall();

		// panination
		$scope.currentPage = 1, $scope.numPerPage = 10,
			$scope.maxSize = 10;

		$scope.$watch('currentPage + numPerPage', function () {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

			$scope.filteredRivers = $scope.rivers.slice(begin, end);
		});
	}
]);

naempApp.controller('RiverDetailCtrl', [ '$scope', '$routeParams', 'RiverService','$location',
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
naempApp.controller('RiverCreationCtrl', [ '$scope', 'RiversService', '$location',
		function($scope, RiversService, $location) {
			// callback for ng-click 'createRiver':
			$scope.createRiver = function() {
				RiversService.create($scope.river);
				$location.path('/river-list');
			};
			$scope.cancel = function() {
				$location.path('/river-list');
			};
		} ]);


/**
 * SiteCode Controller
 */
naempApp.controller('SiteCodeListCtrl',['$scope','SiteCodeFindAll','SiteCodeDelete','$location',
		function($scope, SiteCodeFindAll, SiteCodeDelete,
			$location) {
	
		// callback for ng-click 'editSiteCode':
		$scope.editSiteCode = function(siteCodeID) {
			$location
					.path('/siteCode-detail/' + siteCodeID);
		};
		
		$scope.cancel = function () {
			
			$scope.search = "";
		};
		// callback for ng-click 'deleteSiteCode':
		$scope.deleteSiteCode = function(siteCode) {
			SiteCodeDelete.del(siteCode);
			$location.path('/siteCode-list');
		};
		// callback for ng-click 'createSite':
		$scope.createSiteCode = function() {
			$location.path('/siteCode-creation');
		};
	
		$scope.siteCodes = SiteCodeFindAll.findall();
	
		// cache
		// var cache = dmsCache.get('siteList");
		// if(cache){ // if there's something in the cache,
		// use it!
		// $scope.sites = cache;
		// }esle{ // otherwise, let's generate a new
		// instance!
		// dmsCache.put('siteList', SiteFindAll.findall());
		// $scope.sites = dmsCache.get('siteList');
		// }
	
		// panination
		$scope.currentPage = 1, $scope.numPerPage = 10,
				$scope.maxSize = 10;
	
		$scope.$watch('currentPage + numPerPage',
		function() {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
					+ $scope.numPerPage;
			$scope.filteredSiteCodes = $scope.siteCodes
					.slice(begin, end);
		});
} ]);

naempApp.controller('SiteCodeDetailCtrl',
		[
				'$scope',
				'$routeParams',
				'SiteCodeUpdate',
				'SiteCodeFind',
				'$location',
				function($scope, $routeParams, SiteCodeUpdate, SiteCodeFind,
						$location) {
					// callback for ng-click 'updateSiteCode':
					$scope.updateSiteCode = function() {
						SiteCodeUpdate.update($scope.siteCode);
						$location.path('/siteCode-list');
					};
					// callback for ng-click 'cancel':
					$scope.cancel = function() {
						$location.path('/siteCode-list');
					};
					$scope.siteCode = SiteCodeFind.find({
						id : $routeParams.id
					});
				} ]);
naempApp.controller('SiteCodeCreationCtrl', [ '$scope', 'SitesService',
		'SiteCodeCreate', '$location',
		function($scope, SitesService, SiteCodeCreate, $location) {
			$scope.sites = SitesService.findall();
			var getSite;
			$scope.getSelectedSite = function(site) {
				getSite = $scope.ddlSites;
				alert(getSite.siteID);
				$scope.selectedSiteName = getSite.siteName;
				$scope.siteCode.site = getSite;
				// $window.alert("Selected Value: " + siteID + "\nSelected Text:
				// " + siteID);
			};
			// callback for ng-click 'createSiteCode':
			$scope.createSiteCode = function() {
				SiteCodeCreate.create($scope.siteCode);
				$location.path('/siteCode-list');
			};

			$scope.cancel = function() {
				$location.path('/siteCode-list');
			};

		} ]);

/**
 * Featrues
 */

naempApp.controller('FeatureListCtrl',['$scope', 'FeaturesService', 'FeatureService', '$location',
		function($scope, FeaturesService, FeatureService,$location) {
			$scope.cancel = function () {
				$scope.search = "";
			};
			// callback for ng-click 'editFeature':
			$scope.editFeature = function(featureID) {
				$location.path('/feature-detail/' + featureID);
			};
			$scope.findFishByFeature = function(featureID) {
				$location.path('/feature-fish/' + featureID);
			};
			// callback for ng-click 'deleteFeature':
			$scope.deleteFeature = function(feature) {
				FeatureService.del(feature);
				$location.path('/feature-list');
			};
			// callback for ng-click 'createFeature':
			$scope.createFeature = function() {
				$location.path('/feature-creation');
			};
			
			$scope.showDetail = function(featureID){
				$location.path('/fish-detail/'+ featureID);
			};
			
			$scope.features = FeaturesService.findall();

			// panination
			$scope.currentPage = 1, $scope.numPerPage = 10,
					$scope.maxSize = 10;

			$scope.$watch('currentPage + numPerPage',
						function() {
							var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
									+ $scope.numPerPage;
							$scope.filteredFeatures = $scope.features
									.slice(begin, end);
						});

		} ]);
naempApp.controller('FeatureDetailCtrl', [ '$scope', '$routeParams', 'FeatureService', '$location',
		function($scope, $routeParams, FeatureService, $location) {
			// callback for ng-click 'updateFeaturee':
			$scope.updateFeature = function() {
				FeatureService.update($scope.feature);
				$location.path('/feature-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/feature-list');
			};
			$scope.feature = FeatureService.find({
				id : $routeParams.id
			});
		} ]);
naempApp.controller('FeatureCreationCtrl', [ '$scope', 'FeaturesCreate','$location', 
        function($scope, FeaturesService, $location) {
			// callback for ng-click 'createNewFeature':
			$scope.createFeature = function() {
				FeaturesService.create($scope.feature);
				$location.path('/feature-list');
			};
			$scope.cancel = function() {
				$location.path('/feature-list');
			};
		} ]);

naempApp.controller('FeatureFishDetailCtrl', [ '$scope', '$routeParams','FishByFeature', '$location',
		function($scope, $routeParams, FishByFeature, $location) {
			$scope.updateFish = function() {
				// FishUpdate.update($scope.fish);
				$location.path('/feature-list');
			};
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/feature-list');
			};

			$scope.fish = FishByFeature.find({
				id : $routeParams.id
			});
		} ]);

/**
 * Fishes
 */

naempApp.controller('FishListCtrl',['$scope', 'FishesService', 'FishService', '$location',
		function($scope, FishesService, FishService, $location) {
			$scope.cancel = function(){
				$scope.search = "";
			};
			// callback for ng-click 'editFish':
			$scope.editFish = function(fishID) {
				$location.path('/fish-detail/' + fishID);
			};
			// callback for ng-click 'deleteFish':
			$scope.deleteFish = function(fish) {
				FishService.del(fish);
				$location.path('/fish-list');
			};
			// callback for ng-click 'createFish':
			$scope.createFish = function() {
				$location.path('/fish-creation');
			};
			$scope.fishes = FishesService.findall();

			// panination
			$scope.currentPage = 1, $scope.numPerPage = 10,
					$scope.maxSize = 10;

			$scope.$watch('currentPage + numPerPage',function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
						+ $scope.numPerPage;
				$scope.filteredFishes = $scope.fishes.slice(begin, end);
			});

		} ]);
naempApp.controller('FishDetailCtrl', [ '$scope', '$routeParams', 'FishService', '$location',
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
naempApp.controller('FishCreationCtrl', [ '$scope', 'FishesService', '$location',
		function($scope, FishesService, $location) {
			// callback for ng-click 'createNewFish':
			$scope.createFish = function() {
				FishesService.create($scope.fish);
				$location.path('/fish-list');
			};
			$scope.cancel = function() {
				$location.path('/fish-list');
			};
		} ]);

/**
 * Methods
 */

naempApp.controller('MethodListCtrl',['$scope', 'MethodsService', 'MethodService', '$location',
		function($scope, MethodsService, MethodService, $location) {
			$scope.cancel = function(){
				$scope.search = "";
			}
			// callback for ng-click 'editMethod':
			$scope.editMethod = function(methodID) {
				$location.path('/method-detail/' + methodID);
			};

			// callback for ng-click 'deleteMethod':
			$scope.deleteMethod = function(method) {
				MethodService.del(method);
				$location.path('/method-list');
			};
			// callback for ng-click 'createMethod':
			$scope.createMethod = function() {
				$location.path('/method-creation');
			};
			$scope.methods = MethodsService.findall();

			// panination
			$scope.currentPage = 1, $scope.numPerPage = 10,
					$scope.maxSize = 10;

			$scope.$watch('currentPage + numPerPage',function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
						+ $scope.numPerPage;
				$scope.filteredMethods = $scope.methods
						.slice(begin, end);
			});

		} ]);
naempApp.controller('MethodDetailCtrl', [ '$scope', '$routeParams', 'MethodService', '$location',
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
naempApp.controller('MethodCreationCtrl', [ '$scope', 'MethodsService', '$location', 
       function($scope, MethodsCreate, $location) {
			// callback for ng-click 'createNewFish':
			$scope.createMethod = function() {
				MethodsService.create($scope.method);
				$location.path('/method-list');
			};
			$scope.cancel = function() {
				$location.path('/method-list');
			};
		} ]);
/**
 * Variables
 */

naempApp.controller('VariableListCtrl', ['$scope', 'VariablesService', 'VariableService', '$location',
		function($scope, VariablesService, VariableService,
				$location) {
			$scope.cancel = function(){
				$scope.search = "";
			}
			// callback for ng-click 'editVariable':
			$scope.editVariable = function(variableID) {
				$location.path('/variable-detail/' + variableID);
			};

			// callback for ng-click 'deleteVariable':
			$scope.deleteVariable = function(variable) {
				VariableService.del(variable);
				$location.path('/variable-list');
			};
			// callback for ng-click 'createVariable':
			$scope.createVariable = function() {
				$location.path('/variable-creation');
			};
			$scope.variables = VariablesService.findall();

			// panination
			$scope.currentPage = 1, $scope.numPerPage = 10,
					$scope.maxSize = 10;

			$scope.$watch('currentPage + numPerPage',function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
						+ $scope.numPerPage;
				$scope.filteredVariables = $scope.variables
						.slice(begin, end);
			});

		} ]);
naempApp.controller('VariableDetailCtrl',['$scope', '$routeParams', 'VariableService', 'UnitsService', '$location',
			function($scope, $routeParams, VariableService, UnitsService,
					$location) {
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
				$scope.statuses = UnitsService.findall();
				
			} ]);
naempApp.controller('VariableCreationCtrl', [ '$scope', 'VariablesService',
		'$location', function($scope, VariablesService, $location) {
			// callback for ng-click 'createNewFeature':
			$scope.createVariable = function() {
				VariableServices.create($scope.variable);
				$location.path('/variable-list');
			};
			$scope.cancel = function() {
				$location.path('/variable-list');
			};
		} ]);

/**
 * Units
 */

naempApp.controller('UnitListCtrl', ['$scope', 'UnitsService', 'UnitService', '$location',
		function($scope, UnitsService, UnitService,	$location) {
			$scope.cancel = function(){
				$scope.search = "";
			}
			// callback for ng-click 'editUnit':
			$scope.editUnit = function(unitID) {
				$location.path('/unit-detail/' + unitID);
			};

			// callback for ng-click 'deleteUnit':
			$scope.deleteUnit = function(unit) {
				UnitService.del(unit);
				$location.path('/unit-list');
			};
			// callback for ng-click 'createUnit':
			$scope.createUnit = function() {
				$location.path('/unit-creation');
			};
			$scope.units = UnitsService.findall();

			// panination
			$scope.currentPage = 1, $scope.numPerPage = 10,
					$scope.maxSize = 10;

			$scope.$watch('currentPage + numPerPage',function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
						+ $scope.numPerPage;
				$scope.filteredUnits = $scope.units
						.slice(begin, end);
			});

		} ]);
naempApp.controller('UnitDetailCtrl', ['$scope', '$routeParams', 'UnitService', '$location',
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
naempApp.controller('UnitCreationCtrl', [ '$scope', 'UnitsService', '$location', 
        function($scope, UnitsService, $location) {
			// callback for ng-click 'createNewFeature':
			$scope.createUnit = function() {
				UnitsService.create($scope.unit);
				$location.path('/unit-list');
			};
			$scope.cancel = function() {
				$location.path('/unit-list');
			};
		} ]);
