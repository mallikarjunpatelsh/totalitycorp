package com.mallikarjun.totalitycorp.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.mallikarjun.totalitycorp.R
import com.mallikarjun.totalitycorp.camera.CameraActivity

class MainActivity : AppCompatActivity() {
    private lateinit var previewImageView: ImageView
    private lateinit var clickSelfie : Button
    private lateinit var galleryImage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindIds()
    }

    private fun bindIds() {
        this.previewImageView = findViewById(R.id.preview_view)
        this.clickSelfie = findViewById(R.id.click_selfie)
        this.clickSelfie.setOnClickListener {
            this.onClickSelfie(it)
        }
        this.galleryImage = findViewById(R.id.gallery_image)
        this.galleryImage.setOnClickListener {
            this.onGalleryImage(it)
        }
    }

    private fun onGalleryImage(it: View?) {
        val intent: Intent = Intent(this,CameraActivity::class.java)
        intent.putExtra("camera",false)
        startActivity(intent)
    }

    private fun onClickSelfie(it: View?) {
        val intent: Intent = Intent(this,CameraActivity::class.java)
        intent.putExtra("camera",true)
        startActivity(intent)
    }
}