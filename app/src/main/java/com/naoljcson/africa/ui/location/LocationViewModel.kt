package com.naoljcson.africa.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naoljcson.africa.data.model.Location
import com.naoljcson.africa.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: LocationRepository) :
    ViewModel() {
    private val _ldLocations = MutableStateFlow<List<Location>?>(null)
    val ldLocations = _ldLocations.asStateFlow()

    fun getLocations() = viewModelScope.launch {
        repository.getLocations().collect {
            _ldLocations.emit(it)
        }
    }
}