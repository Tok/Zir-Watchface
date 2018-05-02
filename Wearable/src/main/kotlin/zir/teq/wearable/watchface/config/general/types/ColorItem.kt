package zir.teq.wearable.watchface.config.general.types

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.activity.color.BackgroundActivity


class ColorItem(prefId: Int, nameId: Int, iconId: Int, activity: Class<out Activity>) :
        Item(prefId, nameId, iconId, activity) {
    val viewType = R.layout.list_item_circle_text
    companion object {
        val COLOR_BACKGROUND = ColorItem(R.string.saved_background, R.string.label_background, R.drawable.icon_background, BackgroundActivity::class.java)
        val all = listOf(COLOR_BACKGROUND)
    }
}
