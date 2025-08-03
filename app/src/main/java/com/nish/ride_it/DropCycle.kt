package com.nish.ride_it

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DropCycle : AppCompatActivity() {

    private lateinit var cycleImageCard: CardView
    private lateinit var frameLayout: FrameLayout
    private lateinit var imageView: ImageView

    private lateinit var currentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_cycle)  // Correct layout for DropCycleActivity

        // Initialize views
        cycleImageCard = findViewById(R.id.cycleImageCard)  // Make sure this ID matches the XML layout
        frameLayout = findViewById(R.id.d_framelayout)  // Ensure correct FrameLayout ID for displaying the image

        // Create an ImageView to show the captured photo
        imageView = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        frameLayout.addView(imageView)

        // Set click listener on CardView to open the camera
        cycleImageCard.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }
    }

    // Check if the app has camera permission
    private fun checkCameraPermissionAndOpenCamera() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            // Open the camera if permission is granted
            dispatchTakePictureIntent()
        }
    }

    // Dispatch the intent to open the camera and capture a picture
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                // Create the file to store the image
                createImageFile()
            } catch (ex: IOException) {
                // Handle error while creating the file
                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show()
                null
            }

            photoFile?.also {
                // Get URI for the file
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider",  // Ensure correct FileProvider authority
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Create a file to store the captured image
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",  // Prefix for file name
            ".jpg",  // File extension
            storageDir  // Directory to store the file
        ).apply {
            currentPhotoPath = absolutePath  // Store the file path to access later
        }
    }

    // Handle the result of the image capture
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Decode the captured image and display it in the ImageView
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            imageView.setImageBitmap(bitmap)
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, open the camera
            dispatchTakePictureIntent()
        } else {
            // If permission is denied, show a message
            Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show()
        }
    }
}
