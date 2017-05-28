package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity


class StrokeConfigItem internal constructor(type: Type,
                                            pref: String,
                                            name: String) :
        ConfigItem(type, pref, name) {
    val activity = StrokeSelectionActivity::class.java
}
