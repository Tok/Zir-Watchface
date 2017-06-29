package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrameData
import zir.teq.wearable.watchface.model.data.frame.AmbientFrameData
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.model.data.types.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.data.types.State.ACTIVE
import zir.teq.wearable.watchface.model.data.types.State.AMBIENT
import zir.watchface.DrawUtil

object Points {
    //TODO implement point stacking?
    fun drawActiveCenter(can: Canvas, data: ActiveFrameData, p: Paint) {
        if (ConfigData.theme.get(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.outline.isOn) {
                makeCenter(can, data, DrawUtil.makeOutline(p))
            }
            makeCenter(can, data, p)
        }
    }

    fun drawActive(can: Canvas, data: ActiveFrameData, p: Paint) {
        if (ConfigData.theme.get(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.outline.isOn) {
                val outlineP = DrawUtil.makeOutline(p)
                makeSeconds(can, data, outlineP)
                makeMinAndHr(can, data, outlineP)
            }
            makeSeconds(can, data, p)
            makeMinAndHr(can, data, p)
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrameData) {
        if (ConfigData.theme.get(POINTS to AMBIENT)) {
            val p = Palette.createPaint(PaintType.POINT)
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.outline.isOn) {
                drawAmbientPoints(can, data, DrawUtil.makeOutline(p))
            }
            drawAmbientPoints(can, data, p)
        }
    }

    private fun drawAmbientPoints(can: Canvas, data: AmbientFrameData, p: Paint) {
        can.drawPoint(data.center.x, data.center.y, p)
        can.drawPoint(data.min.x, data.min.y, p)
        can.drawPoint(data.hr.x, data.hr.y, p)
    }

    private fun makeCenter(can: Canvas, data: ActiveFrameData, p: Paint) {
        can.drawPoint(data.center.x, data.center.y, p)
    }

    private fun makeSeconds(can: Canvas, data: ActiveFrameData, p: Paint) {
        can.drawPoint(data.sec.x, data.sec.y, p)
    }

    private fun makeMinAndHr(can: Canvas, data: ActiveFrameData, p: Paint) {
        can.drawPoint(data.hr.x, data.hr.y, p)
        can.drawPoint(data.min.x, data.min.y, p)
    }
}
