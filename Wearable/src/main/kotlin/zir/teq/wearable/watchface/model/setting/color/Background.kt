package zir.teq.wearable.watchface.model.setting.color

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item

class BackgroundConfigItem() : ColorConfigItem {
    override val configId: Int = Item.COLOR_BACKGROUND.code
}

data class Background(val name: String, val id: Int) {
    //TODO reimplement Bip and low bit ambient support?
    //val makeDark = isAmbient && (isLowBitAmbient || isBurnInProtection)

    companion object {
        val BLACK = Background("Black", R.color.black)
        val ALMOST_BLACK = Background("Almost Black", R.color.almost_black)
        val VERY_DARK = Background("Very Dark", R.color.very_dark)
        val DARKER = Background("Darker", R.color.darker)
        val DARK = Background("Dark", R.color.dark)
        val GRAY = Background("Gray", R.color.gray)
        val ULTRA_GRAY = Background("Ultra Gray", R.color.ultra_gray)
        val DARK_GRAY = Background("Dark Gray", R.color.dark_gray)
        val FOO_GRAY = Background("Foo Gray", R.color.foo_gray)
        val LIGHT_GRAY = Background("Light Gray", R.color.light_gray)

        val default = BLACK
        val all = listOf(BLACK, ALMOST_BLACK, VERY_DARK, DARKER, DARK,
                GRAY, ULTRA_GRAY, DARK_GRAY, FOO_GRAY, LIGHT_GRAY)

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Background = all.find { it.name.equals(name) } ?: default
    }
}
