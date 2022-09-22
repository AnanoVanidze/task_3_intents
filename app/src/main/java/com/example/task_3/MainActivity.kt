package com.example.task_3

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.task_3.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var imageUri: Uri? = null

    private val takePictureResult = registerForActivityResult(ActivityResultContracts.TakePicture()){
            if(it){
                imageUri?.let { uri ->
                    binding.imageview.setImageURI(uri)
                }
            }
        }


    private val choosePictureResult = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
            binding.imageview.setImageURI(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.takePictureBtn.setOnClickListener {
            takePicture()
        }
        binding.choosePictureBtn.setOnClickListener {
           galleryChooseImage()
        }

    }


    private fun takePicture() {

        getTmpFileUri().let { uri ->
            imageUri = uri
            takePictureResult.launch(uri)

        }
    }


    private fun galleryChooseImage(){
        choosePictureResult.launch("image/*")
    }



    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }





}