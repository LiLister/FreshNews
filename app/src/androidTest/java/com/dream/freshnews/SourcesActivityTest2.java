package com.dream.freshnews;

/**
 * Created by lixingming on 25/10/2017.
 */

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.dream.freshnews.sources.SourcesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SourcesActivityTest2 {
    public SourcesActivityTest2() {
        super();
    }

    @Rule
    public ActivityTestRule<SourcesActivity> mActivityRule = new ActivityTestRule<>(SourcesActivity.class);

    @Before
    public void setup() {

    }

    @Test
    public void testTextViewDisplay() {
        onView(withId(R.id.menu_refresh)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testRefresh() {
        onView(withId(R.id.menu_refresh)).perform(click());

        onView(withId(R.id.rv_sources)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_top_headlines)).check(ViewAssertions.matches(isDisplayed()));
    }

}
