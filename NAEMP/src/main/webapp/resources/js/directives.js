/**
 * 
 */

naempApp.directive('back', ['$window', function($window) {
	return {
        restrict: 'A',
        link: function (scope, elem, attrs) {
            elem.bind('click', function () {
                $window.history.back();
            });
        }
    };
}]);
// smart table select button
naempApp.directive('csSelect', function () {
    return {
        require: '^stTable',
        template: '<input type="checkbox" >',
        scope: {
            row: '=csSelect'
        },
        link: function (scope, element, attr, ctrl) {

            element.bind('change', function (evt) {
                scope.$apply(function () {
                    ctrl.select(scope.row, 'multiple');
                });
            });

            scope.$watch('row.isSelected', function (newValue, oldValue) {
                if (newValue === true) {
                    element.parent().addClass('st-selected');
                } else {
                    element.parent().removeClass('st-selected');
                }
            });
        }
    };
});
naempApp.directive('ngConfirmBoxClick', [ function() {
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmBoxClick;
                var clickAction = attr.confirmedClick;
                element.bind('click', function (event) {
                    if (window.confirm(msg)) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
} ]);
naempApp.directive('bsDropdown', function ($compile) {
    return {
        restrict: 'E',
        scope: {
            items: '=dropdownData',
            doSelect: '&selectVal',
            selectedItem: '=preselectedItem'
        },
        link: function (scope, element, attrs) {
            var html = '';
            switch (attrs.menuType) {
                case "button":
                    html += '<div class="btn-group"><button class="btn button-label btn-info">{{selectedItem}}</button><button class="btn btn-info dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>';
                    break;
                default:
                    html += '<div class="dropdown"><a class="dropdown-toggle" role="button" data-toggle="dropdown"  href="javascript:;">Dropdown<b class="caret"></b></a>';
                    break;
            }
            html += '<ul class="dropdown-menu"><li ng-repeat="item in items"><a tabindex="-1" data-ng-click="selectVal(item.unitName)">{{item.unitName}}</a></li></ul></div>';
            element.append($compile(html)(scope));
            for (var i = 0; i < scope.items.length; i++) {
                if (scope.items[i].unitName === scope.selectedItem) {
                    scope.bSelectedItem = scope.items[i];
                    break;
                }
            }
            scope.selectVal = function (item) {
                switch (attrs.menuType) {
                    case "button":
                        $('button.button-label', element).html(item);
                        break;
                    default:
                        $('a.dropdown-toggle', element).html('<b class="caret"></b> ' + item);
                        break;
                }
                scope.doSelect({
                    selectedVal: item
                });
            };
            
            scope.selectVal(scope.bSelectedItem);
        }
    };
});

(function(ng){
	
naempApp.directive('stSelectDistinct', [function() {
      return {
        restrict: 'E',
        require: '^stTable',
        scope: {
          collection: '=',
          predicate: '@',
          predicateExpression: '='
        },
        template: '<select class="form-control" ng-model="selectedOption" ng-change="optionChanged(selectedOption)" ng-options="opt for opt in distinctItems"></select>',
        link: function(scope, element, attr, table) {
          var getPredicate = function() {
            var predicate = scope.predicate;
            if (!predicate && scope.predicateExpression) {
              predicate = scope.predicateExpression;
            }
            return predicate;
          }

          scope.$watch('collection', function(newValue) {
            var predicate = getPredicate();

            if (newValue) {
              var temp = [];
              scope.distinctItems = ['All'];

              angular.forEach(scope.collection, function(item) {
                var value = item[predicate];
                if (value && value.trim().length > 0 && temp.indexOf(value) === -1) {
                  temp.push(value);
                }
              });
              temp.sort();

              scope.distinctItems = scope.distinctItems.concat(temp);
              scope.selectedOption = scope.distinctItems[0];
              scope.optionChanged(scope.selectedOption);
            }
          }, true);

          scope.optionChanged = function(selectedOption) {
            var predicate = getPredicate();

            var query = {};

            query.distinct = selectedOption;

            if (query.distinct === 'All') {
              query.distinct = '';
            }

            table.search(query, predicate);
          };
        }
      }
    }]);
naempApp.directive('stSelectMultiple', [function() {
      return {
        restrict: 'E',
        require: '^stTable',
        scope: {
          collection: '=',
          predicate: '@',
          predicateExpression: '='
        },
        templateUrl: 'resources/template/search/stSelectMultiple.html',
        link: function(scope, element, attr, table) {
          scope.dropdownLabel = '';
          scope.filterChanged = filterChanged;

          initialize();

          function initialize() {
            bindCollection(scope.collection);
          }

          function getPredicate() {
            var predicate = scope.predicate;
            if (!predicate && scope.predicateExpression) {
              predicate = scope.predicateExpression;
            }
            return predicate;
          }

          function getDropdownLabel() {
            var allCount = scope.distinctItems.length;

            var selected = getSelectedOptions();

            if (allCount === selected.length || selected.length === 0) {
              return 'All';
            }

            if (selected.length === 1) {
              return selected[0];
            }

            return selected.length + ' items';
          }

          function getSelectedOptions() {
            var selectedOptions = [];

            angular.forEach(scope.distinctItems, function(item) {
              if (item.selected) {
                selectedOptions.push(item.value);
              }
            });

            return selectedOptions;
          }

          function bindCollection(collection) {
            var predicate = getPredicate();
            var distinctItems = [];

            angular.forEach(collection, function(item) {
              var value = item[predicate];
              fillDistinctItems(value, distinctItems);
            });

            distinctItems.sort(function(obj, other) {
              if (obj.value > other.value) {
                return 1;
              } else if (obj.value < other.value) {
                return -1;
              }
              return 0;
            });

            scope.distinctItems = distinctItems;

            filterChanged();
          }

          function filterChanged() {
            scope.dropdownLabel = getDropdownLabel();

            var predicate = getPredicate();

            var query = {
              matchAny: {}
            };

            query.matchAny.items = getSelectedOptions();
            var numberOfItems = query.matchAny.items.length;
            if (numberOfItems === 0 || numberOfItems === scope.distinctItems.length) {
              query.matchAny.all = true;
            } else {
              query.matchAny.all = false;
            }

            table.search(query, predicate);
          }

          function fillDistinctItems(value, distinctItems) {
            if (value && value.trim().length > 0 && !findItemWithValue(distinctItems, value)) {
              distinctItems.push({
                value: value,
                selected: true
              });
            }
          }

          function findItemWithValue(collection, value) {
            var found = _.find(collection, function(item) {
              return item.value === value;
            });

            return found;
          }
        }
      }
    }]);
naempApp.directive('stNumberRange', ['$timeout', function ($timeout) {
    return {
        restrict: 'E',
        require: '^stTable',
        scope: {
            lower: '=',
            higher: '='
        },
        templateUrl: 'resources/template/search/stNumberRange.html',
        link: function (scope, element, attr, table) {
            var inputs = element.find('input');
            var inputLower = ng.element(inputs[0]);
            var inputHigher = ng.element(inputs[1]);
            var predicateName = attr.predicate;

            [inputLower, inputHigher].forEach(function (input, index) {

                input.bind('blur', function () {
                    var query = {};

                    if (scope.lower) {
                        query.lower = scope.lower;
                    }

                    if (scope.higher) {
                        query.higher = scope.higher;
                    }

                    scope.$apply(function () {
                        table.search(query, predicateName)
                    });
                });
            });
        }
    };
}]);
})(angular);  
/*
naempApp.directive('map',[ function () {
	return {
	  template: '<div></div>',
	  restrict: 'EA',
	  replace: true,
	  link: function (scope, element) {

	    scope.markers = [];

	    scope.map = new google.maps.Map(element[0], {
	      center: new google.maps.LatLng(36.1813, 127.3946),
	      zoom: 11
	    });

	    scope.addMarker = function (lat, lnt) {
	      var marker = new google.maps.Marker({
	        map: scope.map,
	        position:  new google.maps.LatLng(lat, lng)
	      });

	      scope.markers.push(marker);
	    };

	  }
	};
}]);
*/

