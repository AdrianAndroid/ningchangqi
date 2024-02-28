package com.dongnaoedu.jetpackcomposegesture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposegesture.ui.theme.JetpackComposeGestureTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeGestureTheme {
                GestureSample()
            }
        }
    }
}

@Composable
fun GestureSample() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TransformableSample()
    }
}

@Composable
fun ClickableSample() {
    val count = remember { mutableStateOf(0) }
    Text(
        text = count.value.toString(),
        textAlign = TextAlign.Center,
        modifier = Modifier
            // clickable 修饰符允许应用检测对已应用该修饰符的元素的点击。
            //.clickable { count.value += 1 }
            // 当需要更大灵活性时，您可以通过 pointerInput 修饰符提供点按手势检测器：
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { Log.d("ning", "Called when the gesture starts") },
                    onDoubleTap = { Log.d("ning", "Called on Double Tap") },
                    onLongPress = { Log.d("ning", "Called on Long Press") },
                    onTap = { Log.d("ning", "Called on Tap") }
                )
            }
            .wrapContentSize()
            .background(Color.LightGray)
            .padding(horizontal = 50.dp, vertical = 40.dp)
    )
}

// 滚动修饰符
@Composable
fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        repeat(10) {
            Text(text = "Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}

// 借助 ScrollState，您可以更改滚动位置或获取当前状态。
@Composable
fun ScrollBoxesSmooth() {
    val state = rememberScrollState()
    // 在第一个合成时平滑滚动 100px
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state),
    ) {
        repeat(10) {
            Text(text = "Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}

// 可滚动的修饰符
// scrollable 修饰符与滚动修饰符不同，区别在于 scrollable 可检测滚动手势，但不会偏移其内容。
@Composable
fun ScrollableSample() {
    var offset by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                // 此修饰符需要 ScrollableController 才能正常运行。构造 ScrollableController 时，
                // 您必须提供一个 consumeScrollDelta 函数，该函数将在每个滚动步骤（通过手势输入、平滑滚动或投掷）调用，
                // 并且增量以像素为单位。为了确保正确传播事件，必须从此函数返回使用的滚动距离量。
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = offset.toString())
    }
}

// 嵌套滚动
@Composable
fun NestedScrollSample() {
    // 0（灰色） - 1000（白色） 渐变
    val gradient = Brush.verticalGradient(
        0f to Color.Gray, 1000f to Color.White
    )
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)  // Text 高度大于外部容器 Box 的高度
                    )
                }
            }
        }
    }
}

// 拖动
@Composable
fun DraggableSample() {
    var offsetX by remember { mutableStateOf(0f) }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            // draggable 修饰符是向单一方向拖动手势的高级入口点，并且会报告拖动距离（以像素为单位）。
            // 此修饰符与 scrollable 类似，仅检测手势。您需要保存状态并在屏幕上表示，例如通过 offset 修饰符移动元素：
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            ),
        text = "Drag me!"
    )
}

// 如果您需要控制整个拖动手势，请考虑改为通过 pointerInput 修饰符使用拖动手势检测器。
@Composable
fun DragWhereYouWantSample() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    // 消费按下与移动手势
                    // change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
        )
    }
}

// 滑动
// 使用 swipeable 修饰符，您可以拖动元素，释放后，这些元素通常朝一个方向定义的两个或多个锚点呈现动画效果。
// 其常见用途是实现“滑动关闭”模式。
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSample() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    // 将dp转换为像素，需要用到当前的密度
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    // fun Dp.toPx(): Float = value * density
    // value 属于 squareSize（Dp），density 属于 Density（current）

    // 锚点
    val anchors = mapOf(0f to 0, sizePx to 1)

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                // 可以将滑动手势配置为具有不同的阈值类型
                // 分数阈值将位于两个锚之间路径的分数处。
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

// 多点触控：平移、缩放、旋转
@Composable
fun TransformableSample() {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    Box(
        Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state = state)
            .background(Color.Blue)
            .size(100.dp,200.dp)
    )

}