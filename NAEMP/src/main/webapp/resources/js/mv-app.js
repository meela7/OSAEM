var myapp = angular.module('myapp', ['ui.router'])
myapp.config(function($stateProvider) {
	$stateProvider.state('index', {
		url : "",
		views : {
			"viewA" : {
				templateUrl : 'template/request.html',
				controller: 'RequestCtrl'
			},
			"viewB" : {
				templateUrl : "template/datatable.html"
			}
		}
	}).state('route1', {
		url : "/route1",
		views : {
			"viewA" : {
				template : "route1.viewA"
			},
			"viewB" : {
				template : "route1.viewB"
			}
		}
	}).state('route2', {
		url : "/route2",
		views : {
			"viewA" : {
				templateUrl : 'template/request.html',
				controller: 'RequestCtrl'
			},			
			"viewB" : {
				template : "route2.viewB"
			}
		}
	}).state('route3', {
		url : "/route3",
		views : {
			"viewA" : {
				templateUrl : "template/request.html"
			},
			"viewB" : {
				template : "route3.viewB"
			}
		}
	})
})