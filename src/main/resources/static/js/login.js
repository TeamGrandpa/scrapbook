(function () {

    let passwordInput = document.getElementById('passwordInput');

    passwordInput.addEventListener('keyup', function (event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            let inputForm = document.getElementById('inputForm');
            inputForm.submit();
        }
    })

})();