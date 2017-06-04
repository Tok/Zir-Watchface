package zir.teq.wearable.watchface.model.data.types

data class PaintType(val name: String, val isAmbient: Boolean, val isOutline: Boolean) {
    companion object {
        val HAND = PaintType("HAND", false, false)
        val HAND_AMB = PaintType("HAND_AMB", true, false)
        val HAND_OUTLINE = PaintType("OUTLINE", false, true)
        val SHAPE = PaintType("SHAPE", false, false)
        val SHAPE_AMB = PaintType("SHAPE_AMB", true, false)
        val SHAPE_OUTLINE = PaintType("SHAPE_OUTLINE", false, true)
        val CIRCLE = PaintType("CIRCLE", false, false)
        val CIRCLE_AMB = PaintType("CIRCLE_AMB", true, false)
        val CIRCLE_OUTLINE = PaintType("CIRCLE_OUTLINE", false, true)
        val POINT = PaintType("POINT", false, false)
        val POINT_OUTLINE = PaintType("POINT_OUTLINE", false, true)
    }
}
