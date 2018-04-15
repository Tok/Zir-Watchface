package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R

data class Stack(val name: String, val iconId: Int) {
    companion object {
        val GROUPED = Stack("Grouped", R.drawable.icon_dummy) //TODO replace icons
        val LEGACY = Stack("Legacy", R.drawable.icon_dummy)
        val FAST_TOP = Stack("Fast", R.drawable.icon_dummy)
        val SLOW_TOP = Stack("Slow", R.drawable.icon_dummy)
        val all = listOf(GROUPED, LEGACY, FAST_TOP, SLOW_TOP)
        val default = GROUPED
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Stack = all.find { it.name.equals(name) } ?: default
    }
}
