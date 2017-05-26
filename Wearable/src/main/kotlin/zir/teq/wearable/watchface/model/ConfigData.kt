package zir.teq.wearable.watchface.model

import android.content.Context
import android.graphics.Typeface
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.model.item.ColorConfigItem
import zir.teq.wearable.watchface.model.item.StrokeConfigItem
import zir.teq.wearable.watchface.model.item.ThemeConfigItem
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*
import java.util.concurrent.TimeUnit

object ConfigData {
    interface ConfigItemType {
        val configType: Int
    }

    val isStayActive = false //TODO reimplement
    val isFastUpdate = false //TODO reimplement

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

    val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
    val FAST_UPDATE_RATE_MS = 20L
    val NORMAL_UPDATE_RATE_MS = 1000L
    val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
    fun updateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
    fun activeUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
}
