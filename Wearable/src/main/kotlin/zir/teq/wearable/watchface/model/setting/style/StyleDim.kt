package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleDim(override val label: String, override val value: Float) : Setting {
    OFF("Off", 255F - (255F * 0 / 100)),
    _3("3%", 255F - (255F * 3 / 100)),
    _6("6%", 255F - (255F * 6 / 100)),
    _9("9%", 255F - (255F * 9 / 100)),
    _12("12%", 255F - (255F * 12 / 100)),
    _15("15%", 255F - (255F * 15 / 100)),
    _18("18%", 255F - (255F * 18 / 100)),
    _21("21%", 255F - (255F * 21 / 100)),
    _24("24%", 255F - (255F * 24 / 100)),
    _27("27%", 255F - (255F * 27 / 100)),
    _30("30%", 255F - (255F * 30 / 100));

    override val pref: String = name

    companion object : Config {
        override val code = Item.DIM.code
        override val label = Zir.string(R.string.label_dim)
        override val pref = Zir.string(R.string.saved_style_dim)
        override val iconId = R.drawable.icon_dim
        override val default = OFF
        override val all = values().toList()
        private fun valueOf(pref: String): Setting = values().find { it.pref.equals(pref) }
                ?: default

        override fun index() = all.indexOfFirst { it.equals(load()) }
        override fun load() = valueOf(ConfigData.prefs.getString(pref, default.name))
        override fun save(setting: Setting) {
            val editor = ConfigData.prefs.edit()
            editor.putString(pref, setting.pref)
            editor.apply()
        }
    }
}
