package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R

data class Dim(val name: String, val value: Int, val iconId: Int = R.drawable.icon_dummy) {
    companion object {
        val RANGE = 255 downTo 0 step 16
        val all = RANGE.map { Dim(((255 - it) * 100 / 255).toString() + "%", it) }
        val default = all.first()
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Dim = all.find { it.name.equals(name) } ?: default
    }
}
