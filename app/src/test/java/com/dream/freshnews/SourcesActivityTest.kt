package com.dream.freshnews

import com.dream.freshnews.data.source.local.NewsLocalDataSource
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk= intArrayOf(22))
//@Config(constants = BuildConfig::class)

class SourcesActivityTest {

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        val app= FreshNewsApp()
//        app.setLocationProvider(mockLocationProvider)
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

//    @Test
//    fun testSourcesActivity() {
//        System.out.println("Hello")
//
//        val sourcesActivity = ActivityScenario.launch(SourcesActivity::class.java)
//
//        assertNotNull(sourcesActivity)
//
////        assertEquals(sourcesActivity.title, "News Sources")
////
////        val recyclerView = sourcesActivity.findViewById<RecyclerView>(R.id.rv_sources)
////        assertNotNull(recyclerView)
//    }
}
