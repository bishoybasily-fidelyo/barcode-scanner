package com.gmail.bishoybasily.barcodescanner

import com.google.zxing.BarcodeFormat

/**
 * Created by bishoy on 9/12/17.
 */

enum class Format(val value: Int, val format: BarcodeFormat? = null) {

    CODE_128(1, BarcodeFormat.CODE_128),
    CODE_39(2, BarcodeFormat.CODE_39),
    CODE_93(4, BarcodeFormat.CODE_93),
    CODABAR(8, BarcodeFormat.CODABAR),
    DATA_MATRIX(16, BarcodeFormat.DATA_MATRIX),
    EAN_13(32, BarcodeFormat.EAN_13),
    EAN_8(64, BarcodeFormat.EAN_8),
    ITF(128, BarcodeFormat.ITF),
    QR_CODE(256, BarcodeFormat.QR_CODE),
    UPC_A(512, BarcodeFormat.UPC_A),
    UPC_E(1024, BarcodeFormat.UPC_E),
    PDF417(2048, BarcodeFormat.PDF_417),
    AZTEC(4096, BarcodeFormat.AZTEC);

    companion object {
        fun findByValue(value: Int): Format? {
            return Format.values().firstOrNull { it.value == value }
        }
    }

}