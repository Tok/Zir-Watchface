package zir.teq.wearable.watchface.config.select.config

import android.content.Context
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.*
import zir.teq.wearable.watchface.config.select.main.MainConfigActivity


data class Type(val code: Int, val prefId: Int, val secondaryPrefId: Int?,
                val nameId: Int, val iconId: Int? = null) {
    fun isPair() = code in 100..999 //TODO refactor
    companion object {
        fun createInstance(ctx: Context, type: Type): Item {
            val pref = ctx.getString(type.prefId)
            val name = ctx.getString(type.nameId)
            return when (type) {
                THEME -> Item(type, pref, name, ThemeSelectionActivity::class.java)
                PALETTE -> Item(type, pref, name, PaletteSelectionActivity::class.java)
                BACKGROUND -> Item(type, pref, name, BackgroundSelectionActivity::class.java)
                WAVE -> Item(type, pref, name, WaveSelectionActivity::class.java)
                STROKE -> Item(type, pref, name, StrokeSelectionActivity::class.java)
                OUTLINE -> Item(type, pref, name, OutlineSelectionActivity::class.java)
                GROWTH -> Item(type, pref, name, GrowthSelectionActivity::class.java)
                ALPHA -> Item(type, pref, name, AlphaSelectionActivity::class.java)
                DIM -> Item(type, pref, name, DimSelectionActivity::class.java)
                STACK -> Item(type, pref, name, StackSelectionActivity::class.java)
                else -> Item(type, pref, name, MainConfigActivity::class.java)
            }
        }
        val WAVE = Type(1, R.string.saved_wave, null, R.string.label_wave, R.drawable.icon_wave)
        val PALETTE = Type(2, R.string.saved_palette, null, R.string.label_palette, R.drawable.icon_color)
        val BACKGROUND = Type(3, R.string.saved_background, null, R.string.label_background, R.drawable.icon_background)
        val THEME = Type(4, R.string.saved_theme, null, R.string.label_theme, R.drawable.icon_theme)
        val STROKE = Type(5, R.string.saved_stroke, null, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = Type(6, R.string.saved_outline, null, R.string.label_outline, R.drawable.icon_outline)
        val GROWTH = Type(7, R.string.saved_growth, null, R.string.label_growth, R.drawable.icon_growth)
        val ALPHA = Type(8, R.string.saved_alpha, null, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = Type(9, R.string.saved_dim, null, R.string.label_dim, R.drawable.icon_dim)
        val STACK = Type(10, R.string.saved_stack, null, R.string.label_stack, R.drawable.icon_stack)

        val DRAW_HANDS = Type(100, R.string.saved_hands_act, R.string.saved_hands_amb, R.string.label_hands_act)
        val DRAW_TRIANGLES = Type(200, R.string.saved_triangles_act, R.string.saved_triangles_amb, R.string.label_triangles_act)
        val DRAW_CIRCLES = Type(300, R.string.saved_circles_act, R.string.saved_circles_amb, R.string.label_circles_act)
        val DRAW_POINTS = Type(400, R.string.saved_points_act, R.string.saved_points_amb, R.string.label_points_act)
        val DRAW_TEXT = Type(500, R.string.saved_text_act, R.string.saved_text_amb, R.string.label_text_act)

        val FAST_UPDATE = Type(1000, R.string.saved_fast_update, null, R.string.label_fast_update)
        val IS_ELASTIC = Type(1100, R.string.saved_is_elastic, null, R.string.label_is_elastic)
        val ALL_TYPES = listOf<Type>(WAVE, PALETTE, BACKGROUND, THEME, STROKE, OUTLINE, GROWTH,
                STACK, ALPHA, DIM, DRAW_HANDS, DRAW_TRIANGLES, DRAW_CIRCLES, DRAW_POINTS, DRAW_TEXT,
                FAST_UPDATE, IS_ELASTIC)

        fun valueOf(code: Int) = ALL_TYPES.find { it.code == code }
    }
}