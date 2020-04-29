package com.dream.freshnews

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dream.freshnews.util.debug

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var progressView: LinearLayout
    private lateinit var progressMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        debug {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
        }
    }

    override fun onPause() {
        if (::progressView.isInitialized) {
            progressView.visibility = View.GONE
        }

        super.onPause()
    }

    fun isActivityValid(): Boolean {
        if (this.isFinishing) {
            return false
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) && isActivityDestroyed()) {
            return false
        }

        return true
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun isActivityDestroyed(): Boolean {
        return this.isDestroyed
    }

    open fun initProgressView() {
        progressView = findViewById(R.id.progressView)
        progressMsg = findViewById(R.id.progressMsg)
    }

    open fun hideLoading() {
        if (!isActivityValid()) return

        if (::progressView.isInitialized) {
            progressView.visibility = View.GONE
        }
    }

    open fun showLoading(msg: String = "Loading...") {
        if (!isActivityValid()) return

        if (::progressView.isInitialized) {
            progressMsg.text = msg
            progressMsg.visibility = if (msg.isBlank()) View.GONE else View.VISIBLE
            progressView.visibility = View.VISIBLE
        }
    }
}
