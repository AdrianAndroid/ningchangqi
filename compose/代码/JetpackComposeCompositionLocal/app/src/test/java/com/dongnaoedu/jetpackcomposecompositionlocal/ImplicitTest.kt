package com.dongnaoedu.jetpackcomposecompositionlocal

import org.junit.Test

/**
 * 隐式传参
 */
class ImplicitTest {

    var color: String = "黑色"

    private fun Layout() {
        Text()
        // 直接修改全局变量，会影响后续的值
        // color = "红色"
        provider("红色") {
            Grid()
        }
        Text()
        Text()
    }

    private fun Grid() {
        println("other components in Grid")
        Text()
    }

    private fun Text() {
        println("Text")
        println(color.toString())
    }

    private fun provider(value: String, content: (() -> Unit)) {
        // 方法执行前，修改全局变量
        color = value
        content()
        // 方法执行完之后，修改回来
        color = "黑色"
    }

    @Test
    fun test_implicit() {
        Layout()
    }
}