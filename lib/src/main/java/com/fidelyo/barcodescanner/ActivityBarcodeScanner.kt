package com.fidelyo.barcodescanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_barcode_scanner.*

class ActivityBarcodeScanner : AppCompatActivity(), Detector.Processor<Barcode> {

    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector

    val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_barcode_scanner)
        supportActionBar?.hide()

        if (barcodeDetector.isOperational) {
            barcodeDetector.setProcessor(this)
            initializeCamera()
        }

    }

    @SuppressLint("MissingPermission")
    private fun initializeCamera() {
        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                mainHandler.post { cameraSource.start(p0) }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                mainHandler.post { cameraSource.stop() }
            }
        })
    }

    override fun receiveDetections(detections: Detector.Detections<Barcode>) {
        val barcode: Barcode? = detections.detectedItems.asList().first()
        if (barcode != null) {

            val code = Code()
            code.value = barcode.rawValue
            code.format = Format.findByValue(barcode.format)

            setResult(RESULT_OK, intent.putExtra(BarcodeScanner.EXTRA, code)); finish()

        }
    }

    override fun release() {

    }
}
