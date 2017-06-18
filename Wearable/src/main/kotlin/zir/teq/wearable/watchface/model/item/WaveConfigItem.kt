package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.activity.WaveSelectionActivity

class WaveConfigItem internal constructor(type: Type,
                                          pref: String,
                                          name: String) :
        ConfigItem(type, pref, name) {
    val activity = WaveSelectionActivity::class.java
}
