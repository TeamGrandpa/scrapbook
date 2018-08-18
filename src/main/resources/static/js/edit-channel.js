(function () {

    const kidColorNum = document.querySelector('#kidColorNum').textContent;
    let radioButtons = document.querySelectorAll('.radio');

    radioButtons.forEach(radio => {
        if (radio.value === kidColorNum) {
            radio.setAttribute('checked', 'checked');
        }
    })

})();