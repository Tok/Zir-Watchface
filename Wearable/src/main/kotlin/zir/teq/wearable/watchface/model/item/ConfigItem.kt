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
        val COLORS = Type(2, R.string.saved_theme, R.string.label_color, R.drawable.icon_color)
        val STROKE = Type(3, R.string.saved_theme, R.string.label_stroke, R.drawable.icon_stroke)
        val DRAW_HANDS_ACTIVE = Type(10, R.string.saved_hands_act, R.string.label_hands_act, R.drawable.icon_dummy)
        val DRAW_HANDS_AMBIENT = Type(11, R.string.saved_hands_amb, R.string.label_hands_amb, R.drawable.icon_dummy)
        val DRAW_TRIANGLES_ACTIVE = Type(20, R.string.saved_triangles_act, R.string.label_triangles_act, R.drawable.icon_dummy)
        val DRAW_TRIANGLES_AMBIENT = Type(21, R.string.saved_triangles_amb, R.string.label_triangles_amb, R.drawable.icon_dummy)
        val DRAW_CIRCLES_ACTIVE = Type(30, R.string.saved_circles_act, R.string.label_circles_act, R.drawable.icon_dummy)
        val DRAW_CIRCLES_AMBIENT = Type(31, R.string.saved_circles_amb, R.string.label_circles_amb, R.drawable.icon_dummy)
        val DRAW_POINTS_ACTIVE = Type(40, R.string.saved_points_act, R.string.label_points_act, R.drawable.icon_dummy)
        val DRAW_POINTS_AMBIENT = Type(41, R.string.saved_points_amb, R.string.label_points_amb, R.drawable.icon_dummy)
        val DRAW_TEXT_ACTIVE = Type(50, R.string.saved_text_act, R.string.label_text_act, R.drawable.icon_dummy)
        val DRAW_TEXT_AMBIENT = Type(51, R.string.saved_text_amb, R.string.label_text_amb, R.drawable.icon_dummy)
        //TODO replace dummy icons
        val ALL_TYPES = listOf<Type>(THEME, COLORS, STROKE,
                DRAW_HANDS_ACTIVE, DRAW_HANDS_AMBIENT,
                DRAW_TRIANGLES_ACTIVE, DRAW_TRIANGLES_AMBIENT,
                DRAW_CIRCLES_ACTIVE, DRAW_CIRCLES_AMBIENT,
                DRAW_POINTS_ACTIVE, DRAW_POINTS_AMBIENT,
                DRAW_TEXT_ACTIVE, DRAW_TEXT_AMBIENT)
        fun valueOf(code: Int) = ALL_TYPES.find { it.code == code }
    }
}