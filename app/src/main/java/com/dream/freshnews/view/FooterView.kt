package com.dream.freshnews.view

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.dream.freshnews.R
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

/**
 * Created by lixingming on 26/03/2018.
 */
class FooterView(val context: Context) {

    private lateinit var view: View
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    fun getView(): View {
        if (!::view.isInitialized) {
            view = context.layoutInflater.inflate(R.layout.load_more_footer_view, null)
            progressBar = view.find(R.id.pb_load_more)
            progressBar.visibility = View.GONE

            textView = view.find(R.id.tv_info)
        }
        return view
    }

    fun updateState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LS_NOT_ENABLED -> {
                progressBar.visibility = View.GONE
                textView.text = context.resources.getString(R.string.load_more_not_enabled)
            }
            LoadingState.LS_ENABLED -> {
                progressBar.visibility = View.GONE
                textView.text = context.resources.getString(R.string.load_more_enabled)
            }
            LoadingState.LS_LOADING -> {
                progressBar.visibility = View.VISIBLE
                textView.text = context.resources.getString(R.string.load_more_loading)
            }
            LoadingState.LS_LOADED -> {
                progressBar.visibility = View.GONE
                textView.text = context.resources.getString(R.string.load_more_loaded)
            }
        }
    }
}

enum class LoadingState {
    LS_NOT_ENABLED,
    LS_ENABLED,
    LS_LOADING,
    LS_LOADED
}