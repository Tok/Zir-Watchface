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
import zir.teq.wearable.watchface.config.general.ConfigItem
import zir.teq.wearable.watchface.model.types.State
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT


data class Theme(override val name: String, val iconId: Int, val map: Map<String, Boolean>,
                 override val configId: Int = 0) : ConfigItem {
    fun get(pair: Pair<Component, State>) = get(pair.first, pair.second)
    private fun get(comp: Component, state: State) = get(Component.createKey(comp, state))
    private fun get(key: String): Boolean = map.get(key) ?: false

    companion object {
        var INSTANCE = Theme("Instance", R.drawable.icon_dummy,
                (Component.KEYS.map { it to false }).toMap())
        private val POINTS_ONLY = Theme("Points", R.drawable.comp_points, (Component.KEYS.map {
            when (it) {
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val MINIMAL = Theme("Minimal", R.drawable.comp_minimal, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val PLAIN = Theme("Plain", R.drawable.comp_plain, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val SHAPES = Theme("Shape", R.drawable.comp_shape, (Component.KEYS.map {
            when (it) {
                Component.createKey(SHAPE, ACTIVE) -> it to true
                Component.createKey(SHAPE, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val ORIGINAL = Theme("Original", R.drawable.comp_original, (Component.KEYS.map {
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
        private val FIELDS = Theme("Fields", R.drawable.comp_fields, (Component.KEYS.map {
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
        private val CIRCLES = Theme("Circles", R.drawable.comp_circles, (Component.KEYS.map {
            when (it) {
                Component.createKey(CIRCLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }
        }).toMap())
        private val GEOMETRY = Theme("Geometry", R.drawable.comp_geometry, (Component.KEYS.map {
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
        fun getByName(name: String): Theme = ALL.find { it.name.equals(name) } ?: default

        val PREF = "SHARED_THEME"
        val EXTRA = this::class.java.getPackage().name + PREF

        fun saveComponentStates(theme: Theme) {
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