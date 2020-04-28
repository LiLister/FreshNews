package com.dream.freshnews.sources

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.dream.freshnews.BaseActivity
import com.dream.freshnews.R
import com.dream.freshnews.data.Source
import com.dream.freshnews.data.source.NewsDataSource
import com.dream.freshnews.data.source.NewsRepository
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource
import com.dream.freshnews.topheadlines.TopHeadlinesActivity
import com.dream.freshnews.util.MyInjectionUtil
//import com.dream.freshnews.util.DialogHelper
//import com.dream.freshnews.util.MyInjectionUtil
import kotlinx.android.synthetic.main.activity_sources.*

class SourcesActivity : BaseActivity() {

    private lateinit var sourceAdapter: SourceAdapter
    private val newsRepository = NewsRepository.getInstance(NewsLocalDataSource(), NewsRemoteDataSource())//MyInjectionUtil.newsRepository
//        MyInjectionUtil.newsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        title = "News Sources"

        sourceAdapter = SourceAdapter(this)
        sourceAdapter.setOnItemClick { position, source ->
            TopHeadlinesActivity.startMe(this@SourcesActivity, source.id)
        }

        rv_sources.layoutManager =
            LinearLayoutManager(this)
        rv_sources.adapter = sourceAdapter

        // load source
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.source_refresh, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_refresh -> {
                newsRepository.clearCachedSources()

                loadData()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        showLoading()

        newsRepository.getSources {
                ok, errMsg, data ->

            hideLoading()

            if (!ok) {
//                DialogHelper.showSimpleInfoDialog(this.fragmentManager, resources.getString(
//                    R.string.failed_to_load_sources, "" + errMsg))
            } else {
                sourceAdapter.setData(data)
            }
        }
    }
}

class SourceAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mData: MutableList<Source> = mutableListOf()

    private var onItemClick: ((position: Int, source: Source) -> Unit)? = null

    fun setOnItemClick(itemClickHandler: ((position: Int, source: Source) -> Unit)) {
        onItemClick = itemClickHandler
    }

    fun setData(data: List<Source>) {
        mData.clear()
        mData.addAll(data)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_source, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mData.get(position)
        (holder as ViewHolder).bindView(data, position)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position, data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private var tvURL: TextView = itemView.findViewById(R.id.tv_url)

        fun bindView(data: Source, position: Int) {
            tvTitle.text = data.name
            tvDescription.text = data.description
            tvURL.text = data.url
        }
    }
}
