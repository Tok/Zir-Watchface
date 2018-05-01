package zir.teq.wearable.watchface.config.general

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.ColorItem
import zir.teq.wearable.watchface.config.general.types.MainItem
import zir.teq.wearable.watchface.config.general.types.StyleItem
import zir.teq.wearable.watchface.config.general.types.WaveItem
import zir.teq.wearable.watchface.config.select.activity.ConfigActivity

abstract class Item(val prefId: Int, val nameId: Int,
                    val iconId: Int = R.drawable.icon_dummy,
                    val activity: Class<out Activity> = ConfigActivity::class.java) {
    fun layoutId(): Int = R.layout.list_item_main
    val pref = Zir.string(prefId)
    val name = Zir.string(nameId)
    val icon = Zir.drawable(iconId)
    val code: Int = name.hashCode()
}
