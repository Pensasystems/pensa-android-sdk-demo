//  Copyright (c) 2024 by Pensa Systems, Inc. -- All rights reserved
//  Confidential and Proprietary
package com.pensasystems.shelfxpert

import android.app.Application
import com.pensasystems.pensasdk.PensaSdk

class ShelfXpertApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PensaSdk.enableLogging()
        PensaSdk.initPensa(
            applicationContext = this,
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            onSuccess = { println("Configured successfully") },
            onError = { println(it) }
        )
    }
}
