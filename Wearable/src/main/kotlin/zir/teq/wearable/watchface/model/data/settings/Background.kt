package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R

data class Background(val name: String, val id: Int) {
    //TODO reimplement Bip and low bit ambient support?
    //val makeDark = isAmbient && (isLowBitAmbient || isBurnInProtection)

    companion object {
        val BLACK = zir.teq.wearable.watchface.model.data.settings.Background("Black", R.color.black)
        val ALMOST_BLACK = zir.teq.wearable.watchface.model.data.settings.Background("Almost Black", R.color.almost_black)
        val VERY_DARK = zir.teq.wearable.watchface.model.data.settings.Background("Very Dark", R.color.very_dark)
        val DARKER = zir.teq.wearable.watchface.model.data.settings.Background("Darker", R.color.darker)
        val DARK = zir.teq.wearable.watchface.model.data.settings.Background("Dark", R.color.dark)
        val GRAY = zir.teq.wearable.watchface.model.data.settings.Background("Gray", R.color.gray)
        val ULTRA_GRAY = zir.teq.wearable.watchface.model.data.settings.Background("Ultra Gray", R.color.ultra_gray)
        val DARK_GRAY = zir.teq.wearable.watchface.model.data.settings.Background("Dark Gray", R.color.dark_gray)
        val FOO_GRAY = zir.teq.wearable.watchface.model.data.settings.Background("Foo Gray", R.color.foo_gray)
        val LIGHT_GRAY = zir.teq.wearable.watchface.model.data.settings.Background("Light Gray", R.color.light_gray)

        val default = zir.teq.wearable.watchface.model.data.settings.Background.Companion.BLACK
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Background.Companion.BLACK, zir.teq.wearable.watchface.model.data.settings.Background.Companion.ALMOST_BLACK, zir.teq.wearable.watchface.model.data.settings.Background.Companion.VERY_DARK, zir.teq.wearable.watchface.model.data.settings.Background.Companion.DARKER, zir.teq.wearable.watchface.model.data.settings.Background.Companion.DARK,
                zir.teq.wearable.watchface.model.data.settings.Background.Companion.GRAY, zir.teq.wearable.watchface.model.data.settings.Background.Companion.ULTRA_GRAY, zir.teq.wearable.watchface.model.data.settings.Background.Companion.DARK_GRAY, zir.teq.wearable.watchface.model.data.settings.Background.Companion.FOO_GRAY, zir.teq.wearable.watchface.model.data.settings.Background.Companion.LIGHT_GRAY)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Background.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Background = zir.teq.wearable.watchface.model.data.settings.Background.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Background.Companion.default
    }
}