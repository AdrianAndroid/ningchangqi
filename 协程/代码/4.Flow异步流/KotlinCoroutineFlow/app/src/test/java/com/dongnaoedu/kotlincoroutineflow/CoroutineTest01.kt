package com.dongnaoedu.kotlincoroutineflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class CoroutineTest01 {

    //返回了多个值，但不是异步
    fun simpleList(): List<Int> = listOf<Int>(1, 2, 3)

    //返回了多个值，是同步
    fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
            //Thread.sleep(1000)  //阻塞，假装在计算
            //delay(1000)
            yield(i)
        }
    }

    //返回了多个值，是异步，一次性返回了多个值
    suspend fun simpleList2(): List<Int> {
        delay(1000)
        return listOf<Int>(1, 2, 3)
    }

    //返回多个值，是异步的
    fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            delay(1000) //假装在一些重要的事情
            emit(i) //发射，产生一个元素
        }
    }


    @Test
    fun `test multiple values`() {
        //simpleList().forEach { value -> println(value) }

        //simpleSequence().forEach { value -> println(value) }
    }

    @Test
    fun `test multiple values2`() = runBlocking<Unit> {
        simpleList2().forEach { value -> println(value) }
    }

    @Test
    fun `test multiple values3`() = runBlocking<Unit> {
        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(1500)
            }
        }

        simpleFlow().collect { value -> println(value) }
    }


    fun simpleFlow2() = flow<Int> {
        println("Flow started")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun `test flow is cold`() = runBlocking<Unit> {
        val flow = simpleFlow2()
        println("Calling collect...")
        flow.collect { value -> println(value) }
        println("Calling collect again...")
        flow.collect { value -> println(value) }
    }


    @Test
    fun `test flow continuation`() = runBlocking<Unit> {
        (1..5).asFlow().filter {
            it % 2 == 0
        }.map {
            "string $it"
        }.collect {
            println("Collect $it")
        }
    }


    @Test
    fun `test flow builder`() = runBlocking<Unit> {
        /*flowOf("one","two","three")
                .onEach { delay(1000) }
                .collect { value ->
                    println(value)
                }*/

        (1..3).asFlow().collect { value ->
            println(value)
        }
    }


    fun simpleFlow3() = flow<Int> {
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }


    fun simpleFlow4() = flow<Int> {
        withContext(Dispatchers.IO) {
            println("Flow started ${Thread.currentThread().name}")
            for (i in 1..3) {
                delay(1000)
                emit(i)
            }
        }
    }


    @Test
    fun `test flow context`() = runBlocking<Unit> {
        simpleFlow4()
                .collect { value -> println("Collected $value ${Thread.currentThread().name}") }
    }

    fun simpleFlow5() = flow<Int> {
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    @Test
    fun `test flow on`() = runBlocking<Unit> {
        simpleFlow5()
                .collect { value -> println("Collected $value ${Thread.currentThread().name}") }
    }


    //事件源
    fun events() = (1..3)
            .asFlow()
            .onEach { delay(100) }
            .flowOn(Dispatchers.Default)


    @Test
    fun `test flow launch`() = runBlocking<Unit> {
        val job = events()
                .onEach { event -> println("Event: $event ${Thread.currentThread().name}") }
                //.collect {  }
                //.launchIn(CoroutineScope(Dispatchers.IO))
                .launchIn(this)

        //delay(200)
        //job.cancelAndJoin()
    }


    fun simpleFlow6() = flow<Int> {
        for (i in 1..3) {
            delay(1000)
            emit(i)
            println("Emitting $i")
        }
    }


    @Test
    fun `test cancel flow`() = runBlocking<Unit> {
        withTimeoutOrNull(2500) {
            simpleFlow6().collect { value -> println(value) }
        }
        println("Done")
    }


    fun simpleFlow7() = flow<Int> {
        for (i in 1..5) {
            emit(i)
            println("Emitting $i")
        }
    }

    @Test
    fun `test cancel flow check`() = runBlocking<Unit> {
        /*simpleFlow7().collect { value ->
            println(value)
            if (value == 3) cancel()
        }*/

        (1..5).asFlow().cancellable().collect { value ->
            println(value)
            if (value == 3) cancel()
        }
    }


    fun simpleFlow8() = flow<Int> {
        for (i in 1..3) {
            delay(100)
            emit(i)
            println("Emitting $i ${Thread.currentThread().name}")
        }
    }

    @Test
    fun `test flow back pressure`() = runBlocking<Unit> {
        val time = measureTimeMillis {
            simpleFlow8()
                    //.flowOn(Dispatchers.Default)
                    //.buffer(50)
                    //.conflate()
                    .collectLatest { value ->
                    //.collect { value ->
                delay(300)   //处理这个元素消耗300ms
                println("Collected $value ${Thread.currentThread().name}")
            }
        }

        println("Collected in $time ms")
    }

}