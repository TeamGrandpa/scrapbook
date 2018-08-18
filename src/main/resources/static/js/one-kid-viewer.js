(function () {

    let kidImages = [];
    const kidId = document.querySelector('#kidId').textContent;
    const kidColorClass = document.querySelector('#kidColorClass').textContent;

    getKidImages();
    addNavEventListeners();

    function getCookie(cname) {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    //Nav open and close
    function addNavEventListeners() {

        const navMenu = document.querySelector('#navMenu');
        const menuIcon = document.querySelector('#menuIcon');

        const navClickHandler = function () {
            navMenu.classList.toggle('open');
            menuIcon.classList.toggle('change');
            event.stopPropagation();
        };

        menuIcon.addEventListener('click', navClickHandler);

    };

    function getKidImages() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                kidImages = JSON.parse(xhr.response);
                console.log(kidImages);
                kidImageListRender();
            };
        };
        xhr.open('GET', '/kid-images/' + kidId);
        xhr.send();
    }

    function kidImageListRender() {

        //Add editor menu if user is an editor
        const menuIcon = document.querySelector('#menuIcon');
        if (getCookie('role') === 'editor') {
            menuIcon.setAttribute('style', 'display:block');
        }

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
                let newDateLikeDiv = document.createElement('div');
                newDateLikeDiv.classList.add('dateLikeDiv');
                let newDate = document.createElement('span');
                newDate.setAttribute('class', 'timeStamp');
                newDate.textContent = image.date;
                let heartAndCountDiv = document.createElement('div');
                heartAndCountDiv.classList.add('hCountDiv');
                const heartCount = document.createElement('span');
                heartCount.innerHTML = image.hearts.length;
                //Added heart icon
                let newHeart = document.createElement('img');
                newHeart.classList.add('heartIcon');
                const enduserUserName = getCookie('name');
                if (image.hearts.find(heart => heart.enduser.userName === enduserUserName)) {
                    newHeart.setAttribute('src', '/img/heart-on.svg');
                    newHeart.classList.add('has-heart');
                } else {
                    newHeart.setAttribute('src', '/img/heart-off.svg');
                    newHeart.classList.add('no-heart');
                }
                let newImg = document.createElement('img');
                newImg.classList.add('kidImages');
                newImg.setAttribute('src', image.imageUrl);
                let newFigcaption = document.createElement('figcaption');
                newFigcaption.textContent = image.caption;


                newDateLikeDiv.appendChild(newDate);
                heartAndCountDiv.appendChild(heartCount);
                heartAndCountDiv.appendChild(newHeart);
                newDateLikeDiv.appendChild(heartAndCountDiv);
                newFigure.appendChild(newDateLikeDiv);
                newFigure.appendChild(newImg);
                newFigure.appendChild(newFigcaption);
                newImageDiv.appendChild(newFigure);

                //Added Heart Functionality here
                newHeart.addEventListener('click', toggleHeart);
                function toggleHeart() {
                    const enduserUserName = getCookie('name');
                    let updatedHeartCount = +heartCount.innerHTML;
                    if (newHeart.classList.contains('no-heart')) {
                        newHeart.classList.remove('no-heart');
                        newHeart.setAttribute('src', '/img/heart-on.svg');
                        newHeart.classList.add('has-heart');
                        updatedHeartCount++;
                    } else {
                        newHeart.classList.remove('has-heart');
                        newHeart.setAttribute('src', '/img/heart-off.svg');
                        newHeart.classList.add('no-heart');
                        updatedHeartCount--;
                    }

                    const xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            const imageHeartCount = JSON.parse(xhr.response);
                            console.log(imageHeartCount);

                            heartCount.innerHTML = updatedHeartCount;
                        };
                    };
                    xhr.open('POST', '/hearts/enduserUserName/' + enduserUserName + '/imageid/' + image.id);
                    xhr.send();
                };

                //Add image comments
                let commentContainer = document.createElement('div');
                commentContainer.setAttribute('class', 'commentsContainer');
                commentContainer.setAttribute('id', 'c' + image.id);
                newImageDiv.appendChild(commentContainer);
                renderComments(commentContainer, image.comments);

                //Add thin line before add comment button
                let newThinLine = document.createElement('div');
                newThinLine.setAttribute('class', 'thinLine');
                newImageDiv.appendChild(newThinLine);

                //Add comment button
                let buttonDiv = document.createElement('div');
                buttonDiv.setAttribute('class', 'buttonDiv');

                let addCommentButton = document.createElement('a');
                addCommentButton.setAttribute('class', 'commentButton');
                addCommentButton.setAttribute('id', 'b' + image.id);
                addCommentButton.textContent = String.fromCharCode(10011) + ' Add Comment';
                buttonDiv.appendChild(addCommentButton);

                //Add comment form
                let commentForm = document.createElement('form');
                commentForm.setAttribute('class', 'commentForm');
                commentForm.setAttribute('id', 'f' + image.id);
                let commentTextInput = document.createElement('textarea');
                commentTextInput.setAttribute('class', 'commentTextInput');
                commentTextInput.setAttribute('id', 't' + image.id);
                commentTextInput.setAttribute('form', 'commentInput');
                commentTextInput.setAttribute('name', 'commentText');
                commentTextInput.setAttribute('rows', '2');
                commentTextInput.setAttribute('maxlength', '140');
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
            let imageNum = commentButtonId.substring(1, commentButtonId.length);
            let commentTextInput = document.getElementById('t' + imageNum);
            let commentForm = document.getElementById('f' + imageNum);
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
            let imageNum = commentFormId.substring(1, commentFormId.length);
            let commentTextInput = document.getElementById('t' + imageNum);

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
                            let commentContainer = document.getElementById('c' + imageNum);
                            renderComments(commentContainer, updatedImageComments);

                            //Reset comment form
                            commentForm.querySelector('[name=commentText]').value = '';
                            let commentButton = document.getElementById('b' + imageNum);
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

    function removeComment(commentContainer, commentId) {

        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                let updatedImageComments = JSON.parse(xhr.response);
                renderComments(commentContainer, updatedImageComments);
            };
        };
        xhr.open('PUT', `/removeComment?commentId=${commentId}`);
        xhr.send();
    }

    function renderComments(commentContainer, imageComments) {

        commentContainer.innerHTML = '';
        if (!imageComments.length) {

        }
        else {
            imageComments.forEach(comment => {
                let newCommentDiv = document.createElement('div');
                newCommentDiv.setAttribute('class', 'commentsDiv');
                let newCommentAuthor = document.createElement('span');
                newCommentAuthor.setAttribute('class', 'commentAuthor');
                newCommentAuthor.textContent = comment.enduser.userName;
                let newCommentText = document.createElement('span');
                newCommentText.textContent = comment.commentContent;

                if (getCookie('name') === comment.enduser.userName || getCookie('role') === 'editor') {
                    let commentDeleteButton = document.createElement('a');
                    commentDeleteButton.setAttribute('class', 'commentDeleteButton');
                    commentDeleteButton.textContent = String.fromCharCode(10006);
                    newCommentDiv.appendChild(commentDeleteButton);

                    commentDeleteButton.addEventListener('click', function (event) {
                        event.preventDefault();
                        removeComment(commentContainer, comment.id);
                    });
                }

                newCommentDiv.appendChild(newCommentAuthor);
                newCommentDiv.appendChild(newCommentText);
                commentContainer.appendChild(newCommentDiv);
            })
        }
    }

    // This will be an event listener
    // When each heart is rendered for the first time (above), set its
    // click event listener to be this function
    // function toggleHeart(event) {
    //     const heart = this; // OR event.target... same thing here
    //     Get a reference to the heart count
    //     const heartcount = heart.parentElement.querySelector('.___');

    // Check heart class to see if currently hearted
    // if hearted...
    // send request to un-heart / delete heart in database
    // when response comes, change heart class to unhearted and reduce heart count
    // else (if NOT hearted)...
    // send request to heart / create heart in db
    // on response, change heart class to appear red, increase heart count

    //     var clicks = 0;
    //     var hasClicked = false;
    //            function onClick() 
    //         {
    //             if (!hasClicked) 
    //                 {
    //                     clicks += 1;
    //                     document.getElementById("output").innerHTML = clicks;
    //                     hasClicked = true; 
    //                 };

    //         };
    // }

    return;

    $count.text(function (idx, txt) {
        // convert text to number and increment by one
        return +txt + 1;
    });
    $(document).on('click', ".notliked", function () {
        var $this = $(this);
        $this.removeClass('notliked');
        $this.addClass('liked')
        $count = $('.likes-count');
        $count.text(function (idx, txt) {
            return +txt + 1;
        });

    });

    $(document).on('click', ".liked", function () {
        var $this = $(this);
        $this.removeClass('liked');
        $this.addClass('notliked');
        $count = $('.likes-count');
        $count.text(function (idx, txt) {
            return (+txt == 0) ? 0 : (+txt - 1);
        });

    });


})();