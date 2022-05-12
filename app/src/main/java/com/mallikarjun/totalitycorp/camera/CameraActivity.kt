package com.mallikarjun.totalitycorp.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.mallikarjun.totalitycorp.R

class CameraActivity : AppCompatActivity() {
    private lateinit var bitmapOfImage: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selection = intent.getBooleanExtra("camera",true)
        if (selection){
            camera()
        }else{
            gallery()
        }
    }

    private fun gallery() {
        if(checkPermission()){
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            galleryActivityLauncher.launch(intent)
        }else{
            cameraPermission.launch(arrayOf<String>(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
        }
    }

    private fun camera() {
        if (checkPermission()){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraActivityLauncher.launch(intent)
        }else{
            cameraPermission.launch(arrayOf<String>(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
        }
    }

    private fun checkPermission() : Boolean{
        val cameraPermission:Boolean = ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val readStoragePermission:Boolean = ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val writeStoragePermission:Boolean = ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        return cameraPermission && readStoragePermission && writeStoragePermission
    }

    val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        var granted = true
        for ((k,v) in it){
            if (!v) {
                granted = false
                break
            }
        }
        if (granted)
            camera()
        else
            Toast.makeText(applicationContext,"Allow Permission",Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            setContentView(R.layout.activity_camera)
            val data = it.data
            val image = data?.extras?.get("data") as Bitmap
            var previewView = findViewById<ImageView>(R.id.preview_view)
            previewView.setImageBitmap(image)
            bitmapOfImage = image
            findViewById<Button>(R.id.rotate_pic).setOnClickListener{
                val matrix = Matrix()
                matrix.postScale(1.toFloat(), 1.toFloat())
                matrix.postRotate(90f)
//                val bitmap: Bitmap = Bitmap.createBitmap(previewView.getWidth(), previewView.getHeight(), Bitmap.Config.RGB_565);
                val bitmap2 = Bitmap.createBitmap(
                    bitmapOfImage, 0, 0, bitmapOfImage.width,
                    bitmapOfImage.height, matrix, false
                )
                previewView.setImageBitmap(bitmap2)
                bitmapOfImage = bitmap2
            }
        }
    }

    val galleryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }

}