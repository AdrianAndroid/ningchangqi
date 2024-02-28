package com.dongnaoedu.jetpackcomposelayouts

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

val topics = listOf(
    "Arts & Crafts",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths"
)

// 模仿交错表格布局
@Composable
fun StaggeredGrid(
    modifier: Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        //用于保存每行的宽度值
        val rowWidths = IntArray(rows) { 0 }
        //用于保存每行的高度值
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            // 测量每一个元素
            val placeable = measurable.measure(constraints)
            // 计算每一行的宽度与高度
            // 元素下标，假设总共11个元素
            // index: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            // 行数，假设3行
            // rows: 3
            // 保存行宽高数组的下标值：
            // row: 0, 1, 2
            val row = index % rows
            // 一行的宽度等于这一行所有元素宽度之和
            rowWidths[row] += placeable.width
            // 一行的高度，应该是这一行中高度最高的那个元素的高度
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        // 计算表格的宽高
        // 表格的宽度，应该是所有行当中最宽的那一行的宽度
        val width = rowWidths.maxOrNull()
            //?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) //[koʊˈɜːrs]确保宽度在这个范围之内
            ?: constraints.minWidth
        // 表格的高度，应该是所有行高度之和
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // 设置每一行的Y坐标
        val rowY = IntArray(rows) { 0 }
        // 索引从1开始，因为第一行Y坐标为0，row[0] = 0
        for (i in 1 until rows) {
            // rowY[1] = rowY[0] + rowHeights[0]
            // 第1行的Y坐标，等于第0行Y坐标加上第0行的行高
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // 设置表格布局的宽高
        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            // 设置每一个元素的坐标
            placeables.forEachIndexed { index, placeable ->
                // index: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
                // rows: 3
                // row: 0, 1, 2
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                // 第一列，X坐标全部为0，下一列的X坐标要累加上前面元素的宽度
                // 设置下一列的X坐标
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    // 一个卡片，圆角，里面包含一个Row，第一列是Box，第二列是文本
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline), //Hairline，发际线，这里等于1像素
        shape = RoundedCornerShape((8.dp))
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //正方形
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun StaggeredGridBodyContent(modifier: Modifier = Modifier) {
    //modifier写不同的顺序，会有不同的效果，左侧的值会影响右侧的值
    Row(modifier = modifier
        .background(color = Color.LightGray)
        // 约束是从左到右传播，这种写法StaggeredGrid的实际宽高为200-(16x2)=168
        //.size(200.dp)
        .padding(16.dp)
        // 这种写法StaggeredGrid的实际宽高为200，Row的实际宽高为200+(16x2)=232，这里跟HTML里有点像
        //.padding(16.dp)
        //.size(200.dp)
        .horizontalScroll(rememberScrollState()),
        content = {
            StaggeredGrid(modifier = Modifier) {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }
    )
}

@Preview
@Composable
fun StaggeredGridPreview() {
    StaggeredGridBodyContent()
}