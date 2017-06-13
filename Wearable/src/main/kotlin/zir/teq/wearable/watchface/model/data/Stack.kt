package zir.teq.wearable.watchface.model.data

data class Stack(val name: String) {
    companion object {
        val LEGACY = Stack("Legacy")
        val FAST_TOP = Stack("Fast")
        val SLOW_TOP = Stack("Slow")
        val all = listOf(LEGACY, FAST_TOP, SLOW_TOP)
        val default = LEGACY
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Stack = all.find { it.name.equals(name) } ?: default
    }
}