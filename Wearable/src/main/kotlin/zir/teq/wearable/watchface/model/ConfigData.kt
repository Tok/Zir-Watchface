package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.model.item.*
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
    interface ConfigItemType {
        val configType: Int
    }

    val watchFaceServiceClass: Class<*> get() = ZirWatchFaceService::class.java

    fun getDataToPopulateAdapter(ctx: Context): ArrayList<ConfigItemType> {
        val settingsConfigData = ArrayList<ConfigItemType>()

        val themeConfigItem = ThemeConfigItem(
                ConfigItem.THEME.code,
                ctx.getString(R.string.config_marker_theme_label),
                R.drawable.icon_theme,
                ctx.getString(R.string.saved_theme_name),
                ThemeSelectionActivity::class.java)
        settingsConfigData.add(themeConfigItem)
        val colorConfigItem = ColorConfigItem(
                ConfigItem.COLORS.code,
                ctx.getString(R.string.config_marker_color_label),
                R.drawable.icon_color,
                ctx.getString(R.string.saved_color_name),
                ColorSelectionActivity::class.java)
        settingsConfigData.add(colorConfigItem)
        val strokeConfigItem = StrokeConfigItem(
                ConfigItem.STROKE.code,
                ctx.getString(R.string.config_marker_stroke_label),
                R.drawable.icon_stroke,
                ctx.getString(R.string.saved_stroke_name),
                StrokeSelectionActivity::class.java)
        settingsConfigData.add(strokeConfigItem)

        val drawCirclesConfigItem = ConfigItem(
                ConfigItem.DRAW_CIRCLES.code,
                ctx.getString(R.string.config_marker_draw_circles_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_circles_name))
        settingsConfigData.add(drawCirclesConfigItem)
        val drawTrianglesConfigItem = ConfigItem(
                ConfigItem.DRAW_TRIANGLES.code,
                ctx.getString(R.string.config_marker_draw_triangles_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_triangles_name))
        settingsConfigData.add(drawTrianglesConfigItem)
        val drawActiveHandsConfigItem = ConfigItem(
                ConfigItem.DRAW_ACTIVE_HANDS.code,
                ctx.getString(R.string.config_marker_draw_active_hands_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_active_hands_name))
        settingsConfigData.add(drawActiveHandsConfigItem)
        val drawHandsConfigItem = ConfigItem(
                ConfigItem.DRAW_HANDS.code,
                ctx.getString(R.string.config_marker_draw_hands_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_hands_name))
        settingsConfigData.add(drawHandsConfigItem)
        val drawPointsConfigItem = ConfigItem(
                ConfigItem.DRAW_POINTS.code,
                ctx.getString(R.string.config_marker_draw_points_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_points_name))
        settingsConfigData.add(drawPointsConfigItem)
        val drawTextConfigItem = ConfigItem(
                ConfigItem.DRAW_TEXT.code,
                ctx.getString(R.string.config_marker_draw_text_label),
                R.drawable.icon_dummy,
                ctx.getString(R.string.saved_draw_text_name))
        settingsConfigData.add(drawTextConfigItem)

        return settingsConfigData
    }
}
