package com.example.alibaba.ui.create

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.alibaba.R
import com.example.alibaba.ui.room.Alibaba
import com.example.alibaba.ui.room.AlibabaDao
import com.example.alibaba.ui.room.AppAlibabaDatabase
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : Fragment() {

    private var productImageView: ImageView? = null
    private val REQUEST_CODE = 1
    private var mCurrentPhotoPath: String? = null
    private var alibabaDao: AlibabaDao? = null
    private var nameProduct: String? = null
    private var descriptionProduct: String? = null
    private var stockProduct: String? = null
    private var imageProduct: ByteArray? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //declarar los componentes creados en el xml
        val saveButton: Button = view.findViewById(R.id.saveButton)
        productImageView = view.findViewById(R.id.productImageView)
        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val descriptionEditText: EditText = view.findViewById(R.id.descriptionEditText)
        val stockEditText: EditText = view.findViewById(R.id.stockEditText)

        //image capture
        productImageView!!.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                }
            } else {
                openCamera()
            }
        })


        nameProduct = nameEditText.text.toString()
        descriptionProduct = descriptionEditText.text.toString()
        stockProduct = stockEditText.text.toString()

        saveButton.setOnClickListener {
            try {
                imageProduct = imageViewToByte(productImageView!!)
                alibabaDao = AppAlibabaDatabase.getInstance(requireContext()).alibabaDao()
                alibabaDao!!.insertAlibaba(
                    Alibaba(
                        nameEditText.text.toString(),
                        imageViewToByte(productImageView!!)!!,
                        stockEditText.text.toString()!!,
                        descriptionEditText.text.toString()!!,
                        "no"
                    )
                )
                Toast.makeText(context, "Se agrego correctamente!", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(context, "Debe ingresar todos los campos!", Toast.LENGTH_SHORT).show()

            }

        }
    }
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    private fun openCamera() {
        //funcion que permite ABRIR LA CAMARA
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*Photo 1*/
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            productImageView!!.setImageURI(image_uri)
        }
    }


    private fun imageViewToByte(image: ImageView): ByteArray? {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        return stream.toByteArray()
    }
}