package com.dongnaoedu.jetpackcomposesideeffects

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dongnaoedu.jetpackcomposesideeffects.samples.*
import com.dongnaoedu.jetpackcomposesideeffects.ui.theme.JetpackComposeSideEffectsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeSideEffectsTheme {
                // SideEffectSample(onBackPressedDispatcher)
                SnapshotFlowSample()
            }
        }
    }
}
