package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R

data class Theme(val name: String, val iconId: Int,
                 val hands: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting,
                 val triangles: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting,
                 val circles: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting,
                 val points: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting,
                 val text: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting) {
    companion object {
        private fun set(both: Boolean) = zir.teq.wearable.watchface.model.data.settings.Theme.Companion.Setting(both, both)
        data class Setting(val active: Boolean, val ambient: Boolean)
        //TODO refactor
        val PLAIN = zir.teq.wearable.watchface.model.data.settings.Theme("Plain", R.drawable.theme_plain, set(true), set(false), set(false), set(true), set(false))
        val DEFAULT = zir.teq.wearable.watchface.model.data.settings.Theme("Default", R.drawable.theme_default, Setting(true, false), set(true), Setting(false, true), set(true), set(true))
        val FIELDS = zir.teq.wearable.watchface.model.data.settings.Theme("Fields", R.drawable.theme_fields, set(true), set(true), set(false), set(true), set(false))
        val CIRCLES = zir.teq.wearable.watchface.model.data.settings.Theme("Circles", R.drawable.theme_circles, set(false), set(false), set(true), set(true), set(false))
        val GEOMETRY = zir.teq.wearable.watchface.model.data.settings.Theme("Geometry", R.drawable.theme_geometry, set(true), set(true), set(true), set(true), set(false))
        val default = zir.teq.wearable.watchface.model.data.settings.Theme.Companion.DEFAULT
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Theme.Companion.PLAIN, zir.teq.wearable.watchface.model.data.settings.Theme.Companion.DEFAULT, zir.teq.wearable.watchface.model.data.settings.Theme.Companion.FIELDS, zir.teq.wearable.watchface.model.data.settings.Theme.Companion.CIRCLES, zir.teq.wearable.watchface.model.data.settings.Theme.Companion.GEOMETRY)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Theme.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Theme = zir.teq.wearable.watchface.model.data.settings.Theme.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Theme.Companion.default
    }
}