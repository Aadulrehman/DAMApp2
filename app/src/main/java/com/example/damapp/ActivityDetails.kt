package com.example.damapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damapp.databinding.ActivityAddScreenBinding
import com.example.damapp.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class ActivityDetails : AppCompatActivity() {
    private lateinit var viewBinder:ActivityDetailsBinding
    private lateinit var mFirebaseAuth : FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(viewBinder.root)
        mFirebaseAuth = Firebase.auth
        mFirestore= FirebaseFirestore.getInstance()

        viewBinder.SignOutOption.setOnClickListener {
            logOutUser()
        }
        viewBinder.addbtnforact.setOnClickListener {
            startActivity(Intent(this,AddActivityScreen::class.java))
        }
    }
    override fun onStart()
    {
        super.onStart()
        getAllDocuments()
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

    private fun getAllDocuments()
    {
        try
        {
            mFirestore.collection("Details")
                .get()
                .addOnSuccessListener { docs->
                    if(docs.isEmpty)
                    {
                        Toast.makeText(applicationContext, "DataSet is empty", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        var listofdets = ArrayList<DataClass>()
                        for (doc in docs)
                        {
                            val listdet = doc.toObject<DataClass>()

                            listofdets.add(listdet)
                        }

                        viewBinder.fbWorkingRv .layoutManager = LinearLayoutManager(applicationContext)
                        viewBinder.fbWorkingRv.adapter = CustomRV(listofdets,applicationContext)
                    }

                }
                .addOnFailureListener {ex->
                    Toast.makeText(applicationContext, "Exception while getting data:${ex.message}", Toast.LENGTH_SHORT).show();

                }
        }
        catch (ex: Exception)
        {
            Toast.makeText(applicationContext, "Exception:${ex.message}", Toast.LENGTH_SHORT).show();
        }
    }

}