angular.module('registrationApp', [])
    .controller('RegistrationController', ['$scope', '$http', function($scope, $http) {
        $scope.userData = {};
        $scope.address = {};
        $scope.user = {};
        $scope.tutor = {};
        $scope.seeker = {};
        $scope.registerUser = function() {
            var userData = {
                name: $scope.userData.firstName,
                surname: $scope.userData.surname,
                email: $scope.userData.email,
                phone: $scope.userData.phone,
                secName: $scope.userData.secondName,
                age: $scope.userData.age,
                user: {
                    login: $scope.user.username,
                    password: $scope.user.password
                },
                tutor: null,
                seeker: null
            };
            // Получаем выбранный элемент radio button
            var selectedRole = document.querySelector('input[name="role"]:checked');

            if (selectedRole && selectedRole.value === "tutor") {
                userData.user.roles = [{
                    id: 2,
                    name: "TUTOR"
                }]
                userData.tutor = {
                    specialisation: $scope.tutor.specialisation,
                    price: $scope.tutor.price,
                    description: $scope.tutor.description,
                    address: {
                        country: $scope.address.country,
                        city: $scope.address.city,
                        street: $scope.address.street,
                        house: $scope.address.house,
                        office: $scope.address.office,
                    }
                };
            } else if (selectedRole && selectedRole.value === "seeker") {
                userData.user.roles = [{
                    id: 1,
                    name: "SEEKER"
                }]
                userData.seeker = {
                    description: $scope.seeker.description,
                    city: $scope.seeker.city
                };
            }

            console.log(userData);
            $http.post('/registration/register', userData, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    // Обработка успешной регистрации
                    console.log(response.data);

                    // Перенаправление на /home
                    window.location.href = '/home';
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
   /* .controller('ProfileController', ['$scope', '$http', function($scope, $http){
        $scope.userData = {};
        $scope.newOreder = function() {
            var userData = {
                }
            console.log(userData);
            $http.post('/registration/register', userData, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    // Обработка успешной регистрации
                    console.log(response.data);

                    // Перенаправление на /home
                    window.location.href = '/home';
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
}])*/;