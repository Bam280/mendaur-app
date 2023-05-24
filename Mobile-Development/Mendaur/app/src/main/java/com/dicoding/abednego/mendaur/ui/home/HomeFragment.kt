package com.dicoding.abednego.mendaur.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaur.R
import com.dicoding.abednego.mendaur.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

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
    }

    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Selamat pagi"
            in 12..15 -> "Selamat siang"
            else -> "Selamat malam"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}