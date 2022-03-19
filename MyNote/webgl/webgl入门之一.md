## 用WebGL在浏览器上画一个点

<iframe src="http://www.acodingdog.site/DemoWebgl/web/HelloCanvas.html" height=400 width=400></iframe>

效果如上图所示，H5标准提供了`canvas`标签，需要浏览器提供对应的支持，**WebGL**的效果就展示在这个元素上。

JavaScript代码如下

```javascript
let vShaderSource = `
void main() {
    gl_Position = vec4(0.0, 0.0, 0.0, 1.0);
    gl_PointSize = 10.0;
}
`
let fShaderSource = `
void main() {
    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}

`
function main() {
    let canvas = document.getElementById("webgl")

    let ctx = getWebGlContext(canvas)
    if (!initShader(ctx, vShaderSource, fShaderSource)) {
        console.error('init shader fail')
    }
    ctx.clearColor(0.0, 0.0, 0.0, 1.0)
    ctx.clear(ctx.COLOR_BUFFER_BIT)
    ctx.drawArrays(ctx.POINTS, 0, 1)
}
```

main方法是入口方法，首先通过canvas得到WebGL的上下文，我们使用了一个辅助函数

```javascript
/**
 * get webgl context from canvas dom element
 * @param canvas dom canvas element
 * @param attributes
 * @returns {*}
 */
function getWebGlContext(canvas, attributes={alpha:false}) {
    return canvas.getContext('webgl') || canvas.getContext("experimental-webgl")
}
```

不同浏览器取得WebGL上下文的方法不一样，笔者的chrome浏览器是支持这样的。

WebGL的运作流程同OpenGL类似，先通过JavaScript脚本准备好数据，通过着色器渲染到画布上

就像OpenGL一样，有两种着色器，顶点着色器（vertex shader）和像素着色器（fragment shader/ pixel shader），他们需要在JavaScript中编译好，因此需要是一个JavaScript字符串。

在顶点着色器中，有两个内置的变量``gl_Position``和``gl_PointSize``，``gl_Position``会把对应的位置当作一个顶点，然后光栅化。我们要画的点的位置定在画布中心，画布的中心也是WebGL坐标系的中心。

在像素着色器中，``gl_FragColor``内置变量表示顶点的颜色

准备好这两个shader，我们需要将shader编译后加载到WebGL的上下文中，这里用到了另一个辅助函数

```JavaScript
function initShader(ctx, vs, fs) {
    let program = createProgram(ctx, vs, fs);
    if (!program) {
        console.error("fail to create program")
        return false
    }
    ctx.useProgram(program)
    return true
}
```

这里的`program`保存了`shader`编译后的信息，`createProgram`细节如下

```javascript
function createProgram(ctx, vs, fs) {
    let vshader = createVertexShader(ctx, vs);
    let fshader = createFragmentShader(ctx, fs);
    let program = ctx.createProgram();
    if (!program) {
        console.error("Fail to create program")
        return
    }
    ctx.attachShader(program, vshader);
    ctx.attachShader(program, fshader);
    ctx.linkProgram(program);
    // check if linked successful
    let linkedStatus = ctx.getProgramParameter(program, ctx.LINK_STATUS);
    if (!linkedStatus) {
        let errMsg = ctx.getProgramInfoLog(program);
        console.error("fail to link program")
        ctx.deleteProgram(program)
        ctx.deleteShader(vshader)
        ctx.deleteShader(fshader)
        return
    }
    return program;
}
```

在`createVertexShader`和`createFragmentShader`中，我们已经将shader编译好，得到了shaderObject，代码细节如下

```javascript
function createVertexShader(ctx, shaderSource) {
    return createShaderObj(ctx, ctx.VERTEX_SHADER, shaderSource);
}

function createFragmentShader(ctx, shaderSource) {
    return createShaderObj(ctx, ctx.FRAGMENT_SHADER, shaderSource);
}

function createShaderObj(ctx, type, shaderSource) {
    let shader = ctx.createShader(type);
    if (!shader) {
        console.error("fail to create shader object");
        return;
    }
    ctx.shaderSource(shader, shaderSource);
    ctx.compileShader(shader);
    let compiledRes = ctx.getShaderParameter(shader, ctx.COMPILE_STATUS);
    if (!compiledRes) {
        let log = ctx.getShaderInfoLog(shader);
        console.error(`Fail to compile shader :${log}`);
        return;
    }
    return shader;
}
```

通过`attachShader`和`linkProgram`，我们已经准备好了运行渲染函数。渲染的数据存储在WebGL的COLOR_BUFFER_BIT中，每次绘图完成，我们都需要重置这个BUFFER，重置后我们运行`ctx.drawArrays(ctx.POINTS, 0, 1)`，表示，我们要画一个点，从第一个顶点开始，运行一次shader，我们的shader中只有一个点，最终的结果就是将这个点画出来。
