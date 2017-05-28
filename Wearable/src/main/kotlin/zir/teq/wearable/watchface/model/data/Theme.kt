package zir.teq.wearable.watchface.model.data

import zir.teq.wearable.watchface.R

data class Theme(val name: String, val iconId: Int, val isFastUpdate: Boolean,
                 val hands: Setting,
                 val triangles: Setting,
                 val circles: Setting,
                 val points: Setting,
                 val text: Setting) {
    companion object {
        private fun set(both: Boolean) = Setting(both, both)
        data class Setting(val active: Boolean, val ambient: Boolean)
        val PLAIN = Theme("Plain", R.drawable.theme_plain, false, set(true), set(false), set(false), set(true), set(false))
        val FIELDS = Theme("Fields", R.drawable.theme_fields, false, set(false), set(true), set(false), set(true), set(false))
        val CIRCLES = Theme("Circles", R.drawable.theme_circles, true, set(false), set(false), set(true), set(true), set(false))
        val GEOMETRY = Theme("Geometry", R.drawable.theme_geometry, true, set(false), set(true), set(true), set(true), set(false))
        val defaultTheme = PLAIN
        val ALL_THEMES = listOf(PLAIN, FIELDS, CIRCLES, GEOMETRY)
        fun createThemeOptions() = ALL_THEMES.toCollection(ArrayList())
        fun getThemeByName(name: String): Theme = ALL_THEMES.find { it.name.equals(name) } ?: defaultTheme
    }
}