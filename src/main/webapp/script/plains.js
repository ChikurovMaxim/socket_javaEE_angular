var app = angular.module('server', ['ngTable','ngResource', 'ui.bootstrap']);

app.controller('plainController', function ($scope,NgTableParams,deletePlain,deleteMetric,
                                            savePlain,saveMetric,getAllPlains,getAllMetrics) {
    $scope.plains = this;
    var plainData = getAllPlains.get();
    $scope.plains.tableParams = new NgTableParams({}, { dataset: plainData});

    $scope.savePlainName = null;

    $scope.saveMetricName = null;
    $scope.saveMetricValue = null;

    $scope.getMetricsByPlain = function(plainId) {
        $scope.metrics = this;
        var metricDate = getAllMetrics({id:plainId});
        $scope.metrics.tableParams = new NgTableParams({}, {dataset: metricDate});
    };

    $scope.savePlain = function(){
        $scope.savePl = savePlain.save({plainName:$scope.savePlainName});
    };

    $scope.savePlainMetric = function(pid){
        var metricJSON = {name:$scope.saveMetricName,value:$scope.saveMetricValue};
        $scope.savePlMetr = saveMetric.save({plain:pid},metricJSON);
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