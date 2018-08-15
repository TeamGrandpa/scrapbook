(function () {

    const canvas = document.getElementById('newCanvas');
    const kidId = document.querySelector('#kidId').textContent;

    sampleCanvas();
    uploadCanvas();


    function sampleCanvas() {
        let ctx = canvas.getContext('2d');

        let grd = ctx.createLinearGradient(0, 0, 0, canvas.height);
        grd.addColorStop(0, 'blue');
        grd.addColorStop(1, 'green');

        ctx.fillStyle = grd;
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.font = '36px Arial';
        ctx.fillStyle = 'white';
        ctx.textAlign = 'center';
        ctx.fillText('Test Status Message', canvas.width / 2, canvas.height / 2);

    }

    function uploadCanvas() {
           
        $('#statusSubmitButton').click(function() { 
            let dataURL = canvas.toDataURL();

            $.ajax({
                type: "POST",
                url: "/uploadStatus",
                data: {
                    kidId: kidId,
                    imgBase64: dataURL
                }
            }).done(function (o) {
                console.log('saved');
                
            });














        })
    }
















})();