package com.koto.servicesplayground

import android.app.IntentService
import android.content.Intent
import android.app.Activity
import android.os.Environment
import java.io.*
import java.net.URL


class DownloadService : IntentService("DownloadService") {

    private var result = Activity.RESULT_CANCELED
    val URL = "urlpath"
    val FILENAME = "filename"
    val FILEPATH = "filepath"
    val RESULT = "result"
    val NOTIFICATION = "com.koto.servicesplayground"

    override fun onHandleIntent(intent: Intent) {
        val urlPath = intent.getStringExtra(URL)
        val fileName = intent.getStringExtra(FILENAME)
        val output = File(Environment.getExternalStorageDirectory(), fileName)
        if (output.exists()) {
            output.delete()
        }

        var stream: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            val url = URL(urlPath)
            stream = url.openConnection().getInputStream()
            val reader = InputStreamReader(stream)
            fos = FileOutputStream(output.path)
            var next = reader.read()
            while (next != -1) {
                fos.write(next)
                next = reader.read()
            }
            // successfully finished
            result = Activity.RESULT_OK
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        publishResults(output.absolutePath, result)
    }

    private fun publishResults(outputPath: String, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(FILEPATH, outputPath)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }
}