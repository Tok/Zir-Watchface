package zir.teq.wearable.watchface.model.data.types

data class PaintType(val name: String, val isAmbient: Boolean, val isOutline: Boolean) {
    companion object {
        val HAND = PaintType("HAND", false, false)
        val HAND_AMB = PaintType("HAND_AMB", true, false)
        val SHAPE = PaintType("SHAPE", false, false)
        val SHAPE_AMB = PaintType("SHAPE_AMB", true, false)
        val CIRCLE = PaintType("CIRCLE", false, false)
        val CIRCLE_AMB = PaintType("CIRCLE_AMB", true, false)
        val POINT = PaintType("POINT", false, false)
    }
}
