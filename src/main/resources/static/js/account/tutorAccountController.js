angular.module('tutorApp', [])
    .controller('tutorAccountController', ['$scope', '$http', function($scope, $http) {
        $scope.userData = {
        };
        $scope.address = {
        };
        $scope.user = {
        };
        $scope.tutor = {
        };
        $scope.image= null;
        $scope.originalData = {};
        $scope.isEditingUserForm = false;
        /*$scope.isEditingUserDataForm = false;
        $scope.isEditingAddressForm = false;
        $scope.isEditingTutorForm = false;*/
        // Функция для обработки нажатия кнопки "Изменить"
        $scope.editFormUser = function() {
            $scope.isEditingUserForm = true;
            $scope.user = {
                login: $scope.user.login,
                password: $scope.user.password
            }
            $scope.originalData = angular.copy($scope.user);
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditUser = function() {
            setUserDataCancel();
            $scope.isEditingUserForm = false;
        };


        $scope.updateUser = function (){
            var user = getUserData();

            console.log(user);
            $http.post('/account/tutor/setuser', user, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    setNewUserData(user);
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
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            $scope.isEditingTutorForm = false;
        }

       /* $scope.editFormUserData = function() {
            $scope.isEditingUserForm = true;
            $scope.user = {
                login: $scope.user.login,
                password: $scope.user.password
            }
            $scope.originalData = angular.copy($scope.user);
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditUserData = function() {
            $scope.user = angular.copy($scope.originalData)
            $scope.isEditingUserForm = false;
        };*/
        $scope.updateUserData = function (){
            var userData = {
                name: $scope.userData.name,
                surname: $scope.userData.surname,
                secName: $scope.userData.secName,
                eMail: $scope.userData.email,
                phone: $scope.userData.phone,
                age: $scope.userData.age
            }

            console.log(userData);
            $http.post('/account/tutor/setuserdata', userData, { responseType: 'text' })
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
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            /*$scope.isEditing = false;*/
        }
        /*$scope.editFormAddress = function() {
            $scope.isEditingUserForm = true;
            $scope.user = {
                login: $scope.user.login,
                password: $scope.user.password
            }
            $scope.originalData = angular.copy($scope.user);
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditAddress = function() {
            $scope.user = angular.copy($scope.originalData)
            $scope.isEditingUserForm = false;
        };*/
        $scope.updateAddress = function (){
            var address = {
                country: $scope.address.country,
                city: $scope.address.city,
                street: $scope.address.street,
                house: $scope.address.house,
                office: $scope.address.office
            }

            console.log(address);
            $http.post('/account/tutor/setaddress', address, { responseType: 'text' })
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
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            /*$scope.isEditing = false;*/
        }
        /*$scope.editFormTutor = function() {
            $scope.isEditingUserForm = true;
            $scope.user = {
                login: $scope.user.login,
                password: $scope.user.password
            }
            $scope.originalData = angular.copy($scope.user);
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditTutor = function() {
            $scope.user = angular.copy($scope.originalData)
            $scope.isEditingUserForm = false;
        };*/
        $scope.updateTutor = function (){
            var tutor = {
                specialisation: $scope.tutor.specialisation,
                price: $scope.tutor.price,
                description: $scope.tutor.description,
            }

            console.log(tutor);
            $http.post('/account/tutor/setTutor', tutor, { responseType: 'text' })
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
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            /*$scope.isEditing = false;*/
        }

        $scope.uploadPhoto = function () {
            var image = getPhoto();
            console.log(image);
           /* $http.post('/account/tutor/uploadFoto', image, { responseType: 'text' })
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
                });*/
        }

    }])