package com.dream.freshnews.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.dream.freshnews.R

/**
 * Created by lixingming on 26/03/2018.
 */
class FooterView(val viewGroup: ViewGroup) {

    private var view: View? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    fun getView(): View {
        if (this.view == null) {
            val inflater = viewGroup.context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE) as
                    LayoutInflater
            view = inflater.inflate(R.layout
                .load_more_footer_view, viewGroup, false)
            progressBar = view?.findViewById(R.id.pb_load_more)!!
            progressBar.visibility = View.GONE

            textView = view?.findViewById(R.id.tv_info)!!
        }
        return view!!
    }

    fun updateState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LS_NOT_ENABLED -> {
                progressBar.visibility = View.GONE
                textView.text = viewGroup.context.resources.getString(R.string
                    .load_more_not_enabled)
            }
            LoadingState.LS_ENABLED -> {
                progressBar.visibility = View.GONE
                textView.text = viewGroup.context.resources.getString(R.string.load_more_enabled)
            }
            LoadingState.LS_LOADING -> {
                progressBar.visibility = View.VISIBLE
                textView.text = viewGroup.context.resources.getString(R.string.load_more_loading)
            }
            LoadingState.LS_LOADED -> {
                progressBar.visibility = View.GONE
                textView.text = viewGroup.context.resources.getString(R.string.load_more_loaded)
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