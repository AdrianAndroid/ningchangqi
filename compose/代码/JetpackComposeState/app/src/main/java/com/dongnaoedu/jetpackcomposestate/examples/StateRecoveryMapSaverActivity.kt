package com.dongnaoedu.jetpackcomposestate.examples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposestate.ui.theme.JetpackComposeStateTheme


class StateRecoveryMapSaverActivity : ComponentActivity() {

    data class City(val name: String, var country: String)


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
        val citySaver = run {
            val nameKey = "Name"
            val countryKey = "Country"
            // 指定 mapSaver，用于存储时如何构建一个 map 对象，获取时如何构建一个 City 对象
            mapSaver(
                save = { mapOf(nameKey to it.name, countryKey to it.country) },
                restore = { City(it[nameKey] as String, it[countryKey] as String) }
            )
        }
        val (city, setCity) = rememberSaveable(stateSaver = citySaver) {
            mutableStateOf(City("Madrid", "Spain"))
        }
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