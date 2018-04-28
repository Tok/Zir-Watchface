package zir.teq.wearable.watchface.config.general

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import zir.teq.wearable.watchface.config.select.color.activity.BackgroundActivity
import zir.teq.wearable.watchface.config.select.color.activity.PaletteActivity
import zir.teq.wearable.watchface.config.select.component.activity.ComponentActivity
import zir.teq.wearable.watchface.config.select.main.activity.MainConfigActivity
import zir.teq.wearable.watchface.config.select.style.activity.*
import zir.teq.wearable.watchface.config.select.wave.activity.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.ConfigItemTypes
import java.util.concurrent.TimeUnit


open class Item(val type: Type, val pref: String, val name: String,
                val activity: Class<out Activity>) : ConfigItemType {
    override val configType: Int get() = type.code
    override fun toString() = name

    companion object {
        fun createMainConfig(ctx: Context): ConfigItemTypes = Type.MAIN_TYPES.map { create(ctx, it) }
        fun createStyleConfig(ctx: Context) = Type.STYLE_TYPES.map { create(ctx, it) }
        fun createWaveConfig(ctx: Context) = Type.WAVE_TYPES.map { create(ctx, it) }
        fun createBackgroundItem(ctx: Context): Item = create(ctx, Type.BACKGROUND)
        private fun create(ctx: Context, type: Type): Item { //TODO refactor
            val pref = ctx.getString(type.prefId)
            val name = ctx.getString(type.nameId)
            return when (type) {
                Type.COMPONENT -> Item(type, pref, name, ComponentActivity::class.java)
                Type.PALETTE -> Item(type, pref, name, PaletteActivity::class.java)
                Type.STYLE -> Item(type, pref, name, StyleActivity::class.java)
                Type.BACKGROUND -> Item(type, pref, name, BackgroundActivity::class.java)
                Type.WAVE_PROPS -> Item(type, pref, name, WavePropsActivity::class.java)
                Type.WAVE_SPECTRUM -> Item(type, pref, name, WaveSpectrumActivity::class.java)
                Type.WAVE_VELOCITY -> Item(type, pref, name, WaveVelocityActivity::class.java)
                Type.WAVE_FREQUENCY -> Item(type, pref, name, WaveFrequencyActivity::class.java)
                Type.WAVE_INTENSITY -> Item(type, pref, name, WaveIntensityActivity::class.java)
                Type.STROKE -> Item(type, pref, name, StrokeActivity::class.java)
                Type.OUTLINE -> Item(type, pref, name, OutlineActivity::class.java)
                Type.GROWTH -> Item(type, pref, name, GrowthActivity::class.java)
                Type.ALPHA -> Item(type, pref, name, AlphaActivity::class.java)
                Type.DIM -> Item(type, pref, name, DimActivity::class.java)
                Type.STACK -> Item(type, pref, name, StackActivity::class.java)
                else -> Item(type, pref, name, MainConfigActivity::class.java)
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
