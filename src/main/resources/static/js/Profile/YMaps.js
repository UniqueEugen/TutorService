ymaps.ready(init);

function init() {
    // Создание карты.
    var myMap = new ymaps.Map("map", {
        center: [52.89884751645912, 30.046546073857204], // Координаты центра карты (Москва)
        zoom: 10 // Уровень масштабирования
    });

    // Получение адреса репетитора из модели и добавление метки на карту.
    var address = "";
    // Форматирование названия страны
    address += document.getElementById("country").textContent;

    // Форматирование города
    address += ", город:" + document.getElementById("city").textContent;

    // Форматирование улицы и дома
    address += ", " + document.getElementById("street").textContent + ", " + document.getElementById("house").textContent;
    console.log(address);
    ymaps.geocode(address).then(function (res) {
        var firstGeoObject = res.geoObjects.get(0);

        // Позиционирование карты на найденный адрес.
        myMap.geoObjects.add(firstGeoObject);
        myMap.setBounds(firstGeoObject.properties.get('boundedBy'), {checkZoomRange: true});
    });
}