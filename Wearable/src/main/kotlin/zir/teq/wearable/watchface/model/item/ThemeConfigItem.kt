package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class ThemeConfigItem internal constructor(val name: String,
                                           val iconResourceId: Int,
                                           val sharedPrefString: String,
                                           val activityToChoosePreference: Class<ThemeSelectionActivity>) : ConfigData.ConfigItemType {
    override val configType: Int get() = ZirWatchConfigAdapter.TYPE_THEME_CONFIG
}