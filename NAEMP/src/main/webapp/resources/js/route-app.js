var myApp = angular.module("myApp", [ 'ngRoute']);

dmsApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/router.html',
			controller : 'RouteCtrl'
		})
		.otherwise({
	    redirectTo: '/home'
	  });
		
} ]);