package zir.teq.wearable.watchface.model.item

open class ConfigItem internal constructor(val name: String,
                                           val iconId: Int,
                                           val pref: String) {
    companion object {
        val TYPE_COLOR_CONFIG = 2
        val TYPE_STROKE_CONFIG = 3
        val TYPE_THEME_CONFIG = 4
    }
}