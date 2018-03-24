package com.dream.freshnews.data

/**
 * Created by lixingming on 24/03/2018.
 */

/**
* class to hold the top headline info
*/
data class TopHeadline(val author: String, val title: String, val description: String, val url: String,
                       val urlToImage: String, val publishedAt: String) {
}