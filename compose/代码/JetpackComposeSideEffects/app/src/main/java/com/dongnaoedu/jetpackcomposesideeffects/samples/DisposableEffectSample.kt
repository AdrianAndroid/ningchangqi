package com.dongnaoedu.jetpackcomposesideeffects.samples

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*

// DisposableEffect：需要清理的效应
// 对于需要在键发生变化或可组合项退出组合后进行清理的附带效应，请使用 DisposableEffect。
@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher,
    onBack: () -> Unit
) {
    // 当有新的 lambda 表达式提供时，安全的更新 onBack 值
    val currentOnBack by rememberUpdatedState(onBack)
    // 当按下返回键时，执行 onBack 函数
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }

    // 当 backDispatcher 发生改变，会在 onDispose 中进行最后的清理工作
    DisposableEffect(backDispatcher) {
        // 添加回调
        backDispatcher.addCallback(backCallback)
        // 当可组合项退出组合后，移除回调
        onDispose {
            Log.d("ning", "onDispose")
            backCallback.remove()
        }
    }
}

@Composable
fun DisposableEffectSample(backDispatcher: OnBackPressedDispatcher) {
    // 控制是否添加回调
    var addBackCallback by remember { mutableStateOf(false) }

    Row {
        Switch(
            checked = addBackCallback,
            onCheckedChange = {
                addBackCallback = !addBackCallback
            }
        )
        Text(text = if (addBackCallback) "Add Back Callback" else "Not Add Back Callback")
    }

    // addBackCallback 为 false时，BackHandler 被移除
    if (addBackCallback) {
        BackHandler(backDispatcher) {
            Log.d("ning", "onBack")
        }
    }
}