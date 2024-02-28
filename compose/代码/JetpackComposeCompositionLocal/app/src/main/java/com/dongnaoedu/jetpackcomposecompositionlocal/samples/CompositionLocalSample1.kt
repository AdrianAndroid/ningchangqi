package com.dongnaoedu.jetpackcomposecompositionlocal.samples

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

// Compose 提供了 CompositionLocal，可让您创建以树为作用域的具名对象，这可以用作让数据流经界面树的一种隐式方式。
@Composable
fun CompositionSample1() {
    //  MaterialTheme 对象提供了三个 CompositionLocal 实例，即 colors、typography 和 shapes。
    //  您可以之后在组合的任何后代部分中检索这些实例。
    //  具体来说，这些是可以通过 MaterialTheme colors、shapes 和 typography 属性访问的 LocalColors、LocalShapes 和 LocalTypography 属性。
    MaterialTheme {
        CustomTextLabel(labelText = "Some composable deep in the hierarchy of MaterialTheme")
    }
}

@Composable
fun CustomTextLabel(labelText: String) {
    Text(
        text = labelText,
        // 从 MaterialTheme 中的 LocalColors CompositionLocal 中获取 primary 值
        color = MaterialTheme.colors.primary
    )
}