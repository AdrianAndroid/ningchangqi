/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelab.animation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.codelab.animation.R
import com.example.android.codelab.animation.ui.Amber600
import com.example.android.codelab.animation.ui.AnimationCodelabTheme
import com.example.android.codelab.animation.ui.Green300
import com.example.android.codelab.animation.ui.Green800
import com.example.android.codelab.animation.ui.Purple100
import com.example.android.codelab.animation.ui.Purple700
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
    Home, Work
}

/**
 * Shows the entire screen.
 */
@Composable
fun Home() {
    // String resources.
    val allTasks = stringArrayResource(R.array.tasks)
    val allTopics = stringArrayResource(R.array.topics).toList()

    // The currently selected tab.
    // state，当前选中的选项卡
    var tabPage by remember { mutableStateOf(TabPage.Home) }

    // True if the whether data is currently loading.
    var weatherLoading by remember { mutableStateOf(false) }

    // Holds all the tasks currently shown on the task list.
    val tasks = remember { mutableStateListOf(*allTasks) }

    // Holds the topic that is currently expanded to show its body.
    var expandedTopic by remember { mutableStateOf<String?>(null) }

    // True if the message about the edit feature is shown.
    var editMessageShown by remember { mutableStateOf(false) }

    // Simulates loading weather data. This takes 3 seconds.
    suspend fun loadWeather() {
        if (!weatherLoading) {
            weatherLoading = true
            delay(3000L)
            weatherLoading = false
        }
    }

    // Shows the message about edit feature.
    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    // Load the weather at the initial composition.
    LaunchedEffect(Unit) {
        loadWeather()
    }

    val lazyListState = rememberLazyListState()

    // The background color. The value is changed by the current tab.
    // TODO 1: Animate this color change.
    // 背景颜色在紫色和绿色之间切换
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)

    // The coroutine scope for event handlers calling suspend functions.
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        backgroundColor = backgroundColor,
        floatingActionButton = {
            HomeFloatingActionButton(
                extended = lazyListState.isScrollingUp(),
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            state = lazyListState
        ) {
            // Weather
            item { Header(title = stringResource(R.string.weather)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp
                ) {
                    if (weatherLoading) {
                        LoadingRow()
                    } else {
                        WeatherRow(onRefresh = {
                            coroutineScope.launch {
                                loadWeather()
                            }
                        })
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // Topics
            item { Header(title = stringResource(R.string.topics)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(allTopics) { topic ->
                TopicRow(
                    topic = topic,
                    expanded = expandedTopic == topic,
                    onClick = {
                        expandedTopic = if (expandedTopic == topic) null else topic
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // Tasks
            item { Header(title = stringResource(R.string.tasks)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (tasks.isEmpty()) {
                item {
                    TextButton(onClick = { tasks.clear(); tasks.addAll(allTasks) }) {
                        Text(stringResource(R.string.add_tasks))
                    }
                }
            }
            items(count = tasks.size) { i ->
                val task = tasks.getOrNull(i)
                if (task != null) {
                    key(task) {
                        TaskRow(
                            task = task,
                            onRemove = { tasks.remove(task) }
                        )
                    }
                }
            }
        }
        EditMessage(editMessageShown)
    }
}

/**
 * Shows the floating action button.
 *
 * @param extended Whether the tab should be shown in its expanded state.
 */
// AnimatedVisibility is currently an experimental API in Compose Animation.
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeFloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit
) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
            // Toggle the visibility of the content with animation.
            // TODO 2-1: Animate this visibility change.
            // AnimatedVisibility通过淡入和扩展来显示元素，并通过淡出和缩小来隐藏它。
            AnimatedVisibility(extended) {
                Text(
                    text = stringResource(R.string.edit),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

/**
 * Shows a message that the edit feature is not available.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun EditMessage(shown: Boolean) {
    // TODO 2-2: The message should slide down from the top on appearance and slide up on
    //           disappearance.
    // 消息从顶部滑入和滑出。
    // 自定义动画，将enter和exit参数添加到AnimatedVisibility可组合中。
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // 一个 lambda，它获取内容的完整高度并返回滑入的初始偏移量，默认情况下它返回 -fullHeight/2
            // 通过从 偏移量 -fullHeight 向下滑动到 0 来进入。
            initialOffsetY = { fullHeight -> -fullHeight },
            // LinearOutSlowInEasing：传入元素使用减速缓和设置动画，减速缓和以峰值速度（元素移动的最快点）开始过渡，并在静止时结束。
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // 一个 lambda，它获取内容的完整高度并返回滑出的目标偏移量，默认情况下它返回 fullHeight/2
            // 通过从偏移量 0 向上滑动到 -fullHeight 退出
            targetOffsetY = { fullHeight -> -fullHeight },
            // FastOutLinearInEasing：退出屏幕的元素使用加速度缓和，从静止开始，以峰值速度结束。
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            elevation = 4.dp
        ) {
            Text(
                text = stringResource(R.string.edit_message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Shows the header label.
 *
 * @param title The title to be shown.
 */
@Composable
private fun Header(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.h5
    )
}

/**
 * Shows a row for one topic.
 *
 * @param topic The topic title.
 * @param expanded Whether the row should be shown expanded with the topic body.
 * @param onClick Called when the row is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
    TopicRowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 2.dp,
        onClick = onClick
    ) {
        // TODO 3: Animate the size change of the content.
        // animateContentSize修饰符来动画其大小的变化。
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = topic,
                    style = MaterialTheme.typography.body1
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
    TopicRowSpacer(visible = expanded)
}

/**
 * Shows a separator for topics.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param backgroundColor The background color for the bar.
 * @param tabPage The [TabPage] that is currently selected.
 * @param onTabSelected Called when the tab is switched.
 */
@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage)
        }
    ) {
        HomeTab(
            icon = Icons.Default.Home,
            title = stringResource(R.string.home),
            onClick = { onTabSelected(TabPage.Home) }
        )
        HomeTab(
            icon = Icons.Default.AccountBox,
            title = stringResource(R.string.work),
            onClick = { onTabSelected(TabPage.Work) }
        )
    }
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param tabPage The [TabPage] that is currently selected.
 */
@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    // TODO 4: Animate these value changes.
    // 自定义选项卡指示器
    // 多值动画：在状态发生改变时，有多个动画值要一起发生改变
    // 在这里当 tabPage（当前选项卡） 状态发生改变时，选项卡指示器的偏移距离（offset）、颜色都要一起发生改变
    // 设置一个 Transition，并使用 targetState 提供的目标对其进行更新。
    // 当 targetState 更改时，Transition 将朝着为新 targetState 指定的目标值运行其所有子动画。
    // 可以使用 Transition 动态添加子动画：Transition.animateFloat、animateColor、animateValue 等。
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )

    // 请注意， targetValueByState 是一个可组合的函数。这意味着映射函数可以访问状态、CompositionLocals、主题等。如果 targetValue 在 Transition 运行之外发生变化（即当 Transition 已经达到其 targetState 时），Transition 将再次开始运行以确保此动画顺利到达其新目标.
    // 可以提供一个可选的 transitionSpec 来为每对 initialState 和 targetState 指定（可能不同的）动画。 FiniteAnimationSpec 包括任何非无限动画，例如补间、弹簧、关键帧甚至可重复，但不是无限可重复的。默认情况下，transitionSpec 对所有过渡目标使用弹簧动画。
    // label 用于在 Android Studio 中与同一过渡中的其他动画区分开来。

    // ordinal 为枚举常量的序号
    // TabPosition 包含有关选项卡在屏幕上位置信息的数据类，用于计算显示所选选项卡的指示器的放置位置。
    // tabPositions[tabPage.ordinal].left 获取当前选项卡左侧坐标信息
    // indicatorLeft 为一个 State，它的目标值为当前选项卡的 left 坐标
    val indicatorLeft by transition.animateDp(
        label = "Indicator left"
    ) { tabPage ->
        tabPositions[tabPage.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        label = "Indicator right"
    ) { tabPage ->
        tabPositions[tabPage.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { tabPage ->
        if (tabPage == TabPage.Home) Purple700 else Green800
    }

    /*val indicatorLeft by transition.animateDp(
        transitionSpec = {
            // 指标向右移动，左边缘比右边缘移动得慢。
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // stiffness 刚度、硬度，spring 弹簧、弹性
                // 通过 spring 函数设置弹性系数
                spring(stiffness = Spring.StiffnessVeryLow)
                // 指标向左移动，左边缘比右边缘移动得更快。
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { tabPage ->
        tabPositions[tabPage.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            // 指标向右移动，右边缘比左边缘移动得更快。
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                spring(stiffness = Spring.StiffnessMedium)
                // 指标向左移动，右边缘比左边缘移动得慢。
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { tabPage ->
        tabPositions[tabPage.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { tabPage ->
        if (tabPage == TabPage.Home) Purple700 else Green800
    }*/
    // 边框，通过设置边框的offset x（偏移量）控制边框的移动
    // 边框的宽度为边框左右两侧相减
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

/**
 * Shows a tab.
 *
 * @param icon The icon to be shown on this tab.
 * @param title The title to be shown on this tab.
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
private fun HomeTab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

/**
 * Shows the weather.
 *
 * @param onRefresh Called when the refresh icon button is clicked.
 */
@Composable
private fun WeatherRow(
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(R.string.temperature), fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh)
            )
        }
    }
}

/**
 * Shows the loading state of the weather.
 */
@Composable
private fun LoadingRow() {
    // TODO 5: Animate this value between 0f and 1f, then back to 0f repeatedly.
    // 重复动画，类似于呼吸灯效果
    val infiniteTransition = rememberInfiniteTransition()
    // 为一个 alpha 值设置动画，所以让我们使用animatedFloat. 该initialValue参数应0f和targetValue 1f。
    // 创建动画后，它将从initialValue运行到targetValue并重复。根据提供的animationSpec的RepeatMode，
    // 动画可以在每次迭代后重新启动（即RepeatMode.restart），也可以在每次迭代后反转（即RepeatMode.reverse）。
    // 重复的次数，由函数决定
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                1f at 500 // 添加一个关键帧，指定动画值在某个时间为某个值
            },
            // 动画应该从头开始（即RepeatMode.Restart）
            // 还是从尾开始（即RepeatMode.Reverse）重复
             repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

/**
 * Shows a row for one task.
 *
 * @param task The task description.
 * @param onRemove Called when the task is swiped away and removed.
 */
@Composable
private fun TaskRow(task: String, onRemove: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .swipeToDismiss(onRemove),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

/**
 * Modifier 的扩展函数
 * The modified element can be horizontally swiped away.
 *
 * @param onDismissed Called when the element is swiped to the edge of the screen.
 */
private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // composed 即时合成 Modifier，composed 与 apply 相比，允许传入可组合函数
    // TODO 6-1: Create an Animatable instance for the offset of the swiped element.
    // 创建一个实例Animatable并使用它来表示可滑动元素的水平偏移。
    val offsetX = remember { Animatable(0f) }
    // 创建一个修饰符，用于处理修改元素区域内的光标输入。
    // pointerInputs可以调用PointerInputScope.awaitPointerEventScope，
    // 以安装可以等待PointerEventScope的光标输入处理程序。
    pointerInput(Unit) {
        // 衰减动画通常在投掷姿势后使用，用于计算投掷动画最后的固定位置（样条（样条函数）衰减，还有一个指数衰减）
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // 协程作用域，允许调用挂起函数，执行动画
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // 等待触摸按下事件
                // Wait for a touch down event.
                // awaitPointerEventScope：挂起并安装指针输入块，该块可以等待输入事件并立即响应它们。
                // awaitFirstDown：读取事件，直到收到第一个 down。
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // TODO 6-2: Touch detected; the animation should be stopped.
                // 如果动画当前正在运行，我们应该拦截它。
                offsetX.stop()
                // 准备拖动事件并记录投掷的速度
                // Prepare for drag events and record velocity of a fling.
                val velocityTracker = VelocityTracker()
                // 等待拖动事件
                // Wait for drag events.
                awaitPointerEventScope {
                    // 监听水平滑动
                    // horizontalDrag：读取指针ID的水平位置更改事件，并为每个位置更改调用onDrag。
                    horizontalDrag(pointerId) { change ->
                        // TODO 6-3: Apply the drag change to the Animatable offset.
                        // 我们不断收到拖动事件。我们必须将触摸事件的位置同步到动画值中。为此，我们可以使用snapTo on Animatable。
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        // 启动协程，执行动画
                        launch {
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // 记录滑动的速度
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // 消费掉手势事件，而不是传递给外部
                        // Consume the gesture event, not passed to external
                        change.consumePositionChange()
                    }
                }
                // 拖动完成，计算投掷的速度。速度够快，才能把元素划掉。
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // TODO 6-4: Calculate the eventual position where the fling should settle
                //           based on the current offset value and velocity
                // 我们需要计算投掷的最终位置，以决定是将元素滑回原始位置，还是将其滑开并调用回调。
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                // TODO 6-5: Set the upper and lower bounds so that the animation stops when it
                //           reaches the edge.
                // 我们即将开始动画。但在此之前，我们要为其设置上限和下限，以便在Animatable到达它们时立即停止。
                // 该pointerInput修改允许我们通过访问该元素的size属性，所以我们就用它作为动画的边界。
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    // TODO 6-6: Slide back the element if the settling position does not go beyond
                    //           the size of the element. Remove the element if it does.
                    // 如果退出位置在尺寸以下，则说明抛掷速度不够。我们可以使用animateTo动画将值恢复为 0f。
                    // 否则，我们使用animateDecay来启动投掷动画。
                    if (targetOffsetX.absoluteValue <= size.width){
                        // 速度不够； 滑回来。
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    }else {
                        // 启动衰减动画（即，从给定初始速度开始，以当前可设置动画的value减慢速度，直到速度达到0的动画）。
                        // animationSpec 定义将用于此动画的衰减动画，此animationSpec 的一些选项包括：样条衰减和指数衰减。
                        offsetX.animateDecay(velocity, decay)
                        // 元素被刷掉了，执行回调（从集合中将元素移除）
                        onDismissed()
                    }
                }
            }
        }
    }
        .offset {
            // TODO 6-7: Use the animating offset value here.
            // 我们已经设置了所有动画和手势，所以不要忘记将偏移应用于元素。
            IntOffset(offsetX.value.roundToInt(), 0)
        }
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    HomeTabBar(
        backgroundColor = Purple100,
        tabPage = TabPage.Home,
        onTabSelected = {}
    )
}

@Preview
@Composable
private fun PreviewHome() {
    AnimationCodelabTheme {
        Home()
    }
}
