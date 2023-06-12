package com.dicoding.abednego.mendaurid.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.adapter.HomeArticleAdapter
import com.dicoding.abednego.mendaurid.adapter.JenisSampahAdapter
import com.dicoding.abednego.mendaurid.data.item.DataJenisSampah
import com.dicoding.abednego.mendaurid.databinding.FragmentHomeBinding
import com.dicoding.abednego.mendaurid.ui.daftarartikel.DaftarArtikelActivity
import com.dicoding.abednego.mendaurid.ui.maps.MapsActivity
import com.dicoding.abednego.mendaurid.utils.Result
import com.dicoding.abednego.mendaurid.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val photoUrl = currentUser?.photoUrl
        val displayName = currentUser?.displayName
        val greeting = getString(R.string.tv_selamat_nama, getGreeting(), displayName)

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivProfile)

        binding.tvSelamatNama.text = greeting

        progressBar = binding.progressBar

        binding.tvLihatSemua.setOnClickListener{
            val intent = Intent(context, DaftarArtikelActivity::class.java)
            startActivity(intent)
        }

        binding.ivBanner.setOnClickListener{
            checkLocation()
        }

        val jenisAdapter = JenisSampahAdapter(DataJenisSampah.listJenisSampah, requireContext())
        binding.rvJenis.adapter = jenisAdapter
        binding.rvJenis.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        getArticle()
    }

    private fun getArticle() {
        progressBar.visibility = View.VISIBLE
        homeViewModel.getArticles().observe(viewLifecycleOwner){ result ->
            when (result) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    val data = result.data
                    if (data.articles?.isNotEmpty() == true) {
                        val adapter = HomeArticleAdapter(mutableListOf(), requireContext())
                        adapter.setData(data.articles)
                        binding.rvHomeArticle.adapter = adapter
                    } else {
                        binding.tvArtikelTidakDitemukan.visibility = View.VISIBLE
                    }
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    val error = result.error
                    if (error.contains("timeout")) {
                        Toast.makeText(
                            context,
                            "Error: Timeout",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.tvArtikelTidakDitemukan.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> getString(R.string.selamat_pagi)
            in 12..15 -> getString(R.string.selamat_siang)
            in 16..18 -> getString(R.string.selamat_sore)
            else -> getString(R.string.selamat_malam)
        }
    }

    private fun checkLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}