package com.dongnaoedu.jetpackcomposecompositionlocal.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.dongnaoedu.jetpackcomposecompositionlocal.R

// 如需为 CompositionLocal 提供新值，请使用 CompositionLocalProvider 及其 provides infix 函数
@Composable
fun CompositionSample2() {
    MaterialTheme {
        Column {
            // 默认值为 ContentAlpha.high
            Text(text = "Uses MaterialTheme's provided alpha")
            // infix 函数的原始写法
            //CompositionLocalProvider(LocalContentAlpha.provides(ContentAlpha.medium)){}
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Medium value provided for LocalContentAlpha")
                Text("This Text also uses the medium value")
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    DescendantExample()
                }
            }

            FruitText(2)
        }
    }
}

// 后代组合项
@Composable
fun DescendantExample() {
    Text("This Text uses the disabled alpha now")
}

// 如需访问 CompositionLocal 的当前值，请使用其 current 属性。
// 使用 Android 应用中常用的 LocalContext CompositionLocal 的当前 Context 值来设置文本格式。
@Composable
fun FruitText(fruitSize: Int) {
    val resources = LocalContext.current.resources
    val fruitText =
        // 复数形式
        resources.getQuantityString(R.plurals.fruit_title,fruitSize)

    Text(text = "$fruitSize $fruitText")
}