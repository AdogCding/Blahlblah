### 使用Web API中的animate方法实现文字抖动

最近在开发移动端表单填写，任务很单调。目前有一个场景，当必填元素为空的时候，弹出提示。系统是内部应用，用户的体验也就是刚刚及格而已。然而开发人员对这种列表填表的逻辑已经写腻了，希望写点新东西，但是开发的能力又比较低级，只能实现一些很普通的效果，于是乎，决定搞出一个文字抖动的效果。

考虑过只使用CSS3中的[animation](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Animations/Using_CSS_animations)来实现，可是触发动画效果的时机没有办法表现出来，所以还是用了animate方法，这个方法的说明在[这里](https://developer.mozilla.org/en-US/docs/Web/API/Element/animate)。
想要实现的效果是，当输入框为空的时候，点击下一步，输入框旁边的文字会抖动。抖动，就是让找个dom元素在某个位置上来回平移，在x轴方向上正向移动若干单位，再反向移动相同个单位，回到原点，再正向反向移动若干次，最终回到原点。

我们知道平移的变换矩阵是
$$
\begin{pmatrix}
1 & 0 & tx \\
0 & 1 & ty \\
0 & 0 & 1
\end{pmatrix}
$$


这里的tx，ty是平移的距离，CSS有内置的矩阵函数来表示2D的平移变换

```css
matrix(a, b, c, d, tx, ty)
```

它表示一个矩阵
$$
\begin{pmatrix}
a & b & tx \\
c & d & ty \\
0 & 0 & 1
\end{pmatrix}
$$
我们可以用一个函数来得到一个数组表示这个矩阵，出于简化的目的，我们只要一个表示在x轴上平移的矩阵就可以了

```javascript
function translateX(distance) {
    return [1.0, 0.0, 0.0, 1.0, distance, 0.0]
}
```

还需要一个把数组转换成transform-function的函数

```javascript
function toMatrix(array) {
    return `matrix(${array[0]},${array[1]},${array[2]},${array[3]},${array[4]},${array[5]})`;
}
```

注意，这个矩阵表示的转换是与同元素初始位置相对的。

我们需要移动多次，而非一次，所以需要有个函数来生成这些关键帧

```javascript
function getKeyFrame(times, distance) {
    let frames = [
        {
            transform:toMatrix(translateX(0))
        }
    ]
    for(let i = 0; i < times; i++) {
        frames.push(
            {
                transform: toMatrix(translateX(distance)), //1
            },
            {
                transform:toMatrix(translateX(0)), // 2

            },
            {
                transform: toMatrix(translateX(-distance)), //3
            },
            {
                transform: toMatrix(translateX(0)), // 4
            },
        )
    }
    return frames
}
```

从初始位置开始，x轴正反两方向平移若干次的关键帧就有了。最终，我们要指定一个duration，表示这个动画的持续时间。

```javascript
const div = document.getElementById('target');
div.addEventListener('click', (event) => {
    /**
         * 平移变换矩阵
         *  1 0 20
         *  0 1 0
         *  0 0 1
         */
    let animation = div.animate(getKeyFrame(5, 20), 900)
    });
```

意思是在900毫秒内，震荡5次，每次距离是20px
