var app = angular.module('server', ['ngResource', 'ui.bootstrap']);

app.controller('serverController', function ($scope, isLogIn, startResource, stopResource, logOut,$window) {

    $scope.pilot_name = null;
    $scope.port = null;
    $scope.plain = null;

    isLogedIn();
    function isLogedIn(){
        $scope.logedPerson = isLogIn.get().$promise.then(
            function(){
                $scope.isLoged = true;
            },
            function(){
                $window.location.href = '../socket-1.0-SNAPSHOT/login.html';
            }
        );
    }

    $scope.start = function () {
        $scope.data = {name:$scope.pilot_name,port:$scope.port,plain:$scope.plain};
        $scope.start = startResource.save({start:true},$scope.data).$promise.then(
            function(){
                $scope.isRun = true;
            },
            function () {
                $scope.isRun = false;
            }
        )
    };

    $scope.stop = function () {
        $scope.start = stopResource.get({stop:true});
    };

    $scope.logOutF = function (){
        logOut.get();
        $window.location.href = '../socket-1.0-SNAPSHOT/login.html';
    };

});
app.factory('isLogIn',function($resource){
    return $resource('resources/server/logedIn/');
});
app.factory('startResource',function($resource){
    return $resource('resources/server/startData/:start');
});
app.factory('stopResource',function($resource){
    return $resource('resources/server/stopData/:stop');
});
app.factory('logOut',function($resource){
    return $resource('resources/server/logout/');
});


