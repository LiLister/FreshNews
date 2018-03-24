package com.dream.freshnews

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

open abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var pDialog: ProgressDialog? = null
    open fun hideLoading() {
        if (pDialog != null && pDialog!!.isShowing) {
            try {
                pDialog!!.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    open fun showLoading(msg: String = "Loading...") {
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
