const guarantee = new Promise((resolve, reject) => {

    setTimeout(function() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    resolve(xhr.responseText);
                }
                else reject();
            }
        };
        xhr.open('GET', 'https://swapi.co/api/people/1');
        xhr.send();

    }, 3000);
});

guarantee
    .then(data => console.log(data))
    .catch(err => console.log('ERROR!'));

