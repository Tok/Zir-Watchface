package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.activity.DimSelectionActivity

class DimConfigItem internal constructor(type: Type,
                                         pref: String,
                                         name: String) :
        ConfigItem(type, pref, name) {
    val activity = DimSelectionActivity::class.java
}
