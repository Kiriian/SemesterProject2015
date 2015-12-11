'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/view2', {
              templateUrl: 'app/view2/view2.html',
              controller: 'View2Ctrl'
            });
          }])

        .controller('View2Ctrl', function ($http, $scope) {
            $scope.test = "test";

          $http({
            method: 'GET',
            url: 'api/reservation/'
          }).then(function successCallback(res) {
              console.log(res.data[0].Passengers[0]);
            $scope.data = res.data[0];
            console.log($scope.data);
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });

        });