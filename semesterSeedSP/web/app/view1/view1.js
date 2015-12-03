'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl', function ($scope, $http)
        {
            $scope.search = function () {
                
                var baseUrl = 'api/flightinfo/';
  
                if ($scope.destination !== null)
                {
                    var attributes = $scope.origin + "/" + $scope.destination + "/" + $scope.date + "/" + $scope.nop;
                }else
                {
                   var attributes = $scope.origin + "/" + $scope.date + "/" + $scope.nop; 
                }
            
            var url = baseUrl + attributes;
            alert(url);
            $http.get(url).then(function successCallBack(res) {
                $scope.data = res.data;
            }, function errorCallBack(res) {
                alert("noget gik galt");
            });
            };
        });