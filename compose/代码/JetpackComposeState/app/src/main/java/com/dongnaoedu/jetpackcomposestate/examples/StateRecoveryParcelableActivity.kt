package com.dongnaoedu.jetpackcomposestate.examples

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposestate.ui.theme.JetpackComposeStateTheme
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler



class StateRecoveryParcelableActivity : ComponentActivity() {

    @Parcelize
    data class City(val name: String, var country: String) : Parcelable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeStateTheme {
                CityScreen()
            }
        }
    }

    @Composable
    fun CityScreen() {
        //该状态在“后退”按钮后失效
        //在该示例中，这是预期的行为。反向导航的默认行为是销毁活动。此时将放弃保存的实例状态。这不是唯一的组合，并且自Android 1.0以来一直是Android行为的一部分。
        //这与文档上说的相反。
        //文档中使用的“进程结束”指的是以下流程：
        //  用户在您的应用程序中
        //  用户切换到启动器（通过主页导航）或其他应用程序（例如，通过概览屏幕）
        //  在接下来的几分钟内（但不到30分钟），Android会在后台终止您的进程
        //  通过各种方式使用应用程序的返回
        //在这一点上，Android将为您提供一个全新的进程，并尝试将您的UI恢复到用户离开应用程序之前的状态。保存的实例状态是恢复的一部分。

        val (city, setCity) = rememberSaveable {
            mutableStateOf(City("Madrid", "Spain"))
        }
        /*val (city, setCity) = remember {
            mutableStateOf(City("Madrid", "Spain"))
        }*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            // 点击按钮修改 City 信息
            TextButton(
                colors = ButtonDefaults.buttonColors(),
                onClick = { setCity(City("Beijing", "China")) }
            ) {
                Text(text = "Click to change")
            }
            Text(text = "${city.country} - ${city.name}")
        }
    }
}