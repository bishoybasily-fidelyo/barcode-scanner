package com.fidelyo.barcodescanner

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Created by bishoy on 12/26/17.
 */

class BarcodeScanner {

    private val TAG = javaClass.simpleName

    fun scan(activity: Activity): Observable<Code> {
        return Observable.create { e ->
            getFragment(activity).setEmitter(e).startActivityForResult(Intent(activity, ActivityBarcodeScanner::class.java), CODE)
        }
    }

    private fun getFragment(activity: Activity): BarcodeScannerFragment {
        val fragmentManager = activity.fragmentManager
        var fragment = fragmentManager.findFragmentByTag(TAG)
        if (fragment == null) {
            fragment = BarcodeScannerFragment()
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return fragment as BarcodeScannerFragment
    }

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
                            emitter!!.onNext(data.getSerializableExtra(EXTRA) as Code)
                            emitter!!.onComplete()
                        }
                    }
                }
            }
        }

    }

    companion object {

        val CODE = 987654321
        val EXTRA = "extra"
    }
}
