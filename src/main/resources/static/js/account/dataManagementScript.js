
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
        },
        seeker:{
            city: document.getElementById("citySeeker").value,
            description: document.getElementById("descriptionSeeker").value
        }
    }

    console.log(data);
});
//User block
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



//UserData block
function getUserDataData() {
    return {
        name: document.getElementById("firstName").value,
        surname: document.getElementById("lastName").value,
        secName: document.getElementById("middleName").value,
        age: document.getElementById("age").value,
        phone: document.getElementById("phone").value,
        email: document.getElementById("email").value
    };
}

function setUserDataDataCancel(){
    document.getElementById("firstName").value = data.userData.name;
    document.getElementById("lastName").value = data.userData.surname;
    document.getElementById("middleName").value = data.userData.secName;
    document.getElementById("age").value = data.userData.age;
    document.getElementById("phone").value = data.userData.phone;
    document.getElementById("email").value = data.userData.eMail;
}

function setNewUserDataData(userData){
    data.userData = userData;
}

//Address block
function getAddressData() {
    return {
        country: document.getElementById("country").value,
        city: document.getElementById("city").value,
        street: document.getElementById("street").value,
        house: document.getElementById("house").value,
        office: document.getElementById("office").value,
    };
}

function setAddressDataCancel(){
    document.getElementById("country").value = data.address.country;
    document.getElementById("city").value = data.address.city;
    document.getElementById("street").value = data.address.street;
    document.getElementById("house").value = data.address.house;
    document.getElementById("office").value = data.address.office;
}

function setNewAddress(address){
    data.address = address;
}


function getTutorData() {
    return {
        specialisation: document.getElementById("specialisation").value,
        description: document.getElementById("description").value,
        price: document.getElementById("price").value
    };
}

function setTutorDataCancel(){
    document.getElementById("description").value = data.tutor.description;
    document.getElementById("price").value = data.tutor.price;
    document.getElementById("specialisation").value = data.tutor.specialisation;
}

function setNewTutorData(tutor){
    data.tutor = tutor;
}

function getSeekerData() {
    return {
        login: document.getElementById("citySeeker").value,
        password: document.getElementById("descriptionSeeker").value
    };
}

function setSeekerDataCancel(){
    document.getElementById("citySeeker").value = data.seeker.city;
    document.getElementById("descriptionSeeker").value = data.seeker.description;
}

function setNewSeekerData(seeker){
    data.seeker = seeker;
}





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


