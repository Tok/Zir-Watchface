package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
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

    companion object : Config {
        override val code = Item.OUTLINE.code
        override val label = Zir.string(R.string.label_outline)
        override val pref = Zir.string(R.string.saved_style_outline)
        override val iconId = R.drawable.icon_outline
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
