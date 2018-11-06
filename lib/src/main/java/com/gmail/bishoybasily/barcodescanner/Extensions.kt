package com.gmail.bishoybasily.barcodescanner

import android.util.SparseArray


fun <C> SparseArray<C>.asList(): List<C> {
    val arrayList = ArrayList<C>()
    (0 until size()).mapTo(arrayList) { valueAt(it) }
    return arrayList

}
