package com.naoljcson.africa.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage():FirebaseStorage = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference = firebaseStorage.reference
}