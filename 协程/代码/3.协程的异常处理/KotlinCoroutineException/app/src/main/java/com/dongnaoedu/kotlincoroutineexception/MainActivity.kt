package com.dongnaoedu.kotlincoroutineexception

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("ning", "Caught $exception")
        }

        findViewById<Button>(R.id.button).also {
            it.setOnClickListener {
                GlobalScope.launch {
                    Log.d("ning", "on Click.")
                    "abc".substring(10)
                }
            }
        }
    }

}