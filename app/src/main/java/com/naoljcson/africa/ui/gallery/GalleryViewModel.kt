package com.naoljcson.africa.ui.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naoljcson.africa.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: GalleryRepository) :
    ViewModel() {
    private val _ldImages = MutableStateFlow<List<Uri>?>(null)
    val ldImages = _ldImages.asStateFlow()

    fun getImagesUri() = viewModelScope.launch {
        repository.getImagesUri().collect {
            _ldImages.emit(it)
        }
    }
}