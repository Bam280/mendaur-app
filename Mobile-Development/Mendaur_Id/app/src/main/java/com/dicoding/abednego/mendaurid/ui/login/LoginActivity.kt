package com.dicoding.abednego.mendaurid.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.ActivityLoginBinding
import com.dicoding.abednego.mendaurid.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressBar = binding.progressBar

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        binding.btnLoginGoogle.setOnClickListener {
            googleLogIn()
        }
        playAnimation()
    }

    private fun googleLogIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("account_id", account.id)
                editor.apply()
                val db = Firebase.firestore
                val data = hashMapOf(
                    UID to account.id,
                    NAME to account.displayName,
                    PHOTO_URL to account.photoUrl
                )

                val documentRef = db.collection("users").document(account.id.toString())
                documentRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, "Data already exists in the database")
                        } else {
                            documentRef.set(data, SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d(TAG, "Data added to the database")
                                    firebaseAuthWithGoogle(account.idToken!!)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Failed to add data to the database", e)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Failed to retrieve data from the database", e)
                    }
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, getString(R.string.gagal_login), Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        progressBar.visibility = View.VISIBLE
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, getString(R.string.berhasil_login), Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    progressBar.visibility = View.GONE
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, getString(R.string.gagal_login), Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogoMendaur, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val welcome = ObjectAnimator.ofFloat(binding.tvSelamatDatang, View.ALPHA, 1f).setDuration(500)
        val jargon = ObjectAnimator.ofFloat(binding.tvJargon, View.ALPHA, 1f).setDuration(500)
        val masuk = ObjectAnimator.ofFloat(binding.tvMasuk, View.ALPHA, 1f).setDuration(500)
        val btnGoogleButton = ObjectAnimator.ofFloat(binding.btnLoginGoogle, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                welcome,
                jargon,
                masuk,
                btnGoogleButton
            )
            startDelay = 500
        }.start()
    }

    companion object {
        private const val TAG = "LoginActivity"
        const val UID = "userid"
        const val NAME = "name"
        const val PHOTO_URL = "photourl"
    }
}