package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class ColorConfigItem internal constructor(val name: String,
                                           val iconResourceId: Int,
                                           val sharedPrefString: String,
                                           val activityToChoosePreference: Class<ColorSelectionActivity>) : ConfigData.ConfigItemType {
    override val configType: Int get() = ZirWatchConfigAdapter.TYPE_COLOR_CONFIG
}