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

            $scope.data = [];
          $http({
            method: 'GET',
            url: 'api/reservation/'
          }).then(function successCallback(res) {
              console.log(res.data[0].Passengers[0]);
              
              for (var i = 0; i < res.data.length; i++)
              {
                  $scope.data.push(res.data[i]);
              }
            console.log($scope.data[0].flightID);
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });

        });