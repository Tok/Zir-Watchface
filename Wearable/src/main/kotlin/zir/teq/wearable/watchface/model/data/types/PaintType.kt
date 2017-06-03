package zir.teq.wearable.watchface.model.data.types

data class PaintType(val name: String) {
    companion object {
        val TEXT = PaintType("TEXT")
        val HAND = PaintType("HAND")
        val HAND_AMB = PaintType("HAND_AMB")
        val HAND_OUTLINE = PaintType("OUTLINE")
        val SHAPE = PaintType("SHAPE")
        val SHAPE_AMB = PaintType("SHAPE_AMB")
        val SHAPE_OUTLINE = PaintType("SHAPE_OUTLINE")
        val CIRCLE = PaintType("CIRCLE")
        val CIRCLE_AMB = PaintType("CIRCLE_AMB")
        val CIRCLE_OUTLINE = PaintType("CIRCLE_OUTLINE")
        val POINT = PaintType("POINT")
        val POINT_OUTLINE = PaintType("POINT_OUTLINE")
    }
}
