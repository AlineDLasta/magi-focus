
const display = document.getElementById('display');

let seconds = 0;
let timer;

function update() {
    display.textContent = new Date(seconds * 1000)
        .toISOString().substring(11, 19);
}

document.getElementById('start').onclick = () => {
    if (!timer) timer = setInterval(() => {
        seconds++;
        update();
    }, 1000);
};

document.getElementById('pause').onclick = () => {
    clearInterval(timer);
    timer = null;
};

document.getElementById('reset').onclick = () => {
    clearInterval(timer);
    timer = null;
    seconds = 0;
    update();
};

document.getElementById('ask').onclick = async () => {
    const response = await fetch('http://localhost:8080/api/ia/perguntar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            pergunta: document.getElementById('question').value
        })
    });

    const data = await response.json();
    document.getElementById('answer').textContent = data.resposta;
};

update();

