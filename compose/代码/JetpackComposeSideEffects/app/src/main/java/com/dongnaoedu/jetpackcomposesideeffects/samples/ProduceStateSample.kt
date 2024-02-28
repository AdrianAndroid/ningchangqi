package com.dongnaoedu.jetpackcomposesideeffects.samples

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dongnaoedu.jetpackcomposesideeffects.repository.Image
import com.dongnaoedu.jetpackcomposesideeffects.repository.ImageRepository
import com.dongnaoedu.jetpackcomposesideeffects.repository.Result

@Composable
fun loadNetworkImage(
    url: String,
    imageRepository: ImageRepository
): State<Result<Image>> {
    // url 或者 imageRepository 任意一个 key 发生改变，producer 都会重启
    return produceState(initialValue = Result.Loading as Result<Image>, url, imageRepository) {
        Log.d("ning","thread:${Thread.currentThread().name}")
        // 在一个协程中去加载网络图片
        val image = imageRepository.load(url)
        // 给 MutableState 设置值
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}

// produceState：将非 Compose 状态转换为 Compose 状态
@Composable
fun ProduceStateSample() {
    val imagesList = listOf<String>(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp6.itc.cn%2Fimages01%2F20200616%2F1a650dab707b47578128d15fe3f55e04.jpeg&refer=http%3A%2F%2Fp6.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1634141696&t=d51a2433acdc1630b189d7ef73f2af5f",
        "https://img0.baidu.com/it/u=3224605577,2098593296&fm=26&fmt=auto&gp=0.jpg",
        "https://故意pics1.baidu.com/feed/0dd7912397dda144c0900cbbe92a9fa40df48645.jpeg?token=712278018ad47ae77817ffe301815690&s=83BB738712E37AAC2429DCF20300C035"
    )
    val imageRepository = ImageRepository(LocalContext.current)
    // 通过控制索引值的变化，从集合中取出相应的网络图片路径
    var index by remember { mutableStateOf(0) }
    // 加载网络图片，返回一个 State，当 State 为不同的值时，显示不同的可组合项
    val result = loadNetworkImage(imagesList[index], imageRepository)

    Column {
        Button(
            onClick = {
                index %= imagesList.size
                if (++index == imagesList.size) index = 0
            }
        ) {
            Text(text = "选择第 $index 张图片")
        }

        when(result.value){
            is Result.Success -> {
                Image(
                    bitmap = (result.value as Result.Success).image.imageBitmap,
                    contentDescription = "image load success"
                )
            }
            is Result.Error -> {
                Image(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "image load error",
                    modifier = Modifier.size(200.dp,200.dp)
                )
            }
            else -> {
                CircularProgressIndicator() // 进度条
            }
        }
    }


}