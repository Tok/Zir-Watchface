package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type


data class WaveProps(val name: String, override val configId: Int) : ComponentConfigItem {
    companion object {
        val PIXELATE = WaveProps(Zir.string(Type.WAVE_IS_PIXEL.nameId), Type.WAVE_IS_PIXEL.code)
        val MULTIPLY = WaveProps(Zir.string(Type.WAVE_IS_MULTIPLY.nameId), Type.WAVE_IS_MULTIPLY.code)
        val ALL = listOf(PIXELATE, MULTIPLY)
    }
}
