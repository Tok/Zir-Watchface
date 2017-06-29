package zir.teq.wearable.watchface.config.select.config

import zir.teq.wearable.watchface.R


data class Type(val code: Int,
                val prefId: Int,
                val secondaryPrefId: Int?,
                val nameId: Int,
                val iconId: Int? = null) {
    fun isPair() = code in 100..999 //TODO refactor
    fun getLayoutId() = if (code in 1..99) { R.layout.list_item_main } else null
    companion object {
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
        val FAST_UPDATE = Type(1000, R.string.saved_fast_update, null, R.string.label_fast_update)
        val IS_ELASTIC = Type(1100, R.string.saved_is_elastic, null, R.string.label_is_elastic)

        val ALL_TYPES = listOf<Type>(WAVE, PALETTE, BACKGROUND, THEME, STROKE, OUTLINE, GROWTH,
                STACK, ALPHA, DIM, FAST_UPDATE, IS_ELASTIC)

        fun valueOf(code: Int): Type = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Type code unknown: $code.")
    }
}
