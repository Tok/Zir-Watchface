package zir.teq.wearable.watchface.config.select.config

import zir.teq.wearable.watchface.R


data class Type(val code: Int,
                val prefId: Int,
                val secondaryPrefId: Int?,
                val nameId: Int,
                val iconId: Int? = null,
                val layoutId: Int? = null) {
    fun isPair() = code in 100..999 //TODO refactor
    companion object {
        val WAVE = Type(1, R.string.saved_wave, null, R.string.label_wave, R.drawable.icon_wave, R.layout.config_list_item_wave)
        val PALETTE = Type(2, R.string.saved_palette, null, R.string.label_palette, R.drawable.icon_color, R.layout.config_list_item_palette)
        val BACKGROUND = Type(3, R.string.saved_background, null, R.string.label_background, R.drawable.icon_background, R.layout.config_list_item_background)
        val THEME = Type(4, R.string.saved_theme, null, R.string.label_theme, R.drawable.icon_theme, R.layout.config_list_item_theme)
        val STROKE = Type(5, R.string.saved_stroke, null, R.string.label_stroke, R.drawable.icon_stroke, R.layout.config_list_item_stroke)
        val OUTLINE = Type(6, R.string.saved_outline, null, R.string.label_outline, R.drawable.icon_outline, R.layout.config_list_item_outline)
        val GROWTH = Type(7, R.string.saved_growth, null, R.string.label_growth, R.drawable.icon_growth, R.layout.config_list_item_growth)
        val ALPHA = Type(8, R.string.saved_alpha, null, R.string.label_alpha, R.drawable.icon_alpha, R.layout.config_list_item_alpha)
        val DIM = Type(9, R.string.saved_dim, null, R.string.label_dim, R.drawable.icon_dim, R.layout.config_list_item_dim)
        val STACK = Type(10, R.string.saved_stack, null, R.string.label_stack, R.drawable.icon_stack, R.layout.config_list_item_stack)

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

        fun valueOf(code: Int): Type = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Type code unknown: $code.")
    }
}