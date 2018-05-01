package zir.teq.wearable.watchface.config.general.types

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.activity.wave.*


class WaveItem(prefId: Int, nameId: Int, iconId: Int, activity: Class<out Activity>) :
        Item(prefId, nameId, iconId, activity) {
    companion object {
        val WAVE_IS_OFF = CheckboxItem(R.string.saved_wave_is_off, R.string.label_wave_is_off)
        val WAVE_IS_PIXEL = CheckboxItem(R.string.saved_wave_is_pixelated, R.string.label_wave_is_pixelated)
        val WAVE_IS_MULTIPLY = CheckboxItem(R.string.saved_wave_is_multiply, R.string.label_wave_is_multiply)
        val WAVE_IS_INWARD = CheckboxItem(R.string.saved_wave_is_inward, R.string.label_wave_is_inward)
        val WAVE_IS_STANDING = CheckboxItem(R.string.saved_wave_is_standing, R.string.label_wave_is_standing)
        val WAVE_SPECTRUM = WaveItem(R.string.saved_spectrum, R.string.label_spectrum, R.drawable.wave_icon_spectrum, WaveSpectrumActivity::class.java)
        val WAVE_VELOCITY = WaveItem(R.string.saved_wave_velocity, R.string.label_wave_velocity, R.drawable.wave_icon_velocity, WaveVelocityActivity::class.java)
        val WAVE_FREQUENCY = WaveItem(R.string.saved_wave_frequency, R.string.label_wave_frequency, R.drawable.wave_icon_frequency, WaveFrequencyActivity::class.java)
        val WAVE_INTENSITY = WaveItem(R.string.saved_wave_intensity, R.string.label_wave_intensity, R.drawable.wave_icon_intensity, WaveIntensityActivity::class.java)
        val WAVE_DARKNESS = WaveItem(R.string.saved_wave_darkness, R.string.label_wave_darkness, R.drawable.wave_icon_darkness, WaveDarknessActivity::class.java)
        val WAVE_RESO = WaveItem(R.string.saved_wave_resolution, R.string.label_wave_resolution, R.drawable.wave_icon_resolution, WaveResolutionActivity::class.java)
        val WAVE_AMB_RESO = WaveItem(R.string.saved_wave_ambient_resolution, R.string.label_wave_ambient_resolution, R.drawable.wave_icon_ambient_resolution, WaveAmbientResolutionActivity::class.java)
        val all = listOf(WAVE_IS_OFF, WAVE_IS_PIXEL, WAVE_IS_MULTIPLY, WAVE_IS_INWARD, WAVE_IS_STANDING,
                WAVE_SPECTRUM, WAVE_VELOCITY, WAVE_FREQUENCY, WAVE_INTENSITY,
                WAVE_DARKNESS, WAVE_RESO, WAVE_AMB_RESO)
    }
}
