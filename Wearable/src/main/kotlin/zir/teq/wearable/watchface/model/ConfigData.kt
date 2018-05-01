package zir.teq.wearable.watchface.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Typeface
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.data.settings.color.Background
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.settings.component.Theme
import zir.teq.wearable.watchface.model.data.settings.style.*
import zir.teq.wearable.watchface.model.data.settings.wave.Spectrum
import zir.teq.wearable.watchface.model.data.types.*
import zir.teq.wearable.watchface.model.setting.*
import java.util.concurrent.TimeUnit

object ConfigData {
    val res: Resources = Zir.res()
    val prefs: SharedPreferences = Zir.ctx().getSharedPreferences(
            Zir.string(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)

    private fun prefString(pref: Int, default: String) = prefs.getString(Zir.string(pref), default)

    fun isOn(key: Pair<Component, State>): Boolean = Theme.INSTANCE.get(key)

    fun isFastUpdate() = prefs.getBoolean(Zir.string(R.string.saved_fast_update), true)
    fun isElastic() = prefs.getBoolean(Zir.string(R.string.saved_is_elastic), false)
    var isElasticOutline: Boolean = true //TODO tune
    var isElasticColor: Boolean = true //TODO tune

    fun palette() = Palette.create(prefString(R.string.saved_palette, Palette.default().name))
    fun background() = Background.getByName(prefString(R.string.saved_background, Background.default.name))

    fun waveSpectrum() = Spectrum.getByName(prefString(R.string.saved_spectrum, Spectrum.default.name))
    fun waveIsOff() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_off), false)
    fun waveIsPixelated() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_pixelated), false)
    fun waveIsMultiply() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_multiply), false)
    fun waveOperator() = if (!waveIsMultiply()) Operator.ADD else Operator.MULTIPLY
    fun waveIsInward() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_inward), false)
    fun waveIsStanding() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_standing), false)
    fun waveVelocity() = WaveVelocity.getByName(prefString(R.string.saved_wave_velocity, WaveVelocity.default.name))
    fun waveFrequency() = WaveFrequency.getByName(prefString(R.string.saved_wave_frequency, WaveFrequency.default.name))
    fun waveIntensity() = WaveIntensity.getByName(prefString(R.string.saved_wave_intensity, WaveIntensity.default.name))
    fun waveDarkness() = WaveDarkness.getByName(prefString(R.string.saved_wave_darkness, WaveDarkness.default.name))
    fun waveAmbientResolution() = WaveAmbientResolution.getByName(prefString(R.string.saved_wave_ambient_resolution, WaveAmbientResolution.default.name))
    fun waveResolution() = WaveResolution.getByName(prefString(R.string.saved_wave_resolution, WaveResolution.default.name))

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

    val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
    val FAST_UPDATE_RATE_MS = TimeUnit.MILLISECONDS.toMillis(20)
    val NORMAL_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1)
    val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)

    fun updateRateMs(inMuteMode: Boolean) = if (inMuteMode) activeUpdateRateMs() else ambientUpdateRateMs()
    private fun ambientUpdateRateMs() = if (ConfigData.isFastUpdate()) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
    private fun activeUpdateRateMs() = if (ConfigData.isFastUpdate()) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
}
