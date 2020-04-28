package com.dream.freshnews

import androidx.recyclerview.widget.RecyclerView
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.sources.SourcesActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)

class SourcesActivityTest {

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun testLocalGetSources() {
        val newsDataSource = NewsLocalDataSource()
        newsDataSource.clearCachedSources()
        newsDataSource.getSources {
                ok, errMsg, responseData ->

            assert(ok)

            assert(responseData.isEmpty())
        }
    }

    @Test
    fun testSourcesActivity() {
        System.out.println("Hello")

        val sourcesActivity = Robolectric.setupActivity(SourcesActivity::class.java)

        assertNotNull(sourcesActivity)

        assertEquals(sourcesActivity.title, "News Sources")

        val recyclerView = sourcesActivity.findViewById<RecyclerView>(R.id.rv_sources)
        assertNotNull(recyclerView)
    }
}
