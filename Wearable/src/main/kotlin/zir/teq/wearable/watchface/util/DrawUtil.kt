package zir.watchface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import zir.teq.wearable.watchface.Col
import zir.teq.wearable.watchface.R
import java.util.*


/**
 * Created by Zir on 03.01.2016.
 * Recreated 20.05.2017
 */
class DrawUtil() {
    private inner class Ref(val can: Canvas, val unit: Float, val center: PointF)
    private inner class HandData(val p: PointF, val radians: Float, val maybeExtended: PointF)

    fun drawBackground(can: Canvas, paint: Paint) {
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), paint)
    }

    fun createTime(cal: Calendar): Triple<Int, Int, Int> {
        return Triple(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND))
    }

    fun draw(ctx: Context, col: Col, can: Canvas, bounds: Rect, isAmbient: Boolean, calendar: Calendar) {
        Log.d(TAG, "draw(): " + col)
        val (hh, mm, ss) = createTime(calendar)
        val unit = bounds.width() / 2F
        val center = PointF(unit, unit)
        val ref = Ref(can, unit, center)

        //val stroke = cfg.stroke
        //val distFromBorder = can.height / (can.height + 2F * stroke)
        val distFromBorder = can.height / (can.height + 2F * 3F) //FIXME

        val minRot: Float = (mm + (ss / 60F)) / 30F * PI
        val hrRot: Float = (hh + (mm / 60F)) / 6F * PI

        val drawCircle = true //TODO
        val drawHands = true
        val drawPoints = true
        val drawTriangle = true
        val drawText = true
        val drawActiveCircles = true //FIXME

        if (isAmbient) {
            val minLength = unit * distFromBorder / Config.PHI
            val hrLength = minLength / Config.PHI
            val hr = calcPosition(hrRot, hrLength, unit)
            val min = calcPosition(minRot, minLength, unit)
            val hour = HandData(hr, hrRot, center)
            val minute = HandData(min, minRot, center)
            val ccCenter = calcCircumcenter(center, hr, min)
            val ccRadius = calcDistance(min, ccCenter)
            if (drawCircle) {
                val paint = Config.findPaint(ctx, Config.PaintType.CIRCLE_AMB, col)
                can.drawCircle(ccCenter.x, ccCenter.y, ccRadius, paint)
            }
            if (drawHands) {
                val paint = Config.findPaint(ctx, Config.PaintType.SHAPE_AMB, col)
                drawHand(ref, paint, hour)
                drawHand(ref, paint, minute)
                can.drawLine(min.x, min.y, hr.x, hr.y, paint)
            }
            if (drawPoints) {
                val paint = Config.findPaint(ctx, Config.PaintType.POINT, col)
                can.drawPoint(center.x, center.y, paint)
                can.drawPoint(min.x, min.y, paint)
                can.drawPoint(hr.x, hr.y, paint)
            }
        } else { //active
            val ms = calendar.get(Calendar.MILLISECOND)
            val secRot = (ss + ms / 1000F) / 30F * PI
            val secLength = unit * distFromBorder
            val minLength = secLength / Config.PHI
            val hrLength = minLength / Config.PHI
            val hr = calcPosition(hrRot, hrLength, unit)
            val min = calcPosition(minRot, minLength, unit)
            val sec = calcPosition(secRot, secLength, unit)
            val hrExtended = if (drawActiveCircles) calcPosition(hrRot, secLength, unit) else hr
            val minExtended = if (drawActiveCircles) calcPosition(minRot, secLength, unit) else min
            val secExtended = if (drawActiveCircles) calcPosition(secRot, secLength, unit) else sec
            Log.d(TAG, "hr(): " + hrExtended + " min(): " + minExtended + " sec(): " + secExtended + " ms: " + ms)
            val hour = HandData(hr, hrRot, hrExtended)
            val minute = HandData(min, minRot, minExtended)
            val second = HandData(sec, secRot, secExtended)
            if (drawHands) {
                val paint = Config.findPaint(ctx, Config.PaintType.HAND, col)
                drawHands(ref, paint, hour, minute, second)
            }
            if (drawPoints && !drawActiveCircles) {
                val paint = Config.prepareTextPaint(ctx, R.color.points)
                can.drawPoint(center.x, center.y, paint)
            }
            if (drawTriangle) {
                drawTriangle(ctx, can, hour, minute, second, col)
            }
            if (drawActiveCircles) {
                val paint = Config.findPaint(ctx, Config.PaintType.CIRCLE_AMB, col)
                drawCircleLine(ref, paint, hrRot, secRot, hr, sec)
                drawCircleLine(ref, paint, minRot, secRot, min, sec)
            }
            if (drawPoints) {
                val paint = Config.prepareTextPaint(ctx, R.color.points)
                can.drawPoint(sec.x, sec.y, paint)
            }
            if (drawActiveCircles) {
                val paint = Config.findPaint(ctx, Config.PaintType.CIRCLE, col)
                drawCircleLine(ref, paint, hrRot, minRot, hr, min)
            }
            if (drawPoints) {
                val paint = Config.prepareTextPaint(ctx, R.color.points)
                can.drawPoint(hr.x, hr.y, paint)
                can.drawPoint(min.x, min.y, paint)
                if (drawActiveCircles) {
                    can.drawPoint(center.x, center.y, paint)
                }
            }
        }
        if (drawText) {
            drawText(ctx, can, isAmbient, calendar)
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
        val paint = Config.prepareTextPaint(ctx, R.color.text)
        val yOffset = ctx.resources.getDimension(R.dimen.y_offset)
        val xOffset = ctx.resources.getDimension(R.dimen.x_offset)
        can.drawText(text, xOffset, yOffset, paint)
    }

    private fun drawHands(ref: Ref, paint: Paint, hour: HandData, minute: HandData, second: HandData) {
        drawHand(ref, paint, second)
        drawHand(ref, paint, hour)
        drawHand(ref, paint, minute)
    }

    private fun drawTriangle(ctx: Context, can: Canvas, hr: HandData, min: HandData, sec: HandData, col: Col) {
        val paint = Config.findPaint(ctx, Config.PaintType.SHAPE, col)
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
        private val TAG = DrawUtil::class.java.simpleName
        val PI = Math.PI.toFloat() //180 Degree
        val TAU = PI * 2F //180 Degree
        val ONE_MINUTE_AS_RAD = PI / 30F
        val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F
    }
}
