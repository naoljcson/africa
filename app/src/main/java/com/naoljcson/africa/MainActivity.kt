package com.naoljcson.africa

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val db = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Build.VERSION.SDK_INT >= Q)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)
        // Add a callback that's called when the splash screen is animating to
        // the app content.
        if (Build.VERSION.SDK_INT >= S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }


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

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}