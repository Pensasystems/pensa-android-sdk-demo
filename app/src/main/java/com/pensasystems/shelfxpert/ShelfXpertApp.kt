//  Copyright (c) 2024 by Pensa Systems, Inc. -- All rights reserved
//  Confidential and Proprietary
package com.pensasystems.shelfxpert

import android.app.Application
import com.pensasystems.pensasdk.PensaSdkConfiguration
import com.pensasystems.pensasdk.PensaSdk
import com.pensasystems.pensasdk.listener.CantScanEventListener
import com.pensasystems.pensasdk.listener.ScanUploadListener

class ShelfXpertApp : Application(), ScanUploadListener, CantScanEventListener {

    override fun onCreate() {
        super.onCreate()

        val config = PensaSdkConfiguration.Builder()
            .setClientId(BuildConfig.CLIENT_ID)
            .setClientSecret(BuildConfig.CLIENT_SECRET)
            .setScanUploadListener(this)
            .setCantScanEventListener(this)
            .enableLogging(true)
            .build()


        PensaSdk.initPensa(
            applicationContext = this,
            sdkConfiguration = config,
            onSuccess = { println("Configured successfully") },
            onError = { println(it) }
        )
    }

    override fun onScanUploadProgressUpdate(tdlinxId: String, scanAreaId: String, progress: Int) {
        println("Upload progress: tdlinxId: $tdlinxId | scanAreaId: $scanAreaId | progress: $progress")
    }

    override fun onScanUploadCompleted(
        tdlinxId: String,
        scanAreaId: String
    ) {
        println("Upload completed: tdlinxId: $tdlinxId | scanAreaId: $scanAreaId")
    }

    override fun onScanUploadFailed(
        tdlinxId: String,
        scanAreaId: String,
    ) {
        println("Upload failed: tdlinxId: $tdlinxId | scanAreaId: $scanAreaId")
    }

    override fun onCantScanReported(tdlinxId: String, scanAreaId: String, cantScanReason: String) {
        println("Can't scan reported: tdlinxId: $tdlinxId | scanAreaId: $scanAreaId | cantScanReason: $cantScanReason")
    }
}
