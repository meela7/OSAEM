/**
 * 
 */

var naempApp = angular.module("naempApp", [ 'ngResource', 'ngRoute', 'ui.bootstrap', 'ngMap', 'smart-table', 'highcharts-ng']);
var contextRoot = "resources/template"; 
// data analysis

naempApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/site-fai?year:year&term=:term', {
			templateUrl : 'resources/template/search/fai.html',
			controller : 'FAICtrl'
		});
}]);
// data search management
naempApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/template/site/list.html',
			controller : 'SiteListCtrl'
		})
		.when('/site-map/:id', {
			templateUrl : 'resources/template/search/sitemap.html',
			controller : 'MapCtrl'
		})
		.when('/site-map/:start/:end/:id', {
			templateUrl : 'resources/template/search/sitemap.html',
			controller : 'MapSiteCtrl'
		})
		.when('/fish-map/:start/:end/:id', {
			templateUrl : 'resources/template/search/sitemap.html',
			controller : 'MapValueCtrl'
		})
		.when('/site-map/term/:year/:term/:id', {
			templateUrl : 'resources/template/search/sitemap.html',
			controller : 'MapTermValueCtrl'
		})
		.when('/river-search/', {
			templateUrl : 'resources/template/search/riverlist.html',
			controller : 'RiverSearchCtrl'
		})
		.when('/river-search/:id', {
			templateUrl : 'resources/template/search/riverlist.html',
			controller : 'RiverSearchCtrl'
		})
		.when('/river-site/:id', {
			templateUrl : 'resources/template/search/sitelist.html',
			controller : 'RiverSiteInfoCtrl'
		})
		.when('/site-search/', {
			templateUrl : 'resources/template/search/sitelist.html',
			controller : 'SiteSearchCtrl'
		})
		.when('/site-search/:id/:query', {
			templateUrl : 'resources/template/search/sitelist.html',
			controller : 'SiteSearchCtrl'
		})
		.when('/site-value/term/:year/:term/:id', {
			templateUrl : 'resources/template/search/termvalues.html',
			controller : 'SiteTermValueCtrl'
		})
		.when('/site-value/:start/:end/:id', {
			templateUrl : 'resources/template/search/values.html',
			controller : 'SiteValueCtrl'
		})
		.when('/feature-search/', {
			templateUrl : 'resources/template/search/featurelist.html',
			controller : 'FeatureSearchCtrl'
		})
		.when('/feature-fish/:id', {
			templateUrl : 'resources/template/search/fishlist.html',
			controller : 'FeatureFishCtrl'
		})
		.when('/fish-search/', {
			templateUrl : 'resources/template/search/fishlist.html',
			controller : 'FishSearchCtrl'
		})
		.when('/fish-search/:id', {
			templateUrl : 'resources/template/search/fishlist.html',
			controller : 'FishSearchCtrl'
		})
		.when('/fish-value/:start/:end/:id', {
			templateUrl : 'resources/template/search/values.html',
			controller : 'FishValueCtrl'
		})
		.when('/fish-value/term/:year/:term/:id', {
			templateUrl : 'resources/template/search/termvalues.html',
			controller : 'FishTermValueCtrl'
		})
		.when('/value-fish/term/:year/:term/:id', {
			templateUrl : 'resources/template/search/termvalue4fish.html',
			controller : 'Value4FishCtrl'
		})
		.when('/value-search/:id/:id/:id/time', {
			templateUrl : 'resources/template/search/values.html',
			controller : 'ValueSearchCtrl'
		})
		.when('/value-feature/:id/', {
			templateUrl : 'resources/template/search/featurelist.html',
			controller : 'ValueFeatureCtrl'
		})
		.when('/value-fish/:id/', {
			templateUrl : 'resources/template/search/fishlist.html',
			controller : 'ValueFishCtrl'
		})
		.when('/value-site/:id/', {
			templateUrl : 'resources/template/search/sitelist.html',
			controller : 'SiteFindCtrl'
		})
		.when('/value-river/:id/', {
			templateUrl : 'resources/template/search/riverlist.html',
			controller : 'RiverFindCtrl'
		});
}]);

