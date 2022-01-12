package io.github.carbonsushi.x2chgearchmateproxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.aboutlibraries.LibsBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LibsBuilder().apply {
            aboutShowIcon = true
            aboutAppName = getString(R.string.app_name)
            aboutShowVersionName = true
            aboutDescription = getString(R.string.about_description)
        }.start(this)
        finish()
    }
}