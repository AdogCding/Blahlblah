## 用WebGL画一个立方体


一个立方体有八个面，每个面由两个三角形构成，当我们用ARRAY_BUFFER填充这些顶点时会发现，相同的点要重复多次，因为被多个面共享。出于简化的考虑，我们可以使用ELEMENT_ARRAY_BUFFER，引用顶点的序号来描述图形。

```javascript
function getCube() {
    // front to back
    // up to bottom
    // left to right
    // anti-clockwise
    const points = new Float32Array([
        1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
        -1.0, 1.0, 1.0, 1.0, 0.0, 1.0,
        -1.0, -1.0, 1.0, 1.0, 1.0, 0.0,
        1.0, -1.0, 1.0, 0.0, 0.0, 1.0, // front
        1.0, 1.0, -1.0, 1.0, 0.0, 0.0,
        -1.0, 1.0, -1.0, 1.0, 0.1, 1.0,
        -1.0, -1.0, -1.0, 0.5, 1.0, 1.0,
        1.0, -1.0, -1.0, 0.5, 1.0, 1.0,// back
    ])
    const indices = new Uint8Array([
        0, 1, 2,
        1, 2, 4,
        4, 5, 7,
        5, 6, 7,
        1, 5, 2,
        5, 6, 2,
        4, 0, 7,
        0, 3, 7,
        4, 5, 0,
        5, 1, 0,
        7, 6, 3,
        6, 2, 3
    ])
    return {points, indices}
}
```

`points`描述的是顶点，而`indices`是我们描述的图形。与2D图形不同的是，我们这次在三维空间中画出图形，但是我们看到的是三维图形的一个投影，因此我们需要做一些其他的变换。因此，shader会有变化。

首先是顶点着色器

```glsl
attribute vec4 a_Position;
attribute vec4 a_Color;
varying vec4 v_FragColor;
uniform mat4 u_projMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_modelMatrix;
void main() {
    gl_Position = u_projMatrix * u_viewMatrix * u_modelMatrix * a_Position;
    v_FragColor = a_Color;
}
```

立方体首先找到在世界坐标中的位置，再经过视图矩阵变化，让立方体转换成我们视线的坐标系，但是最终3D图形要画在幕布上，因此要再经过透视矩阵，映射在可视空间中。

库函数已经为我们提供了视图矩阵和透视矩阵，我们可以直接使用。

```javascript
function main() {
    changeSpeed()
    let canvas = document.getElementById('webgl')
    const gl = getWebGlContext(canvas)
    initShader(gl, vShader, fShader)
    gl.enable(gl.DEPTH_TEST)
    let viewMatrix = new Matrix4();
    let projMatrix = new Matrix4();
    let modelMatrix = new Matrix4();
    modelMatrix.setIdentity();
    projMatrix.setPerspective(30, 1, 1, 100)
    viewMatrix.lookAt(3, 3, 7, 0, 0, 0, 0, 1, 0)
    const {aPosition, aColor, uViewMatrix, uProjMatrix, uModelMatrix} = getShaderArguments(gl);
    const {points, indices } = getCube();
    initBuffer(gl, aPosition, aColor, points, indices)
    initViewMatrix(gl, viewMatrix, uViewMatrix)
    initProjMatrix(gl, projMatrix, uProjMatrix)
    gl.clearColor(0 ,0 ,0 ,1)
    const draw = () => {
        console.log(angle)
        modelMatrix.rotate(angle, 0, 0, 1)
        debugger
        gl.uniformMatrix4fv(uModelMatrix, false, modelMatrix.elements)
        gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT)
        gl.drawElements(gl.TRIANGLES, indices.length, gl.UNSIGNED_BYTE, 0)
        return requestAnimationFrame(draw)
    }
    draw()
}
```

我们需要的着色器的参数有点多，因此使用`getShaderArguments`方法来获得。

```javascript
function getShaderArguments(gl) {
    const program = gl.getParameter(gl.CURRENT_PROGRAM)
    const aPosition = gl.getUniformLocation(program, 'a_Position')
    const aColor = gl.getAttribLocation(program, 'a_Color')
    const uViewMatrix = gl.getUniformLocation(program, 'u_viewMatrix')
    const uProjMatrix = gl.getUniformLocation(program, 'u_projMatrix')
    const uModelMatrix = gl.getUniformLocation(program, 'u_modelMatrix')
    return {aPosition, aColor, uViewMatrix, uProjMatrix, uModelMatrix}
}
```

由于我们用到了新的`ELEMENT_BUFFER_ARRAY`，因此准备`initBuffer`函数有些改动。

```javascript
function initProjMatrix(gl, mat, uProjMatrix) {
    gl.uniformMatrix4fv(uProjMatrix, false, mat.elements)
}
function initViewMatrix(gl, mat, uViewMatrix) {
    gl.uniformMatrix4fv(uViewMatrix, false, mat.elements)
}
function initBuffer(gl, aPosition, aColor, points, indices) {
    const vertexBuffer = gl.createBuffer()
    const indicesBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indicesBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, points, gl.STATIC_DRAW);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, indices, gl.STATIC_DRAW);
    const fSize = points.BYTES_PER_ELEMENT;
    gl.vertexAttribPointer(aPosition, 3, gl.FLOAT, false, fSize * 6, 0);
    gl.vertexAttribPointer(aColor, 3, gl.FLOAT, false, fSize * 6, 3 * fSize);
    gl.enableVertexAttribArray(aPosition)
    gl.enableVertexAttribArray(aColor)
}
```

