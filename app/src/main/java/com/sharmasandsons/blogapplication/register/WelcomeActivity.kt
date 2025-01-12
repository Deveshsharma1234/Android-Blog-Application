package com.sharmasandsons.blogapplication.register

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.sharmasandsons.blogapplication.MainActivity
import com.sharmasandsons.blogapplication.R
import com.sharmasandsons.blogapplication.SignInAndRegisterationActivity
import com.sharmasandsons.blogapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

   private  val binding : ActivityWelcomeBinding by lazy {
       ActivityWelcomeBinding.inflate(layoutInflater)
   }
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance();
        auth.signOut()



        binding.loginButton.setOnClickListener {
            val intent = Intent(this, SignInAndRegisterationActivity::class.java)
            intent.putExtra("action","login")
            startActivity(intent)
            finish()
        }


        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignInAndRegisterationActivity::class.java)
            intent.putExtra("action","register")
            startActivity(intent)
            finish()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("WelcomeActivity" , "User is already signed in : ${currentUser.email}")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            Log.d("Welcome Activiry", "User is not signeIn")
        }
    }
}