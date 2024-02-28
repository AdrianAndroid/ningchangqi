package com.dongnaoedu.jetpackcomposelayouts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.dongnaoedu.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // 通过createRefs创建引用，ConstraintLayout中的每个元素都需要关联一个引用
        val (button, text) = createRefs()
        Button(
            onClick = {},
            // 使用Modifier.constrainAs来提供约束，引用作为它的第一个参数
            // 在lambda表达式中指定约束规则
            modifier = Modifier.constrainAs(button) {
                // 使用linkTo指定约束，parent是ConstraintLayout的引用
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            // 按钮上显示的文字
            Text(text = "Button")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            // 在ConstraintLayout中水平居中
            centerHorizontallyTo(parent)
        })
    }
}

@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })
        // 将button1和text组合起来，建立一个屏障（barrier）
        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                // 文字自动换行
                width = Dimension.preferredWrapContent
            }
        )
    }
}

// 解耦的写法
// 之前的写法都是把约束卸载布局内部，有些情况下，需要将约束与布局进行解构
// 比如说，需要根据屏幕方向改变约束

//1.将ConstraintSet作为参数传入ConstraintLayout
//2.使用Modifier.layoutId将ConstraintSet中创建的引用分配给Composable元素
@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(16.dp)  //竖屏
        } else {
            decoupledConstraints(160.dp)  //横屏
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }
            Text(text = "Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    JetpackComposeLayoutsTheme {
        ConstraintLayoutContent()
    }
}

@Preview
@Composable
fun ConstraintLayoutContent2Preview() {
    JetpackComposeLayoutsTheme {
        ConstraintLayoutContent2()
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    JetpackComposeLayoutsTheme {
        LargeConstraintLayout()
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreview() {
    JetpackComposeLayoutsTheme {
        DecoupledConstraintLayout()
    }
}