package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.model.data.types.ComponentConfigItem


data class WaveDarkness(val name: String, val value: Float) : ComponentConfigItem {
    override val configId = Type.WAVE_DARKNESS.code
    val pref = Zir.string(R.string.saved_wave_darkness)
    val iconId = R.drawable.icon_wave_darkness

    companion object {
        val OFF = WaveDarkness("Off", 0F)
        val _1 = WaveDarkness("10%", 0.1F)
        val _2 = WaveDarkness("20%", 0.2F)
        val _3 = WaveDarkness("30%", 0.3F)
        val _4 = WaveDarkness("40%", 0.4F)
        val _5 = WaveDarkness("50%", 0.5F)
        val _6 = WaveDarkness("60%", 0.6F)
        val _7 = WaveDarkness("70%", 0.7F)
        val _8 = WaveDarkness("80%", 0.8F)
        val _9 = WaveDarkness("90%", 0.9F)

        val ALL = listOf(OFF, _1, _2, _3, _4, _5, _6, _7, _8, _9)

        val default = OFF
        fun getByName(name: String): WaveDarkness = ALL.find { it.name.equals(name) } ?: default
    }
}
