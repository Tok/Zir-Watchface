package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Triangles {
    val ELASTICITY = 0.5F / DrawUtil.PHI
    fun draw(can: Canvas, data: DrawUtil.ActiveFrameData) {
        if (ConfigData.theme.triangles.active) {
            val p = Palette.createPaint(PaintType.SHAPE)
            if (ConfigData.hasOutline()) {
                drawTriangle(can, data, p, true)
            }
            drawTriangle(can, data, p)
        }
    }

    private fun drawTriangle(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint, isOutline: Boolean = false) {
        with(data) {
            val hmFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, minute.p)
            val hsFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, second.p)
            val msFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, second.p)
            val hmP = DrawUtil.applyElasticity(p, hmFactor, isOutline)
            val hsP = DrawUtil.applyElasticity(p, hsFactor, isOutline)
            val msP = DrawUtil.applyElasticity(p, msFactor, isOutline)
            can.drawLine(second.p.x, second.p.y, minute.p.x, minute.p.y, hmP)
            can.drawLine(second.p.x, second.p.y, hour.p.x, hour.p.y, hsP)
            can.drawLine(minute.p.x, minute.p.y, hour.p.x, hour.p.y, msP)
        }
    }
}