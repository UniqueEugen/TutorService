
var data ={
}
var croppie;
var cropModal;
document.addEventListener("DOMContentLoaded", function() {
    data = {
        userData: {
            name: document.getElementById("firstName").value,
            surname: document.getElementById("lastName").value,
            secName: document.getElementById("middleName").value,
            eMail: document.getElementById("email").value,
            phone: document.getElementById("phone").value,
            age: document.getElementById("age").value
        },
        user: {
            login: document.getElementById("username").value,
            password: document.getElementById("password").value
        },
        address: {
            country: document.getElementById("country").value,
            city: document.getElementById("city").value,
            street: document.getElementById("street").value,
            house: document.getElementById("house").value,
            office: document.getElementById("office").value
        },
        tutor:{
            specialisation: document.getElementById("specialisation").value,
            price: document.getElementById("price").value,
            description: document.getElementById("description").value
        }
    }

    console.log(data);
});

function getUserData() {
    return {
        login: document.getElementById("username").value,
        password: document.getElementById("password").value
    };
}

function setUserDataCancel(){
    document.getElementById("username").value = data.user.login;
    document.getElementById("password").value = data.user.password;
}

function setNewUserData(user){
    data.user = user;
}

function getPhoto() {
    var formData = new FormData();
    var fileInput = document.getElementById('image');
    var file = fileInput.files[0];
    formData.append("image", file);
    $.ajax({
        url: '/account/tutor/uploadPhoto', // URL обработчика на сервере
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            // Обработка успешного ответа от сервера
            console.log('Изображение успешно загружено.');
        },
        error: function(xhr, status, error) {
            // Обработка ошибок
            console.error('Произошла ошибка при загрузке изображения: ' + error);
        }
    });
    return formData;
}

/*croppie = new Croppie(document.getElementById('image-preview'), {
    viewport: {
        width: 300,  // Начальная ширина области обрезки
        height: 300, // Начальная высота области обрезки
        type: 'square' // Тип области обрезки (квадратная)
    },
    boundary: {
        width: 400,  // Ширина области предварительного просмотра
        height: 400  // Высота области предварительного просмотра
    }
});*/


// Обработчик события нажатия кнопки "Обрезать"
document.getElementById('crop-button').addEventListener('click', function() {
    croppie.result({
        type: 'blob', // Получить результат в виде Blob объекта
        size: { width: 800, height: 800 } // Размер результата
    }).then(function(blob) {
        // Создание объекта FormData и добавление обрезанного изображения
        var formData = new FormData();
        formData.append("image", blob, "cropped_image.png");

        // Отправка обрезанного изображения на сервер
        $.ajax({
            url: '/account/tutor/uploadPhoto', // URL обработчика на сервере
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                // Обработка успешного ответа от сервера
                console.log('Изображение успешно загружено.');
                window.location.href = '/account/tutor';
            },
            error: function(xhr, status, error) {
                // Обработка ошибок
                console.error('Произошла ошибка при загрузке изображения: ' + error);
            }
        });
    });
    cropModal.style.display = 'none';
});






function openCropModal(imageURL) {
    cropModal = document.getElementById('crop-modal');
    var imagePreview = document.getElementById('image-preview');

    // Создание экземпляра Croppie с контейнером для отображения изображения
    croppie = new Croppie(imagePreview, {
        viewport: {
            width: 300,
            height: 300,
            type: 'square' // или 'circle', если нужно
        },
        boundary: {
            width: 400,
            height: 400
        },
        // Дополнительные настройки Croppie
    });

    // Загрузка изображения в Croppie
    croppie.bind({
        url: imageURL
    });

    // Отображение модального окна
    cropModal.style.display = 'block';
}


document.addEventListener("DOMContentLoaded", function() {
    var imageInput = document.getElementById("image-input");
    document.getElementById("image-preview");
    imageInput.addEventListener("change", function() {
        var file = imageInput.files[0];
        var imageURL = URL.createObjectURL(file);
        openCropModal(imageURL);
    });
});