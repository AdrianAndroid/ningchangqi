package com.dongnaoedu.jetpackcomposestate.todo.three

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposestate.todo.TodoIcon
import com.dongnaoedu.jetpackcomposestate.todo.TodoItem
import com.dongnaoedu.jetpackcomposestate.util.generateRandomTodoItem
import kotlin.random.Random

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // 输入框，外加一个灰色背景
        TodoItemInputBackground(elevate = true) {
            TodoItemEntryInput(onAddItem)
        }

        // 多行
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) {
                TodoRow(
                    todo = it,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }

        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add random item")
        }
    }
}

@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
    //iconAlpha: Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }  // 点击事件回调
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween  // 子元素水平均匀分布
    ) {
        Text(text = todo.task)
        // 随机透明度
        //但是每一次recomposition（重组）时，会重新生成透明度值
        //val iconAlpha: Float = randomTint()
        // 1.remember 给了组合函数一个内存空间
        // 2.remember中表达式计算出来的值，将会保存在组合树中（composition tree），
        // 并且只有在remember的键发生改变时，表达式才会重新计算。
        // 3.我们可以将remember视为给函数提供了一个存储对象的内存空间，就像私有属性在对象中所做的那样。
        val iconAlpha: Float = remember(todo.id) { randomTint() }
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
        //LocalContentColor
        //CompositionLocal 包含层次结构中给定位置的首选内容颜色。
        //这种颜色应该用于任何排版/图标，以确保这些颜色在背景颜色改变时进行调整。
        //例如，在深色背景上，文字应该是浅色，而在浅色背景上，文字应该是深色。
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}