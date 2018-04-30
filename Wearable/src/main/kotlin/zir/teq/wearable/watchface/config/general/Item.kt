package zir.teq.wearable.watchface.config.general

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.*
import zir.teq.wearable.watchface.config.select.color.activity.BackgroundActivity
import zir.teq.wearable.watchface.config.select.color.activity.PaletteActivity
import zir.teq.wearable.watchface.config.select.component.activity.ComponentActivity
import zir.teq.wearable.watchface.config.select.main.activity.MainConfigActivity
import zir.teq.wearable.watchface.config.select.style.activity.*
import zir.teq.wearable.watchface.config.select.wave.activity.*

abstract class Item(val code: Int, val prefId: Int, val nameId: Int,
                    val iconId: Int = R.drawable.icon_dummy,
                    val activity: Class<out Activity> = MainConfigActivity::class.java) {
    fun layoutId(): Int = R.layout.list_item_main
    val pref = Zir.string(prefId)
    val name = Zir.string(nameId)
    val icon = Zir.drawable(iconId)
    val configType = code

    companion object {
        val COMPONENT = MainItem(1000, R.string.saved_theme, R.string.label_components, R.drawable.icon_components, ComponentActivity::class.java)

        val PALETTE = MainItem(2000, R.string.saved_palette, R.string.label_colors, R.drawable.icon_color, PaletteActivity::class.java)
        val BACKGROUND = ColorItem(2100, R.string.saved_background, R.string.label_background, R.drawable.icon_background, BackgroundActivity::class.java)

        val WAVE_PROPS = MainItem(3000, R.string.saved_wave_props, R.string.label_wave_props, R.drawable.icon_wave, WavePropsActivity::class.java)
        val WAVE_IS_OFF = CheckboxItem(3010, R.string.saved_wave_is_off, R.string.label_wave_is_off)
        val WAVE_IS_PIXEL = CheckboxItem(3020, R.string.saved_wave_is_pixelated, R.string.label_wave_is_pixelated)
        val WAVE_IS_MULTIPLY = CheckboxItem(3030, R.string.saved_wave_is_multiply, R.string.label_wave_is_multiply)
        val WAVE_IS_INWARD = CheckboxItem(3040, R.string.saved_wave_is_inward, R.string.label_wave_is_inward)
        val WAVE_IS_STANDING = CheckboxItem(3050, R.string.saved_wave_is_standing, R.string.label_wave_is_standing)
        val WAVE_SPECTRUM = WaveItem(3100, R.string.saved_spectrum, R.string.label_spectrum, R.drawable.icon_wave_spectrum, WaveSpectrumActivity::class.java)
        val WAVE_VELOCITY = WaveItem(3200, R.string.saved_wave_velocity, R.string.label_wave_velocity, R.drawable.icon_wave_velocity, WaveVelocityActivity::class.java)
        val WAVE_FREQUENCY = WaveItem(3300, R.string.saved_wave_frequency, R.string.label_wave_frequency, R.drawable.icon_wave_frequency, WaveFrequencyActivity::class.java)
        val WAVE_INTENSITY = WaveItem(3400, R.string.saved_wave_intensity, R.string.label_wave_intensity, R.drawable.icon_wave_intensity, WaveIntensityActivity::class.java)
        val WAVE_DARKNESS = WaveItem(3450, R.string.saved_wave_darkness, R.string.label_wave_darkness, R.drawable.icon_wave_darkness, WaveDarknessActivity::class.java)
        val WAVE_RESO = WaveItem(3500, R.string.saved_wave_resolution, R.string.label_wave_resolution, R.drawable.icon_wave_resolution, WaveResolutionActivity::class.java)
        val WAVE_AMB_RESO = WaveItem(3600, R.string.saved_wave_ambient_resolution, R.string.label_wave_ambient_resolution, R.drawable.icon_wave_ambient_resolution, WaveAmbientResolutionActivity::class.java)

        val STYLE = MainItem(4000, R.string.saved_style, R.string.label_style, R.drawable.icon_style, StyleActivity::class.java)
        val ALPHA = StyleItem(4100, R.string.saved_alpha, R.string.label_alpha, R.drawable.icon_alpha, AlphaActivity::class.java)
        val DIM = StyleItem(4200, R.string.saved_dim, R.string.label_dim, R.drawable.icon_dim, DimActivity::class.java)
        val STACK = StyleItem(4300, R.string.saved_stack, R.string.label_stack, R.drawable.icon_stack, StackActivity::class.java)
        val GROWTH = StyleItem(4400, R.string.saved_growth, R.string.label_growth, R.drawable.icon_growth, GrowthActivity::class.java)
        val STROKE = StyleItem(4500, R.string.saved_stroke, R.string.label_stroke, R.drawable.icon_stroke, StrokeActivity::class.java)
        val OUTLINE = StyleItem(4600, R.string.saved_outline, R.string.label_outline, R.drawable.icon_outline, OutlineActivity::class.java)

        val FAST_UPDATE = CheckboxItem(9010, R.string.saved_fast_update, R.string.label_fast_update)
        val IS_ELASTIC = CheckboxItem(9020, R.string.saved_is_elastic, R.string.label_is_elastic)

        private val ALL_TYPES = listOf(
                COMPONENT, PALETTE, BACKGROUND, WAVE_PROPS,
                WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING,
                WAVE_SPECTRUM, WAVE_VELOCITY, WAVE_FREQUENCY, WAVE_INTENSITY, WAVE_INTENSITY,
                WAVE_RESO, WAVE_AMB_RESO,
                STYLE, ALPHA, DIM, STACK, GROWTH, STROKE, OUTLINE,
                FAST_UPDATE, IS_ELASTIC)

        val MAIN_TYPES = ALL_TYPES.filter { it is MainItem } + listOf(FAST_UPDATE, IS_ELASTIC)
        val STYLE_TYPES = ALL_TYPES.filter { it is StyleItem }
        val WAVE_TYPES = ALL_TYPES.filter { it is WaveItem } +
                listOf(WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING)

        fun valueOf(code: Int): Item = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Item code unknown: $code.")
    }
}
