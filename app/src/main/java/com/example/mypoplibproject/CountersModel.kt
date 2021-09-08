package com.example.mypoplibproject

class CountersModel {

    private val counters = mutableListOf(0, 0, 0)

    private fun getCounter(index: Int) = counters[index]

    fun next(index: Int): Int {
        counters[index]++
        return getCounter(index)
    }

    fun set(index: Int, value: Int) {
        counters[index] = value
    }


    fun getPosition(id: Int): Int {

        return when (id) {
            R.id.btn_counter1 -> 0

            R.id.btn_counter2 -> 1

            R.id.btn_counter3 -> 2

            else -> throw IllegalStateException("Такого индекса нет")
        }
    }
}