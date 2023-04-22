## 简单尝试一下Rust的宏
Rust的宏有两种
- Declarative Macro
- Procedural Macro

Declarative Macro类似C中的Macro，但是从简单的文本替换变成了模式匹配。Rust中的``println!``与``vec!``这些宏都是Declarative Macro。为了区分宏和函数，宏在调用时需要加上`!`。
使用宏是为了避免DRY，但是这里为了学习，画蛇添足，模仿vec!，写一个map!
代码如下

```rust
macro_rules! map {
    ($e:expr) => {
        {
            let mut m = HashMap::new();
            m.insert($e.0, $e.1);
            m
        }

    };
    ($i:ident, $e:expr) => {
        {
            $i.insert($e.0, $e.1);
            $i
        }
    };
    ($i:ident, $e:expr, $($b:tt)+) => {
        {
            $i.insert($e.0, $e.1);
            map!($i, $($b)*)
        }
    };
    ($e:expr, $($b:tt)+) => {
        {
            let mut m = HashMap::new();
            m.insert($e.0, $e.1);
            map!(m, $($b)+)
        }
    }
}
fn main() {
    let map = map!((1,2), (3, 4), (5, 6));
    println!("{:?}", map);
}
```
定义宏的语法可以从这个[链接](https://doc.rust-lang.org/reference/macros-by-example.htm)]找到
```markdown
MacroRulesDefinition :
   macro_rules ! IDENTIFIER MacroRulesDef

MacroRulesDef :
      ( MacroRules ) ;
   | [ MacroRules ] ;
   | { MacroRules }

MacroRules :
   MacroRule ( ; MacroRule )* ;?

MacroRule :
   MacroMatcher => MacroTranscriber

MacroMatcher :
      ( MacroMatch* )
   | [ MacroMatch* ]
   | { MacroMatch* }

MacroMatch :
      Tokenexcept $ and delimiters
   | MacroMatcher
   | $ IDENTIFIER : MacroFragSpec
   | $ ( MacroMatch+ ) MacroRepSep? MacroRepOp

MacroFragSpec :
      block | expr | ident | item | lifetime | literal
   | meta | pat | pat_param | path | stmt | tt | ty | vis

MacroRepSep :
   Token except delimiters and repetition operators

MacroRepOp :
   * | + | ?

MacroTranscriber :
   DelimTokenTree
```
首先用macro_rules!声明map为宏，多个分支对map的输入进行匹配，这里匹配了几个起始条件作为递归的结束。要注意的是，每一个分支都是两层括号，这是因为宏最后只能替换成一个`statement`，`expression`，或者一个`Item`。为啥呢，可以看一下`MacroTranscriber`的定义。

```markdown
DelimTokenTree :
      ( TokenTree* )
   | [ TokenTree* ]
   | { TokenTree* }

TokenTree :
   Tokenexcept delimiters | DelimTokenTree
```

如果不加内层的`{}`，`MacroTranscriber`在第一个`;`就已经结束了，会产生以下错误

```
Error: macro expansion ignores token `match`
```

