package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.AlphaSelectionActivity

class AlphaConfigItem internal constructor(type: Type,
                                         pref: String,
                                         name: String) :
        ConfigItem(type, pref, name) {
    val activity = AlphaSelectionActivity::class.java
}
