package com.dongnaoedu.jetpackcomposecompositionlocal

import org.junit.Test

/**
 * 显示传参
 */
class ExplicitTest {

    private fun Layout(){
        var color:String = "黑色"
        Text(color)
        // color = "红色"
        // 显示传参的问题：
        // 1.参数需要通过层层传递，比较繁琐
        // 2.不好维护
        Grid(color)
        Text(color)
        Text(color)
    }

    private fun Grid(color: String) {
        println("other components in Grid")
        Text(color)
    }

    private fun Text(color: String) {
        println("Text")
        println(color)
    }

    @Test
    fun test_explicit() {
        Layout()
    }
}