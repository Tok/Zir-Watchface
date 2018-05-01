package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData


enum class StyleStroke(override val label: String, override val value: Float,
                       val iconId: Int = R.drawable.icon_stack,
                       val isOn: Boolean = true) : Setting {
    OFF("Off", 0F, R.drawable.theme_stroke_0),
    _1("1 Hair", 1F, R.drawable.theme_stroke_1),
    _2("2 Thin", 2F, R.drawable.theme_stroke_2),
    _3("3 Normal", 3F, R.drawable.theme_stroke_3),
    _5("5", 5F, R.drawable.theme_stroke_5),
    _8("8 Thick", 8F, R.drawable.theme_stroke_8),
    _10("10", 10F, R.drawable.theme_stroke_10),
    _13("13 Bold", 13F, R.drawable.theme_stroke_13),
    _16("16", 16F, R.drawable.theme_stroke_16),
    _21("21 Mega", 21F, R.drawable.theme_stroke_21),
    _26("26", 26F, R.drawable.theme_stroke_26),
    _34("34 Giga", 34F, R.drawable.theme_stroke_34);

    companion object : Config {
        override val code = Item.STROKE.code
        override val label = Zir.string(R.string.label_stroke)
        override val pref = Zir.string(R.string.saved_stroke)
        override val iconId = R.drawable.icon_stroke
        override val default = _3
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
