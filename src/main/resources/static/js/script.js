
document.addEventListener("DOMContentLoaded", function() {
    var avatars = document.querySelectorAll('.avatar');
    avatars.forEach(function(avatar) {
        // Tạo màu nền ngẫu nhiên
        var randomColor = '#' + Math.floor(Math.random()*16777215).toString(16);
        avatar.style.backgroundColor = randomColor;
    });

    // Tạo ra 100 confetti
  for (let i = 0; i < 100; i++) {
    let confetti = document.createElement('div');
    confetti.classList.add('confetti');
    // Xác định vị trí random cho mỗi confetti
    confetti.style.left = `${Math.random() * 100}vw`;
    confetti.style.animationDelay = `${Math.random() * 2}s`; // Delay cho mỗi confetti
    document.body.appendChild(confetti);
  }
});
