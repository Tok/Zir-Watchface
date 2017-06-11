package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Circles {
    fun drawActive(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                   can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(ctx, PaintType.CIRCLE, theme, stroke, pal)
        if (theme.hasOutline) {
            val outlineP = DrawUtil.makeOutline(ctx, p, theme)
            makeSlow(can, data, theme, outlineP)
            makeFast(can, data, theme, outlineP)
        }
        makeSlow(can, data, theme, p)
        makeFast(can, data, theme, p)
    }

    fun drawAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                    can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(ctx, PaintType.CIRCLE_AMB, theme, stroke, pal)
        if (theme.hasOutline) {
            makeAmbient(can, data, theme, DrawUtil.makeOutline(ctx, p, theme))
        }
        makeAmbient(can, data, theme, p)
    }

    private fun makeFast(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.circles.active) {
            drawLine(data.getRef(can), p, data.hrRot, data.secRot, data.hr, data.sec)
            drawLine(data.getRef(can), p, data.minRot, data.secRot, data.min, data.sec)
        }
    }

    private fun makeSlow(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.circles.active) {
            drawLine(data.getRef(can), p, data.hrRot, data.minRot, data.hr, data.min)
        }
    }

    private fun makeAmbient(can: Canvas, data: DrawUtil.AmbientFrameData, theme: Theme, p: Paint) {
        if (theme.circles.ambient) {
            can.drawCircle(data.ccCenter.x, data.ccCenter.y, data.ccRadius, p)
        }
    }

    private fun drawLine(ref: DrawUtil.Ref, paint: Paint, hrRot: Float, secRot: Float, hr: PointF, sec: PointF) {
        if (!areHandsAlmostCollinear(hrRot, secRot)) {
            drawCircle(ref.can, paint, ref.center, hr, sec)
        } else {
            drawFullLine(ref.can, paint, hrRot, secRot, ref.unit)
        }
    }

    private fun drawCircle(can: Canvas, paint: Paint, center: PointF, firstHand: PointF, secondHand: PointF) {
        val ccCenter = DrawUtil.calcCircumcenter(center, firstHand, secondHand)
        val ccRadius = DrawUtil.calcDistance(center, ccCenter)
        can.drawCircle(ccCenter.x, ccCenter.y, ccRadius, paint)
    }

    private fun drawFullLine(can: Canvas, paint: Paint, fromHandRot: Float, toHandRot: Float, unit: Float) {
        val fromExtended = calcOuterPosition(fromHandRot, unit)
        val toExtended = calcOuterPosition(fixRot(toHandRot, fromHandRot), unit)
        can.drawLine(fromExtended.x, fromExtended.y, toExtended.x, toExtended.y, paint)
    }

    private fun areHandsAlmostCollinear(firstRad: Float, secondRad: Float): Boolean {
        val absDiff = if (firstRad < secondRad) secondRad - firstRad else firstRad - secondRad
        return absDiff < DrawUtil.HALF_MINUTE_AS_RAD
    }

    private fun calcOuterPosition(rot: Float, unit: Float): PointF {
        val x = unit + (Math.sin(rot.toDouble()) * unit).toFloat()
        val y = unit + (-Math.cos(rot.toDouble()) * unit).toFloat()
        return PointF(x, y)
    }

    private fun fixRot(value: Float, compare: Float): Float {
        return if (arePointsAligned(value, compare)) clipRad(value + DrawUtil.PI) else value
    }

    private fun clipRad(rad: Float): Float {
        return if (rad < 0F) rad + DrawUtil.TAU else if (rad > DrawUtil.TAU) rad - DrawUtil.TAU else rad
    }

    private fun arePointsAligned(firstRad: Float, secondRad: Float): Boolean {
        return Math.abs(firstRad - secondRad) < 15F * DrawUtil.ONE_MINUTE_AS_RAD
    }
}