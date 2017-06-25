package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R

data class Theme(val name: String, val iconId: Int,
                 val hands: Setting,
                 val triangles: Setting,
                 val circles: Setting,
                 val points: Setting,
                 val text: Setting,
                 val shapes: Setting) {
    companion object {
        private fun set(both: Boolean) = Setting(both, both)
        data class Setting(val active: Boolean, val ambient: Boolean)
        //TODO refactor
        val PLAIN = Theme("Plain", R.drawable.theme_plain, set(true), set(false), set(false), set(true), set(false), set(false))
        val SHAPE = Theme("Shape", R.drawable.icon_dummy, set(false), set(false), set(false), set(false), set(false), set(true)) //TODO repalce icon
        val DEFAULT = Theme("Default", R.drawable.theme_default, Setting(true, false), set(true), Setting(false, true), set(true), set(true), set(false))
        val FIELDS = Theme("Fields", R.drawable.theme_fields, set(true), set(true), set(false), set(true), set(false), set(false))
        val CIRCLES = Theme("Circles", R.drawable.theme_circles, set(false), set(false), set(true), set(true), set(false), set(false))
        val GEOMETRY = Theme("Geometry", R.drawable.theme_geometry, set(true), set(true), set(true), set(true), set(false), set(false))
        val default = DEFAULT
        val all = listOf(PLAIN, SHAPE, DEFAULT, FIELDS, CIRCLES, GEOMETRY)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Theme = all.find { it.name.equals(name) } ?: default
    }
}