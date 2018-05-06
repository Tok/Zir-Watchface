package zir.teq.wearable.watchface

import android.app.Application
import android.content.Context
import zir.teq.wearable.watchface.model.setting.component.Components

class Zir : Application() {
    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
        Components.loadComponentStates()
        //Setup.PLAIN.applySetup()
    }

    companion object {
        private lateinit var mAppContext: Context
        fun ctx() = mAppContext
        fun prefs(name: String, code: Int) = ctx().getSharedPreferences(name, code)
        fun res() = ctx().resources
        fun string(id: Int) = ctx().getString(id)
        fun drawable(id: Int) = ctx().getDrawable(id)
        fun color(id: Int) = ctx().getColor(id)
    }
}
