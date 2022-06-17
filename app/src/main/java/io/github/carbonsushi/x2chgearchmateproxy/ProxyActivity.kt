package io.github.carbonsushi.x2chgearchmateproxy

import android.content.ActivityNotFoundException
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager

class ProxyActivity : AppCompatActivity() {
    companion object {
        private const val X2CHGEAR_PACKAGE_NAME = "jp.emprise.android.x2chGear"
        private const val X2CHGEAR_POST_ACTIVITY_NAME =
            "info.narazaki.android.tuboroid.core.activity.common.MessageInfoPostActivity"

        private const val CHMATE_PACKAGE_NAME = "jp.co.airfront.android.a2chMate"
        private const val CHMATE_POST_ACTIVITY_NAME = "jp.syoboi.a2chMate.activity.ResEditActivity"
    }

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val use2chGear by lazy {
        val use2chGearHostname = sharedPreferences
            .getString("use_2chgear_hostname", ".5ch.net\n.bbspink.com")?.split("\n")
        intent.data?.pathSegments?.contains("read.cgi") == true && use2chGearHostname?.any { hostname ->
            intent.data?.host?.endsWith(hostname) == true
        } == true
    }
    private val wifiManager by lazy { getSystemService<WifiManager>() }
    private val isOffWifi by lazy {
        if (use2chGear
            && sharedPreferences.getBoolean("off_on_wifi", false)
            && wifiManager?.isWifiEnabled == true
        ) {
            wifiManager?.setWifiEnabled(false)
        } else {
            false
        }
    }

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (isOffWifi == true) {
                wifiManager?.isWifiEnabled = true
            }
            setResult(result.resultCode)
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callingPackageName =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) callingPackage else callingActivity?.packageName
        if (callingPackageName != CHMATE_PACKAGE_NAME) {
            Toast.makeText(this, R.string.dont_call_except_chmate_error, Toast.LENGTH_LONG).show()
            finish()
        }

        val sendIntent = if (use2chGear) {
            intent.setClassName(X2CHGEAR_PACKAGE_NAME, X2CHGEAR_POST_ACTIVITY_NAME)
        } else {
            intent.setClassName(CHMATE_PACKAGE_NAME, CHMATE_POST_ACTIVITY_NAME)
        }

        try {
            activityResult.launch(sendIntent)
            isOffWifi
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.activity_not_found_error, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}