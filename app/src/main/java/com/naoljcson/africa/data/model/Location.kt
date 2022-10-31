package com.naoljcson.africa.data.model

import android.graphics.Bitmap
import android.net.Uri

data class Location(
    var id: String? = null,
    var image: String? = null,
    var imageUri: Uri? = null,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var name: String? = null,
    var mapIcon: Bitmap? = null
)
