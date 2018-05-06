package zir.teq.wearable.watchface.model.setting.component

import zir.teq.wearable.watchface.model.setting.ConfigItem
import zir.teq.wearable.watchface.model.types.State


data class Component(override val name: String, override val configId: Int) : ConfigItem {
    val activeKey = createKey(this, State.ACTIVE)
    val ambientKey = createKey(this, State.AMBIENT)

    companion object {
        private val dbLow = 10
        private val dbHigh = 80
        fun isDoubleBoolean(configId: Int) = configId >= dbLow && configId <= dbHigh
        fun createKey(comp: Component, state: State) = comp.name.toUpperCase() + ":" + state.name
        val HAND = Component("Hand", dbLow)
        val TRIANGLE = Component("Triangle", 20)
        val CIRCLE = Component("Circle", 30)
        val POINTS = Component("Points", 40)
        val METAS = Component("Meta Balls", 50)
        val TEXT = Component("Text", 60)
        val SHAPE = Component("Shape", 70)
        val WAVE = Component("Wave Spectrum", dbHigh)
        val ALL = listOf(HAND, TRIANGLE, CIRCLE, POINTS, METAS, TEXT, SHAPE, WAVE)
        val KEYS = ALL.flatMap { comp -> State.values().map { createKey(comp, it) } }
        fun valueOf(configId: Int): ConfigItem {
            return ALL.find { it.configId == configId }
                    ?: throw IllegalArgumentException("Component ID unknown: $configId.")
        }
    }
}
