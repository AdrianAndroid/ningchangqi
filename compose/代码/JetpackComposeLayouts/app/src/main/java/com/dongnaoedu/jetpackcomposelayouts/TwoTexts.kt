package com.dongnaoedu.jetpackcomposelayouts

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String){
    // modifier = modifier.height(IntrinsicSize.Min)
    // Divider扩展到整个屏幕，并不是我们想要的。
    // 发生这种情况是因为Row单独测量每个子元素，Text的高度并不能约束Divider。
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )
        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

@Preview
@Composable
fun TwoTextsPreview(){
    JetpackComposeLayoutsTheme {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}