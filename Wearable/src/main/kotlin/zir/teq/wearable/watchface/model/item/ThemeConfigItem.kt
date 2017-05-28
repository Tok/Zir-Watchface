package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity


class ThemeConfigItem internal constructor(type: Type,
                                           pref: String,
                                           name: String) :
        ConfigItem(type, pref, name) {
    val activity = ThemeSelectionActivity::class.java
}
