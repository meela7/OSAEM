/**
 * @File Name: app.js
 * @Description: Angular JS Module using modules: ngResource, ngRoute(Angular
 *               JS) and ui.bootstrap (BootStrap)
 * 
 * @author Meilan Jiang
 * @since 2016.02.15
 * @version 1.0
 * 
 * Copyright(c) 2016 by CILAB All right reserved.
 */

var naempApp = angular.module('naempApp', [ 'ngResource', 'ngRoute',
		'ngAnimate', 'ui.bootstrap', 'smart-table', 'leaflet-directive', 'ng-fusioncharts']);
var resourceRoot = 'resources/templates';
var searchResource = resourceRoot + '/search';
var manageResource = resourceRoot + '/manage';
//observation data search configuration

naempApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/api', {				
		templateUrl : resourceRoot + '/api.jsp'
	}).when('/search-site', {				// first page for search by site
		templateUrl : searchResource + '/site/list.html',
		controller : 'SearchBySiteCtrl'
	}).when('/site-period/:start/:end/:id', {			// search result of (start, end) search by site
		templateUrl : searchResource + '/value/list.html',
		controller : 'ValueSearchBySiteCtrl'
	}).when('/site-term/:year/:term/:id', {				// search result of (survey year, term) search by site
		templateUrl : searchResource + '/value/list.html',
		controller : 'TermValueSearchBySiteCtrl'
	}).when('/search-fish', {
		templateUrl : searchResource + '/fish/list.html',
		controller : 'SearchByFishCtrl'
	}).when('/fish-period/:start/:end/:id', {
		templateUrl : searchResource + '/value/list.html',
		controller : 'ValueSearchByFishCtrl'
	}).when('/fish-term/:year/:term/:id', {
		templateUrl : searchResource + '/value/list.html',
		controller : 'TermValueSearchByFishCtrl'
	}).	when('/site-map', {								// map page for all site
		templateUrl : searchResource + '/site/map.html',
		controller : 'SiteMapCtrl'
	}).	when('/site-period/map/:start/:end/:id', {			// map for search result of (start, end) search by site
		templateUrl : searchResource + '/value/map.html',
		controller : 'SiteValueMapCtrl'
	}).	when('/site-term/map/:year/:term/:id', {			// map for search result of (survey year, term) search by site
		templateUrl : searchResource + '/value/map.html',
		controller : 'TermSiteValueMapCtrl'
	}).	when('/fish-period/map/:start/:end/:id', {			
		templateUrl : searchResource + '/value/map.html',
		controller : 'FishValueMapCtrl'
	}).	when('/fish-term/map/:year/:term/:id', {			
		templateUrl : searchResource + '/value/map.html',
		controller : 'TermFishValueMapCtrl'
	});
} ]);
// program data management configuration
naempApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/source-list', {
		templateUrl : manageResource + '/source/list.html',
		controller : 'SourceListCtrl'
	}).when('/source-detail/:id', {
		templateUrl : manageResource + '/source/detail.html',
		controller : 'SourceDetailCtrl'
	}).when('/source-creation', {
		templateUrl : manageResource + '/source/creation.html',
		controller : 'SourceCreationCtrl'
	}).when('/site-list', {
		templateUrl : manageResource + '/site/list.html',
		controller : 'SiteListCtrl'
	}).when('/site-detail/:id', {
		templateUrl : manageResource + '/site/detail.html',
		controller : 'SiteDetailCtrl'
	}).when('/site-creation', {
		templateUrl : manageResource + '/site/creation.html',
		controller : 'SiteCreationCtrl'
	}).when('/river-list', {
		templateUrl : manageResource + '/river/list.html',
		controller : 'RiverListCtrl'
	}).when('/river-detail/:id', {
		templateUrl : manageResource + '/river/detail.html',
		controller : 'RiverDetailCtrl'
	}).when('/river-creation', {
		templateUrl : manageResource + '/river/creation.html',
		controller : 'RiverCreationCtrl'
	}).when('/variable-list', {
		templateUrl : manageResource + '/variable/list.html',
		controller : 'VariableListCtrl'
	}).when('/variable-detail/:id', {
		templateUrl : manageResource + '/variable/detail.html',
		controller : 'VariableDetailCtrl'
	}).when('/variable-creation', {
		templateUrl : manageResource + '/variable/creation.html',
		controller : 'VariableCreationCtrl'
	}).when('/fish-list', {
		templateUrl : manageResource + '/fish/list.html',
		controller : 'FishListCtrl'
	}).when('/fish-detail/:id', {
		templateUrl : manageResource + '/fish/detail.html',
		controller : 'FishDetailCtrl'
	}).when('/fish-creation', {
		templateUrl : manageResource + '/fish/creation.html',
		controller : 'FishCreationCtrl'
	}).when('/method-list', {
		templateUrl : manageResource + '/method/list.html',
		controller : 'MethodListCtrl'
	}).when('/method-detail/:id', {
		templateUrl : manageResource + '/method/detail.html',
		controller : 'MethodDetailCtrl'
	}).when('/method-creation', {
		templateUrl : manageResource + '/method/creation.html',
		controller : 'MethodCreationCtrl'
	}).when('/unit-list', {
		templateUrl : manageResource + '/unit/list.html',
		controller : 'UnitListCtrl'
	}).when('/unit-detail/:id', {
		templateUrl : manageResource + '//unit/detail.html',
		controller : 'UnitDetailCtrl'
	}).when('/unit-creation', {
		templateUrl : manageResource + '/unit/creation.html',
		controller : 'UnitCreationCtrl'
	});

} ]);
