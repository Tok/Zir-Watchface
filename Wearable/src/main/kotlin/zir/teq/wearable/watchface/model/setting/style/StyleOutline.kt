package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.StyleItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleOutline(override val label: String, override val value: Float,
                        val iconId: Int, val isOn: Boolean = true) : Setting {
    OFF("Off", 0F, R.drawable.theme_outline_0, false),
    _1("1 Hair", 1F, R.drawable.theme_outline_1),
    _2("2 Thinner", 2F, R.drawable.theme_outline_2),
    _3("3 Thin", 3F, R.drawable.theme_outline_3),
    _5("5 Normal", 5F, R.drawable.theme_outline_5),
    _8("8 Thick", 8F, R.drawable.theme_outline_8),
    _10("10", 10F, R.drawable.theme_outline_10),
    _13("13 Bold", 13F, R.drawable.theme_outline_13),
    _16("16", 16F, R.drawable.theme_outline_16),
    _21("21 Mega", 21F, R.drawable.theme_outline_21);

    override val pref: String = name

    companion object : Config {
        override val code = StyleItem.STYLE_OUTLINE.code
        override val label = Zir.string(R.string.label_outline)
        override val pref = Zir.string(R.string.saved_style_outline)
        override val iconId = R.drawable.style_icon_outline
        override val default = OFF
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
