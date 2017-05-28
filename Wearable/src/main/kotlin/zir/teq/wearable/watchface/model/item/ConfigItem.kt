package zir.teq.wearable.watchface.model.item

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

open class ConfigItem internal constructor(val type: Type,
                                           val pref: String,
                                           val name: String) : ConfigData.ConfigItemType {
    override val configType: Int get() = type.code

    companion object {
        fun createInstance(ctx: Context, type: Type): ConfigItem {
            return when (type) {
                THEME -> ThemeConfigItem(type, ctx.getString(type.prefId), ctx.getString(type.nameId))
                COLORS -> ColorConfigItem(type, ctx.getString(type.prefId), ctx.getString(type.nameId))
                STROKE -> StrokeConfigItem(type, ctx.getString(type.prefId), ctx.getString(type.nameId))
                else -> ConfigItem(type, ctx.getString(type.prefId), ctx.getString(type.nameId))
            }
        }

        data class Type(val code: Int, val prefId: Int, val nameId: Int, val iconId: Int)

        val THEME = Type(1, R.string.saved_theme, R.string.label_theme, R.drawable.icon_theme)
        val COLORS = Type(2, R.string.saved_color, R.string.label_color, R.drawable.icon_color)
        val STROKE = Type(3, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke)
        val DRAW_TRIANGLES = Type(4, R.string.saved_draw_triangles, R.string.label_draw_triangles, R.drawable.icon_dummy)
        val DRAW_CIRCLES = Type(5, R.string.saved_draw_circles, R.string.label_draw_circles, R.drawable.icon_dummy)
        val DRAW_ACTIVE_HANDS = Type(6, R.string.saved_draw_active_hands, R.string.label_draw_active_hands, R.drawable.icon_dummy)
        val DRAW_HANDS = Type(7, R.string.saved_draw_hands, R.string.label_draw_hands, R.drawable.icon_dummy)
        val DRAW_POINTS = Type(8, R.string.saved_draw_points, R.string.label_draw_points, R.drawable.icon_dummy)
        val DRAW_TEXT = Type(9, R.string.saved_draw_text, R.string.label_draw_text, R.drawable.icon_dummy)
        //TODO replace dummy icons
    }
}