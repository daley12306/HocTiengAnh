
var card = document.querySelector(".card");
var playing = false;

card.addEventListener('click',function() {
  if(playing)
    return;
  
  playing = true;
  anime({
    targets: card,
    scale: [{value: 1}, {value: 1.4}, {value: 1, delay: 250}],
    rotateY: {value: '+=180', delay: 200},
    easing: 'easeInOutSine',
    duration: 400,
    complete: function(anim){
       playing = false;
    }
  });
});



const progressBar = document.querySelector(".progress-bar");
const totalCards = 11;
let currentCard = 1;

function updateProgress() {
  const progressPercent = (currentCard / totalCards) * 100;
  progressBar.style.width = `${progressPercent}%`;
}

updateProgress();
