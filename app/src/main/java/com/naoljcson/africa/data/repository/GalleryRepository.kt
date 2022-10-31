package com.naoljcson.africa.data.repository

import android.net.Uri
import android.util.Log
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

class GalleryRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    suspend fun getImagesUri(): Flow<List<Uri>> = flow {
        val snapshot = db.collection("animals").get().await()
        val uris = mutableListOf<Uri>()
        snapshot.toObjects(Animal::class.java)
            .forEach { animal ->
                animal.gallery?.let { gallery ->
                    gallery.forEach { imageName ->
                        getImageUri(imageName.toJPG())?.let { uris.add(it) }
                    }
                }
            }
        emit(uris)
    }.flowOn(Dispatchers.IO)

    suspend fun getImageUri(fileName: String): Uri? {
        var uri: Uri? = null
        try {
            uri = storageReference.child(fileName).downloadUrl.await()
        } catch (e: Exception) {
            Log.i("GalleryRepository", "Error $e")
        }
        return uri
    }
}