package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.ConfigItem
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.types.WaveType


data class WaveProps(override val name: String, override val configId: Int, val iconId: Int?) : ConfigItem {
    fun isBooleanProp() = configId == OFF.configId ||
            configId == PIXELATE.configId ||
            configId == MULTIPLY.configId ||
            configId == INWARD.configId ||
            configId == STANDING.configId

    companion object {
        private fun create(type: WaveType) = WaveProps(Zir.string(type.nameId), type.code, type.iconId)
        private fun createCheckbox(type: Item) = WaveProps(Zir.string(type.nameId), type.code, null)
        val SPECTRUM = create(Item.WAVE_SPECTRUM)
        val VELOCITY = create(Item.WAVE_VELOCITY)
        val FREQUENCY = create(Item.WAVE_FREQUENCY)
        val INTENSITY = create(Item.WAVE_INTENSITY)
        val DARKNESS = create(Item.WAVE_DARKNESS)
        val RESOLUTION = create(Item.WAVE_RESO)
        val AMBIENT_RESOLUTION = create(Item.WAVE_AMB_RESO)
        val OFF = createCheckbox(Item.WAVE_IS_OFF)
        val PIXELATE = createCheckbox(Item.WAVE_IS_PIXEL)
        val MULTIPLY = createCheckbox(Item.WAVE_IS_MULTIPLY)
        val INWARD = createCheckbox(Item.WAVE_IS_INWARD)
        val STANDING = createCheckbox(Item.WAVE_IS_STANDING)

        val ALL = listOf(SPECTRUM, VELOCITY, FREQUENCY, INTENSITY, DARKNESS,
                RESOLUTION, AMBIENT_RESOLUTION,
                OFF, PIXELATE, MULTIPLY, INWARD, STANDING)

        private fun valueOf(viewType: Int) = ALL.find { it.configId == viewType }
        fun isBooleanProp(viewType: Int) = valueOf(viewType)?.isBooleanProp() ?: false
    }
}
