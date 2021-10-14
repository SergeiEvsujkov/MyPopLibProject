package com.example.mypoplibproject


import android.content.Context
import android.graphics.Bitmap

import android.net.Uri
import android.os.Environment

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainPresenter(val model: ConvertModel, private val context: Context) :
    MvpPresenter<MainView>() {

    fun convertOperation(): Boolean {

        return if (model.bitmap != null) {
            bitmapToFile(model.bitmap!!, context)
            true
        } else false
    }

    fun completable() = Completable.create { emitter ->
        val result = convertOperation()
        if (result) {
            emitter.onComplete()
        } else {
            emitter.onError(RuntimeException("Не выполнено"))
        }
    }


    private val TAG = "RxJava"

    /** Выполнение «потребления» */
    fun execute() {
        completable()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Выполнено")
            }, {
                Log.e(TAG, "Ошибка потока", it)
            })
    }

    fun bitmapToFile(bitmap: Bitmap, context: Context): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null









        return try {
            file = File(
                (Environment.getExternalStorageDirectory()).toString() + "/PNG.png"
            )
            file.createNewFile()


            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bitmapdata = bos.toByteArray()


            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
        }
    }


    fun convertImage() {

        Observable.just(model.bitmapLink)
        execute()
    }


    fun setImage(bitmap: Bitmap) {
        model.setImage(bitmap)
    }

    fun getImage(): Bitmap? {
        return model.bitmap
    }

    override fun onFirstViewAttach() {
        viewState.setImage()
    }

    fun setBitmapLink(iconUri: String) {
        model.bitmapLink = iconUri
    }

}