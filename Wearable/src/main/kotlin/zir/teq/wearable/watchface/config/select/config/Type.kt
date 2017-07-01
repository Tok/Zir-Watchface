package zir.teq.wearable.watchface.config.select.config

import zir.teq.wearable.watchface.R


class MainType(code: Int, prefId: Int, nameId: Int, val iconId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_main
}

class StyleType(code: Int, prefId: Int, nameId: Int, val iconId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_main
}

class ColorType(code: Int, prefId: Int, nameId: Int, val iconId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_main
}

class CheckboxType(code: Int, prefId: Int, nameId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_checkbox
}

abstract class Type(val code: Int, val prefId: Int, val nameId: Int) {
    abstract fun layoutId(): Int

    companion object {
        val COMPONENT = MainType(1000, R.string.saved_theme, R.string.label_components, R.drawable.icon_components)

        val PALETTE = MainType(2000, R.string.saved_palette, R.string.label_colors, R.drawable.icon_color)
        val BACKGROUND = ColorType(2010, R.string.saved_background, R.string.label_background, R.drawable.icon_background)

        val WAVE = MainType(3000, R.string.saved_wave, R.string.label_wave, R.drawable.icon_wave)

        val STYLE = MainType(4000, R.string.saved_style, R.string.label_style, R.drawable.icon_dummy) //TODO add icon.
        val ALPHA = StyleType(4010, R.string.saved_alpha, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = StyleType(4020, R.string.saved_dim, R.string.label_dim, R.drawable.icon_dim)
        val STACK = StyleType(4030, R.string.saved_stack, R.string.label_stack, R.drawable.icon_stack)
        val GROWTH = StyleType(4040, R.string.saved_growth, R.string.label_growth, R.drawable.icon_growth)
        val STROKE = StyleType(4050, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = StyleType(4060, R.string.saved_outline, R.string.label_outline, R.drawable.icon_outline)

        val FAST_UPDATE = CheckboxType(9010, R.string.saved_fast_update, R.string.label_fast_update)
        val IS_ELASTIC = CheckboxType(9020, R.string.saved_is_elastic, R.string.label_is_elastic)


        private val ALL_TYPES = listOf<Type>(COMPONENT, PALETTE, WAVE, STYLE, STROKE, OUTLINE, GROWTH,
                STACK, ALPHA, DIM, FAST_UPDATE, IS_ELASTIC, BACKGROUND)
        val MAIN_TYPES = ALL_TYPES.filter { it is MainType }
        val STYLE_TYPES = ALL_TYPES.filter { it is StyleType }
        val CHECKBOX_TYPES = ALL_TYPES.filter { it is CheckboxType }
        val COLOR_TYPES = ALL_TYPES.filter { it is ColorType }
        val COMPONENT_TYPES = ALL_TYPES.filter { it is CheckboxType }

        fun valueOf(code: Int): Type = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Type code unknown: $code.")
    }
}
