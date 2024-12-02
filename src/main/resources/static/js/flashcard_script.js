document.addEventListener("DOMContentLoaded", function() {
  var card = document.querySelector(".card");
  var playing = false;

  if (card) { // Đảm bảo phần tử .card tồn tại
    card.addEventListener('click', function() {
      if (playing) return;

      playing = true;
      anime({
        targets: card,
        scale: [{value: 1}, {value: 1.4}, {value: 1, delay: 250}],
        rotateY: {value: '+=180', delay: 200},
        easing: 'easeInOutSine',
        duration: 200,
        complete: function() {
          playing = false;
        }
      });
    });

  } else {
    console.error('Không tìm thấy phần tử .card');
  }
});

document.addEventListener("DOMContentLoaded", function () {
    const dropdownIcon = document.getElementById("dropdownIcon");
    const dropdownMenu = document.getElementById("dropdownMenu");

    dropdownIcon.addEventListener("click", function () {
        dropdownMenu.classList.toggle("show-menu");
    });

    // Close the dropdown when clicking outside
    document.addEventListener("click", function (event) {
        if (!dropdownIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.classList.remove("show-menu");
        }
    });
});