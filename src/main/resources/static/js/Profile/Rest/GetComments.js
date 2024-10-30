async function fetchComments() {
    // Получаем tutorId из элемента <ul>
   const tutorId = getID()

    if (!tutorId) {
        console.error('ID репетитора не найден.');
        return;
    }

    try {
        // Выполняем запрос к API для получения комментариев
        const response = await fetch(`/profile/api/getcomments?tutorId=${tutorId}`, {
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
        displayComments(comments);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

// Функция для отображения комментариев на странице
function displayComments(comments) {
    const commentsList = document.getElementById('comments');
    commentsList.innerHTML = ''; // Очищаем контейнер перед добавлением комментариев

    comments.forEach(comment => {
        const commentElement = document.createElement('li');
        commentElement.innerHTML = `
            <strong>Комментарий:</strong> ${comment.comment}<br>
            <strong>Рейтинг:</strong> ${comment.rating}<br>
            <strong>Дата:</strong> ${new Date(comment.dateTime).toLocaleString()}<br>
            <strong>Пользователь ID:</strong> ${comment.userId}
        `;
        commentsList.appendChild(commentElement);
    });
}

// Вызов функции для получения комментариев при загрузке страницы
document.addEventListener('DOMContentLoaded', fetchComments);