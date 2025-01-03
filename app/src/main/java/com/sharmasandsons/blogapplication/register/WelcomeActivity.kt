package com.sharmasandsons.blogapplication.register

import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sharmasandsons.blogapplication.R
import com.sharmasandsons.blogapplication.SignInAndRegisterationActivity
import com.sharmasandsons.blogapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

   private  val binding : ActivityWelcomeBinding by lazy {
       ActivityWelcomeBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)



        binding.loginButton.setOnClickListener {
            val intent = Intent(this, SignInAndRegisterationActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignInAndRegisterationActivity::class.java)
            startActivity(intent)

        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}