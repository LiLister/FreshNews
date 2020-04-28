package com.dream.freshnews

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.dream.freshnews.sources.SourcesActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by lixingming on 25/10/2017.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SourcesActivityTest2 {
    @get:Rule
    var mActivityRule = ActivityTestRule(SourcesActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun testTextViewDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.menu_refresh))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testRefresh() {
        Espresso.onView(ViewMatchers.withId(R.id.menu_refresh)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.rv_sources)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_top_headlines))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}