package com.naoljcson.africa.ui.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository:HomeRepository) : ViewModel() {
    private val _ldCoverImages = MutableStateFlow<List<String>>(emptyList())
    val ldCoverImages = _ldCoverImages.asStateFlow()

    private val _ldImageUri = MutableStateFlow<Uri?>(null)
    val ldImageUri = _ldImageUri.asStateFlow()

    private val _ldAnimals = MutableStateFlow<List<Animal>?>(null)
    val ldAnimals = _ldAnimals.asStateFlow()

    fun getCoverImages() = viewModelScope.launch {
        homeRepository.getCoverImages().collect { imageCovers ->
            _ldCoverImages.emit(imageCovers)
        }
    }

    fun getImageURI(imageName: String) = viewModelScope.launch {
        homeRepository.getImageURI(imageName).collect { uri ->
            _ldImageUri.emit(uri)
        }
    }

    fun getAnimals() = viewModelScope.launch {
        homeRepository.getAnimals().collect { animals ->
            _ldAnimals.emit(animals)
        }
    }
}