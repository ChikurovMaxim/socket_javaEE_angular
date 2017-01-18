var app = angular.module('server', ['ngTable','ngResource', 'ui.bootstrap']);

app.controller('serverController', function ($scope, isLogIn, startResource,
             stopResource, logOut, $window, saveUser, getAllRecords, getAllUsers,
             deleteRecord, deleteUser,getAllUsersRecord,NgTableParams) {

    $scope.pilot_name = null;
    $scope.port = null;
    $scope.plain = null;
    $scope.saveUserName = null;
    $scope.saveUserRole = null;
    $scope.saveUserLogin = null;
    $scope.saveUserPassword = null;

    $scope.records = this;
    // $scope.recordsData = getAllRecords.query();
    $scope.recordsData = [{id:1,user:{name:"User1"},date:"2016-11-12",PLAIN_MODEL:{name:"Plain"}},
    {id:2,user:{name:"User1"},date:"2016-11-13",PLAIN_MODEL:{name:"Plain2"}}];
    $scope.records.tableParams = new NgTableParams({}, {dataset: $scope.recordsData});

    $scope.usersData = [{id:1,name:"User1",role:"Administrator"},{id:2,name:"User2",role:"Instructor"}];
    $scope.users = this;
    $scope.users.tableParams = new NgTableParams({}, {dataset: $scope.usersData});

    isLogedIn();
    function isLogedIn(){
        $scope.logedPerson = isLogIn.get();
        if($scope.logedPerson === null)$window.location.href = '../socket-1.0-SNAPSHOT/login.html';
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

    $scope.saveUser = function () {
        var data = {role:$scope.saveUserRole,
            login:$scope.saveUserLogin,
            password:$scope.saveUserPassword};
        $scope.saveUserP = saveUser.save({name:$scope.saveUserName},data)
            .$promise.then(
            function(){
                $scope.userSaved = true;
            },
            function () {
                $scope.userSaved = false;
            }
        );
    };

    $scope.getUsersRecords = function(id){
        $scope.recordsGetData = getAllUsersRecord.query({id:id})
            .$promise.then(
            function () {
                $scope.userSelected = true;
            }
        )
    };

    $scope.getAllUsersF = function(){
        $scope.usersGet = getAllUsers.get();
    };

    $scope.getAllRecordsF = function(){
        $scope.recordsGet = getAllRecords.get();
    };

    $scope.deleteUserF = function (userId){
        deleteUser.delete({id:userId}).$promise.then(
            function () {
                $scope.isRemovedUser = true;
            },
            function(){
                $scope.isRemovedUser = false;
            }
        );
    };

    $scope.deleteRecordF = function (recordId){
        deleteRecord.delete({id:recordId}).$promise.then(
            function () {
                $scope.isRemovedRecord = true;
            },
            function(){
                $scope.isRemovedRecord = false;
            }
        );
    }

});
app.factory('deleteRecord',function($resource){
    return $resource('resources/server/delete-record/:id');
});
app.factory('deleteUser',function($resource){
    return $resource('resources/server/delete-user/:id');
});
app.factory('saveUser',function($resource){
    return $resource('resources/server/user-save/:name');
});
app.factory('getAllUsers',function($resource){
    return $resource('resources/server/get-all-users/');
});
app.factory('getAllRecords',function($resource){
    return $resource('resources/server/get-all-records/');
});
app.factory('startResource',function($resource){
    return $resource('resources/server/startData/:start');
});
app.factory('isLogIn',function($resource){
    return $resource('resources/server/logedIn/');
});
app.factory('stopResource',function($resource){
    return $resource('resources/server/stopData/:stop');
});
app.factory('logOut',function($resource){
    return $resource('resources/server/logout/');
});
app.factory('getAllUsersRecord',function($resource){
    return $resource('resources/server/get-all-user-records/:id');
});



