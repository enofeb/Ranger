package com.enofeb.ranger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enofeb.view.RangerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ranger = findViewById<RangerView>(R.id.ranger)

        ranger.currentValue = 42.0
    }
}