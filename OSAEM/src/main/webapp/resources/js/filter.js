/**
 * @File Name: filter.js
 * @Description: Angular JS Filter
 * 
 * @author Meilan Jiang
 * @since 2016.02.15
 * @version 1.0
 * 
 * Copyright(c) 2016 by CILAB All right reserved.
 */

// Pagination filter 
naempApp.filter('pagination', function() {
	return function(input, start) {
		if (input) {
			start = +start;
			return input.slice(start);
		}
		return [];
	};
});
