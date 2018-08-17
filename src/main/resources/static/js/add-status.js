// TODO: 

// make width size a variable
// figure out line spacing
// incorporate bgColorSelection
// make sure that words over the character limit don't put addText() into inifinite loop
// might make it so that the word takes up its own line regardless of charLimit



(function () {

    /* Manipulating drawing with Text Input */

    /* Canvas Variables */
    const kidId = document.querySelector('#kidId').textContent;

    const generatedCanvas = document.querySelector('#generatedCanvas');
    const generatedCanvasProps = window.getComputedStyle(generatedCanvas); // grabbing rendered values from #generatedCanvas

    console.log(generatedCanvasProps);
    console.log('height: ' + generatedCanvasProps.height);
    console.log('width: ' + generatedCanvasProps.width);


    const ctx2 = generatedCanvas.getContext('2d');

    let canvasWidth = parseFloat(generatedCanvasProps.width); // currently rendered width
    console.log(canvasWidth);
    let canvasHeight = parseFloat(generatedCanvasProps.height); // currently rendered height
    console.log(canvasHeight);

    // setting canvas HTML attributes to match currently rendered canvas dimensions
    generatedCanvas.setAttribute('height', ('' + canvasHeight));
    generatedCanvas.setAttribute('width', ('' + canvasWidth));


    let bgColorSelection = 'white';
    let submittedText = "";
    const charLimit = 32;
    let baselineYPos = Math.ceil((canvasHeight / 2) - 3);
    console.log(baselineYPos);
    let baselineXPos = canvasWidth / 2;

    let lines = [];
    let currentLine = 0;

    let setNewLine = false;

    // Font Style
    ctx2.font = "bold 24px Arial";
    console.log(ctx2.font);

    /* RegEx Font Search */
    let canvasFontSizeRegExp = /\d\dpx/; // TODO: create an expression that accounts for font sizes with differing numbers of digits
    let fontSizeSubstring = ctx2.font.search(canvasFontSizeRegExp);
    let canvasFontSize = parseFloat(ctx2.font.substring(parseFloat(fontSizeSubstring)));
    
    
    console.log(canvasFontSizeRegExp.test(ctx2.font));
    console.log('regex indexes ' + ctx2.font.search(canvasFontSizeRegExp));
    console.log('found font size: ' + canvasFontSize);

    /* END RegEx Font Search */

    let lineShiftByPx = (parseFloat(canvasFontSize) / 2);




    /* Input wiring */

    // Status Text Input


    const textInput = document.querySelector('#inputStatusText');
    // textInput.addEventListener('keyup', addText);

    const textInputSubmit = document.querySelector('#addStatusText');
    textInputSubmit.addEventListener('click', function () {
        clearLines();
        addText();
        printLines();
    })

    function clearLines() {
        while (lines.length !== 0) {
            lines.pop();
        }
    }

    function addText() {

        submittedText = textInput.value;
        let numOfLines = 1;

        // Input Display/Word Wrapping

        // calculate numOfLines
        if (submittedText.length > charLimit) { // charLimit currently at 24
            numOfLines = Math.ceil(submittedText.length / charLimit);
            console.log(numOfLines);
        }

        // Word wrapping calculations

        let lineTextCut;
        let startingLineTextTemp;

        for (let i = 0; i < numOfLines; i++) {

            // First line
            if (i === 0) {

                let submittedLineText = submittedText.substring(0, charLimit);
                console.log("Line 1: submittedLineText: " + submittedLineText);

                let lastSpaceIndex = submittedLineText.lastIndexOf(" ");
                let finalLineText;

                if (submittedText.length >= charLimit) {
                    if (lastSpaceIndex !== (charLimit - 1)) {
                        lineTextCut = submittedLineText.substring(lastSpaceIndex + 1, charLimit);
                        console.log('Line 1: lineTextCut: ' + lineTextCut);
                        finalLineText = submittedLineText.substring(0, lastSpaceIndex);
                        console.log('Line 1: finalLineText: ' + finalLineText)
                    } else {
                        finalLineText = submittedText.substring(0, charLimit);
                        console.log('why am I here?')
                    }

                    lines[i] = {
                        text: finalLineText,
                        yPos: baselineYPos,
                    }

                } else {
                    lines[i] = {
                        text: submittedText.substring(0, (submittedText.length)),
                        yPos: baselineYPos,
                    }
                }

                console.log('end of first line')

                // All other lines

            } else {
                /* Varibles */

                let testSub = submittedText.substring((charLimit * i), (charLimit * (i + 1)));

                console.log('adding to line text cut: ' + submittedText.substring((charLimit * i), (charLimit * (i + 1))) + "Length: " + testSub.length);

                if (typeof lineTextCut !== "undefined") {
                    if (testSub.length !== 0 || typeof testSub !== "undefined") {
                        startingLineTextTemp = lineTextCut + submittedText.substring((charLimit * i), (charLimit * (i + 1)));

                    } else {
                        startingLineNextTemp = lineTextCut;
                    }

                } else {
                    startingLineTextTemp = testSub;
                }

                if (startingLineTextTemp.length > charLimit) {
                    lineTextCut = startingLineTextTemp.substring(charLimit);
                } else {
                    lineTextCut = "";
                }
                console.log(lineTextCut);

                let submittedLineText = startingLineTextTemp.substring(0, charLimit);
                console.log(lineTextCut)

                let lastSpaceIndex = submittedLineText.lastIndexOf(" ");

                let finalLineText;

                /* Wrap Calc */

                // if (lineTextCut.length !== 0) {
                if (lastSpaceIndex !== (charLimit - 1)) {
                    lineTextCut = submittedLineText.substring(lastSpaceIndex + 1, charLimit) + lineTextCut;

                    finalLineText = submittedLineText.substring(0, lastSpaceIndex);
                    console.log('Line 2: finalLineText: ' + finalLineText)
                } else {
                    finalLineText = submittedLineText;
                }

                /* Line yPos shifting/creation */

                // shifting lines before creating new line in lines[] array
                // previous line yPos shift
                for (let j = 0; j < lines.length; j++) {
                    lines[j].yPos -= lineShiftByPx;
                    console.log('Line ' + j + ' yPos: ' + lines[j].yPos)
                }

                console.log(lines.length)

                // Line Creation

                let newLineYPos = baselineYPos + (lineShiftByPx * lines.length);
                console.log(lines.length)

                lines[i] = {
                    text: finalLineText,
                    yPos: newLineYPos,

                }

                // Final Line Creation
                console.log('end line ' + (i + 1))

                let nextLine = submittedText.substring((charLimit * (i + 1)), (charLimit * (i + 2)));

                console.log(nextLine);
                console.log(lines.length);
                if ((typeof lineTextCut !== "undefined") || (lineTextCut.length > 0)) {

                    if (nextLine.length === 0) {

                        if (lines[i].text.length + lineTextCut.length < charLimit) {
                            lines[i].text += ' ' + lineTextCut;
                        } else {
                            for (let j = 0; j < lines.length; j++) {
                                lines[j].yPos -= lineShiftByPx;

                            }
                            newLineYPos = baselineYPos + (lineShiftByPx * lines.length);
                            lines[i + 1] = {
                                text: lineTextCut,
                                yPos: newLineYPos,
                            }
                        }
                        break;
                    }
                    else {
                        numOfLines += 1;
                    }

                }

            }
            console.log('loop finished');
        }

        /* Start Render */

        ctx2.clearRect(0, 0, canvasWidth, canvasHeight); // clears canvas for next render

        addBackgroundColor(bgColorSelection);

        console.log(lines);
    }

    function printLines() {
        // print all lines
        for (let x = 0; x < lines.length; x++) {
            ctx2.textAlign = "center";
            ctx2.textBaseline = "middle";
            ctx2.fillText(lines[x].text, baselineXPos, lines[x].yPos);
            console.log("In the render loop")
        }
    }

    // Background Color Input

    const backgroundColorInputForm = document.querySelector('#backgroundColorInputForm');

    backgroundColorInputForm.addEventListener("click", function () {
        let bgColorData = new FormData(backgroundColorInputForm);
        console.log(bgColorData);

        // Test code
        let output = "";

        for (z = 0; z > bgColorData.length; z++) {
            console.log(bgColorData[z]);
        }

        // needs to be here to find the color value string coming from form 
        for (const entry of bgColorData) {
            bgColorSelection = entry[1];
        };
        // END Test Code

        console.log(bgColorSelection);
        addBackgroundColor(bgColorSelection);
        printLines();
    })

    function addBackgroundColor(color) {

        /* TODO: Add other color settings */

        if (color === "black") {
            ctx2.fillStyle = 'black'; // changing color of the next "drawing method"
            ctx2.fillRect(0, 0, canvasWidth, canvasHeight); // pixel start coordinates/size of drawing INSIDE canvas

            ctx2.fillStyle = 'white'; // text color

        } else if (color === "white") {
            ctx2.fillStyle = 'white'; // changing color of the next "drawing method"
            ctx2.fillRect(0, 0, canvasWidth, canvasHeight); // pixel start coordinates/size of drawing INSIDE canvas

            ctx2.fillStyle = 'black'; // text color

        }

    }

    /* Status update canvas image submission */

    const statusSubmitButton = document.querySelector('#submitStatus');
    statusSubmitButton.addEventListener('click', function () {

        // Currently successfully posts multiple updates.
        // All updates currently point to the same image file, however. May fix itself once we generate multiple file names.

        generatedCanvas.toBlob(canvasBlob => {

            const formData = new FormData();

            // TODO: turn these into end-user fields
            formData.append("caption", ""); // assigning @RequestParam key / value pairs
            formData.append("kidId", kidId);

            formData.append("file", canvasBlob);

            const request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    window.location.href = '/kid?id=' + kidId;
                };
            };
            request.open('POST', '/uploadStatus'); // opens POST request to /uploadImage for upload processing
            request.send(formData); // sends RequestParams and image file itself

        });
    })

})();