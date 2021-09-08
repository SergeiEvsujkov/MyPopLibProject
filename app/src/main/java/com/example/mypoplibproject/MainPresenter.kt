package com.example.mypoplibproject


class MainPresenter(
    private val view: MainView,
    private val model: CountersModel
) {

    fun counterClick(id: Int) {
        val position = model.getPosition(id)
        val nextValue = model.next(position)
        view.setButtonText(position, nextValue.toString())
    }
}