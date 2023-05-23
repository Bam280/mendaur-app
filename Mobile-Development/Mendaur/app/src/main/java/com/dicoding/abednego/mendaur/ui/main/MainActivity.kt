package com.dicoding.abednego.mendaur.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dicoding.abednego.mendaur.R
import com.dicoding.abednego.mendaur.databinding.ActivityMainBinding
import com.dicoding.abednego.mendaur.ui.home.HomeFragment
import com.dicoding.abednego.mendaur.ui.login.LoginActivity
import com.dicoding.abednego.mendaur.ui.profile.ProfileFragment
import com.dicoding.abednego.mendaur.ui.scan.ScanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()

        binding.bottomNavigationView.background = null

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment())
                }
                R.id.nav_profile -> {
                    openFragment(ProfileFragment())
                }
            }
            true
        }

        openFragment(HomeFragment())

        binding.floatBtnScan.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}