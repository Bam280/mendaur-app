package com.dicoding.abednego.mendaurid.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityMapsBinding
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dicoding.abednego.mendaurid.utils.Result
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var positionLat: Double = 0.0
    private var positionLon: Double = 0.0
    private val mapViewModel: MapsViewModel by viewModels {
        ViewModelFactory()
    }
    private var radius = 5000
    private var keyword: String  = KEYWORD
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_tempat_pengepul_terdekat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        progressBar = binding.progressBar
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
        setMapStyle()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    positionLat = location.latitude
                    positionLon = location.longitude
                    onLocationReceived()
                }
            }
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun onLocationReceived() {
        getNearbyBankSampah()
    }

    private fun getNearbyBankSampah() {
        progressBar.visibility = View.VISIBLE
        val url = ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$positionLat,$positionLon&radius=$radius&keyword=$keyword&key="+resources.getString(R.string.API_KEY))
        mapViewModel.getNearbyBankSampah(url).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    val mapsResponse = result.data
                    if (mapsResponse.results != null) {
                        mMap.clear()
                        for (mapItem in mapsResponse.results) {
                            val geometry = mapItem?.geometry
                            if (geometry?.location != null) {
                                val latLng = LatLng(geometry.location.lat ?: 0.0, geometry.location.lng ?: 0.0)
                                val markerOptions = MarkerOptions()
                                    .position(latLng).title(mapItem.name)
                                    .snippet("${getString(R.string.rating)} ${mapItem.rating.toString()}")
                                mMap.addMarker(markerOptions)
                            }
                            else{
                                Toast.makeText(this, getString(R.string.bank_sampah_tidak_ditemukan), Toast.LENGTH_SHORT).show()
                            }
                        }
                        val builder = LatLngBounds.Builder()
                        for (mapItem in mapsResponse.results) {
                            val geometry = mapItem?.geometry
                            if (geometry?.location != null) {
                                val latLng = LatLng(geometry.location.lat ?: 0.0, geometry.location.lng ?: 0.0)
                                builder.include(latLng)
                            }
                        }
                        val bounds = builder.build()
                        val padding = 50
                        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                        mMap.animateCamera(cameraUpdate)
                    } else if (mapsResponse.status == ZEROSTATUS ){
                        Toast.makeText(this, getString(R.string.bank_sampah_tidak_ditemukan), Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.terjadi_kesalahan), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val TAG = "MapsActivity"
        private const val KEYWORD = "Bank Sampah"
        private const val ZEROSTATUS = "ZERO_RESULTS"
    }
}