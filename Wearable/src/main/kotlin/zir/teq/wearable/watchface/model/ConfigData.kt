package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ColorSelectionActivity
import zir.teq.wearable.watchface.config.StrokeSelectionActivity
import zir.teq.wearable.watchface.config.ThemeSelectionActivity
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

        val strokeConfigItem = StrokeConfigItem(
                ctx.getString(R.string.config_marker_stroke_label),
                R.drawable.icon_stroke,
                ctx.getString(R.string.saved_stroke_name),
                StrokeSelectionActivity::class.java)
        settingsConfigData.add(strokeConfigItem)

        val themeConfigItem = ThemeConfigItem(
                ctx.getString(R.string.config_marker_theme_label),
                R.drawable.icon_theme,
                ctx.getString(R.string.saved_theme_name),
                ThemeSelectionActivity::class.java)
        settingsConfigData.add(themeConfigItem)

        return settingsConfigData
    }

    class ColorConfigItem internal constructor(val name: String,
                                               val iconResourceId: Int,
                                               val sharedPrefString: String,
                                               val activityToChoosePreference: Class<ColorSelectionActivity>) : ConfigItemType {
        override val configType: Int get() = ZirWatchConfigAdapter.TYPE_COLOR_CONFIG
    }

    class StrokeConfigItem internal constructor(val name: String,
                                                val iconResourceId: Int,
                                                val sharedPrefString: String,
                                                val activityToChoosePreference: Class<StrokeSelectionActivity>) : ConfigItemType {
        override val configType: Int get() = ZirWatchConfigAdapter.TYPE_STROKE_CONFIG
    }

    class ThemeConfigItem internal constructor(val name: String,
                                                val iconResourceId: Int,
                                                val sharedPrefString: String,
                                                val activityToChoosePreference: Class<ThemeSelectionActivity>) : ConfigItemType {
        override val configType: Int get() = ZirWatchConfigAdapter.TYPE_THEME_CONFIG
    }
}