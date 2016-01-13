var contextRoot = '/dms';

myapp.factory('SurveyYear', function($resource) {
	return $resource(contextRoot + '/value/years', {}, {
		find : {
			method: 'GET',
			cache : true,
			isArray : true			
		}
	})
});