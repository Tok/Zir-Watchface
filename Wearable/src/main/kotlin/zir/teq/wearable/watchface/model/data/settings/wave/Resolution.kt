package zir.teq.wearable.watchface.model.data.settings.wave

enum class Resolution(val value: Int, val blurRadius: Float) {
    _2_(2, 1F),
    _4_(4, 1F),
    _5_(4, 1F),
    _8_(8, 1F),
    _10_(10, 1F),
    _16_(16, 1F),
    _20_(20, 1F),
    _32_(32, 1F),
    _40_(40, 1F),
    _80_(80, 1F),
    _160_(160, 1F);

    companion object {
        val ACTIVE = _32_
        val AMBIENT = _4_
        fun get(isActive: Boolean) = if (isActive) ACTIVE else AMBIENT
    }
}
