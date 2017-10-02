package com.koto.servicesplayground

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var startServiceButton = findViewById(R.id.startServiceButton)
        startServiceButton.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            i.putExtra("KEY1", "Value to be used by the service")
            this.startService(i)
        }

        var stopServiceButton = findViewById(R.id.stopServiceButton)
        stopServiceButton.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            stopService(i)
        }
    }

    fun onClick(view: View) {

    }
}
