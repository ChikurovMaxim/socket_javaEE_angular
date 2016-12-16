var app = angular.module('server', ['ngResource', 'ngGrid', 'ui.bootstrap']);


// Create a controller with name personsListController to bind to the grid section.
app.controller('loginController', function (loginProc, $scope, $rootScope, $window) {

    $scope.pass = null;
    $scope.login = null;


    $scope.loginFunc = function () {
        $scope.proceed = loginProc.save({login:$scope.login},$scope.pass).$promise.then(
            function () {
                $window.location.href = '../socket-1.0-SNAPSHOT/server.html';
            },
            function () {
                $scope.alerts = [
                    {type: 'success', msg: 'Sorry, you are not authorized!Check your login and password'}
                ];
            });
    };

});
app.factory('loginProc', function ($resource) {
    return $resource('resources/login/:login')
});

