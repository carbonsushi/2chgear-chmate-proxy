package io.github.carbonsushi.x2chgearchmateproxy

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ProxyActivity : AppCompatActivity() {
    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            setResult(result.resultCode)
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sendIntent = if (intent.data!!.pathSegments.contains("read.cgi")) {
            intent.setClassName(
                "jp.emprise.android.x2chGear",
                "info.narazaki.android.tuboroid.core.activity.common.MessageInfoPostActivity"
            )
        } else {
            intent.setClassName(
                "jp.co.airfront.android.a2chMate",
                "jp.syoboi.a2chMate.activity.ResEditActivity"
            )
        }

        try {
            activityResult.launch(sendIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.activity_not_found_error, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}