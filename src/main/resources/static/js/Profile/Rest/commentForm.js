let editingCommentId = null; // Для хранения ID редактируемого комментария

document.getElementById('submitComment').addEventListener('click', function() {
    const commentText = document.getElementById('commentText').value;
    const rating = getRating();

    if (editingCommentId) {
        // Логика редактирования комментария
        updateComment(editingCommentId, commentText, rating);
    } else {
        // Логика добавления нового комментария
        addComment(commentText, rating);
    }
    if (getRating().check){
        hideCommentForm(true); // Скрываем форму после сохранения
    }else {
        alert('Пожалуйста, выберите оценку.'); // Сообщение об ошибке
    }

});

document.getElementById('cancelComment').addEventListener('click', hideCommentForm);

function showCommentForm(comment = null, rating = null) {
    const commentForm = document.getElementById('commentForm');
    const commentList = document.getElementById('comments');
    const showFromBtn = document.getElementById('showBtn');
    showFromBtn.style.display = 'none';
    commentForm.style.display = 'block'; // Показываем форму
    setTimeout(() => {
        commentForm.classList.add('show'); // Добавляем класс для анимации
        commentList.style.marginTop = '20px'; // Увеличиваем отступ сверху для комментариев
    }, 10); // Задержка для запуска анимации

    if (rating) {
        // Если комментарий передан, заполняем форму данными
        setRating(rating)
        document.getElementById('commentText').value = comment;
        editingCommentId = comment.commentId;

        // Заполняем рейтинг
        const stars = document.querySelectorAll('.stars i');
        stars.forEach(star => {
            star.classList.remove('selected');
            if (parseInt(star.getAttribute('data-value')) <= comment.rating) {
                star.classList.add('selected');
            }
        });
    } else {
        // Если это новый комментарий, очищаем форму
        document.getElementById('commentText').value = '';
        editingCommentId = null;
    }
}

function hideCommentForm(flag=false) {
    const commentForm = document.getElementById('commentForm');
    const commentList = document.getElementById('comments');

    commentForm.classList.remove('show'); // Удаляем класс для анимации
    commentList.style.marginTop = '0'; // Убираем отступ для комментариев
    setTimeout(() => {
        commentForm.style.display = 'none'; // Скрываем форму после анимации
    }, 300); // Задержка для завершения анимации
    if(flag){
        const showFromBtn = document.getElementById('showBtn');
        showFromBtn.style.display = 'block';
    }
}

// Пример функции для добавления комментария
function addComment(text, rating) {
    // Ваша логика для добавления комментария
    console.log('Добавление комментария:', text, rating);
}

// Пример функции для обновления комментария
function updateComment(commentId, text, rating) {
    // Ваша логика для обновления комментария
    console.log('Обновление комментария:', commentId, text, rating);
}

const stars = document.querySelectorAll('.star');
let ratingValue = 0;

stars.forEach(star => {
    star.addEventListener('click', () => {
        ratingValue = star.getAttribute('data-value');
        updateStars();
    });
});

function updateStars() {
    stars.forEach(star => {
        star.classList.remove('selected');
        if (star.getAttribute('data-value') <= ratingValue) {
            star.classList.add('selected');
        }
    });
}


// Пример вызова формы при добавлении комментария
document.querySelector('.comm').addEventListener('click', function() {
    showCommentForm(); // Показываем форму для нового комментария
});


function getRating() {
    const ratings = document.querySelectorAll('input[name="rating"]');
    let data ={
        check: false,
        rating: 0
    };
    for (let rating of ratings) {
        if (rating.checked) {
            data={
                check: true,
                rating:rating.id.replace('rate', '')
            }
            return  data;// Возвращаем номер оценки
        }
    }
    return data; // Если ничего не выбрано
}


function setRating(rating) {
    // Убедимся, что рейтинг в пределах от 1 до 5
    if (rating < 1 || rating > 5) {
        console.error('Рейтинг должен быть от 1 до 5');
        return;
    }

    const ratingInput = document.getElementById(`rate${rating}`);
    if (ratingInput) {
        ratingInput.checked = true; // Устанавливаем выбранный рейтинг
    }
}

// Пример использования: установить рейтинг на 3
setRating(3);