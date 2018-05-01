package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.frame.data.ActiveData
import zir.teq.wearable.watchface.model.frame.data.AmbientData
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.types.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.data.types.State.ACTIVE
import zir.teq.wearable.watchface.model.data.types.State.AMBIENT
import zir.teq.wearable.watchface.util.DrawUtil

object Points {
    //TODO implement point stacking?
    fun drawActiveCenter(can: Canvas, data: ActiveData, p: Paint) {
        if (ConfigData.isOn(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.style().outline.isOn) {
                makeCenter(can, data, DrawUtil.makeOutline(p))
            }
            makeCenter(can, data, p)
        }
    }

    fun drawActive(can: Canvas, data: ActiveData, p: Paint) {
        if (ConfigData.isOn(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.style().outline.isOn) {
                val outlineP = DrawUtil.makeOutline(p)
                makeSeconds(can, data, outlineP)
                makeMinAndHr(can, data, outlineP)
            }
            makeSeconds(can, data, p)
            makeMinAndHr(can, data, p)
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientData) {
        if (ConfigData.isOn(POINTS to AMBIENT)) {
            val p = Palette.createPaint(PaintType.POINT)
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.style().outline.isOn) {
                drawAmbientPoints(can, data, DrawUtil.makeOutline(p))
            }
            drawAmbientPoints(can, data, p)
        }
    }

    private fun drawAmbientPoints(can: Canvas, data: AmbientData, p: Paint) {
        can.drawPoint(data.center.x, data.center.y, p)
        can.drawPoint(data.min.x, data.min.y, p)
        can.drawPoint(data.hr.x, data.hr.y, p)
    }

    private fun makeCenter(can: Canvas, data: ActiveData, p: Paint) {
        can.drawPoint(data.center.x, data.center.y, p)
    }

    private fun makeSeconds(can: Canvas, data: ActiveData, p: Paint) {
        can.drawPoint(data.sec.x, data.sec.y, p)
    }

    private fun makeMinAndHr(can: Canvas, data: ActiveData, p: Paint) {
        can.drawPoint(data.hr.x, data.hr.y, p)
        can.drawPoint(data.min.x, data.min.y, p)
    }
}
