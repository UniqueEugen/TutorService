angular.module('profileApp', [])
    .controller('ProfileController', ['$scope', '$http', '$location', function($scope, $http, $location){
        $scope.userData = {};

        $scope.redirectOnAccount = function (){
            // Перенаправление на /home
            window.location.href = '/home';
        }

        $scope.saveDateAndTime = function() {
            var userData = {
                date: collectDateData(),
                time: collectTimeData(),
                tutorId: getID()
            };
            console.log(userData);

            $http.post('/profile/newOrder', userData, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    // Обработка успешной регистрации
                    console.log(response.data);
                    closeModel(response.data)

                })
                .catch(function(error) {
                    console.log("error");
                    // Обработка ошибки регистрации
                    console.log(error);

                    // Проверка ошибки совпадения логина
                    if (error.status === 400 && error.data === "Login already exists.") {
                        // Вывод сообщения об ошибке совпадения логина
                        console.log("Login already exists. Please choose a different login.");
                        // Дополнительные действия при ошибке совпадения логина, если необходимо
                        handleLoginError();
                    } else {
                        // Другие типы ошибок
                        // ...
                    }
                });
        };
    }]);