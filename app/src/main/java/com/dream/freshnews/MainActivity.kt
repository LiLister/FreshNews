package com.dream.freshnews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dream.freshnews.sources.SourcesActivity
import org.jetbrains.anko.internals.AnkoInternals

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        AnkoInternals.internalStartActivity(this, SourcesActivity::class.java, arrayOf())
        this.finish()
    }
}
