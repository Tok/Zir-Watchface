package zir.teq.wearable.watchface.model.item

import android.content.Context
import android.graphics.Typeface
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import java.util.concurrent.TimeUnit

open class ConfigItem internal constructor(val type: Type,
                                           val pref: String,
                                           val name: String) : ConfigData.ConfigItemType {
    override val configType: Int get() = type.code

    companion object {
        fun createInstance(ctx: Context, type: Type): ConfigItem {
            val pref = ctx.getString(type.prefId)
            val name = ctx.getString(type.nameId)
            return when (type) {
                THEME -> ThemeConfigItem(type, pref, name)
                COLORS -> ColorConfigItem(type, pref, name)
                BACKGROUND -> BackgroundConfigItem(type, pref, name)
                STROKE -> StrokeConfigItem(type, pref, name)
                OUTLINE -> OutlineConfigItem(type, pref, name)
                GROWTH -> GrowthConfigItem(type, pref, name)
                ALPHA -> AlphaConfigItem(type, pref, name)
                DIM -> DimConfigItem(type, pref, name)
                else -> ConfigItem(type, pref, name)
            }
        }

        data class Type(val code: Int, val prefId: Int, val nameId: Int, val iconId: Int)

        val THEME = Type(1, R.string.saved_theme, R.string.label_theme, R.drawable.icon_theme)
        val COLORS = Type(2, R.string.saved_palette, R.string.label_color, R.drawable.icon_color)
        val BACKGROUND = Type(3, R.string.saved_background, R.string.label_background, R.drawable.icon_background)
        val STROKE = Type(4, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = Type(5, R.string.saved_outline, R.string.label_outline, R.drawable.icon_outline)
        val GROWTH = Type(6, R.string.saved_growth, R.string.label_growth, R.drawable.icon_growth)
        val ALPHA = Type(7, R.string.saved_alpha, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = Type(8, R.string.saved_dim, R.string.label_dim, R.drawable.icon_dim)
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
        val FAST_UPDATE = Type(100, R.string.saved_fast_update, R.string.label_fast_update, R.drawable.icon_dummy)
        //TODO replace dummy icons
        val ALL_TYPES = listOf<Type>(THEME, COLORS, BACKGROUND, STROKE, OUTLINE, GROWTH, ALPHA, DIM,
                FAST_UPDATE,
                DRAW_HANDS_ACTIVE, DRAW_HANDS_AMBIENT,
                DRAW_TRIANGLES_ACTIVE, DRAW_TRIANGLES_AMBIENT,
                DRAW_CIRCLES_ACTIVE, DRAW_CIRCLES_AMBIENT,
                DRAW_POINTS_ACTIVE, DRAW_POINTS_AMBIENT,
                DRAW_TEXT_ACTIVE, DRAW_TEXT_AMBIENT)
        fun valueOf(code: Int) = ALL_TYPES.find { it.code == code }

        val isStayActive = false //TODO reimplement

        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)

        val FAST_UPDATE_RATE_MS = TimeUnit.MILLISECONDS.toMillis(20)
        val NORMAL_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1)
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(inMuteMode: Boolean, isFastUpdate: Boolean) = if (inMuteMode) activeUpdateRateMs(isFastUpdate) else ambientUpdateRateMs(isFastUpdate)
        private fun ambientUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        private fun activeUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
    }
}