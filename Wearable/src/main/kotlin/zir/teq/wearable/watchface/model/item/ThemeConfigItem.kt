package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity


class ThemeConfigItem internal constructor(type: Int,
                                           name: String,
                                           iconId: Int,
                                           pref: String,
                                           val activity: Class<ThemeSelectionActivity>) :
        ConfigItem(type, name, iconId, pref)