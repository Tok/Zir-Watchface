package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R

data class Stack(val name: String, val iconId: Int) {
    companion object {
        val GROUPED = zir.teq.wearable.watchface.model.data.settings.Stack("Grouped", R.drawable.icon_dummy) //TODO replace icons
        val FAST_TOP = zir.teq.wearable.watchface.model.data.settings.Stack("Fast", R.drawable.icon_dummy)
        val SLOW_TOP = zir.teq.wearable.watchface.model.data.settings.Stack("Slow", R.drawable.icon_dummy)
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Stack.Companion.GROUPED, zir.teq.wearable.watchface.model.data.settings.Stack.Companion.FAST_TOP, zir.teq.wearable.watchface.model.data.settings.Stack.Companion.SLOW_TOP)
        val default = zir.teq.wearable.watchface.model.data.settings.Stack.Companion.GROUPED
        fun options() = zir.teq.wearable.watchface.model.data.settings.Stack.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Stack = zir.teq.wearable.watchface.model.data.settings.Stack.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Stack.Companion.default
    }
}