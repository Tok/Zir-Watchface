package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.types.StyleItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleGrowth(override val label: String, override val value: Float,
                       val iconId: Int, val isOn: Boolean = true) : Setting {
    OFF("Off", 0F, R.drawable.theme_growth_0, false),
    _2("2", 2F, R.drawable.theme_growth_2),
    _3("3 Small", 3F, R.drawable.theme_growth_3),
    _5("5", 5F, R.drawable.theme_growth_5),
    _8("8 Normal", 8F, R.drawable.theme_growth_8),
    _10("10", 10F, R.drawable.theme_growth_10),
    _13("13 Big", 13F, R.drawable.theme_growth_13),
    _16("16", 16F, R.drawable.theme_growth_16),
    _21("21 Mega", 21F, R.drawable.theme_growth_21);

    override val pref: String = name

    companion object : Config {
        override val code = StyleItem.STYLE_GROWTH.code
        override val label = Zir.string(R.string.label_growth)
        override val pref = Zir.string(R.string.saved_style_growth)
        override val iconId = R.drawable.style_icon_growth
        override val default = _8
        override val all = values().toList()
        private fun valueOf(pref: String): Setting = values().find { it.pref.equals(pref) }
                ?: default

        override fun index() = all.indexOfFirst { it.equals(load()) }
        override fun load() = valueOf(ConfigData.prefs.getString(pref, default.pref))
        override fun save(setting: Setting) {
            val editor = ConfigData.prefs.edit()
            editor.putString(pref, setting.pref)
            editor.apply()
        }
    }
}
