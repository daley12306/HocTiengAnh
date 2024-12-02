document.addEventListener('DOMContentLoaded', function () {
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
