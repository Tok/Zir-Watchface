package zir.teq.wearable.watchface.draw.complex

data class Operator(val name: String) {
    override fun toString(): String = name.substring(0, 1) + name.substring(1, name.length).toLowerCase()
    companion object {
        val GROUP_NAME = "operator"
        val ADD = Operator("Add")
        val MULTIPLY = Operator("Multiply")
    }
}
