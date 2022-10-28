package com.naoljcson.africa.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Animal
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference){

    suspend fun getCoverImages(): Flow<List<String>> = flow {
        Log.i("", "getCoverImage 1")
        val coverImages = mutableListOf<String>()
        val snapshot = db.collection("covers").get().await()
        snapshot.documents.forEach {
            it?.data?.get("name")?.let { it1 -> coverImages.add("$it1.jpg") }
        }
        Log.i("", "getCoverImage $coverImages")
        emit(coverImages)
    }.catch {
        Log.i("", "getCoverImages repo coverImages Something went wrong")
    }.flowOn(Dispatchers.IO)


    suspend fun getImageURI(imageName: String): Flow<Uri> = flow<Uri> {
        val snapshot = storageReference.child(imageName).downloadUrl.await()
        emit(snapshot)
    }.flowOn(Dispatchers.IO)

    suspend fun getAnimals(): Flow<List<Animal>> = flow {
        val snapshot = db.collection("animals").get().await()
//        Log.i("", "getCoverImages getAnimals ${snapshot.documents}")
        val animals = snapshot.toObjects(Animal::class.java)
        emit(animals)
    }.flowOn(Dispatchers.IO)
}