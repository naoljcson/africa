package com.naoljcson.africa.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naoljcson.africa.data.model.Video
import com.naoljcson.africa.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val repository: VideoRepository) : ViewModel() {
    private val _ldVideos = MutableStateFlow<List<Video>?>(null)
    val ldVideos = _ldVideos.asStateFlow()

    private val _ldVideo = MutableStateFlow<Video?>(null)
    val ldVideo = _ldVideo.asStateFlow()

    fun getVideos() = viewModelScope.launch {
        repository.getVideos().collect { videos ->
            _ldVideos.emit(videos)
        }
    }

    fun getVideo(id: String) = viewModelScope.launch {
        repository.getVideo(id).collect { video ->
            _ldVideo.emit(video)
        }
    }
}