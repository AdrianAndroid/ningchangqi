package com.dongnaoedu.jetpackcomposelayouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme


// 自定义一个布局，模仿Column的布局形式
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 测量元素
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0
        //设置布局的大小
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                // 设置元素的位置
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Preview
@Composable
fun MyOwnColumnPreview() {
    JetpackComposeLayoutsTheme {
        Surface(Modifier.padding(8.dp)) {
            MyOwnColumn {
                Text("MyOwnColumn")
                Text("places items")
                Text("vertically.")
                Text("We've done it by hand!")
            }
        }
    }
}