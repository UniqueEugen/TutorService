document.addEventListener('DOMContentLoaded', function() {
    fetch('home/api/recommended')
        .then(response => response.json())
        .then(data => {
            console.log(data)
            const cardRow = document.getElementById('card-row');

            if (data && data.length > 0) {
                data.forEach(item => {
                    const card = document.createElement('div');
                    card.className = 'col-md-3 mb-4';
                    card.innerHTML = `
                                <div class="card rec-card" style="width: 17.5rem;">
                                     <div class="card-img-top">
                                        <div class="photo ${item.photo ? '' : 'no-photo'}" style=" ${item.photo ? 'background-image: url(/photo/getImage/' + item.photo.id : ''}"></div>
                                     </div>
                                    <!--<img src="${item.photo ? '/photo/getImage/' + item.photo.id : ''}" class="card-img-top ${item.photo ? 'photo' : 'photo no-photo'}" alt="${item.name}">-->
                                    <div class="card-body">
                                        <h5 class="card-title">${item.name} ${item.surname}</h5>
                                        <h6 class="card-subtitle mb-2 text-muted">${item.specialisation}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted"> ${item.price}$</h6>
                                        <p class="card-text rec-card-text" style="min-height: 2rem">${item.description || 'Я стесняюсь рассказывать о себе, поэтому ты видишь эту надпись)'}</p>
                                        <a onclick="redirectToTutorProfile(${item.id})" class="btn mr-2"><i class="fa fa-link"></i>Профиль</a>
                                        <a href="tel:${item.phone}" class="btn"><i class="fa fa-phone"></i>Звонок</a>
                                    </div>
                                </div>
                            `;
                    cardRow.appendChild(card);
                    document.getElementById('recommended-heading').style.display = 'block';
                });
            }
        })
        .catch(error => {
            console.error('Ошибка при загрузке данных:', error);
            document.getElementById('card-row').innerHTML = '<p>Ошибка при загрузке рекомендаций.</p>';
        });
});