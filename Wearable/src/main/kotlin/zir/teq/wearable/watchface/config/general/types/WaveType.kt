package zir.teq.wearable.watchface.config.general.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Type


class WaveType(code: Int, prefId: Int, nameId: Int, val iconId: Int) : Type(code, prefId, nameId) {
    override fun layoutId() = R.layout.list_item_main
}
