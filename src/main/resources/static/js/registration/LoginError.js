var isErrorMessageShown = false; // Переменная для отслеживания состояния подсказки об ошибке

function handleLoginError() {
    // Получение элемента input с вводом логина
    var usernameInput = document.getElementById('username');

    // Проверка наличия уже существующей подсказки
    if (!isErrorMessageShown) {
        // Добавление красной окантовки
        usernameInput.style.borderColor = 'red';

        // Создание элемента для отображения подсказки
        var errorMessage = document.createElement('span');
        errorMessage.style.color = 'red';
        errorMessage.textContent = 'Имя пользователя уже занято';

        // Вставка элемента подсказки после input
        usernameInput.parentNode.insertBefore(errorMessage, usernameInput.nextSibling);

        isErrorMessageShown = true; // Установка флага состояния подсказки
        setTimeout(function() {
            // Моргание окантовки в течение 3 секунд
            usernameInput.style.animation = 'blink-animation 0.5s infinite alternate';

            setTimeout(function() {
                // После 3 секунд окантовка становится красной
                usernameInput.style.animation = '';
                usernameInput.style.borderColor = 'red';
            }, 3000);
        }, 0);
    }
}