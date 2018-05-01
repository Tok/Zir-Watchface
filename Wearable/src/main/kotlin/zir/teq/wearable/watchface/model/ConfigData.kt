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
import zir.teq.wearable.watchface.model.setting.wave.WaveSpectrum
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.model.data.types.State
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

    fun waveSpectrum() = WaveSpectrum.getByName(prefString(R.string.saved_spectrum, WaveSpectrum.default.name))
    fun waveIsOff() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_off), false)
    fun waveIsPixelated() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_pixelated), false)
    fun waveIsMultiply() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_multiply), false)
    fun waveOperator() = if (!waveIsMultiply()) Operator.ADD else Operator.MULTIPLY
    fun waveIsInward() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_inward), false)
    fun waveIsStanding() = prefs.getBoolean(Zir.string(R.string.saved_wave_is_standing), false)

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
