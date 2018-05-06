package zir.teq.wearable.watchface.model.setting.component

import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.CIRCLE
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.HAND
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.TRIANGLE
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.METAS
import zir.teq.wearable.watchface.model.types.State
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT


data class Components(val name: String, val map: Map<String, Boolean>) {
    fun get(pair: Pair<Component, State>) = get(pair.first, pair.second)
    private fun get(comp: Component, state: State) = get(Component.createKey(comp, state))
    private fun get(key: String): Boolean = map.get(key) ?: false

    companion object {
        val POINTS_ONLY = Components("Points", (Component.KEYS.map {
            when (it) {
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val MINIMAL = Components("Minimal", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val PLAIN = Components("Plain", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val META = Components("Meta", (Component.KEYS.map {
            when (it) {
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                Component.createKey(METAS, ACTIVE) -> it to true
                Component.createKey(METAS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val SHAPES = Components("Shape", (Component.KEYS.map {
            when (it) {
                Component.createKey(SHAPE, ACTIVE) -> it to true
                Component.createKey(SHAPE, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val ORIGINAL = Components("Original", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, AMBIENT) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val FIELDS = Components("Fields", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val SHAPE_FIELDS = Components("Shape Fields", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                Component.createKey(SHAPE, ACTIVE) -> it to true
                Component.createKey(SHAPE, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val CIRCLES = Components("Circles", (Component.KEYS.map {
            when (it) {
                Component.createKey(CIRCLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val GEOMETRY = Components("Geometry", (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        val default = PLAIN

        var INSTANCE = Components("Instance", (Component.KEYS.map { it to false }).toMap())
        fun saveComponentState(key: String, value: Boolean) {
            val instanceMap = INSTANCE.map.toMutableMap()
            instanceMap.put(key, value)
            INSTANCE = INSTANCE.copy(map = instanceMap)
            val editor = ConfigData.prefs.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun saveComponentStates(theme: Components) {
            INSTANCE = theme.copy(name = "Instance")
            val editor = ConfigData.prefs.edit()
            Component.KEYS.forEach {
                editor.putBoolean(it, theme.map.get(it) ?: false)
            }
            editor.apply()
        }
        fun loadComponentStates() {
            val map = Component.KEYS.map {
                it to ConfigData.prefs.getBoolean(it, false)
            }.toMap()
            INSTANCE = Components("Instance", map)
        }

        fun getComponentState(comp: Component, state: State): Boolean {
            val key = Component.createKey(comp, state)
            return INSTANCE.map.get(key) ?: false
        }
    }
}
