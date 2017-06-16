package zir.teq.wearable.watchface.draw.complex.data

data class Operator(val name: String) {
    companion object {
        val ADD = Operator("Add")
        val MULTIPLY = Operator("Multiply")
    }
}
