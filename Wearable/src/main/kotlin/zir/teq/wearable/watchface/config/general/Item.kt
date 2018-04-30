package zir.teq.wearable.watchface.config.general

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.select.color.activity.BackgroundActivity
import zir.teq.wearable.watchface.config.select.color.activity.PaletteActivity
import zir.teq.wearable.watchface.config.select.component.activity.ComponentActivity
import zir.teq.wearable.watchface.config.select.main.activity.MainConfigActivity
import zir.teq.wearable.watchface.config.select.style.activity.*
import zir.teq.wearable.watchface.config.select.wave.activity.*
import zir.teq.wearable.watchface.model.ConfigData
import java.util.concurrent.TimeUnit


open class Item(val type: Type, val activity: Class<out Activity>) {
    val pref = Zir.string(type.prefId)
    val name = Zir.string(type.nameId)
    val iconId = type.iconId ?: R.drawable.icon_dummy
    val configType = type.code

    override fun toString() = name

    companion object {
        fun createMainConfig(): List<Item> = Type.MAIN_TYPES.map { create(it) }
        fun createStyleConfig() = Type.STYLE_TYPES.map { create(it) }
        fun createWaveConfig() = Type.WAVE_TYPES.map { create(it) }
        fun createBackgroundItem(): Item = create(Type.BACKGROUND)
        private fun create(type: Type): Item { //TODO refactor
            return when (type) {
                Type.COMPONENT -> Item(type, ComponentActivity::class.java)
                Type.PALETTE -> Item(type, PaletteActivity::class.java)
                Type.STYLE -> Item(type, StyleActivity::class.java)
                Type.BACKGROUND -> Item(type, BackgroundActivity::class.java)
                Type.WAVE_PROPS -> Item(type,  WavePropsActivity::class.java)
                Type.WAVE_SPECTRUM -> Item(type, WaveSpectrumActivity::class.java)
                Type.WAVE_VELOCITY -> Item(type, WaveVelocityActivity::class.java)
                Type.WAVE_FREQUENCY -> Item(type, WaveFrequencyActivity::class.java)
                Type.WAVE_INTENSITY -> Item(type, WaveIntensityActivity::class.java)
                Type.WAVE_DARKNESS -> Item(type, WaveDarknessActivity::class.java)
                Type.WAVE_RESO -> Item(type, WaveResolutionActivity::class.java)
                Type.WAVE_AMB_RESO -> Item(type, WaveAmbientResolutionActivity::class.java)
                Type.STROKE -> Item(type, StrokeActivity::class.java)
                Type.OUTLINE -> Item(type, OutlineActivity::class.java)
                Type.GROWTH -> Item(type, GrowthActivity::class.java)
                Type.ALPHA -> Item(type, AlphaActivity::class.java)
                Type.DIM -> Item(type, DimActivity::class.java)
                Type.STACK -> Item(type, StackActivity::class.java)
                else -> Item(type, MainConfigActivity::class.java)
            }
        }

        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        val FAST_UPDATE_RATE_MS = TimeUnit.MILLISECONDS.toMillis(20)
        val NORMAL_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1)
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(inMuteMode: Boolean) = if (inMuteMode) activeUpdateRateMs() else ambientUpdateRateMs()
        private fun ambientUpdateRateMs() = if (ConfigData.isFastUpdate()) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        private fun activeUpdateRateMs() = if (ConfigData.isFastUpdate()) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
    }
}
