package com.dongnaoedu.kotlincoroutineexception

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {

    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d("ning","Unhandled Coroutine Exception: $exception")
    }
}