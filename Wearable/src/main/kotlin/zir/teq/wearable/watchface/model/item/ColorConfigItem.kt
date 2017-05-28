package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class ColorConfigItem internal constructor(type: Int,
                                           name: String,
                                           iconId: Int,
                                           pref: String,
                                           val activity: Class<ColorSelectionActivity>) :
        ConfigItem(type, name, iconId, pref)