package zir.teq.wearable.watchface.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.data.settings.color.Background
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.settings.component.Theme
import zir.teq.wearable.watchface.model.data.settings.style.*
import zir.teq.wearable.watchface.model.data.settings.wave.Wave
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.model.data.types.WaveFrequency
import zir.teq.wearable.watchface.model.data.types.WaveIntensity
import zir.teq.wearable.watchface.model.data.types.WaveVelocity

object ConfigData {
    val res: Resources = Zir.res()
    val prefs: SharedPreferences = Zir.ctx().getSharedPreferences(
            Zir.string(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)

    private fun prefString(pref: Int, default: String) = prefs.getString(Zir.string(pref), default)

    fun theme() = Theme.getByName(prefs.getString(Zir.string(R.string.saved_theme), Theme.default.name))
    fun isFastUpdate() = prefs.getBoolean(Zir.string(R.string.saved_fast_update), true)
    fun isElastic() = prefs.getBoolean(Zir.string(R.string.saved_is_elastic), false)
    var isElasticOutline: Boolean = true //TODO tune
    var isElasticColor: Boolean = true //TODO tune

    fun palette() = Palette.create(prefString(R.string.saved_palette, Palette.default().name))
    fun background() = Background.getByName(prefString(R.string.saved_background, Background.default.name))

    fun wave() = Wave.getByName(prefString(R.string.saved_wave, Wave.default.name))
    fun waveIsPixelated() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_pixelated), false)
    fun waveIsMultiply() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_multiply), false)
    fun waveOperator() = if (!waveIsMultiply()) Operator.ADD else Operator.MULTIPLY
    fun waveIsStanding() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_standing), false)
    fun waveVelocity() = WaveVelocity.getByName(prefString(R.string.saved_wave_velocity, WaveVelocity.default.name))
    fun waveFrequency() = WaveFrequency.getByName(prefString(R.string.saved_wave_frequency, WaveFrequency.default.name))
    fun waveIntensity() = WaveIntensity.getByName(prefString(R.string.saved_wave_intensity, WaveIntensity.default.name))

    fun style() = Style(savedAlpha(), savedDim(), savedStack(), savedStroke(), savedGrowth(), savedOutline())
    private fun savedAlpha() = Alpha.getByName(prefString(R.string.saved_alpha, Alpha.default.name))
    private fun savedDim() = Dim.getByName(prefString(R.string.saved_dim, Dim.default.name))
    private fun savedStack() = Stack.getByName(prefString(R.string.saved_stack, Stack.default.name))
    private fun savedStroke() = Stroke.create(prefString(R.string.saved_stroke, Stroke.default().name))
    private fun savedGrowth() = Growth.create(prefString(R.string.saved_growth, Growth.default().name))
    private fun savedOutline() = Outline.create(prefString(R.string.saved_outline, Outline.default().name))

    val isAntiAlias = true
    var isAmbient = false
    var isMute = false
}
