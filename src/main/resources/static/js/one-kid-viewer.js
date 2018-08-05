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
                let newImageDiv = document.createElement('div');
                newImageDiv.setAttribute('id', image.id);
                newImageDiv.setAttribute('class', 'imageDiv');

                //Add image with timestamp above and caption below
                let newFigure = document.createElement('figure');
                let newDate = document.createElement('span');
                newDate.setAttribute('class', 'timeStamp');
                newDate.textContent = image.date;
                let newImg = document.createElement('img');
                newImg.setAttribute('src', '/img/' + image.imageUrl);
                let newFigcaption = document.createElement('figcaption');
                newFigcaption.textContent = image.caption;

                newFigure.appendChild(newDate);
                newFigure.appendChild(newImg);
                newFigure.appendChild(newFigcaption);
                newImageDiv.appendChild(newFigure);

                //Add image comments
                let commentContainer = document.createElement('div');
                commentContainer.setAttribute('class', 'commentsContainer');
                commentContainer.setAttribute('id', image.id + 'comments');

                if (!image.comments.length) {

                }
                else {
                    image.comments.forEach(comment => {
                        console.log(comment);
                        let newCommentDiv = document.createElement('div');
                        newCommentDiv.setAttribute('class', 'commentsDiv');
                        let newCommentAuthor = document.createElement('span');
                        newCommentAuthor.setAttribute('class', 'commentAuthor');
                        newCommentAuthor.textContent = comment.enduser.userName;
                        let newCommentText = document.createElement('span');
                        newCommentText.textContent = comment.commentContent;
                        newCommentDiv.appendChild(newCommentAuthor);
                        newCommentDiv.appendChild(newCommentText);
                        commentContainer.appendChild(newCommentDiv);
                    })
                    newImageDiv.appendChild(commentContainer);
                }

                //Add thin line before add comment button
                let newThinLine = document.createElement('div');
                newThinLine.setAttribute('class', 'thinLine');
                newImageDiv.appendChild(newThinLine);

                //Add comment button
                let buttonDiv = document.createElement('div');
                buttonDiv.setAttribute('class', 'buttonDiv');

                let addCommentButton = document.createElement('a');
                addCommentButton.setAttribute('class', 'commentButton');
                addCommentButton.setAttribute('id', image.id + 'button');
                addCommentButton.textContent = String.fromCharCode(10011) + ' Add Comment';
                buttonDiv.appendChild(addCommentButton);

                //Add comment form
                let commentForm = document.createElement('form');
                commentForm.setAttribute('class', 'commentForm');
                commentForm.setAttribute('id', image.id + 'form');
                let commentTextInput = document.createElement('textarea');
                commentTextInput.setAttribute('class', 'commentTextInput');
                commentTextInput.setAttribute('id', image.id + 'text');
                commentTextInput.setAttribute('form', 'commentInput');
                commentTextInput.setAttribute('name', 'commentText');
                commentTextInput.setAttribute('rows', '2');
                commentTextInput.setAttribute('placeholder', 'Type your comment here');
                commentForm.appendChild(commentTextInput);
                let hiddenUserId = document.createElement('input');
                hiddenUserId.setAttribute('type', 'hidden');
                hiddenUserId.setAttribute('name', 'authorName');
                hiddenUserId.setAttribute('value', 'Dad');
                commentForm.appendChild(hiddenUserId);
                let hiddenImageId = document.createElement('input');
                hiddenImageId.setAttribute('type', 'hidden');
                hiddenImageId.setAttribute('name', 'imageId');
                hiddenImageId.setAttribute('value', image.id);
                commentForm.appendChild(hiddenImageId);

                buttonDiv.appendChild(commentForm);

                newImageDiv.appendChild(buttonDiv);

                //Add colored line to separate between images
                let newColorLine = document.createElement('div');
                newColorLine.setAttribute('id', 'colorLine');
                newColorLine.setAttribute('class', kidColorClass);
                newImageDiv.appendChild(newColorLine);

                imageContainer.appendChild(newImageDiv);
            })
        }
        commentButtonEventListener();
        addImageComment();
    }

    function commentButtonEventListener() {

        const commentButtons = document.querySelectorAll('.commentButton');

        commentButtons.forEach(commentButton => {
            let commentButtonId = commentButton.id;
            let commentTextInput = document.getElementById(commentButtonId[0] + 'text');
            let commentForm = document.getElementById(commentButtonId[0] + 'form');
            commentButton.addEventListener('click', function () {
                commentButton.setAttribute('style', 'display:none');
                commentForm.setAttribute('style', 'display:flex');
                commentTextInput.focus();
            });
        })
    }

    function addImageComment() {

        const commentForms = document.querySelectorAll('.commentForm');

        commentForms.forEach(commentForm => {
            let commentFormId = commentForm.id;
            let commentTextInput = document.getElementById(commentFormId[0] + 'text');

            commentTextInput.addEventListener('keyup', function (event) {
                event.preventDefault();
                if (event.keyCode === 13) {
                    let commentText = commentForm.querySelector('[name=commentText]').value;
                    commentText = commentText.substring(0, commentText.length - 1);
                    let imageId = commentForm.querySelector('[name=imageId]').value;
                    let authorName = commentForm.querySelector('[name=authorName]').value;

                    const xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            let updatedImageComments = JSON.parse(xhr.response);
                            let commentContainer = document.getElementById(commentFormId[0] + 'comments');
                            commentContainer.innerHTML = '';

                            if (!updatedImageComments.length) {

                            }
                            else {
                                updatedImageComments.forEach(comment => {
                                    let newCommentDiv = document.createElement('div');
                                    newCommentDiv.setAttribute('class', 'commentsDiv');
                                    let newCommentAuthor = document.createElement('span');
                                    newCommentAuthor.setAttribute('class', 'commentAuthor');
                                    newCommentAuthor.textContent = comment.authorName;
                                    let newCommentText = document.createElement('span');
                                    newCommentText.textContent = comment.commentContent;
                                    newCommentDiv.appendChild(newCommentAuthor);
                                    newCommentDiv.appendChild(newCommentText);
                                    commentContainer.appendChild(newCommentDiv);
                                })
                            };

                            commentForm.querySelector('[name=commentText]').value = '';
                            let commentButton = document.getElementById(commentFormId[0] + 'button');
                            commentForm.setAttribute('style', 'display:none');
                            commentButton.setAttribute('style', 'display:block');

                        };
                    };
                    xhr.open('POST', `/addComment?commentText=${encodeURIComponent(commentText)}&imageId=${imageId}&authorName=${encodeURIComponent(authorName)}`);
                    xhr.send();
                }
            })
        })
    }





})();