'use strict';

angular.module('myApp.view4', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
                });
            }])
        .controller('View4Ctrl', function ($scope, $http) {
            $scope.saveUser = function () {
                $http.post('api/saveUser', $scope.user).
                        success(function () {
                            $scope.myVar = false;
                            $scope.message = "User created";
                           
                        })
                        .error(function () {
                            $scope.myVar = true;
                            $scope.message = "User not created!";
                        });


            };
        });



