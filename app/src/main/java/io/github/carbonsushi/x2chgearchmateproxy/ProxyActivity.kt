package io.github.carbonsushi.x2chgearchmateproxy

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProxyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            startActivity(
                intent.setClassName(
                    "jp.emprise.android.x2chGear",
                    "info.narazaki.android.tuboroid.core.activity.common.MessageInfoPostActivity"
                )
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.activity_not_found_error, Toast.LENGTH_LONG).show()
        }
        finish()
    }
}