package com.dongnaoedu.jetpackcomposesideeffects.samples

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.*

// snapshotFlow：将 Compose 的 State 转换为 Flow
@Composable
fun SnapshotFlowSample() {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(1000) { index ->
            Text(text = "Item $index")
        }
    }
    // 当在 snapshotFlow 块中读取的 State 对象值发生变化时，
    // 如果新值与之前发出的值不相等，Flow 会向其收集器发出新值（此行为类似于 Flow.distinctUntilChanged 的行为）。
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .filter { it > 20 }
            .distinctUntilChanged()
            .collect {
                Log.d("ning", "firstVisibleItemIndex:$it")
            }
    }
}