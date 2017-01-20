var app = angular.module('server', ['ngTable','ngResource', 'ui.bootstrap']);

app.controller('situationController', function (isLogIn,$window,$scope,NgTableParams,deleteSituation,deleteMetric,
                                            saveSituation,saveMetric,getAllSituations,getAllSituationMetrics) {

    $scope.situationSelected = false;
    $scope.situations = this;
    $scope.situationData = getAllSituations.query();
    $scope.situations.tableParams = new NgTableParams({}, {dataset: $scope.situationData});

    $scope.saveSituationName = null;
    $scope.saveSituationMetricId = null;
    $scope.saveMetricName = null;
    $scope.saveMetricValue = null;
    $scope.saveSituationDesc = null;

    $scope.getMetricsBySituation = function(situationId) {
        $scope.situationSelected = true;
        $scope.metrics = this;
        $scope.metricData = getAllSituationMetrics.query({id:situationId});
        $scope.metrics.tableParams = new NgTableParams({}, {dataset: $scope.metricData});
    };

    isLogedIn();
    function isLogedIn(){
        $scope.logedPerson = isLogIn.get();
        if($scope.logedPerson === null)$window.location.href = '../socket-1.0-SNAPSHOT/login.html';
    }


    $scope.saveSituation = function(id){
        $scope.sitJSON = {name:$scope.saveSituationName,desc:$scope.saveSituationDesc};
        $scope.savePl = saveSituation.save({situationName:id},$scope.sitJSON);
    };

    $scope.saveSituationMetric = function(){
        var metricJSON = {name:$scope.saveSituationMetricId};
        $scope.savePlMetr = saveMetric.save({situation:$scope.saveMetricName},metricJSON);
    };

    $scope.deleteSituationF = function(id){
        deleteSituation.delete({delete:true},id);
    };

    $scope.deleteMetricF = function (id) {
        deleteMetric.delete({delete:true},id);
    }
});
app.factory('deleteSituation',function($resource){
    return $resource('resources/situationmetric/delete-situation/:delete');
});
app.factory('deleteMetric',function($resource){
    return $resource('resources/situationmetric/delete-situation-metric/:delete');
});
app.factory('saveSituation',function($resource){
    return $resource('resources/situationmetric/save-situation/:situationName');
});
app.factory('saveMetric',function($resource){
    return $resource('resources/situationmetric/save-metric/:situation');
});
app.factory('getAllSituations',function($resource){
    return $resource('resources/situationmetric/get-situations');
});
app.factory('getAllSituationMetrics',function($resource){
    return $resource('resources/situationmetric/get-situation-metrics/:id');
});
app.factory('isLogIn',function($resource){
    return $resource('resources/server/logedIn/');
});
