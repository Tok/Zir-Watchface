package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleDim(override val label: String, override val value: Float) : Setting {
    OFF("Off", StyleDim.max),
    _3("3%", StyleDim.createValue(3)),
    _6("6%", StyleDim.createValue(6)),
    _9("9%", StyleDim.createValue(9)),
    _12("12%", StyleDim.createValue(12)),
    _15("15%", StyleDim.createValue(15)),
    _18("18%", StyleDim.createValue(18)),
    _21("21%", StyleDim.createValue(21)),
    _24("24%", StyleDim.createValue(24)),
    _27("27%", StyleDim.createValue(27)),
    _30("30%", StyleDim.createValue(30));

    companion object : Config {
        private val max = 255F
        private fun createValue(init: Int) = max - (max * init / 100)
        override val code = Item.DIM.code
        override val label = Zir.string(R.string.label_dim)
        override val pref = Zir.string(R.string.saved_style_dim)
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
