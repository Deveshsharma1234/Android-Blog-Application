package com.sharmasandsons.blogapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sharmasandsons.blogapplication.databinding.ActivitySignInAndRegisterationBinding

class SignInAndRegisterationActivity : AppCompatActivity() {

    private val binding : ActivitySignInAndRegisterationBinding by lazy {
        ActivitySignInAndRegisterationBinding.inflate(layoutInflater);
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val action = intent.getStringExtra("action")
        // adjust visiblity for login

        if(action =="login"){
            binding.editTextTextEmailAddress.visibility = View.VISIBLE
            binding.editTextNumberPassword.visibility = View.VISIBLE
            binding.loginButton.visibility= View.VISIBLE

            binding.newHere.isEnabled = true
            binding.newHere.alpha = 0.5f

            binding.registerButton.isEnabled =false
            binding.registerButton.alpha = 0.5f

            binding.enterNameR.visibility = View.GONE
            binding.enterPassworR.visibility = View.GONE
            binding.enterEmailR.visibility = View.GONE
            binding.cardView.visibility = View.GONE


        }else if(action=="register"){
            binding.loginButton.isEnabled = false
            binding.loginButton.alpha = 0.5f
            binding.newHere.text = "Already Registerd?\n       Login Here..." ;



        }
    }
}