package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ColorSelectionActivity
import zir.teq.wearable.watchface.config.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
    interface ConfigItemType {
        val configType: Int
    }

    val watchFaceServiceClass: Class<*> get() = ZirWatchFaceService::class.java

    fun getDataToPopulateAdapter(ctx: Context): ArrayList<ConfigItemType> {
        val settingsConfigData = ArrayList<ConfigItemType>()
        val colorConfigItem = ColorConfigItem(
                ctx.getString(R.string.config_marker_color_label),
                R.drawable.icon_color,
                ctx.getString(R.string.saved_color_name),
                ColorSelectionActivity::class.java)
        settingsConfigData.add(colorConfigItem)
        return settingsConfigData
    }

    class ColorConfigItem internal constructor(val name: String,
                                               val iconResourceId: Int,
                                               val sharedPrefString: String,
                                               val activityToChoosePreference: Class<ColorSelectionActivity>) : ConfigItemType {
        override val configType: Int get() = ZirWatchConfigAdapter.TYPE_COLOR_CONFIG
    }
}