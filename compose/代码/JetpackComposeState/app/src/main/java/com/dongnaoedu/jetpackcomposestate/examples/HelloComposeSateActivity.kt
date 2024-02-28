package com.dongnaoedu.jetpackcomposestate.examples

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongnaoedu.jetpackcomposestate.databinding.ActivityHelloComposeStateBinding

//从本质上讲，应用程序的状态，可以是任何随着时间变化的值。
//它可能是存储在Room数据库中的值、类中的变量，甚至是从加速度传感器中读取的值。
class HelloComposeSateActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textInput.doAfterTextChanged { text ->
            updateHello(text.toString())
        }
    }

    private fun updateHello(name: String) {
        binding.helloText.text = "Hello, $name"
    }
}

class HelloViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

class HelloComposeStateActivityWithViewModel : AppCompatActivity() {

    private val helloViewModel by viewModels<HelloViewModel>()
    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textInput.doAfterTextChanged {
            helloViewModel.onNameChanged(it.toString())
        }

        helloViewModel.name.observe(this){ name ->
            binding.helloText.text = "Hello, $name"
        }
    }
}