/*
async function getOrders() {
    try {
        // Выполняем запрос к API для получения комментариев
        const response = await fetch(`/analyse/api/getorders`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Проверка на успешный ответ
        if (!response.ok) {
            throw new Error(`Ошибка при получении комментариев: ${response.status}`);
        }

        // Парсим JSON-ответ
        const comments = await response.json();
        console.log(comments);
        displayComments(comments);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

document.addEventListener('DOMContentLoaded', getOrders);*/



// Предопределенная ставка для расчета прибыли
let orderRate = 100; // Например, 100 единиц валюты за заказ

async function fetchTutorPrice() {
    try {
        const res = await fetch('/analyse/api/getprice');

        if (!res.ok) {
            throw new Error(`Error: ${res.statusText}`);
        }

        const price = await res.text(); // Получаем текстовый ответ
        const tutorPrice = parseFloat(price); // Преобразуем в число
        console.log(tutorPrice); // Логируем ID пользователя
        return tutorPrice; // Возвращаем ID пользователя
    } catch (error) {
        console.error('Ошибка получения ID пользователя:', error);
    }
}


async function populateOrdersTable(orders) {
    const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];

    // Очищаем существующие строки таблицы
    ordersTableBody.innerHTML = '';

    // Проходим по заказам и заполняем таблицу
    for (let i = 0; i < Math.min(orders.length, 5); i++) {
        const order = orders[i];

        // Создаем новую строку
        const row = ordersTableBody.insertRow();

        // Заполняем ячейки
        const dateCell = row.insertCell(0);
        const orderCell = row.insertCell(1);
        const statusCell = row.insertCell(2);

        dateCell.textContent = order.date; // Дата
        orderCell.textContent = `Урок по ${order.tutorData.specialisation}`; // Заказ с специальностью

        // Условная логика для статуса
        switch (order.status) {
            case 'PENDING':
                statusCell.textContent = 'В ожидании принятия';
                break;
            case 'CANCELED':
                statusCell.textContent = 'Отменен';
                break;
            case 'CONFIRMED':
                statusCell.textContent = 'Согласован/выполнен';
                break;
            default:
                statusCell.textContent = 'Неизвестный статус'; // На случай, если статус не совпадает
        }
    }

    // Инициализация DataTables
    $('#ordersTable').DataTable({
        paging: false, // Отключаем пагинацию, так как мы ограничим вывод до 5 строк
        info: false, // Отключаем информацию о количестве строк
        searching: false, // Отключаем поиск
        ordering: true // Включаем сортировку
    });
}

async function fetchOrdersData() {
    const response = await fetch('/analyse/api/getorders'); // URL для получения заказов
    const orders = await response.json();
    // Преобразуем временные метки в нужный формат даты
    return orders.map(order => ({
        ...order,
        date: formatTimestampToDate(order.date)
    }));
}

// Функция для преобразования временной метки в формат YYYY-MM-DD
function formatTimestampToDate(timestamp) {
    const date = new Date(timestamp); // Создаем объект даты
    const year = date.getFullYear(); // Получаем год
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Получаем месяц и добавляем 1
    const day = String(date.getDate()).padStart(2, '0'); // Получаем день месяца
    return `${year}-${month}-${day}`; // Возвращаем отформатированную дату
}

async function processOrdersData(orders) {
    const profitData = {};
    const lostProfitData = {};
    const possibleProfitData = {};
    orderRate = await fetchTutorPrice(); // Получаем ставку заказа
    populateOrdersTable(orders);
    orders.forEach(order => {
        const date = order.date; // Используем уже преобразованную дату

        // Общая прибыль
        if (order.status === "CONFIRMED") {
            profitData[date] = (profitData[date] || 0) + orderRate; // Увеличиваем прибыль
        } else {
            lostProfitData[date] = (lostProfitData[date] || 0) + orderRate; // Увеличиваем упущенную прибыль
        }
        possibleProfitData[date] = (possibleProfitData[date] || 0) + orderRate; // Увеличиваем возможную прибыль
    });

    return {
        profit: profitData,
        lostProfit: lostProfitData,
        possibleProfit: possibleProfitData,
        orderRate // Возвращаем orderRate для дальнейших расчетов
    };
}

function calculateTotalOrders(orders) {
    return orders.length; // Общее количество заказов
}

function calculateAverageOrders(orders) {
    if (orders.length === 0) return 0; // Если заказов нет, вернуть 0

    const firstOrderDate = new Date(orders[0].date);
    const currentDate = new Date();
    const diffTime = Math.abs(currentDate - firstOrderDate);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); // Разница в днях

    return (orders.length / diffDays).toFixed(2); // Возвращаем среднее с двумя знаками после запятой
}

function calculateTotalProfit(orders, orderRate) {
    return orders.reduce((total, order) => {
        if (order.status === "CONFIRMED") {
            return total + orderRate; // Увеличиваем общую прибыль на ставку заказа
        }
        return total; // Возвращаем текущую сумму, если статус не "CONFIRMED"
    }, 0);
}

function updateStatistics(orders, orderRate) {
    const totalOrders = calculateTotalOrders(orders);
    const averageOrders = calculateAverageOrders(orders);
    const totalProfit = calculateTotalProfit(orders, orderRate);

    document.getElementById('OrdersSum').innerText = `Общее количество заказов: ${totalOrders}`;
    document.getElementById('OrdersPerDay').innerText = `Среднее количество заказов в день: ${averageOrders}`;
    document.getElementById('TotalProfit').innerText = `Общая прибыль: ${totalProfit}$`; // Предполагается, что у вас есть этот элемент
}

function prepareChartData(ordersData) {
    const dates = Object.keys(ordersData.possibleProfit).sort(); // Сортируем даты
    const profit = dates.map(date => ordersData.profit[date] || 0);
    const lostProfit = dates.map(date => ordersData.lostProfit[date] || 0);
    const possibleProfit = dates.map(date => ordersData.possibleProfit[date] || 0);

    return { dates, profit, lostProfit, possibleProfit };
}

async function renderProfitChart() {
    const orders = await fetchOrdersData();
    const ordersData = await processOrdersData(orders);
    const chartData = prepareChartData(ordersData);

    const ctxProfit = document.getElementById('ordersChart').getContext('2d');
    new Chart(ctxProfit, {
        type: 'line',
        data: {
            labels: chartData.dates,
            datasets: [
                {
                    label: 'Прибыль',
                    data: chartData.profit,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    fill: false,
                },
                {
                    label: 'Упущенная прибыль',
                    data: chartData.lostProfit,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    fill: false,
                },
                {
                    label: 'Возможная прибыль',
                    data: chartData.possibleProfit,
                    borderColor: 'rgba(153, 102, 255, 1)',
                    fill: false,
                },
            ],
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });

    // Обновляем статистику после обработки заказов
    updateStatistics(orders, ordersData.orderRate);
}

document.addEventListener('DOMContentLoaded', renderProfitChart);