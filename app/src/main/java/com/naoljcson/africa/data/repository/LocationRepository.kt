package com.naoljcson.africa.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Location
import com.naoljcson.africa.utils.toJPG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.File.createTempFile
import java.io.IOException
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    suspend fun getLocations(): Flow<List<Location>> = flow {
        val snapshot = db.collection("locations").get().await()
        val locations = snapshot.toObjects(Location::class.java)
        locations.map { it.mapIcon = it.image?.let { it1 -> getImage(it1.toJPG()) } }
        emit(locations)
    }.flowOn(Dispatchers.IO)

    private suspend fun getMapIconUri(fileName: String): Uri? {
        var uri: Uri? = null
        try {
            uri = storageReference.child(fileName).downloadUrl.await()
        } catch (e: Exception) {
            Log.i("LocationRepository", "getMapIconUri error $e")
        }
        return uri
    }

    private suspend fun getImage(fileName: String): Bitmap? {
        var mapIcon: Bitmap? = null
        try {
            val localFile: File = createTempFile("Images", "bmp");
            storageReference.child(fileName).getFile(localFile).await()
            mapIcon = BitmapFactory.decodeFile(localFile.absolutePath);
        } catch (e: IOException) {
            e.printStackTrace();
        }
        return mapIcon
    }
}