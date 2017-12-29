package com.fidelyo.barcodescanner

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable

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

    companion object {

        val CODE = 987654321
        val EXTRA = "extra"
    }
}
