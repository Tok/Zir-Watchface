package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData

enum class StyleDim(override val label: String, override val value: Float) : Setting {
    OFF("Off", StyleDim.max),
    _3("3%", StyleDim.max - (StyleDim.max * 3 / 100)),
    _6("6%", StyleDim.max - (StyleDim.max * 6 / 100)),
    _9("9%", StyleDim.max - (StyleDim.max * 9 / 100)),
    _12("12%", StyleDim.max - (StyleDim.max * 12 / 100)),
    _15("15%", StyleDim.max - (StyleDim.max * 15 / 100)),
    _18("18%", StyleDim.max - (StyleDim.max * 18 / 100)),
    _21("21%", StyleDim.max - (StyleDim.max * 21 / 100)),
    _24("24%", StyleDim.max - (StyleDim.max * 24 / 100)),
    _27("27%", StyleDim.max - (StyleDim.max * 27 / 100)),
    _30("30%", StyleDim.max - (StyleDim.max * 30 / 100));

    companion object : Config {
        private val max = 255F
        override val code = Item.DIM.code
        override val label = Zir.string(R.string.label_dim)
        override val pref = Zir.string(R.string.saved_dim)
        override val iconId = R.drawable.icon_dim
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
