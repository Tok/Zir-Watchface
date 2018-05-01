package zir.teq.wearable.watchface.config.general

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.*
import zir.teq.wearable.watchface.config.select.activity.color.BackgroundActivity
import zir.teq.wearable.watchface.config.select.activity.color.PaletteActivity
import zir.teq.wearable.watchface.config.select.activity.component.ComponentActivity
import zir.teq.wearable.watchface.config.select.activity.main.MainConfigActivity
import zir.teq.wearable.watchface.config.select.activity.main.MainStyleActivity
import zir.teq.wearable.watchface.config.select.activity.main.MainWaveActivity
import zir.teq.wearable.watchface.config.select.activity.style.*
import zir.teq.wearable.watchface.config.select.activity.wave.*

abstract class Item(val code: Int, val prefId: Int, val nameId: Int,
                    val iconId: Int = R.drawable.icon_dummy,
                    val activity: Class<out Activity> = MainConfigActivity::class.java) {
    fun layoutId(): Int = R.layout.list_item_main
    val pref = Zir.string(prefId)
    val name = Zir.string(nameId)
    val icon = Zir.drawable(iconId)

    companion object {
        val MAIN_COMPONENTS = MainItem(1000, R.string.saved_theme, R.string.label_components, R.drawable.main_icon_components, ComponentActivity::class.java)
        val MAIN_COLORS = MainItem(2000, R.string.saved_palette, R.string.label_colors, R.drawable.main_icon_color, PaletteActivity::class.java)
        val MAIN_WAVE = MainItem(3000, R.string.saved_wave_props, R.string.label_wave_props, R.drawable.main_icon_wave, MainWaveActivity::class.java)
        val MAIN_STYLE = MainItem(4000, R.string.saved_style, R.string.label_style, R.drawable.main_icon_style, MainStyleActivity::class.java)
        val MAIN_FAST_UPDATE = CheckboxItem(9010, R.string.saved_fast_update, R.string.label_fast_update)
        val MAIN_IS_ELASTIC = CheckboxItem(9020, R.string.saved_is_elastic, R.string.label_is_elastic)
        val MAIN_TYPES = listOf(MAIN_COMPONENTS, MAIN_COLORS, MAIN_WAVE, MAIN_STYLE, MAIN_FAST_UPDATE, MAIN_IS_ELASTIC)

        val COLOR_BACKGROUND = ColorItem(2100, R.string.saved_background, R.string.label_background, R.drawable.icon_background, BackgroundActivity::class.java)
        val COLOR_TYPES = listOf(COLOR_BACKGROUND)

        val WAVE_IS_OFF = CheckboxItem(3010, R.string.saved_wave_is_off, R.string.label_wave_is_off)
        val WAVE_IS_PIXEL = CheckboxItem(3020, R.string.saved_wave_is_pixelated, R.string.label_wave_is_pixelated)
        val WAVE_IS_MULTIPLY = CheckboxItem(3030, R.string.saved_wave_is_multiply, R.string.label_wave_is_multiply)
        val WAVE_IS_INWARD = CheckboxItem(3040, R.string.saved_wave_is_inward, R.string.label_wave_is_inward)
        val WAVE_IS_STANDING = CheckboxItem(3050, R.string.saved_wave_is_standing, R.string.label_wave_is_standing)
        val WAVE_SPECTRUM = WaveItem(3100, R.string.saved_spectrum, R.string.label_spectrum, R.drawable.wave_icon_spectrum, WaveSpectrumActivity::class.java)
        val WAVE_VELOCITY = WaveItem(3200, R.string.saved_wave_velocity, R.string.label_wave_velocity, R.drawable.wave_icon_velocity, WaveVelocityActivity::class.java)
        val WAVE_FREQUENCY = WaveItem(3300, R.string.saved_wave_frequency, R.string.label_wave_frequency, R.drawable.wave_icon_frequency, WaveFrequencyActivity::class.java)
        val WAVE_INTENSITY = WaveItem(3400, R.string.saved_wave_intensity, R.string.label_wave_intensity, R.drawable.wave_icon_intensity, WaveIntensityActivity::class.java)
        val WAVE_DARKNESS = WaveItem(3450, R.string.saved_wave_darkness, R.string.label_wave_darkness, R.drawable.wave_icon_darkness, WaveDarknessActivity::class.java)
        val WAVE_RESO = WaveItem(3500, R.string.saved_wave_resolution, R.string.label_wave_resolution, R.drawable.wave_icon_resolution, WaveResolutionActivity::class.java)
        val WAVE_AMB_RESO = WaveItem(3600, R.string.saved_wave_ambient_resolution, R.string.label_wave_ambient_resolution, R.drawable.wave_icon_ambient_resolution, WaveAmbientResolutionActivity::class.java)
        val WAVE_TYPES = listOf(WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING,
                WAVE_SPECTRUM, WAVE_VELOCITY, WAVE_FREQUENCY, WAVE_INTENSITY,
                WAVE_DARKNESS, WAVE_RESO, WAVE_AMB_RESO)

        val STYLE_ALPHA = StyleItem(4100, R.string.saved_style_alpha, R.string.label_alpha, R.drawable.style_icon_alpha, StyleAlphaActivity::class.java)
        val STYLE_DIM = StyleItem(4200, R.string.saved_style_dim, R.string.label_dim, R.drawable.style_icon_dim, StyleDimActivity::class.java)
        val STYLE_STACK = StyleItem(4300, R.string.saved_style_stack, R.string.label_stack, R.drawable.style_icon_stack, StyleStackActivity::class.java)
        val STYLE_GROWTH = StyleItem(4400, R.string.saved_style_growth, R.string.label_growth, R.drawable.style_icon_growth, StyleGrowthActivity::class.java)
        val STYLE_STROKE = StyleItem(4500, R.string.saved_style_stroke, R.string.label_stroke, R.drawable.style_icon_stroke, StyleStrokeActivity::class.java)
        val STYLE_OUTLINE = StyleItem(4600, R.string.saved_style_outline, R.string.label_outline, R.drawable.style_icon_outline, StyleOutlineActivity::class.java)
        val STYLE_TYPES = listOf(STYLE_ALPHA, STYLE_DIM, STYLE_STACK, STYLE_GROWTH, STYLE_STROKE, STYLE_OUTLINE)

        private val ALL_TYPES = MAIN_TYPES + COLOR_TYPES + WAVE_TYPES + STYLE_TYPES
        fun valueOf(code: Int): Item = ALL_TYPES.find { it.code == code }
                ?: throw IllegalArgumentException("Item code unknown: $code.")
    }
}
