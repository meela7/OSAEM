myapp.controller('RequestCtrl',
    function($scope, SurveyYear) {
		var years = SurveyYear.find(function(){
			console.log(years);
		});
		$scope.years = years;
});