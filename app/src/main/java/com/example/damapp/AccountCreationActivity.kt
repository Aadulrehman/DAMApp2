package com.example.damapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.damapp.databinding.ActivityAccountCreationBinding
import com.example.damapp.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AccountCreationActivity : AppCompatActivity()
{
    private lateinit var mFirebaseAuth : FirebaseAuth
    private lateinit var viewBinder: ActivityAccountCreationBinding
    private lateinit var mFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityAccountCreationBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        mFirebaseAuth = Firebase.auth
        mFirestore= FirebaseFirestore.getInstance()
       viewBinder.AccountCreatedBtn.setOnClickListener {
           addDocWithClassModel()
           createAccount()

       }
    }

    private fun addDocWithClassModel() {
        try
        {
            if(viewBinder.FullNameTxt.text.isNotBlank()
                && viewBinder.DateOfBirthTxt .text.isNotBlank()
                && viewBinder.AddressTxt .text.isNotBlank()
            )
            {
                val currentEmp=User(viewBinder.FullNameTxt.text.toString(),
                    viewBinder.DateOfBirthTxt.text.toString(),
                    viewBinder.AddressTxt .text.toString()
                )

                mFirestore.collection("User")
                    .document()
                    .set(currentEmp)
                    .addOnSuccessListener {_:Void? ->
                        Toast.makeText(applicationContext,"Data Added",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { fireStoreEx->
                        Toast.makeText(applicationContext,"Ex:${fireStoreEx.message}",Toast.LENGTH_SHORT).show()
                    }

            }
            else
            {
                Toast.makeText(applicationContext,"Some fields are empty",Toast.LENGTH_SHORT).show()
            }
        }
        catch (ex:Exception)
        {
            Toast.makeText(applicationContext,ex.message,Toast.LENGTH_SHORT).show()
        }

    }

    private fun createAccount()
    {
        try
        {
            if(viewBinder.EmailTxt .text.isNotBlank()
                && viewBinder.PasswordTxt .text.isNotBlank())
            {
                mFirebaseAuth.createUserWithEmailAndPassword(
                    viewBinder.EmailTxt.text.toString(),
                    viewBinder.PasswordTxt.text.toString()
                )
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Toast.makeText(applicationContext, "User is registered", Toast.LENGTH_SHORT).show();
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            else
            {
                Toast.makeText(applicationContext, "Some fields are empty", Toast.LENGTH_SHORT).show();
            }
        }
        catch (ex: Exception)
        {
            Toast.makeText(applicationContext, "${ex.message}", Toast.LENGTH_SHORT).show()
        }

    }
}
