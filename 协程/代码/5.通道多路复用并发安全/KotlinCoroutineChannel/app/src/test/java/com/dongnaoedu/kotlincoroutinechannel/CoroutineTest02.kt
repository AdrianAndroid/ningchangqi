package com.dongnaoedu.kotlincoroutinechannel

import com.dongnaoedu.kotlincoroutinechannel.api.User
import com.dongnaoedu.kotlincoroutinechannel.api.userServiceApi
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.selects.select
import org.junit.Test
import java.io.File


private val cachePath = "E://coroutine.cache"
private val gson = Gson()

data class Response<T>(val value: T, val isLocal: Boolean)

fun CoroutineScope.getUserFromLocal(name: String) = async(Dispatchers.IO) {
    //delay(1000) //故意的延迟
    File(cachePath).readText().let { gson.fromJson(it, User::class.java) }
}

fun CoroutineScope.getUserFromRemote(name: String) = async(Dispatchers.IO) {
    userServiceApi.getUser(name)
}

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class CoroutineTest02 {

    @Test
    fun `test select await`() = runBlocking<Unit> {
        GlobalScope.launch {
            val localRequest = getUserFromLocal("xxx")
            val remoteRequest = getUserFromRemote("yyy")

            val userResponse = select<Response<User>> {
                localRequest.onAwait { Response(it, true) }
                remoteRequest.onAwait { Response(it, false) }
            }

            userResponse.value?.let { println(it) }
        }.join()
    }


    @Test
    fun `test select channel`() = runBlocking<Unit> {
        val channels = listOf(Channel<Int>(), Channel<Int>())
        GlobalScope.launch {
            delay(100)
            channels[0].send(200)
        }

        GlobalScope.launch {
            delay(50)
            channels[1].send(100)
        }

        val result = select<Int?> {
            channels.forEach { channel ->
                channel.onReceive { it }
            }
        }
        println(result)
    }

    @Test
    fun `test SelectClause0`() = runBlocking<Unit> {
        val job1 = GlobalScope.launch {
            delay(100)
            println("job 1")
        }

        val job2 = GlobalScope.launch {
            delay(10)
            println("job 2")
        }

        select<Unit> {
            job1.onJoin { println("job 1 onJoin") }
            job2.onJoin { println("job 2 onJoin") }
        }

        delay(1000)
    }


    @Test
    fun `test SelectClause2`() = runBlocking<Unit> {
        val channels = listOf(Channel<Int>(), Channel<Int>())
        println(channels)

        launch(Dispatchers.IO) {
            select<Unit?> {
                launch {
                    delay(10)
                    channels[1].onSend(200) { sentChannel ->
                        println("sent on $sentChannel")
                    }
                }

                launch {
                    delay(100)
                    channels[0].onSend(100) { sentChannel ->
                        println("sent on $sentChannel")
                    }
                }
            }
        }

        GlobalScope.launch {
            println(channels[0].receive())
        }

        GlobalScope.launch {
            println(channels[1].receive())
        }

        delay(1000)
    }


    @Test
    fun `test select flow`() = runBlocking<Unit> {
        // 函数 -> 协程 -> Flow -> Flow合并
        val name = "guest"
        coroutineScope {
            listOf(::getUserFromLocal, ::getUserFromRemote)
                    .map { function ->
                        function.call(name)
                    }.map { deferred ->
                        flow { emit(deferred.await()) }
                    }.merge().collect { user -> println(user) }

        }
    }

}