package zir.teq.wearable.watchface.model.setting.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.StyleItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class StyleStack(override val label: String, override val value: Float,
                      val iconId: Int = R.drawable.style_icon_stack,
                      val isOn: Boolean = true) : Setting {
    GROUPED("Grouped", 0F),
    LEGACY("Legacy", 0F),
    FAST_TOP("Fast", 0F),
    SLOW_TOP("Slow", 0F);

    override val pref: String = name

    companion object : Config {
        override val code = StyleItem.STYLE_STACK.code
        override val label = Zir.string(R.string.label_stack)
        override val pref = Zir.string(R.string.saved_style_stack)
        override val iconId = R.drawable.style_icon_stack
        override val default = GROUPED
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
