package com.example.myclothesselectionapp.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myclothesselectionapp.Adapters.ViewPagerAdapter
import com.example.myclothesselectionapp.Database.DBHelper
import com.example.myclothesselectionapp.Models.Maindatamodel
import com.example.myclothesselectionapp.R
import com.example.myclothesselectionapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    lateinit var modeldm: Maindatamodel;
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageListtop: List<Int>
    lateinit var imageListbottom: List<Int>
    var topinstring: String? =null
    var bottominstring : String? =null

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.floatingAddTop.setOnClickListener(View.OnClickListener {
            openDialog("Add Top", 1)
        })

        binding.floatingAddBottom.setOnClickListener(View.OnClickListener {
            openDialog("Add Bottom", 0)
        })
        binding.floatingDownload.setOnClickListener(View.OnClickListener {
           if (topinstring.equals(null)&& bottominstring.equals(null)){
               val snackBar = Snackbar.make(
                   binding.root, "no file found to save",
                   Snackbar.LENGTH_LONG
               ).setAction("Action", null)
               snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
               val snackBarView = snackBar.view
               snackBarView.setBackgroundColor(Color.WHITE)
               val textView =
                   snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
               textView.setTextColor(Color.BLUE)
               snackBar.show()
           }else if (topinstring.equals(null) || bottominstring.equals(null)) {
                val snackBar = Snackbar.make(
                    binding.root, "Please upload both the files and then save as set",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(Color.WHITE)
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.BLUE)
                snackBar.show()
            } else {
                  save(topinstring, bottominstring)

            }
        })

        binding.floatingFav.setOnClickListener(View.OnClickListener {
            if (topinstring.equals(null)&& bottominstring.equals(null)){
                val snackBar = Snackbar.make(
                    binding.root, "no file found to set favorite",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(Color.WHITE)
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.BLUE)
                snackBar.show()
            }else if (topinstring.equals(null) || bottominstring.equals(null)) {
                val snackBar = Snackbar.make(
                    binding.root, "Please upload both the files and then set as favorite",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(Color.WHITE)
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.BLUE)
                snackBar.show()
            } else {
                savefav(topinstring, bottominstring)

            }
        })
    }

     fun savefav(topinstring: String?, bottominstring: String?) {
        val db:DBHelper = DBHelper(this, null)
        if (topinstring != null) {
            if (bottominstring != null) {
                db.addfav(topinstring, bottominstring)
                binding.floatingFav.setImageDrawable(resources.getDrawable(R.drawable.fav))
                Toast.makeText(this, "set as Favorite", Toast.LENGTH_SHORT).show()

            }
        }
        else{
            Toast.makeText(this,"try Again",Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDialog(text: String, code: Int) {
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(20, 30, 20, 30)
        textView.textSize = 20f
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(resources.getColor(R.color.purple_700))
        textView.setTextColor(Color.WHITE)

        val builder = AlertDialog.Builder(this)
        builder.setCustomTitle(textView)

        builder.setPositiveButton("Camera") { dialogInterface, which ->
            openCamera(text, code)
        }

        builder.setNegativeButton("Gallery") { dialogInterface, which ->
            openGallery(text)
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    fun save(imageUritop: String?, imageUribottom: String?) {
        val db:DBHelper = DBHelper(this, null)
        // val values = ContentValues()
        // values.put("a", imageUri)
        if (imageUritop != null) {
            if (imageUribottom != null) {
                db.add(imageUritop, imageUribottom)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            }
        }
        else{
            Toast.makeText(this,"try Again",Toast.LENGTH_SHORT).show()
        }
    }

//    operator fun get(view: View?) {
//        val c: Cursor = db.rawQuery("select * from imageTb", null)
//        if (c.moveToNext()) {
//            val image: ByteArray = c.getBlob(0)
//            val bmp = BitmapFactory.decodeByteArray(image, 0, image.size)
//            imageView.setImageBitmap(bmp)
//            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun openGallery(chooseclothes: String) {
        if (chooseclothes.equals("Add Top")) {
            val gallerytop =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallerytop, 10)
        } else if (chooseclothes.equals("Add Bottom")) {
            val gallerybottom =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallerybottom, 20)
        }
    }

    private fun openCamera(chooseclothes: String, camerareqcode: Int) {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                camerareqcode
            )
            if (chooseclothes.equals("Add Top")) {
                val CameraIntentTop = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(CameraIntentTop, camerareqcode)
            } else if (chooseclothes.equals("Add Bottom")) {
                val CameraIntentbottom = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(CameraIntentbottom, camerareqcode)
            }
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageUri = data?.data
        if (resultCode == RESULT_OK && requestCode == 10) {
            val top: ImageView = findViewById(R.id.IV_dress_top)
            topinstring = imageUri.toString()
            top.setImageURI(imageUri)
            //  savedata(topinbytes)
        } else if (resultCode == RESULT_OK && requestCode == 20) {
            val bottom: ImageView = findViewById(R.id.IV_dress_bottom)
            bottom.setImageURI(imageUri)
            bottominstring = imageUri.toString()
              // savedata(bottominbytes)
        }
            if (requestCode == 1) {
                val photo = data?.extras?.get("data") as Bitmap
                val top: ImageView = findViewById(R.id.IV_dress_top)
                topinstring = photo.toString()
                top.setImageBitmap(data.extras?.get("data") as Bitmap)
                //   savedata(topinbytes)
            } else if (requestCode == 0) {
                val photo = data?.extras?.get("data") as Bitmap
                val bottom: ImageView = findViewById(R.id.IV_dress_bottom)
                bottominstring = photo.toString()
                Log.d("check", ""+data.data)
                bottom.setImageBitmap(data.extras?.get("data") as Bitmap)
                // savedata(bottominbytes)
            }


        }

//        fun savedata(imageUri: Byte?) {
//            if (imageUri != null) {
//                binding.floatingDownload.setOnClickListener(View.OnClickListener {
//                    save(topinbytes, bottominbytes)
//
//                })
//            } else {
//                val snackBar = Snackbar.make(
//                    binding.root, "no file found to save",
//                    Snackbar.LENGTH_LONG
//                ).setAction("Action", null)
//                snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
//                val snackBarView = snackBar.view
//                snackBarView.setBackgroundColor(Color.WHITE)
//                val textView =
//                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
//                textView.setTextColor(Color.BLUE)
//                snackBar.show()
//            }
//        }


    }







