package com.dongnaoedu.jetpackcomposelayouts

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        // 测量元素
        val placeable = measurable.measure(constraints)
        // 测量之后，获取元素的基线值
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        // 元素新的Y坐标 = 新基线值 - 旧基线值 （新的填充值，new padding）
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        // 设置元素的新宽高
        val height = placeable.height + placeableY
        Log.d("ning","placeable.height:${placeable.height}")
        layout(placeable.width, height) {
            // 设置元素的位置
            placeable.placeRelative(0, placeableY)
        }

    }
)

@Preview
@Composable
fun TextWithPaddingToBaselinePreview(){
    JetpackComposeLayoutsTheme {
        Text(text = "Hi there!", Modifier.firstBaselineToTop(24.dp).background(Color.Red))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview(){
    JetpackComposeLayoutsTheme {
        Text(text = "Hi there!", Modifier.padding(top = 24.dp))
    }
}