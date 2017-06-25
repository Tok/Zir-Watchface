package zir.teq.wearable.watchface.model.data.settings.wave

data class Resolution(val value: Int) {
    fun get() = value
    operator fun times(factor: Float) = (value * 0.5).toInt()
    companion object {
        fun getBlurRadius(isActive: Boolean) = (if (isActive) ACTIVE else AMBIENT).value * 0.5F
        val _2_ = Resolution(2)
        val _4_ = Resolution(4)
        val _5_ = Resolution(5)
        val _8_ = Resolution(8)
        val _10_ = Resolution(10)
        val _16_ = Resolution(16)
        val _20_ = Resolution(20)
        val _32_ = Resolution(32)
        val _40_ = Resolution(40)
        val _80_ = Resolution(80)
        val _160_ = Resolution(160)
        val ACTIVE = _32_
        val AMBIENT = _4_
    }
}
