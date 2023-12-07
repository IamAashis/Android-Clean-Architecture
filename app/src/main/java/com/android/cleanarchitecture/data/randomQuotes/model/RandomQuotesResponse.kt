package com.android.cleanarchitecture.data.randomQuotes.model

/**
 * Created by Aashis on 06,December,2023
 */
data class RandomQuotesResponse(
    var _id: String? = null,
    var content: String? = null,
    var author: String? = null,
//    var tags: String? = null,
    var authorSlug: String? = null,
    var dateAdded: String? = null,
    var dateModified: String? = null,
)
