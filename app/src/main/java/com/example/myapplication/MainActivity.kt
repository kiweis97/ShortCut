package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            ShortCutUtils.createShortCut(
                context = this,
                targetClass = MainActivity::class.java,
                shortCutId = "shortCutId" + (++count),
                label = "shortCutLabel$count",
                iconResId = R.drawable.ic_launcher_foreground
            )
        }
    }

}