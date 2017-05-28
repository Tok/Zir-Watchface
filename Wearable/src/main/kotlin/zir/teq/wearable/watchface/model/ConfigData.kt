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
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.THEME))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.COLORS))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.STROKE))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_TRIANGLES))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_CIRCLES))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_ACTIVE_HANDS))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_HANDS))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_POINTS))
        settingsConfigData.add(ConfigItem.createInstance(ctx, ConfigItem.DRAW_TEXT))
        return settingsConfigData
    }
}
