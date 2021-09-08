package com.example.mypoplibproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mypoplibproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this, CountersModel())

    private var _vb: ActivityMainBinding? = null

    private val vb
        get() = _vb!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val listener = View.OnClickListener {
            presenter.counterClick(it.id)
        }

        vb.btnCounter1.setOnClickListener(listener)
        vb.btnCounter2.setOnClickListener(listener)
        vb.btnCounter3.setOnClickListener(listener)
    }

    override fun setButtonText(index: Int, text: String) {
        when (index) {
            0 -> vb.btnCounter1.text = text
            1 -> vb.btnCounter2.text = text
            2 -> vb.btnCounter3.text = text
        }
    }
}