package com.dongnaoedu.jetpackcomposestate.todo.one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.dongnaoedu.jetpackcomposestate.todo.TodoItem
import com.dongnaoedu.jetpackcomposestate.ui.theme.JetpackComposeStateTheme

class TodoActivity : ComponentActivity() {

    private val todoViewModel: TodoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeStateTheme {
                TodoActivityScreen(todoViewModel)
                /*TodoItemInput { item ->
                    Log.d("ning", "add item:$item")
                }*/
            }
        }
    }

    @Composable
    private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
        // observeAsState 观察 LiveData 并返回一个 State 对象，该对象在 LiveData 修改时更新。
        // 给items的值由委托对象提供
        val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
        TodoScreen(
            items = items,
            onAddItem = { todoViewModel.addItem(it) },
            onRemoveItem = { todoViewModel.removeItem(it) }
        )
    }
}
