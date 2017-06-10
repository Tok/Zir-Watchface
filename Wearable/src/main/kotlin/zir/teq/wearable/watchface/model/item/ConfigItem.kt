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
    override fun toString() = name
    companion object {
        fun createInstance(ctx: Context, type: Type): ConfigItem {
            val pref = ctx.getString(type.prefId)
            val name = ctx.getString(type.nameId)
            return when (type) {
                THEME -> ThemeConfigItem(type, pref, name)
                PALETTE -> PaletteConfigItem(type, pref, name)
                BACKGROUND -> BackgroundConfigItem(type, pref, name)
                STROKE -> StrokeConfigItem(type, pref, name)
                OUTLINE -> OutlineConfigItem(type, pref, name)
                GROWTH -> GrowthConfigItem(type, pref, name)
                ALPHA -> AlphaConfigItem(type, pref, name)
                DIM -> DimConfigItem(type, pref, name)
                else -> ConfigItem(type, pref, name)
            }
        }

        data class Type(val code: Int, val prefId: Int, val secondaryPrefId: Int?,
                        val nameId: Int, val iconId: Int? = null) {
            fun isPair() = code in 10..99 //TODO keep updated or refactor
        }

        val PALETTE = Type(1, R.string.saved_palette, null, R.string.label_palette, R.drawable.icon_color)
        val BACKGROUND = Type(2, R.string.saved_background, null, R.string.label_background, R.drawable.icon_background)
        val THEME = Type(3, R.string.saved_theme, null, R.string.label_theme, R.drawable.icon_theme)
        val STROKE = Type(4, R.string.saved_stroke, null, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = Type(5, R.string.saved_outline, null, R.string.label_outline, R.drawable.icon_outline)
        val GROWTH = Type(6, R.string.saved_growth, null, R.string.label_growth, R.drawable.icon_growth)
        val ALPHA = Type(7, R.string.saved_alpha, null, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = Type(8, R.string.saved_dim, null, R.string.label_dim, R.drawable.icon_dim)
        val DRAW_HANDS = Type(10, R.string.saved_hands_act, R.string.saved_hands_amb, R.string.label_hands_act)
        val DRAW_TRIANGLES = Type(20, R.string.saved_triangles_act, R.string.saved_triangles_amb, R.string.label_triangles_act)
        val DRAW_CIRCLES = Type(30, R.string.saved_circles_act, R.string.saved_circles_amb, R.string.label_circles_act)
        val DRAW_POINTS = Type(40, R.string.saved_points_act, R.string.saved_points_amb, R.string.label_points_act)
        val DRAW_TEXT = Type(50, R.string.saved_text_act, R.string.saved_text_amb, R.string.label_text_act)
        val FAST_UPDATE = Type(100, R.string.saved_fast_update, null, R.string.label_fast_update)
        val ALL_TYPES = listOf<Type>(PALETTE, BACKGROUND, THEME, STROKE, OUTLINE, GROWTH, ALPHA, DIM,
                DRAW_HANDS, DRAW_TRIANGLES, DRAW_CIRCLES, DRAW_POINTS, DRAW_TEXT, FAST_UPDATE)
        fun valueOf(code: Int) = ALL_TYPES.find { it.code == code }

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