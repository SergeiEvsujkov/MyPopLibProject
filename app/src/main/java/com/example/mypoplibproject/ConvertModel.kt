package com.example.mypoplibproject

import android.graphics.Bitmap
import android.net.Uri


class ConvertModel {

    var bitmap: Bitmap? = null

    fun setImage(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    var bitmapLink: String = ""


}