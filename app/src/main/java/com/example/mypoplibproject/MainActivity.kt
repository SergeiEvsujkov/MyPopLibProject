package com.example.mypoplibproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.mypoplibproject.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity

import moxy.ktx.moxyPresenter
import java.io.FileNotFoundException
import java.io.InputStream

private const val REQUEST_ICON = 2
private const val REQUEST_EXTERNAL_STORAGE = 101

class MainActivity : MvpAppCompatActivity(), MainView {


    private var _vb: ActivityMainBinding? = null
    private val presenter by moxyPresenter { MainPresenter(ConvertModel(), applicationContext) }
    private val vb
        get() = _vb!!

    private var iconUri: Uri = Uri.EMPTY
    private lateinit var bitmap: Bitmap

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val listenerSet = View.OnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            //Тип получаемых объектов - image:
            photoPickerIntent.type = "image/*"
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, REQUEST_ICON)
        }

        val listenerConvert = View.OnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE
            )
            presenter.convertImage()
        }

        vb.setImageButton.setOnClickListener(listenerSet)
        vb.convertPngButton.setOnClickListener(listenerConvert)

    }

    override fun setImage() {
        vb.image.setImageBitmap(
            presenter.getImage()
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        imageReturnedIntent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        when (requestCode) {
            REQUEST_ICON ->
                if (resultCode == Activity.RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        val imageUri = imageReturnedIntent?.data
                        iconUri = imageUri!!
                        val imageStream: InputStream? =
                            imageUri.let { this@MainActivity.contentResolver?.openInputStream(it) }


                        var selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
                        presenter.setImage(selectedImage)
                        val propBitmap: Double =
                            (selectedImage.width.toDouble() / selectedImage.height.toDouble())
                        if (selectedImage.width >= selectedImage.height) {
                            selectedImage = Bitmap.createScaledBitmap(
                                selectedImage,
                                300,
                                (300 / propBitmap).toInt(),
                                false
                            )
                        } else {
                            selectedImage =
                                Bitmap.createScaledBitmap(
                                    selectedImage,
                                    (300 * propBitmap).toInt(),
                                    300,
                                    false
                                )
                        }
                        bitmap = selectedImage
                        vb.image.setImageBitmap(selectedImage)

                        iconUri.path?.let { presenter.setBitmapLink(it) }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }

        }


    }


}