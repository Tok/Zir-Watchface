package zir.teq.wearable.watchface.model.data.types

data class PaintType(val name: String) {
    companion object {
        val TEXT = PaintType("TEXT")
        val HAND = PaintType("HAND")
        val HAND_AMB = PaintType("HAND_AMB")
        val SHAPE = PaintType("SHAPE")
        val SHAPE_AMB = PaintType("SHAPE_AMB")
        val CIRCLE = PaintType("CIRCLE")
        val CIRCLE_AMB = PaintType("CIRCLE_AMB")
        val POINT = PaintType("POINT")
    }
}
