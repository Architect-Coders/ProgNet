package com.davidbragadeveloper.prognet.utils

import android.content.Context
import okhttp3.mockwebserver.MockResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets.UTF_8


fun MockResponse.fromJson(context: Context, jsonFile: String): MockResponse =
    setBody(readJsonFile(context, jsonFile))

private fun readJsonFile(context: Context, jsonFilePath: String): String {
    val res = context.packageManager.getResourcesForApplication("com.davidbragadeveloper.prognet.test")
    var line: String?
    val text = StringBuilder()


    try {
        BufferedReader(
            InputStreamReader(
                res.assets.open(
                    jsonFilePath
                ), UTF_8
            )
        ).use {
            do {
                line = it.readLine()
                line?.let { text.append(line) }
            } while (line != null)
        }

    }catch (e: Exception){
        println(e.localizedMessage)
    } finally {
        return text.toString()
    }
}