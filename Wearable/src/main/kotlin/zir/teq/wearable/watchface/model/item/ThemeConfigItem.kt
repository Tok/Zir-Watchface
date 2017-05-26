package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData


class ThemeConfigItem internal constructor(name: String, iconId: Int, pref: String,
                                           val activity: Class<ThemeSelectionActivity>) :
        ConfigItem(name, iconId, pref), ConfigData.ConfigItemType {
    override val configType: Int get() = TYPE_THEME_CONFIG
}