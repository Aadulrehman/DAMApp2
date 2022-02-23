package com.example.damapp

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.damapp.databinding.ActivityLoginBinding
import com.example.damapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var mFirebaseAuth : FirebaseAuth
    private lateinit var viewBinder: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        mFirebaseAuth = Firebase.auth

        viewBinder.LoginButton.setOnClickListener {
            loginAccount()
        }

    }
    private fun loginAccount()
    {
        try
        {
            if(
                viewBinder.EmailTxt .text.isNotBlank()
                && viewBinder.PasswordTxt.text.isNotBlank()
            )
            {
                mFirebaseAuth.signInWithEmailAndPassword(
                   viewBinder.EmailTxt.text.toString(),
                   viewBinder.PasswordTxt.text.toString()
                )
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Toast.makeText(applicationContext, "User is logged in", Toast.LENGTH_SHORT).show();
                            startActivity(Intent(this,ActivityDetails::class.java))
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            else
            {
                Toast.makeText(applicationContext, "Please fill the empty fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch(ex:Exception)
        {
            Toast.makeText(this,"Logical Error : ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }
}