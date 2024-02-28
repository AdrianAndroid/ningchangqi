package com.dongnaoedu.carhome.init

import android.content.Context

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */

const val SERVER_URL = "http://192.168.1.4:8080/pagingserver_war/"

object AppHelper {

    lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }

}