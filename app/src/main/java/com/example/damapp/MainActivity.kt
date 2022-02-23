package com.example.damapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.damapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinder.root)


        connectXML()
        connectXMl2()
    }
    private fun connectXML()
    {
        try
        {
            viewBinder.LoginOption.setOnClickListener {
                moveToS2()
            }
        }
        catch(ex:Exception)
        {
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }
    private fun connectXMl2()
    {
        try
        {

           viewBinder.CreateAcc.setOnClickListener {
                moveToS1()
            }
        }
        catch(ex:Exception)
        {
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun moveToS1()
    {
        try
        {
            startActivity(Intent(this,AccountCreationActivity::class.java))
        }
        catch(ex:Exception)
        {
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }
    private fun moveToS2()
    {
        try
        {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        catch(ex:Exception)
        {
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }
    }

}