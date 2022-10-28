package com.naoljcson.africa.data.model

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class Animal(
    var id: String? = null,
    var name: String? = null,
    var headline: String? = null,
    var description: String? = null,
    var link: String? = null,
    var image: String? = null,
    var imageUri: Uri? = null,
    var gallery: List<String>? = null,
    var fact: List<String>? = null,
)
