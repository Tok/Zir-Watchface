package zir.teq.wearable.watchface.draw

import android.graphics.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.types.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT
import zir.teq.wearable.watchface.util.DrawUtil


object Shapes {
    val ALPHA_FACTOR = 1F / DrawUtil.PHI
    val USE_GRADIENTS = false
    fun drawActive(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(SHAPE to ACTIVE)) {
            p.style = Paint.Style.FILL
            drawCenterTriangle(can, p, frame.center, frame.hr, frame.min)
            drawCenterTriangle(can, p, frame.center, frame.hr, frame.sec)
            drawCenterTriangle(can, p, frame.center, frame.min, frame.sec)
            drawTriangle(can, p, frame.hr, frame.min, frame.sec)
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrame) {
        if (ConfigData.isOn(SHAPE to AMBIENT)) {
            val p = Palette.createPaint(PaintType.SHAPE_AMB)
            p.style = Paint.Style.FILL
            drawCenterTriangle(can, p, data.center, data.hr, data.min)
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
