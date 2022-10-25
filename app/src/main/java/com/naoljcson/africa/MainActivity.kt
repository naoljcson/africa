package com.naoljcson.africa

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        setContentView(binding.root)
//        storageReference.child("buffalo-1.jpg")
//            .downloadUrl.addOnSuccessListener { uri ->
//                Log.d(TAG, "uri $uri")
//                Picasso.get().load(uri).into(binding.ivAnimal)
//            }.addOnFailureListener {
//                Log.d(TAG, "Failed to load ${it.message}")
//            }
//        db.collection("animals")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result) {
//                        Log.d(TAG, document.id + " => " + document.data)
//                    }
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.exception)
//                }
//            }
    }

}