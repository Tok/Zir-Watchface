package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleStack(override val label: String, override val value: Float,
                      val iconId: Int = R.drawable.icon_stack,
                      val isOn: Boolean = true) : Setting {
    GROUPED("Grouped", 0F),
    LEGACY("Legacy", 0F),
    FAST_TOP("Fast", 0F),
    SLOW_TOP("Slow", 0F);

    companion object : Config {
        override val code = Item.STACK.code
        override val label = Zir.string(R.string.label_stack)
        override val pref = Zir.string(R.string.saved_style_stack)
        override val iconId = R.drawable.icon_stack
        override val default = GROUPED
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
