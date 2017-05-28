package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.model.ConfigData

open class ConfigItem internal constructor(val type: Int,
                                           val name: String,
                                           val iconId: Int,
                                           val pref: String): ConfigData.ConfigItemType {
    override val configType: Int get() = type
    companion object {
        data class Type(val code: Int)
        val THEME = Type(1)
        val COLORS = Type(2)
        val STROKE = Type(3)
        val DRAW_TRIANGLES = Type(4)
        val DRAW_CIRCLES = Type(5)
        val DRAW_ACTIVE_HANDS = Type(6)
        val DRAW_HANDS = Type(7)
        val DRAW_POINTS = Type(8)
        val DRAW_TEXT = Type(9)
    }
}