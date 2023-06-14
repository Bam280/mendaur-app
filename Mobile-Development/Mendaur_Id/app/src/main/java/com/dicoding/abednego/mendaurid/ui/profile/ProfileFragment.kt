package com.dicoding.abednego.mendaurid.ui.profile

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dicoding.abednego.mendaurid.R
import com.dicoding.abednego.mendaurid.databinding.FragmentProfileBinding
import com.dicoding.abednego.mendaurid.ui.about.AboutActivity
import com.dicoding.abednego.mendaurid.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val photoUrl = currentUser?.photoUrl
        val userName = currentUser?.displayName
        val emailUser = currentUser?.email

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivProfile)

        binding.tvUsername.text = userName
        binding.tvEmail.text = emailUser
        binding.btnSignOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.confirmation))
                .setMessage(getString(R.string.are_you_sure_log_out))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    signOut()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                .show()
        }
        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.btnAbout.setOnClickListener{
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }
        binding.btnHelp.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_mendaur)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body))
            }

            val packageManager = requireContext().packageManager
            val activities = packageManager.queryIntentActivities(emailIntent, 0)

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.send_email))
                .setMessage(getString(R.string.contact_mendaur_team))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    if (activities.isNotEmpty()) {
                        try {
                            startActivity(emailIntent)
                        } catch (ex: ActivityNotFoundException) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.no_gmail_app),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.no_gmail_app),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}