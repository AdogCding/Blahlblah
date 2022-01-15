## 画一个正方形，并贴上纹理

<iframe src="http://www.acodingdog.site/DemoWebgl/web/HelloTexture.html" height=400 width=400></iframe>

效果如上图所示，上传一个图片，在canvas中渲染出来。

首先我们要做的是异步加载图片，用到`FileReader`这个`WebAPI`。

```javascript
async function loadTexture(files) {
    let imageFile = files[0]
    let fileReader = new FileReader();
    let image = new Image();
    return new Promise((resolve => {
        function loadImage(image) {
            return (evt) => {
                image.src = evt.target.result;
                resolve(image)
            }
        }
        fileReader.onload = loadImage(image);
        fileReader.readAsDataURL(imageFile)
    }))
}
```

这里返回一个`promise`，保证在纹理加载的时候，图片已经上传就绪。

接下来需要顶点着色器和像素着色器，顶点着色器渲染一个正方形，并且将纹理图片的顶点坐标同图形的坐标对齐。像素着色器在纹理图片上采样，得到像素的颜色。

```glsl
attribute vec4 a_Position;
// texture coordinate
attribute vec2 a_TexCoord;
// used for fragment shader
varying vec2 v_TexCoord;
void main() {
    gl_Position = a_Position;
    v_TexCoord = a_TexCoord;
}

```



```glsl
precision mediump float;
uniform sampler2D u_Sampler;
varying vec2 v_TexCoord;
varying vec4 v_FragColor;
void main() {
    vec4 textureRes = texture2D(u_Sampler, v_TexCoord);
    gl_FragColor = vec4(textureRes.x, textureRes.y, textureRes.z, 1);
}

```

从顶点着色器中可以看到，我们需要在传入`attribute`之前映射好纹理坐标和图形坐标的关系，在像素着色器中，着色器自动计算出来图形像素位置对应的纹理坐标，通过`texture2D`得到像素颜色。

```javascript
function getRectanglePoints() {
    return new Float32Array([
        0.5, 0.5, 1.0, 1.0,// right up
        0.5, -0.5, 1.0, 0.0, // right down
        -0.5, 0.5, 0.0, 1.0, // left up
        -0.5, -0.5, 0.0, 0.0 // left down
    ])
}
```

传入shader

```javascript
function initBuffer(gl, rectangleAttriVertex, textureAttriVertex, points) {
    const FSIZE = points.BYTES_PER_ELEMENT
    let vertexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, points, gl.STATIC_DRAW);
    gl.vertexAttribPointer(rectangleAttriVertex, 2, gl.FLOAT, false, 4 * FSIZE, 0);
    gl.vertexAttribPointer(textureAttriVertex, 2, gl.FLOAT, false, 4 * FSIZE, 2 * FSIZE);
    gl.enableVertexAttribArray(rectangleAttriVertex);
    gl.enableVertexAttribArray(textureAttriVertex);
}
```

初始化纹理

```javascript
function initTexture(gl, image) {
    const tex = gl.createTexture();
    const program = gl.getParameter(gl.CURRENT_PROGRAM)
    const uSampler = gl.getUniformLocation(program, 'u_Sampler')
    // flip y axis
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, 1);
    gl.activeTexture(gl.TEXTURE1);
    gl.bindTexture(gl.TEXTURE_2D, tex);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGB, gl.RGB, gl.UNSIGNED_BYTE, image);
    gl.uniform1i(uSampler, 1)
}
```

这里要注意的是

1. 图形坐标和纹理坐标的Y轴是相反的，要先进行反转
2. 当纹理图片的像素不是2的n次方的时候，要将TEXTURE_WRAP设置成CLAM_TO_EDGE，TEXTURE_MIN_FILTER设置成LINEAR，因为WebGL没有办法自动生成金字塔纹理图片。当纹理的像素同图形的像素无法对应的时候，要用其他方式计算。

第二点在chrome中调试没有报错信息，在Firefox中则会提示。

最后就可以画出来图形了

```javascript
function finalDraw(gl, count) {
    gl.clear(gl.COLOR_BUFFER_BIT)
    gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4)
}
```

要记得调用的时候，是一个异步函数。

```javascript
async function drawTexOnRectangle(file) {
    loadTexture(file).then((image) => {
        let {gl} = initCanvas();
        console.log(image)
        initTexture(gl, image)
        finalDraw(gl, 4)
    })
}
```

