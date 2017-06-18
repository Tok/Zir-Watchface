package zir.teq.wearable.watchface.model.data.settings

data class Dim(val name: String, val value: Int) {
    companion object {
        val RANGE = 255 downTo 0 step 16
        val all = zir.teq.wearable.watchface.model.data.settings.Dim.Companion.RANGE.map { zir.teq.wearable.watchface.model.data.settings.Dim(((255 - it) * 100 / 255).toString() + "%", it) }
        val default = zir.teq.wearable.watchface.model.data.settings.Dim.Companion.all.first()
        fun options() = zir.teq.wearable.watchface.model.data.settings.Dim.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Dim = zir.teq.wearable.watchface.model.data.settings.Dim.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Dim.Companion.default
    }
}