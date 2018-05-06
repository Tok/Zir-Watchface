package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.POINTS
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.util.DrawUtil

object Points {
    //TODO implement point stacking?
    fun drawActiveCenter(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if ((StyleOutline.load() as StyleOutline).isOn) {
                makeCenter(can, frame, DrawUtil.makeOutline(p))
            }
            makeCenter(can, frame, p)
        }
    }

    fun drawActive(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(POINTS to ACTIVE)) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if ((StyleOutline.load() as StyleOutline).isOn) {
                val outlineP = DrawUtil.makeOutline(p)
                makeSeconds(can, frame, outlineP)
                makeMinAndHr(can, frame, outlineP)
            }
            makeSeconds(can, frame, p)
            makeMinAndHr(can, frame, p)
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrame) {
        if (ConfigData.isOn(POINTS to AMBIENT)) {
            val p = Palette.createPaint(PaintType.POINT)
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if ((StyleOutline.load() as StyleOutline).isOn) {
                drawAmbientPoints(can, data, DrawUtil.makeOutline(p))
            }
            drawAmbientPoints(can, data, p)
        }
    }

    private fun drawAmbientPoints(can: Canvas, data: AmbientFrame, p: Paint) {
        can.drawPoint(data.center.x, data.center.y, p)
        can.drawPoint(data.min.x, data.min.y, p)
        can.drawPoint(data.hr.x, data.hr.y, p)
    }

    private fun makeCenter(can: Canvas, frame: ActiveFrame, p: Paint) {
        can.drawPoint(frame.center.x, frame.center.y, p)
    }

    private fun makeSeconds(can: Canvas, frame: ActiveFrame, p: Paint) {
        can.drawPoint(frame.sec.x, frame.sec.y, p)
    }

    private fun makeMinAndHr(can: Canvas, frame: ActiveFrame, p: Paint) {
        can.drawPoint(frame.hr.x, frame.hr.y, p)
        can.drawPoint(frame.min.x, frame.min.y, p)
    }
}
