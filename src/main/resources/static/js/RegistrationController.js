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
                /*var file = document.getElementById('photo').files[0];
                if(file){
                    userData.tutor.photo = {}
                }*/
                /*// Получаем выбранное изображение из элемента input типа file
                var selectedFile = document.getElementById('photo').files[0];
                // Проверяем, было ли выбрано изображение
                if (selectedFile) {
                    userData.tutor.photo = {  };
                    // Создаем новый FileReader для чтения содержимого файла
                    var reader = new FileReader();

                    // Устанавливаем обработчик события onload, который будет вызван после завершения чтения файла
                    reader.onload = function(event) {
                        // Получаем содержимое файла в формате Data URL
                        var fileContent = event.target.result;
                        //Заполняем filename
                        userData.tutor.photo.filename = selectedFile.name;
                        // Заполняем поле content в объекте userData
                        userData.tutor.photo.content = fileContent;
                    };

                    // Читаем содержимое файла в виде массива байтов
                    reader.readAsDataURL(selectedFile);
                } else {
                    // Если изображение не выбрано, устанавливаем значение по умолчанию, например, "ТГДД"
                    userData.tutor.photo=null;
                }*/
            } else if (selectedRole && selectedRole.value === "seeker") {
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
    }]);