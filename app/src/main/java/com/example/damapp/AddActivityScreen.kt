package com.example.damapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damapp.databinding.ActivityAddScreenBinding
import com.example.damapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class AddActivityScreen : AppCompatActivity() {
    private lateinit var viewBinder:ActivityAddScreenBinding
    private lateinit var mFirebaseAuth : FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore
    val REQUEST_CODE=100
    private lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityAddScreenBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        mFirebaseAuth = Firebase.auth
        mFirestore= FirebaseFirestore.getInstance()

        viewBinder.Signout.setOnClickListener {
            logOutUser()
        }
        viewBinder.AddActBtn.setOnClickListener {
            Toast.makeText(applicationContext,"Button",Toast.LENGTH_SHORT).show()
            addAct()
        }
        viewBinder.ChoosePicBtn.setOnClickListener {
            imageadd()
        }
    }
    private fun imageadd() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            uri = data?.data!!
            // val uri_to_string = uri.toString()
            //val mystring="Hello"
            //var urii : Uri
            //urii = Uri.parse(uri_to_string);
            //binding.im.setImageURI(urii)
        }
    }

   
    private fun addAct() {
        try
        {
            if(viewBinder.ATitleTxt.text.isNotBlank()
                && viewBinder.ADetailsTxt .text.isNotBlank()
            )
            {
                val classDets =DataClass(viewBinder.ATitleTxt.text.toString(),
                   viewBinder.ADetailsTxt.text.toString(),uri.toString()
                )

                mFirestore.collection("Details")
                    .document()
                    .set(classDets)
                    .addOnSuccessListener {_:Void? ->
                        Toast.makeText(applicationContext,"Data Added",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,ActivityDetails::class.java))
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
    private fun logOutUser() {
        try
        {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser!=null)
            {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(applicationContext, "User is logged out", Toast.LENGTH_SHORT).show();
                startActivity(Intent(this,MainActivity::class.java))
            }
            else
            {
                Toast.makeText(applicationContext, "No User is logged in yet", Toast.LENGTH_SHORT).show();
            }
        }
        catch (ex: Exception)
        {
            Toast.makeText(applicationContext, "${ex.message}", Toast.LENGTH_SHORT).show();
        }
    }




}