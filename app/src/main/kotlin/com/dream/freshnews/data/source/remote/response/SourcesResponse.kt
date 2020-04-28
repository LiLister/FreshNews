package com.dream.freshnews.data.source.remote.response

import com.dream.freshnews.data.Source

/**
 * Created by lixingming on 24/03/2018.
 */
data class SourcesResponse(val status: String, val sources: List<Source>)