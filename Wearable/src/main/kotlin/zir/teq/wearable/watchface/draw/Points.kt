package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Points {
    //TODO implement point stacking?
    fun drawActiveCenter(can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(PaintType.POINT)
        if (ConfigData.hasOutline()) {
            makeCenter(can, data, DrawUtil.makeOutline(p))
        }
        makeCenter(can, data, p)
    }

    fun drawActive(can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(PaintType.POINT)
        if (ConfigData.hasOutline()) {
            val outlineP = DrawUtil.makeOutline(p)
            makeSeconds(can, data, outlineP)
            makeMinAndHr(can, data, outlineP)
        }
        makeSeconds(can, data, p)
        makeMinAndHr(can, data, p)
    }

    fun drawAmbient(can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(PaintType.POINT)
        if (ConfigData.hasOutline()) {
            drawAmbientPoints(can, data, DrawUtil.makeOutline(p))
        }
        drawAmbientPoints(can, data, p)
    }

    private fun drawAmbientPoints(can: Canvas, data: DrawUtil.AmbientFrameData, p: Paint) {
        if (ConfigData.theme.points.ambient) {
            can.drawPoint(data.center.x, data.center.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            can.drawPoint(data.hr.x, data.hr.y, p)
        }
    }

    fun makeCenter(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        if (ConfigData.theme.points.active) {
            can.drawPoint(data.center.x, data.center.y, p)
        }
    }

    fun makeSeconds(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        if (ConfigData.theme.points.active) {
            can.drawPoint(data.sec.x, data.sec.y, p)
        }
    }

    fun makeMinAndHr(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        if (ConfigData.theme.points.active) {
            can.drawPoint(data.hr.x, data.hr.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
        }
    }
}