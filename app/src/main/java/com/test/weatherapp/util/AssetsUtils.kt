package com.test.weatherapp.util

import android.content.Context
import java.io.InputStream

object AssetsUtils {
    @JvmStatic
    fun readFile(context: Context, fileName: String): String? {
        val stream: InputStream = context.assets.open(fileName)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        return  String(buffer)
    }
}