//data model management
naempApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.
	when('/source-list', {
		templateUrl : 'resources/template/source/list.html',
		controller : 'SourceListCtrl'
	}).
	when('/source-detail/:id', {
		templateUrl : 'resources/template/source/detail.html',
		controller : 'SourceDetailCtrl'
	}).
	when('/source-creation', {
		templateUrl : 'resources/template/source/creation.html',
		controller : 'SourceCreationCtrl'
	}).
	when('/site-list', {
		templateUrl : 'resources/template/site/list.html',
		controller : 'SiteListCtrl'
	}).
	when('/site-detail/:id', {
		templateUrl : 'resources/template/site/detail.html',
		controller : 'SiteDetailCtrl'
	}).
	when('/site-creation', {
		templateUrl : 'resources/template/site/creation.html',
		controller : 'SiteCreationCtrl'
	}).
	when('/river-list', {
		templateUrl : 'resources/template/river/list.html',
		controller : 'RiverListCtrl'
	}).
	when('/river-detail/:id', {
		templateUrl : 'resources/template/river/detail.html',
		controller : 'RiverDetailCtrl'
	})
	.when('/river-creation', {
		templateUrl : 'resources/template/river/creation.html',
		controller : 'RiverCreationCtrl'
	})
	.when('/siteCode-list', {
		templateUrl : 'resources/template/siteCode/list.html',
		controller : 'SiteCodeListCtrl'
	}).
	when('/siteCode-detail/:id', {
		templateUrl : 'resources/template/siteCode/detail.html',
		controller : 'SiteCodeDetailCtrl'
	}).
	when('/siteCode-creation', {
		templateUrl : 'resources/template/siteCode/creation.html',
		controller : 'SiteCodeCreationCtrl'
	})
	.when('/feature-list', {
		templateUrl : 'resources/template/feature/list.html',
		controller : 'FeatureListCtrl'
	})
	.when('/feature-detail/:id', {
		templateUrl : 'resources/template/feature/detail.html',
		controller : 'FeatureDetailCtrl'
	})
	.when('/feature-creation', {
		templateUrl : 'resources/template/feature/creation.html',
		controller : 'FeatureCreationCtrl'
	})
	.when('/feature-fish/:id', {
		templateUrl : 'resources/template/fish/detail.html',
		controller : 'FeatureFishDetailCtrl'
	})
	.when('/variable-list', {
		templateUrl : 'resources/template/variable/list.html',
		controller : 'VariableListCtrl'
	})
	.when('/variable-detail/:id', {
		templateUrl : 'resources/template/variable/detail.html',
		controller : 'VariableDetailCtrl'
	})
	.when('/variable-creation', {
		templateUrl : 'resources/template/variable/creation.html',
		controller : 'VariableCreationCtrl'
	})
	.when('/fish-list', {
		templateUrl : 'resources/template/fish/list.html',
		controller : 'FishListCtrl'
	})
	.when('/fish-detail/:id', {
		templateUrl : 'resources/template/fish/detail.html',
		controller : 'FishDetailCtrl'
	})
	.when('/fish-creation', {
		templateUrl : 'resources/template/fish/creation.html',
		controller : 'FishCreationCtrl'
	})
	.when('/method-list', {
		templateUrl : 'resources/template/method/list.html',
		controller : 'MethodListCtrl'
	})
	.when('/method-detail/:id', {
		templateUrl : 'resources/template/method/detail.html',
		controller : 'MethodDetailCtrl'
	})
	.when('/method-creation', {
		templateUrl : 'resources/template/method/creation.html',
		controller : 'MethodCreationCtrl'
	})
	.when('/unit-list', {
		templateUrl : 'resources/template/unit/list.html',
		controller : 'UnitListCtrl'
	})
	.when('/unit-detail/:id', {
		templateUrl : 'resources/template/unit/detail.html',
		controller : 'UnitDetailCtrl'
	})
	.when('/unit-creation', {
		templateUrl : 'resources/template/unit/creation.html',
		controller : 'UnitCreationCtrl'
	});
	
} ]);


