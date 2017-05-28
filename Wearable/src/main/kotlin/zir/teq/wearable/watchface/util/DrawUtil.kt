package zir.watchface

import android.content.Context
import android.graphics.*
import android.text.style.TtsSpan
import android.util.Log
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import java.util.*


/**
 * Created by Zir on 03.01.2016.
 * Recreated 20.05.2017
 */
class DrawUtil() {
    data class HandData(val p: PointF, val radians: Float, val maybeExtended: PointF)
    data class Ref(val can: Canvas, val unit: Float, val center: PointF)
    class FrameData(cal: Calendar, bounds: Rect) {
        val hh = cal.get(Calendar.HOUR_OF_DAY)
        val mm = cal.get(Calendar.MINUTE)
        val ss = cal.get(Calendar.SECOND)
        val minRot: Float = (mm + (ss / 60F)) / 30F * PI
        val hrRot: Float = (hh + (mm / 60F)) / 6F * PI
        val unit = bounds.width() / 2F
        val center = PointF(unit, unit)
        fun getRef(can: Canvas): Ref = Ref(can, unit, center)
        fun calcDistFromBorder(can: Canvas, stroke: Stroke): Float {
            return can.height / (can.height + 2F * stroke.dim)
        }
    }

    fun drawBackground(can: Canvas, paint: Paint) {
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), paint)
    }

    fun draw(ctx: Context, col: Col, stroke: Stroke, theme: Theme, can: Canvas, bounds: Rect, isAmbient: Boolean, calendar: Calendar) {
        Log.d(TAG, "draw(): " + col)
        val data = FrameData(calendar, bounds)
        val isActive = !isAmbient
        if (isActive) {
            val ms = calendar.get(Calendar.MILLISECOND)
            drawFace(ctx, col, stroke, theme, can, data, ms)
            if (theme.text.active) {
                drawText(ctx, can, false, calendar)
            }
        } else {
            drawAmbientFace(ctx, col, stroke, theme, can, data)
            if (theme.text.ambient) {
                drawText(ctx, can, true, calendar)
            }
        }
    }

    fun drawFace(ctx: Context, col: Col, stroke: Stroke, theme: Theme, can: Canvas, data: FrameData, ms: Int) {
        val secRot = (data.ss + ms / 1000F) / 30F * PI
        val secLength = data.unit * data.calcDistFromBorder(can, stroke)
        val minLength = secLength / PHI
        val hrLength = minLength / PHI
        val hr = calcPosition(data.hrRot, hrLength, data.unit)
        val min = calcPosition(data.minRot, minLength, data.unit)
        val sec = calcPosition(secRot, secLength, data.unit)
        val hrExtended = if (theme.circles.active) calcPosition(data.hrRot, secLength, data.unit) else hr
        val minExtended = if (theme.circles.active) calcPosition(data.minRot, secLength, data.unit) else min
        val secExtended = if (theme.circles.active) calcPosition(secRot, secLength, data.unit) else sec
        Log.d(TAG, "hr(): " + hrExtended + " min(): " + minExtended + " sec(): " + secExtended + " ms: " + ms)
        val hour = HandData(hr, data.hrRot, hrExtended)
        val minute = HandData(min, data.minRot, minExtended)
        val second = HandData(sec, secRot, secExtended)
        if (theme.hands.active) {
            val paint = Col.createPaint(ctx, PaintType.HAND, col, stroke)
            drawHands(data.getRef(can), paint, hour, minute, second)
        }
        if (theme.points.active && !theme.circles.active) {
            val paint = Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.center.x, data.center.y, paint)
        }
        if (theme.triangles.active) {
            drawTriangle(ctx, can, hour, minute, second, col, stroke)
        }
        if (theme.circles.active) {
            val paint = Col.createPaint(ctx, PaintType.CIRCLE_AMB, col, stroke)
            drawCircleLine(data.getRef(can), paint, data.hrRot, secRot, hr, sec)
            drawCircleLine(data.getRef(can), paint, data.minRot, secRot, min, sec)
        }
        if (theme.points.active) {
            val paint = Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(sec.x, sec.y, paint)
        }
        if (theme.circles.active) {
            val paint = Col.createPaint(ctx, PaintType.CIRCLE, col, stroke)
            drawCircleLine(data.getRef(can), paint, data.hrRot, data.minRot, hr, min)
        }
        if (theme.points.active) {
            val paint = Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(hr.x, hr.y, paint)
            can.drawPoint(min.x, min.y, paint)
            if (theme.circles.active) {
                can.drawPoint(data.center.x, data.center.y, paint)
            }
        }
    }

    fun drawAmbientFace(ctx: Context, col: Col, stroke: Stroke, theme: Theme, can: Canvas, data: FrameData) {
        val minLength = data.unit * data.calcDistFromBorder(can, stroke) / PHI
        val hrLength = minLength / PHI
        val hr = calcPosition(data.hrRot, hrLength, data.unit)
        val min = calcPosition(data.minRot, minLength, data.unit)
        val hour = HandData(hr, data.hrRot, data.center)
        val minute = HandData(min, data.minRot, data.center)
        val ccCenter = calcCircumcenter(data.center, hr, min)
        val ccRadius = calcDistance(min, ccCenter)
        if (theme.circles.ambient) {
            val paint = Col.createPaint(ctx, PaintType.CIRCLE_AMB, col, stroke)
            can.drawCircle(ccCenter.x, ccCenter.y, ccRadius, paint)
        }
        if (theme.hands.ambient) {
            val paint = Col.createPaint(ctx, PaintType.SHAPE_AMB, col, stroke)
            drawHand(data.getRef(can), paint, hour)
            drawHand(data.getRef(can), paint, minute)
            can.drawLine(min.x, min.y, hr.x, hr.y, paint)
        }
        if (theme.points.ambient) {
            val paint = Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.center.x, data.center.y, paint)
            can.drawPoint(min.x, min.y, paint)
            can.drawPoint(hr.x, hr.y, paint)
        }
    }

    fun drawText(ctx: Context, can: Canvas, isAmbient: Boolean, calendar: Calendar) {
        val hh = calendar.get(Calendar.HOUR_OF_DAY)
        val mm = calendar.get(Calendar.MINUTE)
        val text = if (isAmbient) { // Draw HH:MM in ambient mode or HH:MM:SS in interactive mode.
            String.format("%02d:%02d", hh, mm)
        } else {
            val ss = calendar.get(Calendar.SECOND)
            String.format("%02d:%02d:%02d", hh, mm, ss)
        }
        val paint = Col.prepareTextPaint(ctx, R.color.text)
        val yOffset = ctx.resources.getDimension(R.dimen.y_offset)
        val xOffset = ctx.resources.getDimension(R.dimen.x_offset)
        can.drawText(text, xOffset, yOffset, paint)
    }

    private fun drawHands(ref: Ref, paint: Paint, hour: HandData, minute: HandData, second: HandData) {
        drawHand(ref, paint, second)
        drawHand(ref, paint, hour)
        drawHand(ref, paint, minute)
    }

    private fun drawTriangle(ctx: Context, can: Canvas, hr: HandData, min: HandData, sec: HandData, col: Col, stroke: Stroke) {
        val paint = Col.createPaint(ctx, PaintType.SHAPE, col, stroke)
        can.drawLine(sec.p.x, sec.p.y, min.p.x, min.p.y, paint)
        can.drawLine(sec.p.x, sec.p.y, hr.p.x, hr.p.y, paint)
        can.drawLine(min.p.x, min.p.y, hr.p.x, hr.p.y, paint)
    }

    private fun drawHand(ref: Ref, paint: Paint, hand: HandData) {
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }

    private fun drawCircleLine(ref: Ref, paint: Paint, hrRot: Float, secRot: Float, hr: PointF, sec: PointF) {
        if (!areHandsAlmostCollinear(hrRot, secRot)) {
            drawCircle(ref.can, paint, ref.center, hr, sec)
        } else {
            drawFullLine(ref.can, paint, hrRot, secRot, ref.unit)
        }
    }

    private fun drawCircle(can: Canvas, paint: Paint, center: PointF, firstHand: PointF, secondHand: PointF) {
        val ccCenter = calcCircumcenter(center, firstHand, secondHand)
        val ccRadius = calcDistance(center, ccCenter)
        can.drawCircle(ccCenter.x, ccCenter.y, ccRadius, paint)
    }

    private fun drawFullLine(can: Canvas, paint: Paint, fromHandRot: Float, toHandRot: Float, unit: Float) {
        val fromExtended = calcOuterPosition(fromHandRot, unit)
        val toExtended = calcOuterPosition(fixRot(toHandRot, fromHandRot), unit)
        can.drawLine(fromExtended.x, fromExtended.y, toExtended.x, toExtended.y, paint)
    }

    private fun areHandsAlmostCollinear(firstRad: Float, secondRad: Float): Boolean {
        val absDiff = if (firstRad < secondRad) secondRad - firstRad else firstRad - secondRad
        return absDiff < HALF_MINUTE_AS_RAD
    }

    private fun fixRot(value: Float, compare: Float): Float {
        return if (arePointsAligned(value, compare)) clipRad(value + PI) else value
    }

    private fun clipRad(rad: Float): Float {
        return if (rad < 0F) rad + TAU else if (rad > TAU) rad - TAU else rad
    }

    private fun arePointsAligned(firstRad: Float, secondRad: Float): Boolean {
        return Math.abs(firstRad - secondRad) < 15F * ONE_MINUTE_AS_RAD
    }

    private fun calcOuterPosition(rot: Float, unit: Float): PointF {
        val x = unit + (Math.sin(rot.toDouble()) * unit).toFloat()
        val y = unit + (-Math.cos(rot.toDouble()) * unit).toFloat()
        return PointF(x, y)
    }

    private fun calcPosition(rot: Float, length: Float, centerOffset: Float): PointF {
        val x = centerOffset + (Math.sin(rot.toDouble()) * length).toFloat()
        val y = centerOffset + (-Math.cos(rot.toDouble()) * length).toFloat()
        return PointF(x, y)
    }

    private fun calcCircumcenter(a: PointF, b: PointF, c: PointF): PointF {
        val dA = a.x * a.x + a.y * a.y
        val dB = b.x * b.x + b.y * b.y
        val dC = c.x * c.x + c.y * c.y
        val divisor = 2F * (a.x * (c.y - b.y) + b.x * (a.y - c.y) + c.x * (b.y - a.y))
        val x = (dA * (c.y - b.y) + dB * (a.y - c.y) + dC * (b.y - a.y)) / divisor
        val y = -(dA * (c.x - b.x) + dB * (a.x - c.x) + dC * (b.x - a.x)) / divisor
        return PointF(x, y)
    }

    private fun calcDistance(a: PointF, b: PointF): Float {
        val p = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
        return Math.sqrt(p.toDouble()).toFloat()
    }

    companion object {
        private val TAG = this::class.java.simpleName
        val PHI = 1.618033988F
        val PI = Math.PI.toFloat() //180 Degree
        val TAU = PI * 2F //180 Degree
        val ONE_MINUTE_AS_RAD = PI / 30F
        val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F
        val yOffset: Float = 98F
        var xOffset: Float = 0.toFloat()
    }
}
