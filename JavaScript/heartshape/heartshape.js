function drawHeartShape(ctx, chars){
    var font = '10px monospace';
    ctx.font = font;
    var multiple = 10;
    var x_bound = 150 * multiple;
    var y_bound = 140 * multiple;
    var x_site, y_site;
    for(var y = 0; y < y_bound; y+=10){
        var index = 0;
        for(var x = 0; x < x_bound; x+=10){
            x_site = (x - x_bound/2)/(50*multiple);
            y_site = -(y - y_bound/2)/(50*multiple);
            var a = x_site*x_site + y_site*y_site - 1;
            var b = x_site*x_site*y_site*y_site*y_site;
            if (a*a*a - b <= 0){
                ctx.fillText(chars[index%chars.length], x, y);
                console.log(a*a*a - b, 0, chars[index%chars.length]);
                index++;
            }else{
            }
        }
    }
}
function printCanvas(){
    var str = document.getElementById('string').value;
    if (!str) {
        window.alert("Empty String");
    } else {
        var canvas = document.createElement('canvas');
        canvas.setAttribute('width', '1500px');
        canvas.setAttribute('height', '1500px');
        canvas.setAttribute('id', 'canvas');
        const ctx = canvas.getContext('2d');
        drawHeartShape(ctx, str);
        var preCanvas = document.getElementById('canvas');
        if(preCanvas){
            document.body.removeChild(preCanvas);
            document.body.appendChild(canvas);
        }else{
            console.log("Done");
            document.body.appendChild(canvas);
        }
    }

}
var buttonEvent = document.getElementById('button-addon2').addEventListener('click', printCanvas);
