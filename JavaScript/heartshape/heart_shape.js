function drawHeartShape(ctx, chars){
    let font = '10px monospace';
    ctx.font = font;
    const multiple = 10;
    const x_bound = 150 * multiple;
    const y_bound = 140 * multiple;
    let x_site, y_site;
    for(let y = 0; y < y_bound; y+=10){
        let index = 0;
        for(let x = 0; x < x_bound; x+=10){
            x_site = (x - x_bound/2)/(50*multiple);
            y_site = -(y - y_bound/2)/(50*multiple);
            let a = x_site*x_site + y_site*y_site - 1;
            let b = x_site*x_site*y_site*y_site*y_site;
            if (a*a*a - b <= 0){
                ctx.fillText(chars[index%chars.length], x, y);
                console.log(a*a*a - b, 0, chars[index%chars.length]);
                index++;
            }
        }
    }
}
function printCanvas(){
    let str = document.getElementById('string').value;
    if (!str) {
        window.alert("Empty String");
    } else {
        let canvas = document.createElement('canvas');
        canvas.setAttribute('width', '1500px');
        canvas.setAttribute('height', '1500px');
        canvas.setAttribute('id', 'canvas');
        const ctx = canvas.getContext('2d');
        drawHeartShape(ctx, str);
        let preCanvas = document.getElementById('canvas');
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