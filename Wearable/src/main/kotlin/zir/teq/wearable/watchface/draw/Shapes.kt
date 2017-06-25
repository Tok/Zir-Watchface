package zir.teq.wearable.watchface.draw

import android.graphics.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrameData
import zir.teq.wearable.watchface.model.data.frame.AmbientFrameData
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil


object Shapes {
    val ALPHA_FACTOR = 1F / DrawUtil.PHI
    val USE_GRADIENTS = false
    fun drawActive(can: Canvas, data: ActiveFrameData, p: Paint) {
        p.style = Paint.Style.FILL
        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
        if (ConfigData.hasOutline()) {
            val outlineP = DrawUtil.makeOutline(p)
            drawShapes(can, data, outlineP)
        }
        drawShapes(can, data, p)
    }

    fun drawAmbient(can: Canvas, data: AmbientFrameData) {
        val p = Palette.createPaint(PaintType.SHAPE_AMB)
        p.style = Paint.Style.FILL
        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
        if (ConfigData.hasOutline()) {
            drawAmbientShape(can, data, DrawUtil.makeOutline(p))
        }
        drawAmbientShape(can, data, p)
    }

    private fun drawAmbientShape(can: Canvas, data: AmbientFrameData, p: Paint) {
        if (ConfigData.theme.shapes.ambient) {
            drawCenterTriangle(can, p, data.center, data.hr, data.min)
        }
    }

    private fun drawShapes(can: Canvas, data: ActiveFrameData, p: Paint) {
        if (ConfigData.theme.shapes.active) {
            drawCenterTriangle(can, p, data.center, data.hr, data.min)
            drawCenterTriangle(can, p, data.center, data.hr, data.sec)
            drawCenterTriangle(can, p, data.center, data.min, data.sec)
            drawTriangle(can, p, data.hr, data.min, data.sec)
        }
    }

    private fun drawTriangle(can: Canvas, p: Paint, a: PointF, b: PointF, c: PointF) {
        p.alpha = (p.alpha * ALPHA_FACTOR).toInt()
        drawTrianglePath(can, p, a, b, c)
    }

    private fun drawCenterTriangle(can: Canvas, p: Paint, center: PointF, a: PointF, b: PointF) {
        p.alpha = (p.alpha * ALPHA_FACTOR).toInt()
        if (USE_GRADIENTS) {
            val aDist = DrawUtil.calcDistance(center, a)
            val bDist = DrawUtil.calcDistance(center, b)
            val shaderRad = Math.max(aDist, bDist)
            p.shader = RadialGradient(center.x, center.y, shaderRad, p.color, Color.BLACK, Shader.TileMode.CLAMP)
        }
        drawTrianglePath(can, p, center, a, b)
    }

    private fun drawTrianglePath(can: Canvas, p: Paint, a: PointF, b: PointF, c: PointF) {
        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(a.x, a.y)
        path.lineTo(b.x, b.y)
        path.lineTo(c.x, c.y)
        path.close()
        can.drawPath(path, p)
    }
}
