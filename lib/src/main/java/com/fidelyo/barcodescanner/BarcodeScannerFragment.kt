package com.fidelyo.barcodescanner

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import io.reactivex.ObservableEmitter

class BarcodeScannerFragment : Fragment() {

    private var emitter: ObservableEmitter<Code>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun setEmitter(emitter: ObservableEmitter<Code>): BarcodeScannerFragment {
        this.emitter = emitter
        return this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == BarcodeScanner.CODE) {
                if (data != null) {
                    if (emitter != null) {
                        emitter!!.onNext(data.getSerializableExtra(BarcodeScanner.EXTRA) as Code)
                        emitter!!.onComplete()
                    }
                }
            }
        }
    }

    companion object {
        val TAG = javaClass.simpleName
    }

}