package com.sharmasandsons.blogapplication

import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sharmasandsons.blogapplication.databinding.ActivitySignInAndRegisterationBinding
import  com.sharmasandsons.blogapplication.Model.UserData
import com.sharmasandsons.blogapplication.register.WelcomeActivity

class SignInAndRegisterationActivity : AppCompatActivity() {

    private val binding: ActivitySignInAndRegisterationBinding by lazy {
        ActivitySignInAndRegisterationBinding.inflate(layoutInflater);
    }

    private lateinit var fireBaseAuth: FirebaseAuth //firebase variable
    private lateinit var firebaseDatabase: FirebaseDatabase//Firebase database to store users
    private var PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null;
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        fireBaseAuth =
            FirebaseAuth.getInstance()// Inilisation of firebase auth , we can create users using this
        firebaseDatabase =
            FirebaseDatabase.getInstance("https://blogapplication-7cab8-default-rtdb.asia-southeast1.firebasedatabase.app")

//For Visiblity of fields
        val action = intent.getStringExtra("action")
        // adjust visiblity for login
// for visiblity of field at the time of login
        if (action == "login") {
            binding.loginEmail.visibility = View.VISIBLE
            binding.loginPassword.visibility = View.VISIBLE
            binding.loginButton.visibility = View.VISIBLE

            binding.newHere.isEnabled = true
            binding.newHere.alpha = 0.5f

            binding.registerButton.isEnabled = false
            binding.registerButton.alpha = 0.5f

            binding.enterNameR.visibility = View.GONE
            binding.enterPassworR.visibility = View.GONE
            binding.enterEmailR.visibility = View.GONE
            binding.cardView.visibility = View.GONE

            binding.loginButton.setOnClickListener {
                val loginEmail = binding.loginEmail.text.toString();
                val loginPassword = binding.loginPassword.text.toString();
                if (loginEmail.isEmpty() || loginPassword.isEmpty()) {
                    Toast.makeText(this, "Enter All the details requiered!", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    fireBaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Login Succesfull", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Login Unsucsesfull", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }


        } else if (action == "register") {// for visiblity of the fields at the time of register
            binding.loginButton.isEnabled = false
            binding.loginButton.alpha = 0.5f
            binding.newHere.text = "Already Registerd?\n       Login Here...";



            binding.registerButton.setOnClickListener {
                val registerName = binding.enterNameR.text.toString();
                val registerEmail = binding.enterEmailR.text.toString();
                val registerPasswrod = binding.enterPassworR.text.toString();
                if (registerName.isBlank() || registerEmail.isEmpty() || registerPasswrod.isEmpty()) {
                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                } else {
                    fireBaseAuth.createUserWithEmailAndPassword(registerEmail, registerPasswrod)
                        .addOnCompleteListener { Task ->
                            if (Task.isSuccessful) {
                                val user = fireBaseAuth.currentUser
                                fireBaseAuth.signOut()
                                user?.let {
                                    //save user data into realtime database
                                    val userReference = firebaseDatabase.getReference("users")
                                    val userId = user.uid
                                    val userData = UserData(registerName, registerEmail)

                                    userReference.child(userId).setValue(userData)
                                        .addOnSuccessListener {
                                            Log.d("Tag", "OnCreate : Data saved to database")
                                        }.addOnFailureListener { e ->
                                            Log.d("tag", "Erro on saving the data ${e.message}")

                                        }
                                    Toast.makeText(
                                        this, "User Registered Succesfull", Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, WelcomeActivity::class.java))
                                    finish()

                                }


                            } else {
                                Toast.makeText(
                                    this, "User registeratio failed!", Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    // Set on Click Listener for choose image
                    binding.cardView.setOnClickListener {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            Intent.createChooser(intent, "select image"), PICK_IMAGE_REQUEST
                        )


                    }

                }

            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(this).load(imageUri).apply(RequestOptions.circleCropTransform())
                .into(binding.userImage)
        }
    }
}