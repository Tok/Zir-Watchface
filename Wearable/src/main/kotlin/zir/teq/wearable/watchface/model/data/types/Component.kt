package zir.teq.wearable.watchface.model.data.types

enum class State { ACTIVE, AMBIENT }

interface ComponentConfigItem {
    val configId: Int
}

data class Component(val name: String, override val configId: Int) : ComponentConfigItem {
    val activeKey = createKey(this, State.ACTIVE)
    val ambientKey = createKey(this, State.AMBIENT)

    companion object {
        private val dbLow = 10
        private val dbHigh = 70
        fun isDoubleBoolean(configId: Int) = configId >= dbLow && configId <= dbHigh
        fun createKey(comp: Component, state: State) = comp.name.toUpperCase() + ":" + state.name
        val HAND = Component("Hand", dbLow)
        val TRIANGLE = Component("Triangle", 20)
        val CIRCLE = Component("Circle", 30)
        val POINTS = Component("Points", 40)
        val TEXT = Component("Text", 50)
        val SHAPE = Component("Shape", 60)
        val WAVE = Component("Wave", dbHigh)
        val ALL = listOf(HAND, TRIANGLE, CIRCLE, POINTS, TEXT, SHAPE, WAVE)
        val KEYS = ALL.flatMap { comp -> State.values().map { createKey(comp, it) } }
        fun valueOf(configId: Int): ComponentConfigItem {
            return ALL.find { it.configId == configId }
                    ?: throw IllegalArgumentException("Component ID unknown: $configId.")
        }
    }
}
