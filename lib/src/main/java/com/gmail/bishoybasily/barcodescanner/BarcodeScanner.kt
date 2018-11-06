package com.gmail.bishoybasily.barcodescanner

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable

/**
 * Created by bishoy on 12/26/17.
 */

class BarcodeScanner {

    fun with(activity: Activity): Scanner {
        return Scanner(activity)
    }

    open class Scanner(val activity: Activity) {

        val TAG = javaClass.simpleName

        fun scan(): Observable<Code> {
            return Observable.create { e ->
                getFragment(activity).setEmitter(e).startActivityForResult(Intent(activity, ActivityBarcodeScanner::class.java), CODE)
            }
        }

        private fun getFragment(activity: Activity): BarcodeScannerFragment {
            val fragmentManager = activity.fragmentManager
            var fragment = fragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = BarcodeScannerFragment()
                fragmentManager
                        .beginTransaction()
                        .add(fragment, TAG)
                        .commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
            return fragment as BarcodeScannerFragment
        }
    }


    companion object {

        val CODE = 41
        val EXTRA = "extra_code"
    }
}
