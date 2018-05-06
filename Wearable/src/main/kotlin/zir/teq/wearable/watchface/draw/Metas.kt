package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.METAS
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT

object Metas {
    fun drawActive(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(METAS to ACTIVE)) {

        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrame) {
        if (ConfigData.isOn(METAS to AMBIENT)) {

        }
    }
}
