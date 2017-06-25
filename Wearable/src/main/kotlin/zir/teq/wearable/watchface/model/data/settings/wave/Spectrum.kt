package zir.teq.wearable.watchface.model.data.settings.wave

data class Spectrum(val name: String) {
    companion object {
        val BW = Spectrum("Black White")
        val PALETTE = Spectrum("Palette")
        val DARK = Spectrum("Dark")
        val DARK_WAVE = Spectrum("Dark Wave")
        val FULL = Spectrum("Full")
        val LINES = Spectrum("Lines")
        val SPOOK = Spectrum("Spook")
        val RAIN = Spectrum("Rain")
        val DEFAULT = BW
    }
}
