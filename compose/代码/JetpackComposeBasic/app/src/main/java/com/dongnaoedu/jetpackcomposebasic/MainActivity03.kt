package com.dongnaoedu.jetpackcomposebasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.dongnaoedu.jetpackcomposebasic.ui.theme.JetpackComposeBasicTheme

class MainActivity03 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android", "Jetpack Compose"))
        }
    }

    @Composable
    fun MessageCard(msg: Message) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = null
            )
            Column {
                Text(text = msg.author)
                Text(text = msg.body)
            }
        }
    }

}
