function applyFilterAndSort() {
    var sortOrder = document.getElementById("sortOrder").value;
    var filterField = document.getElementById("filterField").value;
    var filterValue = document.getElementById("filterValue").value.toLowerCase();

    var tutors = document.getElementsByClassName("col-md-4");

    Array.from(tutors).forEach(function (tutor) {
        var tutorSpecialization = tutor.getElementsByClassName("card-title")[0].innerText.toLowerCase();
        var tutorName = tutor.getElementsByTagName("p")[0].innerText.toLowerCase();
        var tutorCity = tutor.getElementsByTagName("p")[2].innerText.toLowerCase();

        if (
            (filterField === "specialization" && !tutorSpecialization.includes(filterValue)) ||
            (filterField === "lastName" && !tutorName.includes(filterValue)) ||
            (filterField === "city" && !tutorCity.includes(filterValue))
        ) {
            tutor.style.display = "none";
        } else {
            tutor.style.display = "block";
        }
    });

    var tutorsContainer = document.getElementsByClassName("row")[1];
    var tutorsArray = Array.from(tutorsContainer.getElementsByClassName("col-md-4"));

    if (sortOrder === "asc") {
        tutorsArray.sort(function (a, b) {
            console.log(a)
            var price = a.getElementsByTagName("p")[a.getElementsByTagName("p").length-1].innerText.replace("Price: $", "");
            console.log(price)
            var tutorPriceA = parseFloat(a.getElementsByTagName("p")[a.getElementsByTagName("p").length-1].innerText.replace("Price: $", ""));
            price = b.getElementsByTagName("p")[b.getElementsByTagName("p").length-1].innerText.replace("Price: $", "");
            console.log(b)
            console.log(price)
            var tutorPriceB = parseFloat(b.getElementsByTagName("p")[b.getElementsByTagName("p").length-1].innerText.replace("Price: $", ""));
            return tutorPriceA - tutorPriceB;
        });
    } else if (sortOrder === "desc") {
        tutorsArray.sort(function (a, b) {
            var tutorPriceA = parseFloat(a.getElementsByTagName("p")[a.getElementsByTagName("p").length-1].innerText.replace("Price: $", ""));
            var tutorPriceB = parseFloat(b.getElementsByTagName("p")[b.getElementsByTagName("p").length-1].innerText.replace("Price: $", ""));
            return tutorPriceB - tutorPriceA;
        });
    }

    tutorsArray.forEach(function (tutor) {
        tutorsContainer.appendChild(tutor);
    });
}

document.getElementById("sortForm").addEventListener("submit", function (event) {
    event.preventDefault();
    applyFilterAndSort();
});