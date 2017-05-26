package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class ColorConfigItem internal constructor(name: String,
                                           iconId: Int,
                                           pref: String,
                                           val activity: Class<ColorSelectionActivity>) : ConfigItem(name, iconId, pref), ConfigData.ConfigItemType {
    override val configType: Int get() = ZirWatchConfigAdapter.TYPE_COLOR_CONFIG
}