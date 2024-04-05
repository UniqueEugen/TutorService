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
        $scope.isEditingUserDataForm = false;
        $scope.isEditingAddressForm = false;
        $scope.isEditingTutorForm = false;
        // Функция для обработки нажатия кнопки "Изменить"
        $scope.editFormUser = function() {
            $scope.isEditingUserForm = true;
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditUser = function() {
            setUserDataCancel();
            $scope.isEditingUserForm = false;
        };

        $scope.update = function () {
            if (!$scope.isEditingUserDataForm && !$scope.isEditingTutorForm && !$scope.isEditingAddressForm) {
                $scope.updateUser(true);
            }else {
                $scope.isEditingUserForm = false;
                return true;
            }
        }


        $scope.updateUser = function (flag){
            var user = getUserData();
            if(flag){
                $scope.sendUserData(user);
            }else {
                return user;
            }
            console.log(user);
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            $scope.isEditingUserForm = false;
        }

        $scope.sendUserData =function (user){
            $http.post('/account/tutor/setuser', user, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    setNewUserData(user);
                    // Обработка успешной регистрации
                    console.log(response.data);

                    // Перенаправление на /home
                    window.location.href = '/home/logout';
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
        }
        $scope.editFormUserData = function() {
            $scope.isEditingUserDataForm = true;
            $scope.isEditingUserForm=true;
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditUserData = function() {
            setUserDataDataCancel();
            setUserDataCancel();
            $scope.isEditingUserDataForm = false;
            $scope.isEditingUserForm = false;
        };
        $scope.updateUserData = function (){
            var user = $scope.updateUser(false);
            var userData = getUserDataData();

            console.log(userData);
            $scope.sendUserDataData(userData, user);
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            $scope.isEditingUserDataForm = false;
        }

        $scope.sendUserDataData= function (userData, user){
            $http.post('/account/tutor/setuserdata', userData, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    // Обработка успешной регистрации
                    console.log(response.data);
                    $scope.sendUserData(user)
                    setNewUserDataData(userData);
                    $scope.updateUser();
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
        }
        $scope.editAddressForm = function() {
            $scope.isEditingAddressForm = true;
            $scope.isEditingUserForm=true;
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditAddress = function() {
            setAddressDataCancel();
            setUserDataCancel();
            $scope.isEditingUserForm = false;
            $scope.isEditingUserForm = false;
        };
        $scope.updateAddress = function (){
            var user = $scope.updateUser(false);
            var address = getAddressData();

            console.log(address);
            $scope.sendAddress(address, user);
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            $scope.isEditingAddressForm = false;
        }

        $scope.sendAddress = function (address, user){
            $http.post('/account/tutor/setaddress', address, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    $scope.sendUserData(user);
                    // Обработка успешной регистрации
                    console.log(response.data);
                    setNewAddress(address)
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
        }

        $scope.editFormTutor = function() {
            $scope.isEditingTutorForm = true;
            $scope.isEditingUserForm = true;
        };

        // Функция для обработки нажатия кнопки "Отменить"
        $scope.cancelEditTutor = function() {
            setTutorDataCancel();
            setUserDataCancel();
            $scope.isEditingUserForm= false;
            $scope.isEditingTutorForm = false;
        };
        $scope.updateTutor = function (){
            var tutor = getTutorData();
            var user = $scope.updateUser(false);
            console.log(tutor);
            $scope.sendTutor(tutor, user);
            // После сохранения/обновления данных сбросить форму и выйти из режима редактирования
            $scope.isEditingTutorForm = false;
        }
        $scope.sendTutor = function (tutor, user){
            $http.post('/account/tutor/setTutor', tutor, { responseType: 'text' })
                .then(function(response) {
                    console.log("success");
                    $scope.sendUserData(user);
                    // Обработка успешной регистрации
                    console.log(response.data);

                    setNewTutorData(tutor);
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
        }
    }])