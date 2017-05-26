package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.model.item.ColorConfigItem
import zir.teq.wearable.watchface.model.item.StrokeConfigItem
import zir.teq.wearable.watchface.model.item.ThemeConfigItem
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
}
