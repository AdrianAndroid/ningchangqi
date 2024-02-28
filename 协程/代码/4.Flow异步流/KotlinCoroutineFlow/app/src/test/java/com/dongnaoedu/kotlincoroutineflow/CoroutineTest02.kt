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
class CoroutineTest02 {

    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    @Test
    fun `test transform flow operator`() = runBlocking<Unit> {
        /*(1..3).asFlow()
                .map { request -> performRequest(request) }
                .collect { value -> println(value) }*/


        (1..3).asFlow()
                .transform { request ->
                    emit("Making request $request")
                    emit(performRequest(request))
                }.collect { value -> println(value) }

    }


    fun numbers() = flow<Int> {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }

    @Test
    fun `test limit length operator`() = runBlocking<Unit> {
        numbers().take(2).collect { value -> println(value) }
    }


    @Test
    fun `test terminal operator`() = runBlocking<Unit> {
        val sum = (1..5).asFlow()
                .map { it * it }
                .reduce { a, b -> a + b }
        println(sum)
    }


    @Test
    fun `test zip`() = runBlocking<Unit> {
        val numbs = (1..3).asFlow()
        val strs = flowOf("One", "Two", "Three")
        numbs.zip(strs) { a, b -> "$a -> $b" }.collect { println(it) }
    }


    @Test
    fun `test zip2`() = runBlocking<Unit> {
        val numbs = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("One", "Two", "Three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        numbs.zip(strs) { a, b -> "$a -> $b" }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
    }


    fun requestFlow(i: Int) = flow<String> {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    @Test
    fun `test flatMapConcat`() = runBlocking<Unit> {
        //Flow<Flow<String>>
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
                .onEach { delay(100) }
                //.map { requestFlow(it) }
                .flatMapConcat { requestFlow(it) }
                .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }


    @Test
    fun `test flatMapMerge`() = runBlocking<Unit> {
        //Flow<Flow<String>>
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
                .onEach { delay(100) }
                //.map { requestFlow(it) }
                .flatMapMerge { requestFlow(it) }
                .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }


    @Test
    fun `test flatMapLatest`() = runBlocking<Unit> {
        //Flow<Flow<String>>
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
                .onEach { delay(100) }
                //.map { requestFlow(it) }
                .flatMapLatest { requestFlow(it) }
                .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }

}