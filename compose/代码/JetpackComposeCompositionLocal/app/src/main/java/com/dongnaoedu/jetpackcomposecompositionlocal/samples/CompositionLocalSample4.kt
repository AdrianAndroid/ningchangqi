package com.dongnaoedu.jetpackcomposecompositionlocal.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


var isStatic = true
var compositionLocalName = ""
val currentLocalColor = if (isStatic) {
    compositionLocalName = "StaticCompositionLocal 场景"
    staticCompositionLocalOf { Color.Black }
} else {
    compositionLocalName = "DynamicCompositionLocal 场景"
    compositionLocalOf { Color.Black }
}

// 重组标记，组件第一次加载时，recomposeFlag 为 Init
// 重组之前（也就是第二次加载之前），将 recomposeFlag 设置为 Recompose
var recomposeFlag = "Init"

@Composable
fun CompositionSample4() {

    val (color, setColor) = remember { mutableStateOf(Color.Green) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = compositionLocalName)
            Spacer(Modifier.height(20.dp))
            //3个 TaggedBox 组件，只有“Middle” 使用了 CompositionLocal
            CompositionLocalProvider(currentLocalColor provides color) {
                TaggedBox("Wrapper: $recomposeFlag", 400.dp, Color.Red) {
                    TaggedBox("Middle: $recomposeFlag", 300.dp, currentLocalColor.current) {
                        TaggedBox("Inner: $recomposeFlag", 200.dp, Color.Yellow)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            // 点击按钮改变状态，将颜色设置为蓝色，观察3个 TaggedBox 组件是否重组
            Button(
                onClick = {
                    setColor(Color.Blue)
                    recomposeFlag = "Recompose"
                }
            ) {
                Text(text = "Change Theme")
            }
        }
    }

}

@Composable
fun TaggedBox(tag: String, size: Dp, background: Color, content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .size(size)
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tag)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
