package zir.teq.wearable.watchface.config.general

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.types.*

abstract class Type(val code: Int, val prefId: Int, val nameId: Int) {
    abstract fun layoutId(): Int

    companion object {
        val COMPONENT = MainType(1000, R.string.saved_theme, R.string.label_components, R.drawable.icon_components)

        val PALETTE = MainType(2000, R.string.saved_palette, R.string.label_colors, R.drawable.icon_color)
        val BACKGROUND = ColorType(2100, R.string.saved_background, R.string.label_background, R.drawable.icon_background)

        val WAVE_PROPS = MainType(3000, R.string.saved_wave_props, R.string.label_wave_props, R.drawable.icon_wave)
        val WAVE_IS_OFF = CheckboxType(3010, R.string.saved_wave_is_off, R.string.label_wave_is_off)
        val WAVE_IS_PIXEL = CheckboxType(3020, R.string.saved_wave_is_pixelated, R.string.label_wave_is_pixelated)
        val WAVE_IS_MULTIPLY = CheckboxType(3030, R.string.saved_wave_is_multiply, R.string.label_wave_is_multiply)
        val WAVE_IS_INWARD = CheckboxType(3040, R.string.saved_wave_is_inward, R.string.label_wave_is_inward)
        val WAVE_IS_STANDING = CheckboxType(3050, R.string.saved_wave_is_standing, R.string.label_wave_is_standing)
        val WAVE_SPECTRUM = WaveType(3100, R.string.saved_spectrum, R.string.label_spectrum, R.drawable.icon_wave)
        val WAVE_VELOCITY = WaveType(3200, R.string.saved_wave_velocity, R.string.label_wave_velocity, R.drawable.icon_dummy)
        val WAVE_FREQUENCY = WaveType(3300, R.string.saved_wave_frequency, R.string.label_wave_frequency, R.drawable.icon_dummy)
        val WAVE_INTENSITY = WaveType(3400, R.string.saved_wave_intensity, R.string.label_wave_intensity, R.drawable.icon_dummy)

        val STYLE = MainType(4000, R.string.saved_style, R.string.label_style, R.drawable.icon_style)
        val ALPHA = StyleType(4100, R.string.saved_alpha, R.string.label_alpha, R.drawable.icon_alpha)
        val DIM = StyleType(4200, R.string.saved_dim, R.string.label_dim, R.drawable.icon_dim)
        val STACK = StyleType(4300, R.string.saved_stack, R.string.label_stack, R.drawable.icon_stack)
        val GROWTH = StyleType(4400, R.string.saved_growth, R.string.label_growth, R.drawable.icon_growth)
        val STROKE = StyleType(4500, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke)
        val OUTLINE = StyleType(4600, R.string.saved_outline, R.string.label_outline, R.drawable.icon_outline)

        val FAST_UPDATE = CheckboxType(9010, R.string.saved_fast_update, R.string.label_fast_update)
        val IS_ELASTIC = CheckboxType(9020, R.string.saved_is_elastic, R.string.label_is_elastic)

        private val ALL_TYPES = listOf(
                COMPONENT, PALETTE, BACKGROUND, WAVE_PROPS,
                WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING,
                WAVE_SPECTRUM, WAVE_VELOCITY, WAVE_FREQUENCY, WAVE_INTENSITY,
                STYLE, ALPHA, DIM, STACK, GROWTH, STROKE, OUTLINE,
                FAST_UPDATE, IS_ELASTIC)
        val MAIN_TYPES = ALL_TYPES.filter { it is MainType } + listOf(FAST_UPDATE, IS_ELASTIC)
        val WAVE_TYPES = ALL_TYPES.filter { it is WaveType } +
                listOf(WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING)
        val STYLE_TYPES = ALL_TYPES.filter { it is StyleType }

        fun valueOf(code: Int): Type = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Type code unknown: $code.")
    }
}
