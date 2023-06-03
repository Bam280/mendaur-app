package com.dicoding.abednego.mendaurid.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
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
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
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
        callbackManager = CallbackManager.Factory.create()

        binding.btnLoginGoogle.setOnClickListener {
            googleLogIn()
        }
        binding.btnLoginFacebook.setOnClickListener{
            facebookLogIn()
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
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {

                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun facebookLogIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:${result.accessToken}")
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
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
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {
                    progressBar.visibility = View.GONE
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Gagal untuk login", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        progressBar.visibility = View.VISIBLE
        Log.d(TAG, "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                } else {
                    progressBar.visibility = View.GONE
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Gagal untuk login", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
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
        val btnFacebookButton = ObjectAnimator.ofFloat(binding.btnLoginFacebook, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                welcome,
                jargon,
                masuk,
                btnGoogleButton,
                btnFacebookButton
            )
            startDelay = 500
        }.start()
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}