package com.example.mypoplibproject


class MainPresenter(
    private val view: MainView,
    private val model: CountersModel
) {

    fun counterClick(id: Int) {

        when (model.getPosition(id)) {
            1 -> {
                val nextValue = model.next(0)
                view.setButtonText(0, nextValue.toString())
            }
            2 -> {
                val nextValue = model.next(1)
                view.setButtonText(1, nextValue.toString())
            }
            3 -> {
                val nextValue = model.next(2)
                view.setButtonText(2, nextValue.toString())
            }
            else -> throw IllegalStateException("Такого индекса нет")
        }

    }
}