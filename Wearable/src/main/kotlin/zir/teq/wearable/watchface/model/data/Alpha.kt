package zir.teq.wearable.watchface.model.data

data class Alpha(val name: String, val value: Int) {
    fun get(inMuteMode: Boolean): Int = if (inMuteMode) (value * MUTE_FACTOR).toInt() else value
    companion object {
        val MUTE_FACTOR = 0.4F

        val FULL = Alpha("Full", 15)
        val HIGH = Alpha("High", 31)
        val HALF = Alpha("Half", 63)
        val LOW = Alpha("Low", 127)
        val OFF = Alpha("Off", 255)

        val default = OFF
        val all = listOf(FULL, HIGH, HALF, LOW, OFF)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Alpha = all.find { it.name.equals(name) } ?: default
    }
}