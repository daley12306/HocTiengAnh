
document.addEventListener("DOMContentLoaded", function() {
    var avatars = document.querySelectorAll('.avatar');
    avatars.forEach(function(avatar) {
        // Tạo màu nền ngẫu nhiên
        var randomColor = '#' + Math.floor(Math.random()*16777215).toString(16);
        avatar.style.backgroundColor = randomColor;
    });
});

