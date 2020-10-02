function drawImageOnCanvas(sourceImg){
    var canvas = document.getElementById('canvas');
    canvasContext = canvas.getContext('2d');
    canvas.width = sourceImg.width;
    canvas.height = sourceImg.height;
    canvasContext.drawImage(sourceImg, 0, 0);
    return canvasContext;
}


function rgbToGray(R, G, B){
    return 0.3*R + 0.59*G + 0.11*B;
    // return parseInt((R + G + B)/3);
}

function Graify(ctx, sourceImg){
    var imageData = ctx.getImageData(0, 0, sourceImg.width, sourceImg.height);
    for(var dot = 0; dot < sourceImg.width * sourceImg.height; dot++){
        var R = imageData.data[dot*4];
        var G = imageData.data[dot*4+1];
        var B = imageData.data[dot*4+2];
        var Y = rgbToGray(R, G, B);
        imageData.data[dot*4] = Y;
        imageData.data[dot*4+1] = Y;
        imageData.data[dot*4+2] = Y;
    }
    ctx.putImageData(imageData, 0, 0);
}
function __toAscii(data, interval, ascii){
    var p;
    for(p = 0; p < ascii.length; p++){
        if (interval[p] > data){
            return ascii[p-1];
        }
    }
    return ascii[p-1];
}

function toAscii(ctx,sourceImg){
    var imageData = ctx.getImageData(0, 0, sourceImg.width, sourceImg.height);
    console.log(imageData)
    var ascii = "$%^&*#@!QWERTYUIOPLKJHG";
    var interval = [];
    var step = parseInt(255/ascii.length);
    for(var i = 0; i < ascii.length; i += 1) {
        interval.push(i*step);
    }
    if (ascii.length != interval.length){
        console.log("Panic");
    } else {
        console.log(interval);
        console.log(ascii);
    }
    var res = "";
    for(var row = 0; row < sourceImg.height; row+=1){
        rowChar = "";
        for(var col = 0; col < sourceImg.width; col+=1){
            var index = row * sourceImg.width + col;
            rowChar += __toAscii(imageData.data[index*4], interval, ascii);
        }
        rowChar += "\n";
        res += rowChar;
    }
    var t = document.createTextNode(res);
    var p = document.createElement('p');
    var b = document.getElementById('body');
    p.setAttribute('id', 'res');
    var pre_p = document.getElementById('res');
    if (pre_p){
        b.removeChild(pre_p);
    }
    p.style.fontFamily = 'monospace';
    //remove previous textNode
    p.appendChild(t);
    b.appendChild(p);
}
function preview(){
    var input = document.getElementById('input');
    var file = input.files[0];
    var reader = new FileReader();
    var sourceImg = document.createElement('img');
    sourceImg.onload = function(){
        var ctx = drawImageOnCanvas(sourceImg);
        Graify(ctx, sourceImg);
        console.log("Graified");
        toAscii(ctx,sourceImg);
    }
    reader.addEventListener('load', function(){
        sourceImg.src = reader.result;
    },false)

    if(file){
        reader.readAsDataURL(file);
    }

}
