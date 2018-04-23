package zir.teq.wearable.watchface.model.data.settings.wave

enum class Spectrum {
    PALETTE, BW, DARK, DARK_WAVE, FULL, LINES, SPOOK, RAIN;

    fun getName() = this.name.toLowerCase().capitalize()

    companion object {
        val default = BW
    }
}
