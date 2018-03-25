package com.dream.freshnews

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import com.dream.freshnews.util.debug

open abstract class BaseActivity : AppCompatActivity() {

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

    private var pDialog: ProgressDialog? = null
    open fun hideLoading() {
        if (!isActivityValid()) return

        if (pDialog != null && pDialog!!.isShowing) {
            try {
                pDialog!!.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    open fun showLoading(msg: String = "Loading...") {
        if (!isActivityValid()) return

        if (pDialog != null && pDialog!!.isShowing()) {
            pDialog!!.setMessage(msg)

            return
        }
        if (pDialog == null) {
            pDialog = ProgressDialog(this)
            pDialog?.isIndeterminate = false
            pDialog?.setCancelable(false)
            pDialog?.setCanceledOnTouchOutside(false)
        }
        pDialog?.setMessage(msg)

        pDialog?.show()
    }
}
