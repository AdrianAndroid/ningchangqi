package com.dongnaoedu.jetpackcomposelayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    //可以通过modifier对组件进行装饰
    Row(
        modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            //.padding(16.dp).clickable(onClick = {})
            .clickable(onClick = {}).padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ){
            Image(
                painter = painterResource(id = R.drawable.beauty),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            //在此代码片段中，我们使用 CompositionLocalProvider。 它允许我们通过组合树隐式传递数据。
            //在本例中，我们访问的是 ContentAlpha.medium，即中等不透明度级别，在本例中由 MaterialTheme 在主题级别定义。
            //LocalContentAlpha.provides(ContentAlpha.medium)，infix函数简写
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview(){
    JetpackComposeLayoutsTheme {
        PhotographerCard()
    }
}