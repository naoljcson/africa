package com.naoljcson.africa.ui.animal.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(private val repository: AnimalRepository) :
    ViewModel() {
    private var _ldAnimal = MutableStateFlow<Animal?>(null)
     val ldAnimal = _ldAnimal.asStateFlow()

    fun getAnimal(id: String) = viewModelScope.launch {
        repository.getAnimal(id).collect { animal ->
            _ldAnimal.emit(animal)
        }
    }
}