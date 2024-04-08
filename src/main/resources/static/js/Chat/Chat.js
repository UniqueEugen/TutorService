// Подключение к серверу WebSocket
var socket = new SockJS('/websocket-endpoint');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected to WebSocket');

    // Подписка на тему комнаты
    var roomId = [[${roomId}]];
    var topic = '/topic/room/' + roomId;

    stompClient.subscribe(topic, function (message) {
        var chatMessage = JSON.parse(message.body);
        displayChatMessage(chatMessage);
    });
});

// Функция для отправки сообщения
function sendMessage() {
    var messageInput = $('#messageInput');
    var roomId = [[${roomId}]];

    var payload = {
        roomId: roomId,
        message: messageInput.val()
    };

    stompClient.send("/app/chat/" + roomId + "/sendMessage", {}, JSON.stringify(payload));

    messageInput.val('');
}

// Обработка отправки формы
$('#messageForm').submit(function (event) {
    event.preventDefault();
    sendMessage();
});

// Функция для отображения сообщения
function displayChatMessage(chatMessage) {
    var messageContainer = $('#messageContainer');
    var messageDiv = $('<div>').addClass('alert alert-info').text(chatMessage.message);
    messageContainer.append(messageDiv);
}