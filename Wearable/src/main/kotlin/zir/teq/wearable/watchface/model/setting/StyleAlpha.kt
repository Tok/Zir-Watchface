package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData

enum class StyleAlpha(override val label: String, override val value: Float) : Setting {
    OFF("Off", StyleAlpha.max),
    _3("3%", StyleAlpha.max - (StyleAlpha.max * 3 / 100)),
    _6("6%", StyleAlpha.max - (StyleAlpha.max * 6 / 100)),
    _9("9%", StyleAlpha.max - (StyleAlpha.max * 9 / 100)),
    _12("12%", StyleAlpha.max - (StyleAlpha.max * 12 / 100)),
    _15("15%", StyleAlpha.max - (StyleAlpha.max * 15 / 100)),
    _18("18%", StyleAlpha.max - (StyleAlpha.max * 18 / 100)),
    _21("21%", StyleAlpha.max - (StyleAlpha.max * 21 / 100)),
    _24("24%", StyleAlpha.max - (StyleAlpha.max * 24 / 100)),
    _27("27%", StyleAlpha.max - (StyleAlpha.max * 27 / 100)),
    _30("30%", StyleAlpha.max - (StyleAlpha.max * 30 / 100));

    companion object : Config {
        private val max = 255F
        override val code = Item.ALPHA.code
        override val label = Zir.string(R.string.label_alpha)
        override val pref = Zir.string(R.string.saved_alpha)
        override val iconId = R.drawable.icon_alpha
        override val default = OFF
        override val all = values().toList()
        override fun getByName(name: String) = values().find { it.name.equals(name) } ?: default
        override fun load() = getByName(ConfigData.prefs.getString(pref, default.name))
        override fun save(setting: Setting) {
            val editor = ConfigData.prefs.edit()
            editor.putString(pref, setting.name)
            editor.apply()
        }
    }
}
