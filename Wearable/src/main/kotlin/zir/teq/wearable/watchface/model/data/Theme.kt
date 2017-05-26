package zir.teq.wearable.watchface.model.data

import zir.teq.wearable.watchface.R

data class Theme(val name: String,
                 val iconId: Int,
                 val drawCircle: Boolean,
                 val drawActiveCircles: Boolean,
                 val drawHands: Boolean,
                 val drawTriangle: Boolean,
                 val drawText: Boolean,
                 val drawPoints: Boolean) {
    companion object {
        val PLAIN = Theme("Plain", R.drawable.theme_plain, true, true, true, false, false, false)
        val FIELDS = Theme("Fields", R.drawable.theme_fields, true, false, true, true, false, false)
        val CIRCLES = Theme("Circles", R.drawable.theme_circles, true, true, false, false, false, false)
        val GEOMETRY = Theme("Geometry", R.drawable.theme_geometry, true, false, true, true, false, false)
        val defaultTheme = PLAIN
        val ALL_THEMES = listOf(PLAIN, FIELDS, CIRCLES, GEOMETRY)
        fun createThemeOptions() = ALL_THEMES.toCollection(ArrayList())
        fun getThemeByName(name: String): Theme = ALL_THEMES.find { it.name.equals(name) } ?: defaultTheme
    }
}