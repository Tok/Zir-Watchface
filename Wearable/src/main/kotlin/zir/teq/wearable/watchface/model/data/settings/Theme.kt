package zir.teq.wearable.watchface.model.data.settings


import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.model.data.types.Component.Companion.CIRCLE
import zir.teq.wearable.watchface.model.data.types.Component.Companion.HAND
import zir.teq.wearable.watchface.model.data.types.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.data.types.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.data.types.Component.Companion.TRIANGLE
import zir.teq.wearable.watchface.model.data.types.ComponentConfigItem
import zir.teq.wearable.watchface.model.data.types.State
import zir.teq.wearable.watchface.model.data.types.State.ACTIVE
import zir.teq.wearable.watchface.model.data.types.State.AMBIENT


data class Theme(val name: String, val iconId: Int, val map: Map<String, Boolean>,
                 override val configId: Int = 0): ComponentConfigItem {
    fun get(pair: Pair<Component, State>) = get(pair.first, pair.second)
    fun get(comp: Component, state: State) = get(Component.createKey(comp, state))
    fun get(key: String): Boolean = map.get(key) ?: false
    companion object {
        val PLAIN = Theme("Plain", R.drawable.theme_plain, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }}).toMap())
        val SHAPES = Theme("Shape", R.drawable.icon_dummy, (Component.KEYS.map {
            when (it) {
                Component.createKey(SHAPE, ACTIVE) -> it to true
                Component.createKey(SHAPE, AMBIENT) -> it to true
                else -> it to false
            }}).toMap())
        val ORIGINAL = Theme("Original", R.drawable.theme_default, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, AMBIENT) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }}).toMap())
        val FIELDS = Theme("Fields", R.drawable.theme_fields, (Component.KEYS.map {
            when (it) {
                Component.createKey(HAND, ACTIVE) -> it to true
                Component.createKey(HAND, AMBIENT) -> it to true
                Component.createKey(TRIANGLE, ACTIVE) -> it to true
                Component.createKey(TRIANGLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }}).toMap())
        val CIRCLES = Theme("Circles", R.drawable.theme_circles, (Component.KEYS.map {
            when (it) {
                Component.createKey(CIRCLE, ACTIVE) -> it to true
                Component.createKey(CIRCLE, AMBIENT) -> it to true
                Component.createKey(POINTS, ACTIVE) -> it to true
                Component.createKey(POINTS, AMBIENT) -> it to true
                else -> it to false
            }}).toMap())
        val GEOMETRY = Theme("Geometry", R.drawable.theme_geometry, (Component.KEYS.map {
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
            }}).toMap())
        val default = ORIGINAL
        val ALL = listOf(PLAIN, SHAPES, ORIGINAL, FIELDS, CIRCLES, GEOMETRY)
        fun getByName(name: String): Theme = ALL.find { it.name.equals(name) } ?: default

        val PREF = "SHARED_THEME"
        val EXTRA = this::class.java.getPackage().name + PREF

        fun saveComponentStates(theme: Theme) {
            ConfigData.theme = theme
            val editor = ConfigData.prefs.edit()
            editor.putString(PREF, theme.name)
            Log.d(TAG, "theme: $theme, Component.KEYS: " + Component.KEYS)
            Component.KEYS.forEach {
                editor.putBoolean(it, theme.map.get(it) ?: false)
            }
            editor.apply()
        }

        fun isOn(key: String) = ConfigData.prefs.getBoolean(key, false)
        fun loadComponentStates(name: String, iconId: Int) = Theme(name, iconId, savedComponentStates())
        private fun savedComponentStates() = Component.KEYS.map { it to isOn(it) }.toMap()
        private val TAG = this::class.java.simpleName
    }
}
