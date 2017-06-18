package zir.teq.wearable.watchface.model.data.types

data class Operator(val name: String) {
    companion object {
        val ADD = Operator("Add")
        val MULTIPLY = Operator("Multiply")
    }
}
