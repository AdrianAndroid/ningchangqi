package com.dongnaoedu.jetpackcomposecompositionlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposecompositionlocal.samples.CompositionSample4
import com.dongnaoedu.jetpackcomposecompositionlocal.ui.theme.JetpackComposeCompositionLocalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCompositionLocalTheme {
                 CompositionSample4()
            }
        }
    }
}
