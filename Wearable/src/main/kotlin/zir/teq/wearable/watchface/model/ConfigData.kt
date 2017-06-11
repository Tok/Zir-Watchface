package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Alpha
import zir.teq.wearable.watchface.model.data.Background
import zir.teq.wearable.watchface.model.data.Dim
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
    val isAntiAlias = true
    var isAmbient = false
    var isMute = false
    var background = Background.default
    var alpha = Alpha.default
    var dim = Dim.default
    var isElastic = false

    interface ConfigItemType {
        val configType: Int
    }

    val watchFaceServiceClass: Class<*> get() = ZirWatchFaceService::class.java

    fun getDataToPopulateAdapter(ctx: Context): ArrayList<ConfigItemType> {
        val settingsConfigData = ArrayList<ConfigItemType>()
        ConfigItem.ALL_TYPES.forEach {
            settingsConfigData.add(ConfigItem.createInstance(ctx, it))
        }
        return settingsConfigData
    }

    fun prefs(ctx: Context) = ctx.getSharedPreferences(
            ctx.getString(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)
}
