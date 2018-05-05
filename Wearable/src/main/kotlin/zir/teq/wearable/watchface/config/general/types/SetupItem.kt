package zir.teq.wearable.watchface.config.general.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.activity.ConfigActivity


class SetupItem(prefId: Int, nameId: Int, iconId: Int) :
        Item(prefId, nameId, iconId, ConfigActivity::class.java) {
    companion object {
        val SETUP_PLAIN = SetupItem(R.string.saved_setup_plain, R.string.label_setup_plain, R.drawable.icon_dummy)
        val all = listOf(SETUP_PLAIN)
    }
}
