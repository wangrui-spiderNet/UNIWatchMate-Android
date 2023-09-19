package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.base.api.UNIWatchMate
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.`interface`.log.WmLog
import com.example.myapplication.MyApplication
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_actiivty)

        val btnConnect = findViewById<Button>(R.id.btn_connect)
        val btnExchange = findViewById<Button>(R.id.btn_exchange)

        btnConnect.setOnClickListener {
            UNIWatchMate.scanQr("www.shenju.watch?mac=00:00:56:78:9A:BC?name=SJ 8020N")
        }

        btnExchange.setOnClickListener {
            UNIWatchMate.connect("12:34:56:78:9A:BC", WmDeviceMode.SJ_WATCH)
        }
    }
}