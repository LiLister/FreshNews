package com.dream.freshnews.sources

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dream.freshnews.BaseActivity
import com.dream.freshnews.R
import com.dream.freshnews.data.Source
import com.dream.freshnews.data.source.NewsRepository
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource
import kotlinx.android.synthetic.main.activity_sources.*
import org.jetbrains.anko.onClick

class SourcesActivity : BaseActivity() {

    private lateinit var sourceAdapter: SourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        sourceAdapter = SourceAdapter(this)

        rv_sources.layoutManager = LinearLayoutManager(this)

        rv_sources.adapter = sourceAdapter


        showLoading()

        val newsRepository = NewsRepository.getInstance(NewsLocalDataSource(), NewsRemoteDataSource())

        newsRepository.getSources {
            sourceAdapter.setData(it)

            hideLoading()
        }
    }
}

class SourceAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mData: MutableList<Source> = mutableListOf()

    fun setData(data: List<Source>) {
        mData.clear()
        mData.addAll(data)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_source, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val data = mData.get(position)
        (holder as ViewHolder).bindView(data, position)

        holder.itemView.onClick {
//            onItemClick?.invoke(data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private var tvURL: TextView = itemView.findViewById(R.id.tv_url)

//        init {
//            tvTitle = itemView.findViewById(R.id.tv_title)
//            tvDescription = itemView.findViewById(R.id.tv_description)
//
//        }

        fun bindView(data: Source, position: Int) {
            tvTitle.text = data.name
            tvDescription.text = data.description
            tvURL.text = data.url
        }

    }
}



