package zir.teq.wearable.watchface.config.select.config

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import zir.teq.wearable.watchface.config.select.*
import zir.teq.wearable.watchface.config.select.main.MainConfigActivity
import zir.teq.wearable.watchface.model.ConfigData
import java.util.concurrent.TimeUnit

typealias ConfigItemTypes = List<ConfigItemType>
interface ConfigItemType {
    val configType: Int
}

open class Item(val type: Type, val pref: String, val name: String,
                val activity: Class<out Activity>) : ConfigItemType {
    override val configType: Int get() = type.code
    override fun toString() = name

    companion object {
        fun createMainConfig(ctx: Context): ConfigItemTypes {
            val mainTypes = Type.MAIN_TYPES.map { create(ctx, it) }
            val cbTypes = Type.CHECKBOX_TYPES.map { create(ctx, it) }
            return mainTypes + cbTypes
        }

        fun createStyleConfig(ctx: Context) = Type.STYLE_TYPES.map { create(ctx, it) }
        fun createBackgroundItem(ctx: Context): Item = create(ctx, Type.BACKGROUND)
        private fun create(ctx: Context, type: Type): Item { //TODO refactor
            val pref = ctx.getString(type.prefId)
            val name = ctx.getString(type.nameId)
            return when (type) {
                Type.COMPONENT -> Item(type, pref, name, ComponentSelectionActivity::class.java)
                Type.PALETTE -> Item(type, pref, name, PaletteSelectionActivity::class.java)
                Type.STYLE -> Item(type, pref, name, StyleSelectionActivity::class.java)
                Type.BACKGROUND -> Item(type, pref, name, BackgroundSelectionActivity::class.java)
                Type.WAVE -> Item(type, pref, name, WaveSelectionActivity::class.java)
                Type.STROKE -> Item(type, pref, name, StrokeSelectionActivity::class.java)
                Type.OUTLINE -> Item(type, pref, name, OutlineSelectionActivity::class.java)
                Type.GROWTH -> Item(type, pref, name, GrowthSelectionActivity::class.java)
                Type.ALPHA -> Item(type, pref, name, AlphaSelectionActivity::class.java)
                Type.DIM -> Item(type, pref, name, DimSelectionActivity::class.java)
                Type.STACK -> Item(type, pref, name, StackSelectionActivity::class.java)
                else -> Item(type, pref, name, MainConfigActivity::class.java)
            }
        }

        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        val FAST_UPDATE_RATE_MS = TimeUnit.MILLISECONDS.toMillis(20)
        val NORMAL_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1)
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(inMuteMode: Boolean) = if (inMuteMode) activeUpdateRateMs() else ambientUpdateRateMs()
        private fun ambientUpdateRateMs() = if (ConfigData.isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        private fun activeUpdateRateMs() = if (ConfigData.isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
    }
}
