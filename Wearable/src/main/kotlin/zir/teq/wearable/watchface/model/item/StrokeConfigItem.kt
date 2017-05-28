package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity

class StrokeConfigItem internal constructor(type: Int,
                                           name: String,
                                           iconId: Int,
                                           pref: String,
                                           val activity: Class<StrokeSelectionActivity>) :
        ConfigItem(type, name, iconId, pref)