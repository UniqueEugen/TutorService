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
    }])
    .controller('CommentController', function($scope, $http) {

        $scope.tutorId = 25; // Пример ID репетитора
        $scope.rating = 5;    // Пример рейтинга
        $scope.commentText = '"Я очень доволен занятиями с репетитором. Уроки всегда проходят в дружелюбной атмосфере, и я чувствую, что получаю много полезной информации. Репетитор умеет объяснять сложные темы простым языком, что очень помогает в обучении. Я рекомендую его всем, кто хочет улучшить свои знания и навыки!"'; // Пример текста комментария

        $scope.addComment = function() {
            console.log("sf")
            var commentRequest = {
                tutor_id: $scope.tutorId,
                rating: $scope.rating,
                comment: $scope.commentText
            };

            $http({
                method: 'POST',
                url: 'http://localhost:8080/profile/api/addcomment',
                data: commentRequest,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function(response) {
                console.log('Комментарий успешно добавлен!', response);
            }, function(error) {
                console.error('Ошибка при добавлении комментария:', error);
            });
        };
    })
;