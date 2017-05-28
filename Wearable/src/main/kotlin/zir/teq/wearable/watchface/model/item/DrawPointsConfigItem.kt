package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.model.ConfigData

class DrawPointsConfigItem internal constructor(name: String, iconId: Int, pref: String):
        ConfigItem(name, iconId, pref), ConfigData.ConfigItemType {
    override val configType: Int get() = ConfigItem.DRAW_POINTS.code
}