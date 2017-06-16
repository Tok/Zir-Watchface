package zir.teq.wearable.watchface.draw.complex.data

data class AnimationMode(val name: String) {
    companion object {
        val CENTER = AnimationMode("Center")
        val DUAL = AnimationMode("Dual")
        val MANDALA = AnimationMode("Mandala")
    }
}
