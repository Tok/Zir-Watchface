package zir.teq.wearable.watchface.model.data.settings

data class Alpha(val name: String, val value: Int) {
    companion object {
        val RANGE = 255 downTo 0 step 16
        val all = zir.teq.wearable.watchface.model.data.settings.Alpha.Companion.RANGE.map { zir.teq.wearable.watchface.model.data.settings.Alpha(((255 - it) * 100 / 255).toString() + "%", it) }
        val default = zir.teq.wearable.watchface.model.data.settings.Alpha.Companion.all.first()
        fun options() = zir.teq.wearable.watchface.model.data.settings.Alpha.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Alpha = zir.teq.wearable.watchface.model.data.settings.Alpha.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Alpha.Companion.default
    }
}