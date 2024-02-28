package com.dongnaoedu.jetpackcomposestate.todo.four

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposestate.todo.TodoIcon
import com.dongnaoedu.jetpackcomposestate.todo.TodoItem
import com.dongnaoedu.jetpackcomposestate.ui.theme.JetpackComposeStateTheme

/**
 * 当TodoItem列表中的条目被选中时，会弹出一个输入框，用于编辑选中TodoItem的信息
 * @param item 选中的TodoItem条目
 * @param onEditItemChange 编辑条目时的回调
 * @param onEditDone 编辑完成时的回调
 * @param onRemoveItem 删除条目时的回调
 */
@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) {
    TodoItemInput(
        text = item.task,
        onTextChange = { onEditItemChange(item.copy(task = it)) },
        icon = item.icon,
        onIconChange = { onEditItemChange(item.copy(icon = it)) },
        submit = onEditDone,
        iconsVisible = true,
        buttonSlot = {
            // 保存和删除两个图标
            Row {
                val shrinkButtons = Modifier.widthIn(20.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                    Text(
                        text = "\uD83D\uDCBE", // Emoji符号，软盘
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(30.dp)
                    )
                }

                TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                    Text(
                        text = "❌",
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }
        }
    )
}

// 顶部输入框加上一个灰色背景
@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    // 帧动画的形式展现Surface底部的阴影
    val animatedElevation by animateDpAsState(if (elevate) 1.dp else 0.dp, TweenSpec(500))
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        // Surface 底部有一个小小的阴影
        elevation = animatedElevation,
        shape = RectangleShape
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

/**
 * 文本输入框
 * @param text 状态对象
 * @param onTextChange 文本输入框中文本内容发生改变之后的回调函数
 * @param modifier 修饰符
 * @param onImeAction 软键盘中的“完成”点击之后，要执行的回调函数
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        modifier = modifier,
        // 配置软键盘
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide() //点击完成之后，隐藏键盘
        })
    )
}

// 按钮
@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    // IconRow是否可见取决于文本框中是否有文本
    val iconsVisible = text.isNotBlank()
    // 点击“Add”按钮，提交要做的事情
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    // 对TodoItemInput进行封装
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    ) {
        TodoEditButton(
            onClick = submit,
            text = "Add",
            enabled = text.isNotBlank()
        )
    }
}

@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    // 最右侧的图标与按钮，在编辑时，会有删除和保存两个图标，添加时会有一个“Add”按钮
    buttonSlot: @Composable () -> Unit
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit //软键盘点击“完成”，提交
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }

        if (iconsVisible) {
            AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
        } else {
            // 跟底部有一个距离间隙
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// 一排图标，根据文本框是否有内容，自动收起和弹出，带动画效果
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }
    Box(modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@Composable
fun IconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        for (todoIcon in TodoIcon.values()) {
            SelectableIconButton(
                icon = todoIcon.imageVector,
                iconContentDescription = todoIcon.contentDescription,
                onIconSelected = { onIconChange(todoIcon) },
                isSelected = (todoIcon == icon)
            )
        }
    }
}

@Composable
private fun SelectableIconButton(
    icon: ImageVector,
    @StringRes iconContentDescription: Int,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    // 图标选中和未选中状态，颜色不一样
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = { onIconSelected() },
        shape = CircleShape,
        modifier = modifier
    ) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(id = iconContentDescription)
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


@Preview
@Composable
fun TodoItemInputPreview() {
    JetpackComposeStateTheme {
        TodoItemEntryInput {}
    }
}

@Preview
@Composable
fun IconRowPreview() {
    JetpackComposeStateTheme {
        IconRow(icon = TodoIcon.Square, onIconChange = {})
    }
}