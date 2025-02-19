$(function() {
    moment.locale("ru");
    console.log( new Date());
    $('#datetimepicker').datetimepicker({
        //format: 'L',
       // useCurrent: false,
        minDate: new Date(),
        icons: {
            time: "bi bi-clock",
            date: "fa fa-calendar",
            up: "fa fa-chevron-up",
            down: "fa fa-chevron-down",
            previous: "fa fa-chevron-left",
            next: "fa fa-chevron-right",
            today: "fa fa-calendar-check-o",
            clear: "fa fa-trash",
            close: "fa fa-times"
        }
    });
    $('#timepicker').datetimepicker({
        format: 'LT',
        use24hours: true // Использование 24-часового формата
    });
});

function openModal() {
    $('#myModal').modal('show');
}

function closeModel(resText){
    $('#myModal').modal('hide');
    $('#modalText').text(resText);
    $('#successModal').modal('show');
}
function collectDateData() {
    var selectedDate = moment($("#datetimepicker").datetimepicker("viewDate")).format("DD-MM-YYYY");
    console.log("Выбранная дата:", selectedDate);

    // Дополнительная логика обработки или сохранения данных

    // Возвращаем данные, если нужно использовать их в другом месте
    return selectedDate;
}
function collectTimeData() {
    var selectedTime = moment($("#datetimepicker").datetimepicker("viewDate")).format("HH:mm");
    console.log("Выбранное время:", selectedTime);

    // Дополнительная логика обработки или сохранения данных

    // Возвращаем данные, если нужно использовать их в другом месте
    return selectedTime;
}

function getID(){
    var urlString = window.location.href;

// Создаем объект URL с переданным URL адресом
    var url = new URL(urlString);

// Получаем объект URLSearchParams из параметров запроса
    var searchParams = new URLSearchParams(url.search);

// Получаем значение параметра "id"
    return  searchParams.get('id');
}


/*$(function () {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth() + 1; // Месяцы в JavaScript нумеруются с 0, поэтому нужно добавить 1
    var year = today.getFullYear();

// Форматирование даты в формат "DD-MM-YYYY"
    var formattedDate = day + '.' + month + '.' + year;
    /!*$('#datetimepicker1').datetimepicker({
        locale: 'ru',
        stepping:10,
        format: 'DD.MM.YYYY',
        defaultDate: moment('01.11.2017').format('DD.MM.YYYY'),
        daysOfWeekDisabled:[0,6]
    });*!/
    $('#datetimepicker').datetimepicker({
        locale: 'ru',
        stepping:10,
        format: 'DD.MM.YYYY',
        daysOfWeekDisabled:[0,6]
    })
        .data('DateTimePicker').minDate(moment(formattedDate, 'DD.MM.YYYY'))
        .data('DateTimePicker').date;

});*/
