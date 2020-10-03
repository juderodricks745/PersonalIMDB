package com.davidbronn.personalimdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidbronn.personalimdb.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }
}