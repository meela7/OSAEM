// Dropdown Menu Fade    
jQuery(document).ready(function() {
	$(".dropdown").hover(function() {
		$('.dropdown-menu', this).stop().fadeIn("fast");
	}, function() {
		$('.dropdown-menu', this).stop().fadeOut("fast");
	});
});

// Needle in Haystack
function inArray(needle, haystack) {
	var count = haystack.length;
	for (var i = 0; i < count; i++) {
		if (haystack[i] === needle) {
			return true;
		}
	}
	return false;
};
