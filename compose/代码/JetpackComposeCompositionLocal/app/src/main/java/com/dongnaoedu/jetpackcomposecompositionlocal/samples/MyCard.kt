package com.dongnaoedu.jetpackcomposecompositionlocal.samples

import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 建议使用 CompositionLocal 的情况为：其可能会被任何（而非少数几个）后代使用。
data class Elevations(val card: Dp = 0.dp)

// 定义一个有默认值的 CompositionLocal 全局对象
// 该对象可以在应用程序的所有可组合项中访问
val LocalElevations = compositionLocalOf { Elevations() }

// 创建 CompositionLocal
// 有两个 API 可用于创建 CompositionLocal：
// compositionLocalOf：在重组期间更改提供的值只会使读取其 current 值的内容无效。
// staticCompositionLocalOf：与 compositionLocalOf 不同，Compose 不会跟踪 staticCompositionLocalOf 的读取。
// 更改该值会导致提供 CompositionLocal 的整个 content lambda 被重组，而不仅仅是在组合中读取 current 值的位置。

object CardElevation {
    val high: Elevations
        get() = Elevations(card = 10.dp)
    val low: Elevations
        get() = Elevations(card = 5.dp)
}

@Composable
fun MyCard(
    elevation: Dp = LocalElevations.current.card,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        // 在Z轴方向上的高度（层次感），透明度会发生改变，周边看起来有类似阴影的效果
        // Card 默认 elevation 为 1.dp
        elevation = elevation,
        modifier = Modifier.size(200.dp),
        backgroundColor = backgroundColor,
        content = content
    )
}