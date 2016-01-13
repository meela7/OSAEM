myApp.controller('RouteCtrl', 
	function($scope) {
	$scope.template = {
	    "home": "template/loadreq.html",
	    "about": "template/search/values.html",
	    "contact": "contactus.html"
	  }
	
	$scope.message={
	    
	     "home":"Message from home template",
	     "about":"Message from about template",
	     "contact":"Message from contact template"
	}
		
});