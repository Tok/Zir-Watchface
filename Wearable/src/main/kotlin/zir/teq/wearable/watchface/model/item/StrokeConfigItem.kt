package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.model.ConfigData

class StrokeConfigItem internal constructor(val name: String,
                                            val iconResourceId: Int,
                                            val sharedPrefString: String,
                                            val activityToChoosePreference: Class<StrokeSelectionActivity>) : ConfigData.ConfigItemType {
    override val configType: Int get() = ZirWatchConfigAdapter.TYPE_STROKE_CONFIG
}