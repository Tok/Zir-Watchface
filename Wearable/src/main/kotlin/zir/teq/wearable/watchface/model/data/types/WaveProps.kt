package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.config.general.types.WaveType


data class WaveProps(val name: String, override val configId: Int, val iconId: Int?) : ComponentConfigItem {
    fun isBooleanProp() = configId == PIXELATE.configId ||
            configId == MULTIPLY.configId ||
            configId == STANDING.configId

    companion object {
        private fun create(type: WaveType) = WaveProps(Zir.string(type.nameId), type.code, type.iconId)
        private fun createCheckbox(type: Type) = WaveProps(Zir.string(type.nameId), type.code, null)
        val VELOCITY = create(Type.WAVE_VELOCITY)
        val FREQUENCY = create(Type.WAVE_FREQUENCY)
        val INTENSITY = create(Type.WAVE_INTENSITY)
        val PIXELATE = createCheckbox(Type.WAVE_IS_PIXEL)
        val MULTIPLY = createCheckbox(Type.WAVE_IS_MULTIPLY)
        val STANDING = createCheckbox(Type.WAVE_IS_STANDING)
        val ALL = listOf(VELOCITY, FREQUENCY, INTENSITY, PIXELATE, MULTIPLY, STANDING)

        private fun valueOf(viewType: Int) = ALL.find { it.configId == viewType }
        fun isBooleanProp(viewType: Int) = valueOf(viewType)?.isBooleanProp() ?: false
    }
}
