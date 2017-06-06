package zir.teq.wearable.watchface.model.data

data class Dim(val name: String, val value: Int) {
    companion object {
        val MEGA = Dim("Mega", 31)
        val HIGH = Dim("High", 63)
        val HALF = Dim("Half", 127)
        val SOME = Dim("Some", 191)
        val LOW = Dim("Low", 223)
        val BIT = Dim("Bit", 241)
        val OFF = Dim("Off", 255)
        val default = OFF
        val all = listOf(MEGA, HIGH, HALF, SOME, LOW, BIT, OFF)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Dim = all.find { it.name.equals(name) } ?: default
    }
}