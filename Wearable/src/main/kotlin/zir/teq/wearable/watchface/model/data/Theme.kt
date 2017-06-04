package zir.teq.wearable.watchface.model.data

import zir.teq.wearable.watchface.R

data class Theme(val name: String, val iconId: Int, val isFastUpdate: Boolean,
                 val hands: Setting,
                 val triangles: Setting,
                 val circles: Setting,
                 val points: Setting,
                 val text: Setting,
                 val outlineName: String,
                 val growthName: String) {
    val hasOutline = !outlineName.equals(Outline.OFF.name)
    companion object {
        private fun set(both: Boolean) = Setting(both, both)
        data class Setting(val active: Boolean, val ambient: Boolean)
        //TODO refactor
        val PLAIN = Theme("Plain", R.drawable.theme_plain, false, set(true), set(false), set(false), set(true), set(false), Outline.OFF.name, Growth.OFF.name)
        val DEFAULT = Theme("Default", R.drawable.theme_plain, false, Setting(true, false), set(true), Setting(false, true), set(true), set(true), Outline.OFF.name, Growth.OFF.name)
        val FIELDS = Theme("Fields", R.drawable.theme_fields, false, set(true), set(true), set(false), set(true), set(false), Outline.BOLD.name, Growth.BOLDER.name)
        val CIRCLES = Theme("Circles", R.drawable.theme_circles, true, set(false), set(false), set(true), set(true), set(false), Outline.BOLD.name, Growth.BOLDER.name)
        val GEOMETRY = Theme("Geometry", R.drawable.theme_geometry, true, set(true), set(true), set(true), set(true), set(false), Outline.BOLD.name, Growth.BOLDER.name)
        val default = DEFAULT
        val all = listOf(PLAIN, DEFAULT, FIELDS, CIRCLES, GEOMETRY)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Theme = all.find { it.name.equals(name) } ?: default
    }
}