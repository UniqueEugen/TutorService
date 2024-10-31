async function fetchUserId() {
    try {
        const res = await fetch('api/getuserid');

        if (!res.ok) {
            throw new Error(`Error: ${res.statusText}`);
        }

        const id = await res.text(); // Получаем текстовый ответ
        const userId = parseInt(id, 10); // Преобразуем в число
        console.log(userId); // Логируем ID пользователя
        return userId; // Возвращаем ID пользователя
    } catch (error) {
        console.error('Ошибка получения ID пользователя:', error);
    }
}

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
        const userId = await fetchUserId();
        displayComments(comments, userId);
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

// Функция для отображения комментариев на странице
/*function displayComments(comments, currentUserId) {
    const commentsList = document.getElementById('comments');
    commentsList.innerHTML = ''; // Очищаем контейнер перед добавлением комментариев
    console.log(currentUserId);
    // Сортируем комментарии, чтобы текущий пользователь был первым
    comments.sort((a, b) => (a.userId === currentUserId ? -1 : 1));

    comments.forEach(comment => {
        const commentElement = document.createElement('div');
        commentElement.className = 'card mb-3';
        commentElement.innerHTML = `
            <div class="card-body">
                <h5 class="card-title">${comment.nameSurname}</h5>
                <p class="card-text"><strong>Комментарий:</strong> ${comment.comment}</p>
                <p class="card-text"><strong>Рейтинг:</strong> ${comment.rating}</p>
                <p class="card-text"><strong>Дата:</strong> ${new Date(comment.dateTime).toLocaleString()}</p>
                <p>${comment.commentId}</p>
                <p>${comment.userId}</p>
                <div class="btn-group" role="group">
                    ${comment.userId == currentUserId ? `
                        <button class="btn btn-warning btn-sm" onclick="editComment(${comment.commentId})">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-danger btn-sm" onclick="deleteComment(${comment.commentId})">
                            <i class="bi bi-trash"></i>
                        </button>
                    ` : ''}
                </div>
            </div>
        `;
        commentsList.appendChild(commentElement);
    });*/
function displayComments(comments, currentUserId) {
    const commentsList = document.getElementById('comments');
    commentsList.innerHTML = ''; // Очищаем контейнер перед добавлением комментариев
    console.log(comments);


    // Сортируем комментарии, чтобы текущий пользователь был первым
    comments.sort((a, b) => (a.userId === currentUserId ? -1 : 1));

    comments.forEach(comment => {
       /* const dateArray = comment.dateTime; // предполагается, что это массив
        const formattedDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0] + " " + dateArray[3] + ":" + dateArray[4];*/
        const dateArray = comment.dateTime; // предполагается, что это массив
        const year = dateArray[0];
        const month = String(dateArray[1]).padStart(2, '0'); // Добавляем ведущий ноль
        const day = String(dateArray[2]).padStart(2, '0'); // Добавляем ведущий ноль
        const hours = String(dateArray[3]).padStart(2, '0'); // Добавляем ведущий ноль
        const minutes = String(dateArray[4]).padStart(2, '0'); // Добавляем ведущий ноль

        // Форматируем дату
        const formattedDate = `${day}-${month}-${year} ${hours}:${minutes}`;

        const commentElement = document.createElement('div');
        commentElement.className = 'comment-card d-flex flex-column';

        const commentHeader = document.createElement('div');
        commentHeader.className = 'comment-header d-flex justify-content-between align-items-center';

        const commentAuthor = document.createElement('div');
        commentAuthor.className = 'comment-author';
        commentAuthor.textContent = comment.nameSurname;

        const commentActions = document.createElement('div');
        commentActions.className = 'comment-actions d-none d-md-block';

        if (comment.userId === currentUserId) {
            const editButton = document.createElement('button');
            editButton.className = 'btn btn-sm btn-outline-secondary';
            editButton.innerHTML = '<i class="bi bi-pencil"></i>';
            editButton.onclick = () => editComment(comment.commentId);

            const deleteButton = document.createElement('button');
            deleteButton.className = 'btn btn-sm btn-outline-danger';
            deleteButton.innerHTML = '<i class="bi bi-trash"></i>';
            deleteButton.onclick = () => deleteComment(comment.commentId);

            commentActions.appendChild(editButton);
            commentActions.appendChild(deleteButton);
        }

        commentHeader.appendChild(commentAuthor);
        commentHeader.appendChild(commentActions);

        const commentContent = document.createElement('div');
        commentContent.className = 'comment-content';
        commentContent.textContent = comment.comment;

        const commentRating = document.createElement('div');
        commentRating.className = 'comment-rating d-flex align-items-center';

        const stars = document.createElement('div');
        stars.className = 'stars';

        for (let i = 0; i < 5; i++) {
            const star = document.createElement('i');
            star.className = `bi bi-star-fill ${i < comment.rating ? 'active' : ''}`;
            stars.appendChild(star);
        }

        commentRating.appendChild(stars);

        const commentDate = document.createElement('div');
        commentDate.className = 'comment-date text-muted text-end';
        commentDate.textContent = formattedDate;

        commentElement.appendChild(commentHeader);
        commentElement.appendChild(commentRating);
        commentElement.appendChild(commentContent);
        commentElement.appendChild(commentDate);

        commentsList.appendChild(commentElement);
    });
    // Проверяем, нужно ли отображать кнопку добавления комментария
    const addCommentButton = document.querySelector('.comm');
    if (comments.some(comment => comment.userId === currentUserId)) {
        addCommentButton.style.display = 'none'; // Скрываем кнопку, если комментарий текущего пользователя уже есть
    } else {
        addCommentButton.style.display = 'block'; // Показываем кнопку, если комментариев текущего пользователя нет
    }
    adjustCommentListHeight();
}

function adjustCommentListHeight() {
    const commentsList = document.getElementById('comments');
    const comments = commentsList.children;

    if (comments.length === 0) {
        commentsList.style.height = 'auto'; // Если комментариев нет, высота не ограничена
        return;
    }

    let maxHeight = 0;

    // Вычисляем максимальную высоту комментария
    for (let comment of comments) {
        const height = comment.offsetHeight;
        if (height > maxHeight) {
            maxHeight = height;
        }
    }

    // Устанавливаем высоту списка комментариев
    commentsList.style.height = `${maxHeight * 2}px`; // Умножаем на 2 для гибкости
}

// Функции для редактирования и удаления комментариев
function editComment(commentId) {
    // Логика редактирования комментария
    console.log(`Редактировать комментарий с ID: ${commentId}`);
}

function deleteComment(commentId) {
    // Логика удаления комментария
    console.log(`Удалить комментарий с ID: ${commentId}`);
}

// Вызов функции для получения комментариев при загрузке страницы
document.addEventListener('DOMContentLoaded', fetchComments);