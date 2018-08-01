(function () {

    let kidImages = [];
    const kidId = document.querySelector('#kidId').textContent;
    const kidColorClass = document.querySelector('#kidColorClass').textContent;

    getKidImages();

    function getKidImages() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                kidImages = JSON.parse(xhr.response);
                kidImageListRender();
            };
        };
        xhr.open('GET', '/kid-images/' + kidId);
        xhr.send();
    }

    function kidImageListRender() {

        //Get container and clear it
        const imageContainer = document.querySelector('#imageContainer');
        imageContainer.innerHTML = '';

        if (!kidImages.length) {
            let newFigure = document.createElement('figure');
            let newFigcaption = document.createElement('figcaption');
            newFigcaption.textContent = 'No current images';
            newFigure.appendChild(newFigcaption);
            imageContainer.appendChild(newFigure);
        }
        else {
            kidImages.forEach(image => {
                
                let imageComments = [];
                getImageComments(image.id)
                    .then(data => {
                        let newFigure = document.createElement('figure');
                        let newImg = document.createElement('img');
                        newImg.setAttribute('src', '/img/' + image.imageUrl);
                        let newFigcaption = document.createElement('figcaption');
                        newFigcaption.textContent = image.caption;
                        
                        newFigure.appendChild(newImg);
                        newFigure.appendChild(newFigcaption);
                        imageContainer.appendChild(newFigure);
                        
                        imageComments = data;
                        console.log(imageComments);
                        let commentContainer = document.createElement('div');
                        commentContainer.setAttribute('class', 'commentsContainer');

                        if (!imageComments.length) {
                            let newCommentDiv = document.createElement('div');
                            newCommentDiv.setAttribute('class', 'commentsDiv');
                            let newCommentText = document.createElement('span');
                            newCommentText.textContent = 'No current comments';
                            newCommentDiv.appendChild(newCommentText);
                            commentContainer.appendChild(newCommentDiv);
                            imageContainer.appendChild(commentContainer);
                        }
                        else {
                            imageComments.forEach(comment => {
                                console.log(comment);
                                let newCommentDiv = document.createElement('div');
                                newCommentDiv.setAttribute('class', 'commentsDiv');
                                let newCommentText = document.createElement('span');
                                newCommentText.textContent = comment.commentContent;
                                newCommentDiv.appendChild(newCommentText);



                                let commentAuthorDiv = document.createElement('div');
                                commentAuthorDiv.setAttribute('id', 'commentAuthor');
                                let newCommentAuthor = document.createElement('span');
                                newCommentAuthor.textContent = '- ' + comment.authorName;
                                commentAuthorDiv.appendChild(newCommentAuthor);
                                newCommentDiv.appendChild(commentAuthorDiv);
                                commentContainer.appendChild(newCommentDiv);
                                
                            })
                            imageContainer.appendChild(commentContainer);
                        }

                        
                        let newDivLine = document.createElement('div');
                        newDivLine.setAttribute('id', 'lightLine');
                        newDivLine.setAttribute('class', kidColorClass);
                        console.log('Drew a line');
                        imageContainer.appendChild(newDivLine);

                    })
                    .catch(err => console.log('ERROR!'));

                

            })
        }

    }


    function getImageComments(imageId) {
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        resolve(JSON.parse(xhr.response));
                    }
                    else reject();
                }
            };
            xhr.open('GET', '/image-comments/' + imageId);
            xhr.send();
        })
    }
















































})();