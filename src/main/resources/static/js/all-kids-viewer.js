(function () {

    let allKids = [];

    getAllKids();

    function getAllKids() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                allKids = JSON.parse(xhr.response);
                allKidListRender();
            }
        };
        xhr.open('GET', '/all-kids');
        xhr.send();
    }

    function allKidListRender() {

        //Get container and clear it
        const kidListSection = document.querySelector('#kidList');
        kidListSection.innerHTML = '';

        //Add element and Tags content
        if (!allKids.length) {
            return;
        }
        else {
            allKids.forEach(kid => {
                let newKidDiv = document.createElement('div');
                let newKidA = document.createElement('a');
                let kidHref = '/kid?id=' + kid.id;
                newKidA.setAttribute('href', kidHref);
                let kidColor = 'Color' + kid.colorNum;
                newKidA.setAttribute('class', kidColor);
                let newH3 = document.createElement('h3');
                newH3.textContent = kid.name;
                let newImg = document.createElement('img');
                let newImgSrc = '/portraits/' + kid.portraitUrl;
                newImg.setAttribute('src', newImgSrc);

                newKidA.appendChild(newH3);
                newKidA.appendChild(newImg);
                newKidDiv.appendChild(newKidA);
                kidListSection.appendChild(newKidDiv);
            })
        }
    }

})();