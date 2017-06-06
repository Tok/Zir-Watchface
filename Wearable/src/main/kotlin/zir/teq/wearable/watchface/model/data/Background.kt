package zir.teq.wearable.watchface.model.data

import zir.teq.wearable.watchface.R

data class Background(val name: String, val id: Int) {
    //TODO reimplement Bip and low bit ambient support?
    //val makeDark = isAmbient && (isLowBitAmbient || isBurnInProtection)

    companion object {
        val BLACK = Background("Black", R.color.black)
        val DARK = Background("Dark", R.color.dark)
        val GRAY = Background("Gray", R.color.gray)
        val DARK_GRAY = Background("Dark Gray", R.color.dark_gray)

        val default = BLACK
        val all = listOf(BLACK, DARK, GRAY, DARK_GRAY)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Background = all.find { it.name.equals(name) } ?: default
    }
}