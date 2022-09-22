package com.example.task_3

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.task_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    private lateinit var binding: ActivityMainBinding

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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,1)

    }


    private fun galleryChooseImage(){
        val chooseIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(chooseIntent, 2)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode ,data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if(data != null){
                val imageBitmap: Bitmap? = data.getParcelableExtra("data")
                binding.imageview.setImageBitmap(imageBitmap)
            }

        }else if(requestCode == 2 && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.data
                binding.imageview.setImageURI(imageUri)
            }

        }

    }


}