package zir.teq.wearable.watchface.model.data

data class Theme(val name: String,
                 val drawCircle: Boolean,
                 val drawActiveCircles: Boolean,
                 val drawHands: Boolean,
                 val drawTriangle: Boolean,
                 val drawText: Boolean,
                 val drawPoints: Boolean) {
    companion object {
        val PLAIN = Theme("Plain", true, true, true, false, false, false)
        val FIELDS = Theme("Fields", true, false, true, true, false, false)
        val CIRCLES = Theme("Circles", true, true, false, false, false, false)
        val GEOMETRY = Theme("Geometry", true, false, true, true, false, false)
        val defaultTheme = PLAIN
        val ALL_THEMES = listOf(PLAIN, FIELDS, CIRCLES, GEOMETRY)
        fun createThemeOptions() = ALL_THEMES.toCollection(ArrayList())
        fun getThemeByName(name: String): Theme = ALL_THEMES.find { it.name.equals(name) } ?: defaultTheme
    }
}