package com.naoljcson.africa.data.model

import android.net.Uri

data class Video(
    var headline: String? = null,
    var id: String? = null,
    var name: String? = null,
    var imageUri: Uri? = null,
    var videoUri: Uri? = null,
)
