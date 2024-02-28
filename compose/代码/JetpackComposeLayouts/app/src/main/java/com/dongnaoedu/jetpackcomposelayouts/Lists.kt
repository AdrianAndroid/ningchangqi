package com.dongnaoedu.jetpackcomposelayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun SimpleColumn() {
    Column {
        repeat(100) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

//由于Column默认情况下不处理滚动，因此某些项目在屏幕之外时不可见。
//添加 verticalScroll Modifier启用滚动Column。
//这种方式会渲染所有元素，即使是那些在屏幕之外，不可见的元素。
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

//使用LazyColumn提高性能
@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F00%2F62%2F83%2F58984c3b7e496_610.jpg&refer=http%3A%2F%2Fpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630673373&t=2b891dc18f737f73e7fd6f4e0e06e4de"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}


//添加两个按钮，使我们能够平滑地滚动到列表的顶部和底部。
//为了避免在我们滚动时阻塞列表呈现，滚动 API 是挂起函数。因此，我们需要在协程中调用它们。
@Composable
fun ScrollingList() {
    val listSize = 100
    //用这个状态保存滚动位置
    val scrollState = rememberLazyListState()
    //保存将执行动画滚动的协程范围
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                Text(text = "Scroll to the top")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }) {
                Text(text = "Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Preview
@Composable
fun SimpleColumnPreview() {
    SimpleColumn()
}

@Preview
@Composable
fun SimpleListPreview() {
    SimpleList()
}

@Preview
@Composable
fun LazyListPreview() {
    LazyList()
}

@Preview
@Composable
fun ImageListItemPreview() {
    ImageListItem(index = 1)
}

@Preview
@Composable
fun ScrollingListPreview() {
    ScrollingList()
}
