package com.dream.freshnews.topheadlines

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dream.freshnews.BaseActivity
import com.dream.freshnews.R
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.NewsRepository
import com.dream.freshnews.data.source.NewsRepository.Companion.KEY_PAGE
import com.dream.freshnews.data.source.NewsRepository.Companion.KEY_PAGE_SIZE
import com.dream.freshnews.data.source.NewsRepository.Companion.KEY_SOURCE
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource
import com.dream.freshnews.util.DateTimeUtil
import com.dream.freshnews.util.DialogHelper
import com.dream.freshnews.view.FooterView
import com.dream.freshnews.view.LoadingState
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_top_headlines.*

class TopHeadlinesActivity : BaseActivity() {

    private lateinit var topHeadinesAdapter: TopHeadinesAdapter
    private var pageNo: Int = 1
    private val pageSize: Int = 5
    private var hasMore: Boolean = true
    private lateinit var source: String
    private val newsRepository = NewsRepository.getInstance(NewsLocalDataSource(), NewsRemoteDataSource())
    private lateinit var footerView: FooterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)

        title = "Top Headlines"

        initViews()

        source = intent.getStringExtra(KEY_SOURCE_NAME)

        loadData()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val position = (rv_top_headlines.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        // force the RecycyclerView to redraw its items
        rv_top_headlines.adapter = topHeadinesAdapter

        rv_top_headlines.scrollToPosition(position)

        super.onConfigurationChanged(newConfig)
    }

    private fun initViews() {
        footerView = FooterView(this)

        topHeadinesAdapter = TopHeadinesAdapter(this)

        rv_top_headlines.layoutManager =
            LinearLayoutManager(this)
        rv_top_headlines.adapter = topHeadinesAdapter

        swipe_refresh.setOnPullRefreshListener(object : SuperSwipeRefreshLayout.OnPullRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }

            override fun onPullDistance(p0: Int) { }

            override fun onPullEnable(p0: Boolean) { }
        })

        swipe_refresh.setFooterView(footerView.getView())
        swipe_refresh.setOnPushLoadMoreListener(object : SuperSwipeRefreshLayout.OnPushLoadMoreListener {
            override fun onLoadMore() {
                if (hasMore) {
                    pageNo += 1

                    footerView.updateState(LoadingState.LS_LOADING)

                    loadData()
                } else {
                    swipe_refresh.setLoadMore(false)
                }
            }

            override fun onPushDistance(p0: Int) { }

            override fun onPushEnable(p0: Boolean) {
                val loadingState = if (p0) LoadingState.LS_ENABLED else LoadingState.LS_NOT_ENABLED
                footerView.updateState(loadingState)
            }
        })
    }

    private fun loadData() {
        showLoading()

        newsRepository.getTopHeadlines(constructParameters()) {
                ok, errMsg, data ->

            hideLoading()

            if (isActivityValid()) {
                swipe_refresh.isRefreshing = false
                swipe_refresh.setLoadMore(false)
                footerView.updateState(LoadingState.LS_LOADED)

                if (!ok) {
                    DialogHelper.showSimpleInfoDialog(
                        this.supportFragmentManager, resources.getString(
                            R.string.failed_to_load_top_headlines, "" + errMsg
                        )
                    )
                } else {
                    if (pageNo == 1) {
                        topHeadinesAdapter.setData(data)
                    } else {
                        topHeadinesAdapter.appendData(data)
                    }

                    // TODO not return the totalResults to check more accurately. Should be optimized later
                    hasMore = data.size >= pageSize
                }
            }
        }
    }

    private fun constructParameters(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        result.put(KEY_SOURCE, source)
        result.put(KEY_PAGE_SIZE, pageSize.toString())
        result.put(KEY_PAGE, pageNo.toString())

        return result
    }

    companion object {
        val KEY_SOURCE_NAME = "SOURCE_NAME"
        fun startMe(context: Context, source: String) {
            val intent = Intent()
            intent.putExtra(KEY_SOURCE_NAME, source)
            intent.setClass(context, TopHeadlinesActivity::class.java)
            context.startActivity(intent)
        }
    }
}

class TopHeadinesAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mData: MutableList<TopHeadline> = mutableListOf()

    private var onItemClick: ((position: Int, source: TopHeadline) -> Unit)? = null

    fun setOnItemClick(itemClickHandler: ((position: Int, source: TopHeadline) -> Unit)) {
        onItemClick = itemClickHandler
    }

    fun setData(data: List<TopHeadline>) {
        mData.clear()
        mData.addAll(data)

        notifyDataSetChanged()
    }

    fun appendData(data: List<TopHeadline>) {
        mData.addAll(data)

        notifyItemMoved(mData.size - data.size, mData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_top_headline, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mData.get(position)
        (holder as ViewHolder).bindView(context, data, position)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position, data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private var tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
        private var tvPublishedDay: TextView = itemView.findViewById(R.id.tv_published_day)
        private var ivThumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)

        fun bindView(context: Context, data: TopHeadline, position: Int) {
            tvTitle.text = data.title
            tvDescription.text = data.description
            tvAuthor.text = data.author
            // format date
            tvPublishedDay.text = DateTimeUtil.toLocalDateTime(data.publishedAt)

            Glide.with(context)
                .load(data.urlToImage)
                .into(ivThumbnail)
//                .placeholder(R.drawable.placeholder)
        }
    }
}
