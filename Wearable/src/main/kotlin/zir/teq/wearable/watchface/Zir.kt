package zir.teq.wearable.watchface

import android.app.Application
import android.content.Context

class Zir : Application() {
    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
    }

    companion object {
        private lateinit var mAppContext: Context
        fun getAppContext() = mAppContext
    }
}
