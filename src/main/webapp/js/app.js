var app = angular.module('abiturient', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider.
        when('/abiturient', {
            templateUrl: '/views/guest.html',
            controller: 'GuestController'
        }).
        when('/user/enrollees', {
            templateUrl: 'views/enrollees.html',
            controller: 'EnrolleesController'
        }).
        when('/login', {
            templateUrl: 'views/login.html',
            controller: 'LoginController'
        }).when('/contact', {
            templateUrl: 'views/contact.html',
            controller: 'ContactController'
        }).otherwise({
            // templateUrl: 'views/enrollees.html'
            redirectTo: '/abiturient'
        }
    );
}]);

var controllers = {};

controllers.GuestController = function ($scope) {
    $scope.list = [1, 2, 3, 4, 5];
};
controllers.EnrolleesController = function ($scope, $http) {
    $scope.list = ['a', '2', 'c'];
    $scope.enrollees = [];
    $scope.getEnrollees = function () {

        var res = $http.get('/authorized/getAllEnrollees');
        res.success(function (data, status, headers, config) {
            $scope.enrollees = data;
            document.getElementById('authorized-content').hidden = false;
            //swal("Success!", "Enrollees:  " + JSON.stringify(data), "success");
        });
        res.error(function (data, status, headers, config) {
                var unauthorized = status == 401;
                if (unauthorized) {
                    document.getElementById('not-authorized-content').hidden = false;
                    var htmlMessage = "<h2>Please <a href=\"http://abiturient-sevgu.rhcloud.com/#/login\">log-in</a></h2>";
                    swal({
                            title: "Access denied",
                            text: "Please log-in",
                            type: "warning",

                            showCancelButton: true,
                            cancelButtonColor: "#DD6B55",
                            confirmButtonText: "Log-in",
                            cancelButtonText: "Cancel"/*,
                            closeOnConfirm: false,
                            closeOnCancel: false*/
                        },
                        function (isConfirm) {
                            if (isConfirm) {
                                location.href = "http://abiturient-sevgu.rhcloud.com/#/login";
                                //swal("Deleted!", "Your imaginary file has been deleted.", "success");
                            } /*else {
                                swal("Cancelled", "Your imaginary file is safe :)", "error");
                            }*/
                        }
                    );


                   /* swal({
                        title: "Access denied",
                        text: htmlMessage,
                        html: true
                    });*/
                } else {
                    swal("Error! Status: " + status,
                        JSON.stringify(data),
                        "error");
                }

            }
        );
    };
    //$scope.enrollees = $scope.getEnrollees();
    $scope.getEnrollees();

}
;

controllers.LoginController = function ($scope, $http) {
    /*    $scope.userLogin = '';
     $scope.userPassword = '';*/

    $scope.login = function () {

        var dataObj = {
            login: $scope.userLogin,
            password: $scope.userPassword
        };
        var res = $http.post('/login', dataObj);
        res.success(function (data, status, headers, config) {
            location.href = "http://abiturient-sevgu.rhcloud.com/#/user/enrollees";
            //swal("Success!", "Now you are logged in! " + JSON.stringify(data), "success");*/
        });
        res.error(function (data, status, headers, config) {
            swal("Error status: " + status, JSON.stringify(data), "error");
        });
    }
};
controllers.ContactController = function ($scope) {
    $scope.data = "ContactController";
};

app.controller(controllers);

/*
 var myApp = angular.module('myApp', ['ngRoute']);

 myApp.config(function ($routerProvider, $locationProvider) {

 $routerProvider.when('/login', {
 controller: 'LoginController'

 }).when('/enrollees', {
 controller: 'EnrolleesController',
 templateUrl: '/views/enrollees.html'
 }).when('/', {
 controller: 'GuestController',
 templateUrl: '/views/guest.html'
 }).otherwise(
 {
 // templateUrl: 'views/enrollees.html'
 redirectTo: '/'}
 );

 // $locationProvider.html5Mode(true);
 });


 myApp.controller('LoginController', function($scope) {
 /!*
 отправляем данные из полей login и password на сервер(url: /login)
 если результат нормальный - перенаправляем на EnrolleesController иначе - выводим сообщение о неверном логине/пароле
 *!/
 });

 myApp.controller('EnrolleesController', function($scope){

 });
 myApp.controller('GuestController', function($scope){
 $scope.name = "Ivan";
 });*/
