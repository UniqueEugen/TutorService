document.addEventListener("DOMContentLoaded", function() {
    // Функция для получения списка репетиторов
    fetch('/api/recommended')
        .then(response => {
            if (!response.ok) {
                throw new Error('Сеть ответила с ошибкой: ' + response.status);
            }
            return response.json(); // Преобразуем ответ в JSON
        })
        .then(data => {
            // Обработка полученных данных
            //const tutorsListDiv = document.getElementById('tutors-list');
            //tutorsListDiv.innerHTML = ''; // Очищаем содержимое

            /*if (data && Array.isArray(data)) {
                data.forEach(tutor => {
                    const tutorDiv = document.createElement('div');
                    tutorDiv.textContent = `Имя: ${tutor.name}, Специализация: ${tutor.specialisation}`;
                    tutorsListDiv.appendChild(tutorDiv);
                });
            } else {
                tutorsListDiv.textContent = 'Нет доступных репетиторов.';
            }*/
            console.log(data)
        })
        .catch(error => {
            console.error('Ошибка при получении данных:', error);
        });
});