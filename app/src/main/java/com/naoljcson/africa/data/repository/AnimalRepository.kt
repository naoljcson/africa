package com.naoljcson.africa.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.utils.toJPG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    suspend fun getAnimal(id: String): Flow<Animal> = flow {
        val snapshot = db.collection("animals")
            .whereEqualTo("id", id)
            .get()
            .await()
        val animal = snapshot.toObjects(Animal::class.java)[0]
        animal.imageUri = animal.image?.let { fileName ->
            getImageURI(fileName.toJPG())
        }
        animal.gallery?.forEach { fileName ->
            animal.galleryUri?.add(getImageURI(fileName.toJPG()))
        }
        emit(animal)
    }.flowOn(Dispatchers.IO)

    suspend fun getImageURI(imageName: String): Uri {
        return storageReference.child(imageName).downloadUrl.await()
    }
}