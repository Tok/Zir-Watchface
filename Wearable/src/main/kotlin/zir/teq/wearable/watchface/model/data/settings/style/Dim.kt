package zir.teq.wearable.watchface.model.data.settings.style

data class Dim(val name: String, val value: Int) {
    companion object {
        val RANGE = 255 downTo 0 step 16
        val all = RANGE.map { Dim(((255 - it) * 100 / 255).toString() + "%", it) }
        val default = all.first()
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Dim = all.find { it.name.equals(name) } ?: default
    }
}