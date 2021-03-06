package zir.teq.wearable.watchface.model.types

data class PaintType(val name: String, val isAmbient: Boolean) {
    companion object {
        val HAND = PaintType("HAND", false)
        val HAND_AMB = PaintType("HAND_AMB", true)
        val SHAPE = PaintType("SHAPE", false)
        val SHAPE_AMB = PaintType("SHAPE_AMB", true)
        val CIRCLE = PaintType("CIRCLE", false)
        val CIRCLE_AMB = PaintType("CIRCLE_AMB", true)
        val META = PaintType("META", false)
        val POINT = PaintType("POINT", false)
        val BACKGROUND = PaintType("Background", false)
    }
}
