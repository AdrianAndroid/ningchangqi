package com.dongnaoedu.jetpackcomposestate.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.dongnaoedu.jetpackcomposestate.todo.four.TodoScreen
import com.dongnaoedu.jetpackcomposestate.todo.four.TodoViewModel
import com.dongnaoedu.jetpackcomposestate.ui.theme.JetpackComposeStateTheme

class TodoActivity : ComponentActivity() {

    private val todoViewModel: TodoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeStateTheme {
                TodoActivityScreen(todoViewModel)
            }
        }
    }

    @Composable
    private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
        TodoScreen(
            items = todoViewModel.todoItems,
            currentlyEditing = todoViewModel.currentEditItem,
            onAddItem = todoViewModel::addItem,
            onRemoveItem = todoViewModel::removeItem,
            onStartEdit = todoViewModel::onEditItemSelected,
            onEditItemChange = todoViewModel::onEditItemChange,
            onEditDone = todoViewModel::onEditDone
        )
    }
}
