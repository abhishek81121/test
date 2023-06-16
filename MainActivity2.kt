package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

lateinit var linear : LinearLayout
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        linear=findViewById(R.id.body)
        val intent = intent
        val path = intent.getStringExtra("path")



        val existingFiles = File(path).listFiles()

        if(existingFiles == null)
        {

        }


        for (F in existingFiles) {
            Log.i("hjdg",F.getName())
            val textView = TextView(this)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.gravity = Gravity.CENTER
            textView.text = F.getName()
            textView.height = 100
            textView.maxWidth


                textView.setOnClickListener {
                    if(F.isFile)
                    {
                        val intent = Intent(Intent.ACTION_VIEW)
                        val file = File(F.absolutePath)
                        val fileUri: Uri = Uri.fromFile(file)
                        val apkURI: Uri = FileProvider.getUriForFile(
                            applicationContext,
                            applicationContext
                                .getPackageName() + ".provider", F
                        )
                        intent.setDataAndType(apkURI, "*/*")
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                        val chooserIntent = Intent.createChooser(intent, "Open file with")

                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(chooserIntent)
                        } else {
                            // No app available to open the file
                            // Handle the error case or provide an appropriate message to the user
                        }

                    }
                    else {
                        val intent = Intent(applicationContext, MainActivity2::class.java)
                        val path: String = F.absolutePath
                        intent.putExtra("path", path)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(intent)
                    }

                }

                // Add TextView to LinearLayout
                linear.addView(textView)

        }

    }
}