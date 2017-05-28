package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.model.ConfigData

class DrawCirclesConfigItem internal constructor(name: String, iconId: Int, pref: String):
        ConfigItem(name, iconId, pref), ConfigData.ConfigItemType {
    override val configType: Int get() = ConfigItem.DRAW_CIRCLES.code
}