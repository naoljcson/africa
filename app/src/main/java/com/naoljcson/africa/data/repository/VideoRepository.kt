package com.naoljcson.africa.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Video
import com.naoljcson.africa.utils.toJPG
import com.naoljcson.africa.utils.toMp4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) {
    suspend fun getVideos(): Flow<List<Video>> = flow {
        val snapshot = db.collection("videos").get().await()
        val videos = snapshot.toObjects(Video::class.java)
        videos.map { it.imageUri = getMediaURI(it.id.toString().toJPG()) }
        emit(videos)
    }.flowOn(Dispatchers.IO)

    suspend fun getVideo(id: String): Flow<Video> = flow {
        val snapshot = db.collection("videos")
            .whereEqualTo("id", id)
            .get()
            .await()
        val video = snapshot.toObjects(Video::class.java)[0]
        video.videoUri = getMediaURI(video.id.toString().toMp4())
        emit(video)
    }.flowOn(Dispatchers.IO)

    private suspend fun getMediaURI(fileName: String): Uri? {
        var uri: Uri? = null
        try {
            uri = storageReference.child(fileName).downloadUrl.await()
        } catch (e: Exception) {
            Log.i("fatal", "getMediaURI filename = $fileName, $e")
        }
        return uri
    }
}