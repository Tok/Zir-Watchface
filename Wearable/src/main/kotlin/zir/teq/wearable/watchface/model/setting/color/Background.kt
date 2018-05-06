package zir.teq.wearable.watchface.model.setting.color

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.types.ColorItem
import zir.teq.wearable.watchface.model.ConfigData

class BackgroundConfigItem : ColorConfigItem {
    override val pref: String = ColorItem.COLOR_BACKGROUND.pref
    override val viewType: Int = ColorItem.COLOR_BACKGROUND.viewType
}

data class Background(val name: String, val id: Int) : ColorConfigItem {
    //TODO reimplement Bip and low bit ambient support?
    //val makeDark = isAmbient && (isLowBitAmbient || isBurnInProtection)
    override val pref = name
    override val viewType = R.layout.list_item_circle_text

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

        val default = DARKER
        val all = listOf(BLACK, ALMOST_BLACK, VERY_DARK, DARKER, DARK,
                GRAY, ULTRA_GRAY, DARK_GRAY, FOO_GRAY, LIGHT_GRAY)

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Background = all.find { it.name.equals(name) } ?: default

        val EXTRA = this::class.java.getPackage().name + "SHARED_BACKGROUND"
        fun save(item: Background) {
            val editor = ConfigData.prefs.edit()
            editor.putString(EXTRA, item.name)
            editor.apply()
        }
    }
}
