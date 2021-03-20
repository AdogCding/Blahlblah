function drawImageOnCanvas(sourceImg){
    let canvas = document.getElementById('canvas');
    canvasContext = canvas.getContext('2d');
    canvas.width = sourceImg.width;
    canvas.height = sourceImg.height;
    canvasContext.drawImage(sourceImg, 0, 0);
    return canvasContext;
}
function drawAsciiPictureOnCanvas(ctx, string) {
    let y = 0
    string.forEach((row) => {
        let x = 0;
        row.forEach((char)=>{
            ctx.fillText(char, x, y)
            x+=5;
        })
        y+=5
    })
}
function drawAsciiCodeOnCanvas(ctx, ascii, x, y) {
    ctx.fillText(ascii, x, y);
}
// 计算灰度
function rgbToGray(R, G, B){
    return 0.3*R + 0.59*G + 0.11*B;
}
// 图片灰度化
function Graify(ctx, sourceImg){
    let imageData = ctx.getImageData(0, 0, sourceImg.width, sourceImg.height);
    for(let dot = 0; dot < sourceImg.width * sourceImg.height; dot++){
        let R = imageData.data[dot*4];
        let G = imageData.data[dot*4+1];
        let B = imageData.data[dot*4+2];
        let Y = rgbToGray(R, G, B);
        imageData.data[dot*4] = Y;
        imageData.data[dot*4+1] = Y;
        imageData.data[dot*4+2] = Y;
    }
    ctx.putImageData(imageData, 0, 0);
}
function __toAscii(data, step, string){
    let index = parseInt(data/step)
    return string[index]
}

function toAscii(ctx, width, height){
    let imageData = ctx.getImageData(0, 0, width, height);
    let string = "$%^&*#@!)(*&^%$#";
    let step = parseInt(255/string.length);
    let res = [];
    for(let row = 0; row < height; row+=1){
        let rowCharArray = [];
        for(let col = 0; col < width; col+=1){
            let index = row * width + col;
            debugger
            let char = __toAscii(imageData.data[index*4], step, string);
            rowCharArray.push(char);
        }
        res.push(rowCharArray)
    }
    console.log(res)
    return res;
}
function preview(){
    let input = document.getElementById('input');
    let file = input.files[0];
    let reader = new FileReader();
    let sourceImg = document.createElement('img');
    sourceImg.onload = function(){
        let ctx = drawImageOnCanvas(sourceImg);
        Graify(ctx, sourceImg);
        let height = sourceImg.height
        let width = sourceImg.width
        asciiString = toAscii(ctx,width, height);
        let asciiPictureHeight = asciiString.length;
        let asciiPictureWidth = asciiString[0].length;
        let asciiCanvas = document.createElement("canvas");
        asciiCanvas.setAttribute('width', asciiPictureWidth*10);
        asciiCanvas.setAttribute('height', asciiPictureHeight*10);
        asciiCanvas.setAttribute('id', 'asciiCanvas');
        drawAsciiPictureOnCanvas(asciiCanvas.getContext('2d'), asciiString)
        document.body.appendChild(asciiCanvas)
        console.log(asciiCanvas)
    }
    reader.addEventListener('load', function(){
        sourceImg.src = reader.result;
    },false)

    if(file){
        reader.readAsDataURL(file);
    }
}