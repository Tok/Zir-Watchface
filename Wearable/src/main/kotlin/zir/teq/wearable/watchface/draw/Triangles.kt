package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Triangles {
    fun draw(can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(PaintType.SHAPE)
        if (ConfigData.hasOutline()) {
            prepareAndDraw(can, data, DrawUtil.makeOutline(p))
        }
        prepareAndDraw(can, data, p)
    }

    private fun prepareAndDraw(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        if (ConfigData.theme.triangles.active) {
            drawTriangle(can, data, p)
        }
    }

    private fun drawTriangle(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        with(data) {
            val hmP = DrawUtil.applyElasticity(p, unit / DrawUtil.calcDistance(hour.p, minute.p))
            val hsP = DrawUtil.applyElasticity(p, unit / DrawUtil.calcDistance(hour.p, second.p))
            val msP = DrawUtil.applyElasticity(p, unit / DrawUtil.calcDistance(minute.p, second.p))
            can.drawLine(second.p.x, second.p.y, minute.p.x, minute.p.y, hmP)
            can.drawLine(second.p.x, second.p.y, hour.p.x, hour.p.y, hsP)
            can.drawLine(minute.p.x, minute.p.y, hour.p.x, hour.p.y, msP)
        }
    }
}