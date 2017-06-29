package zir.teq.wearable.watchface.config.select.config

import zir.teq.wearable.watchface.R


class MainType(code: Int, prefId: Int, nameId: Int, val iconId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_main
}

class CheckboxType(code: Int, prefId: Int, nameId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_checkbox
}

abstract class Type(val code: Int, val prefId: Int, val nameId: Int) {
    abstract fun layoutId(): Int
    companion object {
        val COMPONENT = MainType(1, R.string.saved_theme, R.string.label_components, R.drawable.icon_components)
        val WAVE = MainType(2, R.string.saved_wave, R.string.label_wave, R.drawable.icon_wave)
        val PALETTE = MainType(3, R.string.saved_palette, R.string.label_palette, R.drawable.icon_color)
        val BACKGROUND = MainType(4, R.string.saved_background, R.string.label_background, R.drawable.icon_background)
        val STROKE = MainType(5, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = MainType(6, R.string.saved_outline, R.string.label_outline, R.drawable.icon_outline)
        val GROWTH = MainType(7, R.string.saved_growth, R.string.label_growth, R.drawable.icon_growth)
        val ALPHA = MainType(8, R.string.saved_alpha, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = MainType(9, R.string.saved_dim, R.string.label_dim, R.drawable.icon_dim)
        val STACK = MainType(10, R.string.saved_stack, R.string.label_stack, R.drawable.icon_stack)
        val FAST_UPDATE = CheckboxType(1000, R.string.saved_fast_update, R.string.label_fast_update)
        val IS_ELASTIC = CheckboxType(1100, R.string.saved_is_elastic, R.string.label_is_elastic)

        val ALL_TYPES = listOf<Type>(COMPONENT, WAVE, PALETTE, BACKGROUND, STROKE, OUTLINE, GROWTH,
                STACK, ALPHA, DIM, FAST_UPDATE, IS_ELASTIC)

        fun valueOf(code: Int): Type = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Type code unknown: $code.")
    }
}
