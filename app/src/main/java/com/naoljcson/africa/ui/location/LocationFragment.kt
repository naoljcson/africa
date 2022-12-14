package com.naoljcson.africa.ui.location

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.naoljcson.africa.data.model.Location
import com.naoljcson.africa.databinding.FragmentLocationBinding
import com.naoljcson.africa.utils.getCircledBitmap
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = LocationFragment()
        private val TAG = LocationFragment::class.qualifiedName
    }

    private val viewModel: LocationViewModel by viewModels()
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private val locations = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLocations()
        observeLocation()
        val mapFragment =
            childFragmentManager.findFragmentById(com.naoljcson.africa.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observeLocation() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldLocations.collect {
                if (it != null) {
                    locations.addAll(it)
                    val height = 100
                    val width = 100

                    locations.forEach { location ->
                        val smallMarker = location.mapIcon?.let { it1 ->
                            Bitmap.createScaledBitmap(
                                it1, width, height, false
                            )
                        }
                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(location.latitude, location.longitude))
                                .anchor(0.5f, 1f)
                                .icon(smallMarker?.let { it1 ->
                                    BitmapDescriptorFactory.fromBitmap(
                                        it1.getCircledBitmap()
                                    )
                                })
                        )
                    }
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    com.naoljcson.africa.R.raw.style_json
                )
            )
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(6.600286, 16.4377599), 3.2f))
        mMap.uiSettings.isZoomControlsEnabled = true;
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}