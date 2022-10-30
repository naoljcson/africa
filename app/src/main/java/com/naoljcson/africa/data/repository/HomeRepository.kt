package com.naoljcson.africa.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.utils.toJPG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    suspend fun getCoverImages(): Flow<List<Uri>> = flow {
        val coverImages = mutableListOf<Uri>()
        val snapshot = db.collection("covers").get().await()
        snapshot.documents.forEach {
            it?.data?.get("name")?.let { fileName ->
                getImageURI(fileName.toString().toJPG())?.let { it1 -> coverImages.add(it1) }
            }
        }
        emit(coverImages)
    }.catch {
        Log.i("", "getCoverImages repo coverImages Something went wrong")
    }.flowOn(Dispatchers.IO)


    private suspend fun getImageURI(imageName: String): Uri? {
        var uri: Uri? = null
        try {
            uri = storageReference.child(imageName).downloadUrl.await()
        } catch (e: Exception) {
            Log.i("", "getImageURI $e")
        }
        return uri
    }

    suspend fun getAnimals(): Flow<List<Animal>> = flow {
        val snapshot = db.collection("animals").get().await()
        val animals = mutableListOf<Animal>()
        animals.addAll(snapshot.toObjects(Animal::class.java))
        animals.map {
            it.imageUri = it.image?.let { fileName ->
                getImageURI(fileName.toJPG())
            }
        }
        emit(animals)
    }.flowOn(Dispatchers.IO)

}