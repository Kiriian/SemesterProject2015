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
                    controllerAs: 'ctrl'})
                .when('/Reservation', {templateUrl: 'app/view1/newReservation.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'});
    }]);


app.controller('View1Ctrl', ['MyService', 'factoryThing', '$rootScope', '$scope', '$http', '$window', function (MyService, factoryThing, $rootScope, $scope, $http, $window) {

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
                $scope.error = "There are no available flights"
                $scope.data = null;
            });
        };

        $scope.addFlight = function (data) {
            $http.get("api/demo").then(function successCallBack(res){
                MyService.addFlight(data);
                $window.location.href = '#/Reserve';
            }, function errorCallBack(res){
               
            });
        };

        $scope.flight = MyService.getFlight();


        $scope.reservation = {Passengers: []};
        for (var i = 0; i < $scope.flight.numberOfSeats; i++) {
            $scope.reservation.Passengers.push({});
        }
        ;
        
        $scope.newReservationData = {};
        $scope.$on('set', function () {
            $scope.newReservationData = factoryThing.getThing();
        });

        $scope.reservationData = {};
        $scope.reserveTicket = function ()
        {
            var url = 'api/reservation/' + $scope.flight.airline;

            $scope.reservationData.flightID = $scope.flight.flightID;
            $scope.reservationData.numberOfSeats = $scope.flight.numberOfSeats;
            $scope.reservationData.ReserveeName = $scope.ReserveeName;
            $scope.reservationData.ReservePhone = $scope.ReservePhone;
            $scope.reservationData.ReserveeEmail = $scope.ReserveeEmail;
            $scope.reservationData.Passengers = $scope.reservation.Passengers;

            $http.post(url, $scope.reservationData).then
                    (function successCallBack(res) {
                        factoryThing.addThing(res.data, $rootScope);

                    }, function errorCallBack(res)
                    {
                        alert(res.data);
                    }
                    );
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

app.factory('factoryThing', function ($rootScope) {

    var reservation = {};
    // the factory returns an object, which becomes the API for the service
    return {
        getThing: function () {
            console.log(reservation);
            return reservation;
        },
        addThing: function (data) {
            reservation = data;
            $rootScope.$broadcast('set', reservation);
            console.log(reservation);
        }
    };
});




        