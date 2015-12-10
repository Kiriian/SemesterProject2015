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


app.controller('View1Ctrl', ['MyService', 'thing', '$scope', '$http', function (MyService, thing, $scope, $http) {

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
        for (var i = 0; i < $scope.flight.numberOfSeats - 1; i++) {
            $scope.reservation.Passengers.push({});
        }
        ;

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
                        thing.addThing(res.data);

                    }, function errorCallBack(res)
                    {
                        alert(res.data);
                    }
                    );
        };
        $scope.newReservationData = thing.getThing();
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

app.factory('thing', function () {

    var reservation = {};
    // the factory returns an object, which becomes the API for the service
    return {
        getThing: function () {
            console.log(reservation);
            return reservation;
        },
        addThing: function (data) {
            reservation = data;
            console.log(reservation);
        }
    };
});




        