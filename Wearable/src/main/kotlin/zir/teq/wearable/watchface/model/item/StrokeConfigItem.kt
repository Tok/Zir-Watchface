package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class StrokeConfigItem internal constructor(name: String, iconId: Int, pref: String,
                                            val activity: Class<StrokeSelectionActivity>):
        ConfigItem(name, iconId, pref), ConfigData.ConfigItemType {
    override val configType: Int get() = TYPE_STROKE_CONFIG
}