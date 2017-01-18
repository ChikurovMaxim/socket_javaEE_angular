var app = angular.module('server', ['ngTable','ngResource', 'ui.bootstrap']);

app.controller('plainController', function (isLogIn,$window,$scope,NgTableParams,deletePlain,deleteMetric,
                                            savePlain,saveMetric,getAllPlains,getAllMetrics) {

    $scope.plainSelected = false;
    $scope.plains = this;
    $scope.plainData = getAllPlains.query();
    $scope.plains.tableParams = new NgTableParams({}, {dataset: $scope.plainData});

    $scope.savePlainName = null;
    $scope.savePlainMetricId = null;
    $scope.saveMetricName = null;
    $scope.saveMetricValue = null;

    $scope.getMetricsByPlain = function(plainId) {
        $scope.plainSelected = true;
        $scope.metrics = this;
        $scope.metricData = getAllMetrics.query({id:plainId});
        $scope.metrics.tableParams = new NgTableParams({}, {dataset: $scope.metricData});
    };

    isLogedIn();
    function isLogedIn(){
        $scope.logedPerson = isLogIn.get();
        if($scope.logedPerson === null)$window.location.href = '../socket-1.0-SNAPSHOT/login.html';
    }


    $scope.savePlain = function(){
        $scope.savePl = savePlain.save({plainName:$scope.savePlainName});
    };

    $scope.savePlainMetric = function(){
        var metricJSON = {name:$scope.saveMetricName,value:$scope.saveMetricValue};
        $scope.savePlMetr = saveMetric.save({plain:$scope.savePlainMetricId},metricJSON);
    };

    $scope.deletePlainF = function(id){
        deletePlain.delete({delete:true},id);
    };

    $scope.deleteMetricF = function (id) {
        deleteMetric.delete({delete:true},id);
    }
});
app.factory('deletePlain',function($resource){
    return $resource('resources/plainmetric/delete-plain/:delete');
});
app.factory('deleteMetric',function($resource){
    return $resource('resources/plainmetric/delete-plain-metric/:delete');
});
app.factory('savePlain',function($resource){
    return $resource('resources/plainmetric/save-plain/:plainName');
});
app.factory('saveMetric',function($resource){
    return $resource('resources/plainmetric/save-metric/:plain');
});
app.factory('getAllPlains',function($resource){
    return $resource('resources/plainmetric/get-plains');
});
app.factory('getAllMetrics',function($resource){
    return $resource('resources/plainmetric/get-plain-metrics/:id');
});
app.factory('isLogIn',function($resource){
    return $resource('resources/server/logedIn/');
});
