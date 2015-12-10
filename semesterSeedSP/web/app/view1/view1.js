'use strict';

var app = angular.module('myApp.view1', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
                .when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                })
                .when('/Reserve', {templateUrl: 'app/view1/Reserve.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'});
    }]);


app.controller('View1Ctrl', ['MyService', '$scope', '$http', function (MyService, $scope, $http) {

        $scope.search = function () {

            var baseUrl = 'api/flightinfo/';
            var year = $scope.date.getFullYear();
            var month = $scope.date.getMonth();
            var day = $scope.date.getDate();
            $scope.newDate = new Date(year, month, day, 1);


            var searchDate = $scope.newDate.toISOString();


            if ($scope.destination !== "null")
            {
                if ($scope.origin !== $scope.destination)
                {
                    var attributes = $scope.origin + "/" + $scope.destination + "/" + searchDate + "/" + $scope.nop;
                } else
                {
                    $scope.error = "Depature airport and destination can not be the same";
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
        $scope.addFlight = function (data) {
            MyService.addFlight(data);
        };

        $scope.flight = MyService.getFlight();

        $scope.reservation = {Passengers: []};
        for (var i = 0; i < $scope.flight.numberOfSeats-1; i++) {
            $scope.reservation.Passengers.push({});
        };
        $scope.resData = [];
        $scope.reserveTicket = function ()
        {
            var url = 'api/reservation';
           
            alert($scope.flight.flightID);
            $scope.resData.flightID = $scope.flight.flightID;
            $scope.resData.numberOfSeats = $scope.flight.numberOfSeats;
            $scope.resData.totalPrice = $scope.flight.totalPrice;
            $scope.resData.ReserveeName = $scope.ReserveeName;
            $scope.resData.phone = $scope.phone;
            $scope.resData.email = $scope.email;
            
            $http.post(url, $scope.resData).then(function successCallBack(res) {
                alert("får vi object object herfra?")
                alert(res.data);
            }, function errorCallBack(res) {
                alert("får vi object object herfra - errorcallback?")
                alert(res);
            });
        };

    }]);
app.factory('MyService', function () {

    var item = {};
    // the factory returns an object, which becomes the API for the service
    return {
        getFlight: function () {
            return item;
        },
        addFlight: function (data) {
            item = data;
        }
    };
});



        