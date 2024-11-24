async function fetchUserId() {
    try {
        const res = await fetch('/analyse/api/getuserid');

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
    const tutorId = await fetchUserId();

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
function displayComments(comments) {
    const commentsList = document.getElementById('comments');
    commentsList.innerHTML = ''; // Очищаем контейнер перед добавлением комментариев
    console.log(comments);

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


        const commentUserId = document.createElement('div');
        commentUserId.style.display = 'none';
        commentUserId.className = "userIdClass";
        commentUserId.textContent = comment.userId;

        const commentId = document.createElement('div');
        commentUserId.style.display = 'none';
        commentUserId.className = "commentIdClass";
        commentUserId.textContent = comment.commentId;

        const commentHeader = document.createElement('div');
        commentHeader.className = 'comment-header d-flex justify-content-between align-items-center';

        const commentAuthor = document.createElement('div');
        commentAuthor.className = 'comment-author';
        commentAuthor.textContent = comment.nameSurname;

        const commentActions = document.createElement('div');
        commentActions.className = 'comment-actions d-none d-md-block';

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

// Вызов функции для получения комментариев при загрузке страницы
document.addEventListener('DOMContentLoaded', fetchComments);