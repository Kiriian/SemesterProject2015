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
                var year = $scope.date.getFullYear();
                var month = $scope.date.getMonth();
                var day = $scope.date.getDate();
                $scope.newDate = new Date(year, month, day, 1);


                var searchDate = $scope.newDate.toISOString();


                if ($scope.destination !== "null" )
                {
                    if ($scope.origin !== $scope.destination)
                    {
                        var attributes = $scope.origin + "/" + $scope.destination + "/" + searchDate + "/" + $scope.nop;
                    }else
                    {
                        $scope.error="Depature airport and destination can not be the same";
                    }
                } else
                {
                    var attributes = $scope.origin + "/" + searchDate + "/" + $scope.nop;
                }

                var url = baseUrl + attributes;

                $http.get(url).then(function successCallBack(res) {
                    $scope.data = res.data;
                }, function errorCallBack(res) {
                    alert("noget gik galt");
                });
            };
        });