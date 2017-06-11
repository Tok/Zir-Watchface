package zir.teq.wearable.watchface.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.data.*
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
    val ctx = Zir.getAppContext()
    val res: Resources = ctx.resources
    val prefs: SharedPreferences = ctx.getSharedPreferences(
            ctx.getString(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)
    val isAntiAlias = true
    val isElastic = false
    lateinit var palette: Palette
    lateinit var stroke: Stroke
    var theme = Theme.default
    var isAmbient = false
    var isMute = false
    var background = Background.default
    var alpha = Alpha.default
    var dim = Dim.default

    interface ConfigItemType {
        val configType: Int
    }

    val watchFaceServiceClass: Class<*> get() = ZirWatchFaceService::class.java

    fun getDataToPopulateAdapter(activityContext: Context): ArrayList<ConfigItemType> {
        val settingsConfigData = ArrayList<ConfigItemType>()
        ConfigItem.ALL_TYPES.forEach {
            settingsConfigData.add(ConfigItem.createInstance(activityContext, it))
        }
        return settingsConfigData
    }
}
