package zir.teq.wearable.watchface.config.general.types

import android.app.Activity
import zir.teq.wearable.watchface.config.general.Item


class WaveItem(code: Int, prefId: Int, nameId: Int, iconId: Int, activity: Class<out Activity>) :
        Item(code, prefId, nameId, iconId, activity)
