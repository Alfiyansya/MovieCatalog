package com.achmadalfiansyah.moviecatalog.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.core.domain.model.User
import com.achmadalfiansyah.moviecatalog.databinding.FragmentProfileBinding
import com.achmadalfiansyah.moviecatalog.ui.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class ProfileFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST: Int = 2020

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var imgUri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val currentUser = auth.currentUser
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_ids))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.etFullName.setText(user!!.userName)
                if (user.userPhoneNumber == "null") {
                    binding.etPhoneNumber.setText("")
                } else {
                    binding.etPhoneNumber.setText(user.userPhoneNumber)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
        with(binding) {
            if (currentUser != null){
                if (currentUser.photoUrl == null) {
                    binding.ivProfile.setImageResource(R.drawable.profile_image)
                } else {
                    Glide.with(this@ProfileFragment).load(currentUser.photoUrl)
                        .into(binding.ivProfile)
                }
            }
            etEmail.setText(firebaseUser.email)
            ivProfile.setOnClickListener {
                chooseImage()
            }
            btnSave.setOnClickListener {
                saveProfileToDatabase()
                binding.progressBar.visibility = View.VISIBLE
            }
            verifiedEmailHere.setOnClickListener {
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Verification Email has been sent",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            tvChangePassword.setOnClickListener {
                val actionChangePassword = ProfileFragmentDirections.actionChangePassword()
                Navigation.findNavController(it).navigate(actionChangePassword)
            }
            etEmail.setOnClickListener {
                val actionUpdateEmail = ProfileFragmentDirections.actionUpdateEmail()
                Navigation.findNavController(it).navigate(actionUpdateEmail)
            }
            if (firebaseUser.isEmailVerified) {
                emailUnverified.visibility = View.VISIBLE
                emailUnverified.text = getString(R.string.email_verified)
                verifiedEmailHere.visibility = View.GONE
            } else {
                verifiedEmailHere.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    private fun setUpToolbar() {
        binding.profileToolbar.setOnMenuItemClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data!!.data
            try {
                filePath?.let {
                    if(Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            filePath
                        )
                        binding.progressBar.visibility = View.VISIBLE
                        uploadBitmap(bitmap)
                    } else {
                        val source = ImageDecoder.createSource(activity?.contentResolver!!,
                            filePath!!
                        )
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        binding.progressBar.visibility = View.VISIBLE
                        uploadBitmap(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadBitmap(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img/" + UUID.randomUUID().toString())

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let { uri ->
                            imgUri = uri
                            binding.progressBar.visibility = View.GONE
                            binding.ivProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun saveProfileToDatabase() {
        val user = auth.currentUser
        val name = binding.etFullName.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val uri =
            Uri.parse("android.resource://com.achmadalfiansyah.moviecatalog/drawable/profile_image")
        val image = when {
            ::imgUri.isInitialized -> imgUri
            user?.photoUrl == null -> uri
            else -> user.photoUrl
        }
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["userName"] = name
        hashMap["userPhoneNumber"] = phoneNumber
        hashMap["profileImage"] = filePath.toString()
        databaseReference.updateChildren(hashMap as Map<String, Any>)
        binding.progressBar.visibility = View.GONE
        updateProfile(name, image)
    }

    private fun updateProfile(name: String, image: Uri?) {
        UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(image)
            .build().also {
                auth.currentUser?.updateProfile(it)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, "Profile Updated", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, "${task.exception?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                googleSignInClient.signOut().addOnCompleteListener {
                    Intent(activity, LoginActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}