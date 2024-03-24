var blockOrder = ["userDataBlock", "seekerBlock", "tutorBlock", "addressBlock", "userBlock"];
var currentIndex = 0;

function showBlock(blockId) {
    for (var i = 0; i < blockOrder.length; i++) {
        var block = document.getElementById(blockOrder[i]);
        if (blockId === blockOrder[i]) {
            block.style.display = "block";
        } else {
            block.style.display = "none";
        }
    }
}

function next() {
    if (checkValidation(blockOrder[currentIndex])) {
        console.log(blockOrder)
        if (currentIndex === 0) {
            showSelectedBlock();
        }

        if (currentIndex < blockOrder.length - 1) {
            console.log(blockOrder)
            currentIndex++;
            showBlock(blockOrder[currentIndex]);
        }
    }
}

function previous() {
    if (currentIndex > 0) {
        currentIndex--;
        showBlock(blockOrder[currentIndex]);
    }
}

function showSelectedBlock() {
    var tutorRadio = document.getElementById("roleTutor");
    var seekerRadio = document.getElementById("roleSeeker");
    var formGroupsT = document.getElementsByClassName("requiredChangeTutor");
    var formGroupsS = document.getElementsByClassName("requiredChangeSeeker");

    if (tutorRadio.checked) {
        blockOrder = ["userDataBlock", "tutorBlock", "addressBlock", "userBlock"];

        for (var i = 0; i < formGroupsS.length; i++) {
            var inputElements = formGroupsS[i].getElementsByTagName("input");
            for (var j = 0; j < inputElements.length; j++) {
                inputElements[j].removeAttribute("required");
            }
        }
        for (var i = 0; i < formGroupsT.length; i++) {
            var inputElements = formGroupsT[i].getElementsByTagName("input");
            for (var j = 0; j < inputElements.length; j++) {
                inputElements[j].setAttribute("required", "required");
            }
        }
    } else if (seekerRadio.checked) {
        blockOrder = ["userDataBlock", "seekerBlock", "userBlock"];

        for (var i = 0; i < formGroupsT.length; i++) {
            var inputElements = formGroupsT[i].getElementsByTagName("input");
            for (var j = 0; j < inputElements.length; j++) {
                inputElements[j].removeAttribute("required");
            }
        }
        for (var i = 0; i < formGroupsS.length; i++) {
            var inputElements = formGroupsS[i].getElementsByTagName("input");
            for (var j = 0; j < inputElements.length; j++) {
                inputElements[j].setAttribute("required", "required");
            }
        }
    }
}


function checkValidation(id) {
    // Получение всех корневых блоков с классом 'registration-block'
    var registrationBlock = document.getElementById(id);
    // Получение всех обязательных полей внутри текущего блока
    var requiredFields = registrationBlock.querySelectorAll('input[required]');
    // Проверка каждого обязательного поля
    var isValid = true;
    for (var j = 0; j < requiredFields.length; j++) {
        var field = requiredFields[j];
        if (field.value === '') {
            isValid = false;
            field.style.borderColor = 'red'; // Выделение поля красной окантовкой
            field.placeholder = 'Необходимо заполнить поле'; // Вывод сообщения в placeholder
        } else {
            field.style.borderColor = ''; // Сброс окантовки
            field.placeholder = ''; // Сброс сообщения в placeholder
        }
    }

    // Проверка наличия радио-кнопок внутри текущего блока
    var radioButtons = registrationBlock.querySelectorAll('input[type="radio"][required]');
    if (radioButtons.length > 0) {
        var radioButtonsChecked = false;
        for (var k = 0; k < radioButtons.length; k++) {
            if (radioButtons[k].checked) {
                radioButtonsChecked = true;
                break;
            }
        }

        // Если не выбраны радио-кнопки, выведите сообщение об ошибке
        // Если не выбраны радио-кнопки, выведите сообщение об ошибке
        if (!radioButtonsChecked) {
            // Проверка наличия сообщения об ошибке
            var existingErrorMessage = document.getElementById("errorRadioCheck");
            if (!existingErrorMessage) {
                var errorMessage = document.createElement('div');
                errorMessage.id="errorRadioCheck";
                errorMessage.className = 'error-message';
                errorMessage.innerText = 'Необходимо выбрать вариант.';
                errorMessage.style.color = 'red';
                registrationBlock.insertBefore(errorMessage, registrationBlock.childNodes[registrationBlock.childNodes.length - 6]);
            }
            isValid = false;
        } else {
            // Проверка наличия сообщения об ошибке
            var existingErrorMessage = document.getElementById("errorRadioCheck");
            if (existingErrorMessage) {
                existingErrorMessage.parentNode.removeChild(existingErrorMessage);
            }
        }
    }

    // Если не все поля заполнены, выведите сообщение об ошибке
    if (!isValid) {
        /*var errorMessage = document.createElement('div');
        errorMessage.className = 'error-message';
        errorMessage.innerText = 'Необходимо заполнить все обязательные поля.';
        registrationBlock.insertBefore(errorMessage, registrationBlock.firstChild);*/
        return false;
    } else {
        return true
    }
}