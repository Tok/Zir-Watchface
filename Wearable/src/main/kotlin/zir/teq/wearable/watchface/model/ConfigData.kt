package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
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
}
