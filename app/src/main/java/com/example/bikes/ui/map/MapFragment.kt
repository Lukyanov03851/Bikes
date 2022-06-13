package com.example.bikes.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.bikes.R
import com.example.bikes.databinding.FragmentMapBinding
import com.example.bikes.utils.bitmapDescriptorFromVector
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val TAG = "MapFragment"
        const val MAP_ZOOM_LEVEL = 15
    }

    private var mBinding: FragmentMapBinding? = null
    private val args: MapFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapBinding.inflate(inflater, container, false)
        context ?: return binding.root
        mBinding = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        setupMapFragment()
    }

    private fun fillData() {
        mBinding?.locationDetails?.tvTitle?.text = args.bikeStation.name
        mBinding?.locationDetails?.tvAvailableBikesValue?.text =
            args.bikeStation.availableBikes.toString()
        mBinding?.locationDetails?.tvAvailablePlacesValue?.text =
            args.bikeStation.availablePlaces.toString()
    }

    private fun setupMapFragment() {
        val fragment = SupportMapFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, fragment, SupportMapFragment::class.java.name)
            .commit()
        fragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isZoomControlsEnabled = true
        val position = LatLng(
            args.bikeStation.latitude ?: 0.0,
            args.bikeStation.longitude ?: 0.0
        )
        context?.let { c ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .icon(bitmapDescriptorFromVector(c, R.drawable.ic_bike))
            )
        }

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(position, MAP_ZOOM_LEVEL.toFloat())
        )

    }

}