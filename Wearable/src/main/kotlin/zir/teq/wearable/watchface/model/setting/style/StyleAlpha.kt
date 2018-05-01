package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleAlpha(override val label: String, override val value: Float) : Setting {
    OFF("Off", StyleAlpha.max),
    _3("3%", StyleAlpha.createValue(3)),
    _6("6%", StyleAlpha.createValue(6)),
    _9("9%", StyleAlpha.createValue(9)),
    _12("12%", StyleAlpha.createValue(12)),
    _15("15%", StyleAlpha.createValue(15)),
    _18("18%", StyleAlpha.createValue(18)),
    _21("21%", StyleAlpha.createValue(21)),
    _24("24%", StyleAlpha.createValue(24)),
    _27("27%", StyleAlpha.createValue(27)),
    _30("30%", StyleAlpha.createValue(30));

    companion object : Config {
        private val max = 255F
        private fun createValue(init: Int) = max - (max * init / 100)
        override val code = Item.ALPHA.code
        override val label = Zir.string(R.string.label_alpha)
        override val pref = Zir.string(R.string.saved_style_alpha)
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
