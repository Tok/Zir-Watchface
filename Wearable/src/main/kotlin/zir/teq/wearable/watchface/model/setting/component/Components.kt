package zir.teq.wearable.watchface.model.setting.component

import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.types.Component
import zir.teq.wearable.watchface.model.types.Component.Companion.CIRCLE
import zir.teq.wearable.watchface.model.types.Component.Companion.HAND
import zir.teq.wearable.watchface.model.types.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.types.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.types.Component.Companion.TRIANGLE
import zir.teq.wearable.watchface.model.setting.ConfigItem
import zir.teq.wearable.watchface.model.types.State
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT


data class Components(override val name: String, val iconId: Int, val map: Map<String, Boolean>,
                      override val configId: Int = 0) : ConfigItem {
                      //override val configId: Int = 0) : Setting {
    fun get(pair: Pair<Component, State>) = get(pair.first, pair.second)
    private fun get(comp: Component, state: State) = get(Component.createKey(comp, state))
    private fun get(key: String): Boolean = map.get(key) ?: false

    companion object {
        var INSTANCE = Components("Instance", R.drawable.icon_dummy,
                (Component.KEYS.map { it to false }).toMap())
        private val POINTS_ONLY = Components("Points", R.drawable.comp_points, (Component.KEYS.map {
            when (it) {
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val MINIMAL = Components("Minimal", R.drawable.comp_minimal, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val PLAIN = Components("Plain", R.drawable.comp_plain, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val SHAPES = Components("Shape", R.drawable.comp_shape, (Component.KEYS.map {
            when (it) {
                Component.createKey(SHAPE, ACTIVE) -> it to true
                Component.createKey(SHAPE, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val ORIGINAL = Components("Original", R.drawable.comp_original, (Component.KEYS.map {
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
        private val FIELDS = Components("Fields", R.drawable.comp_fields, (Component.KEYS.map {
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
        private val CIRCLES = Components("Circles", R.drawable.comp_circles, (Component.KEYS.map {
            when (it) {
                Component.createKey(CIRCLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val GEOMETRY = Components("Geometry", R.drawable.comp_geometry, (Component.KEYS.map {
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
        val default = INSTANCE
        val ALL = listOf(POINTS_ONLY, MINIMAL, PLAIN, SHAPES, ORIGINAL, FIELDS, CIRCLES, GEOMETRY)
        fun getByName(name: String): Components = ALL.find { it.name.equals(name) } ?: default

        val PREF = "SHARED_THEME"
        val EXTRA = this::class.java.getPackage().name + PREF

        fun saveComponentStates(theme: Components) {
            val editor = ConfigData.prefs.edit()
            editor.putString(PREF, INSTANCE.name)
            Log.d(TAG, "theme: $theme, Component.KEYS: " + Component.KEYS)
            INSTANCE = theme.copy(name = INSTANCE.name)
            Component.KEYS.forEach {
                editor.putBoolean(it, theme.map.get(it) ?: false)
            }
            editor.apply()
        }

        fun isOn(key: String) = ConfigData.prefs.getBoolean(key, false)
        private fun savedComponentStates() = Component.KEYS.map { it to isOn(it) }.toMap()
        private val TAG = this::class.java.simpleName
    }
}
