package com.dongnaoedu.carhome.init

import android.content.Context
import androidx.startup.Initializer

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        AppHelper.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}