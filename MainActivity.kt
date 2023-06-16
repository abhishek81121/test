package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(this, MainActivity2::class.java)
                val path=Environment.getExternalStorageDirectory().path
                intent.putExtra("path",path)
                startActivity(intent)

            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    lateinit var strgbtn : Button
    lateinit var text : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        strgbtn= findViewById(R.id.button2)
text=findViewById(R.id.textView3)

        text.isVisible=false
        text.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
        strgbtn.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
            {
                val intent = Intent(this, MainActivity2::class.java)
                val path=Environment.getExternalStorageDirectory().path
                intent.putExtra("path",path)
                startActivity(intent)
            }

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                    {

                        text.isVisible=true
                        strgbtn.isEnabled=false

                    }
                },
                5000 // value in milliseconds
            )


        }
    }


}
