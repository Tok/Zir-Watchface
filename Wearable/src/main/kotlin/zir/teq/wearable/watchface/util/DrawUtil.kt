package zir.watchface

import android.content.Context
import android.graphics.*
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
    open class FrameData(cal: Calendar, bounds: Rect) {
        val hh = cal.get(Calendar.HOUR_OF_DAY)
        val mm = cal.get(Calendar.MINUTE)
        val ss = cal.get(Calendar.SECOND)
        val minRot: Float = (mm + (ss / 60F)) / 30F * PI
        val hrRot: Float = (hh + (mm / 60F)) / 6F * PI
        val unit = bounds.width() / 2F
        val center = PointF(unit, unit)
        fun getRef(can: Canvas): Ref = Ref(can, unit, center)
    }

    class ActiveFrameData(cal: Calendar, bounds: Rect, can: Canvas, stroke: Stroke, theme: Theme): FrameData(cal, bounds) {
        val ms = cal.get(Calendar.MILLISECOND)
        val secRot = (ss + ms / 1000F) / 30F * PI
        val secLength = unit * calcDistFromBorder(can, stroke)
        val minLength = secLength / PHI
        val hrLength = minLength / PHI
        val hr = calcPosition(hrRot, hrLength, unit)
        val min = calcPosition(minRot, minLength, unit)
        val sec = calcPosition(secRot, secLength, unit)
        val hrExtended = if (theme.circles.active) calcPosition(hrRot, secLength, unit) else hr
        val minExtended = if (theme.circles.active) calcPosition(minRot, secLength, unit) else min
        val secExtended = if (theme.circles.active) calcPosition(secRot, secLength, unit) else sec
        val hour = HandData(hr, hrRot, hrExtended)
        val minute = HandData(min, minRot, minExtended)
        val second = HandData(sec, secRot, secExtended)
    }

    class AmbientFrameData(cal: Calendar, bounds: Rect, can: Canvas, stroke: Stroke): FrameData(cal, bounds) {
        val minLength = unit * calcDistFromBorder(can, stroke) / PHI
        val hrLength = minLength / PHI
        val hr = calcPosition(hrRot, hrLength, unit)
        val min = calcPosition(minRot, minLength, unit)
        val hour = HandData(hr, hrRot, center)
        val minute = HandData(min, minRot, center)
        val ccCenter = calcCircumcenter(center, hr, min)
        val ccRadius = calcDistance(min, ccCenter)
    }

    fun drawBackground(can: Canvas, paint: Paint) {
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), paint)
    }

    fun draw(ctx: Context, col: Col, stroke: Stroke, theme: Theme, can: Canvas, bounds: Rect,
             isAmbient: Boolean, calendar: Calendar) {
        val isActive = !isAmbient
        if (isActive) {
            val data = ActiveFrameData(calendar, bounds, can, stroke, theme)
            drawActiveFace(ctx, col, stroke, theme, can, data)
            if (theme.text.active) {
                drawText(ctx, can, false, calendar)
            }
        } else {
            val data = AmbientFrameData(calendar, bounds, can, stroke)
            drawAmbientFace(ctx, col, stroke, theme, can, data)
            if (theme.text.ambient) {
                drawText(ctx, can, true, calendar)
            }
        }
    }

    fun drawActiveFace(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                       can: Canvas, data: ActiveFrameData) {
        makeActiveCirclePair(ctx, col, stroke, theme, can, data)
        makeActiveHandPair(ctx, col, stroke, theme, can, data)
        makeActiveCenterPointPair(ctx, col, stroke, theme, can, data)
        makeActiveTrianglesPair(ctx, col, stroke, theme, can, data)
        makeActivePointsPair(ctx, col, stroke, theme, can, data)
    }

    fun drawAmbientFace(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                        can: Canvas, data: AmbientFrameData) {
        makeAmbientCirclePair(ctx, col, stroke, theme, can, data)
        makeAmbientHandsPair(ctx, col, stroke, theme, can, data)
        makeAmbientPonitsPair(ctx, col, stroke, theme, can, data)
    }

    private fun makeAmbientCirclePair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                     can: Canvas, data: AmbientFrameData) {
        if (theme.hasOutline) {
            val circleOutline = Col.createPaint(ctx, PaintType.CIRCLE_OUTLINE, Col.BLACK, stroke)
            makePaintedAmbientCircle(ctx, col, stroke, theme, can, data, circleOutline)
        }
        makePaintedAmbientCircle(ctx, col, stroke, theme, can, data)
    }

    private fun makeAmbientHandsPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                     can: Canvas, data: AmbientFrameData) {
        if (theme.hasOutline) {
            val shapeOutline = Col.createPaint(ctx, PaintType.SHAPE_OUTLINE, Col.BLACK, stroke)
            makePaintedAmbientHands(ctx, col, stroke, theme, can, data, shapeOutline)
        }
        makePaintedAmbientHands(ctx, col, stroke, theme, can, data)
    }

    private fun makeAmbientPonitsPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                     can: Canvas, data: AmbientFrameData) {
        if (theme.hasOutline) {
            val pointOutline = Col.createPaint(ctx, PaintType.POINT_OUTLINE, Col.BLACK, stroke)
            makePaintedAmbientPoints(ctx, col, stroke, theme, can, data, pointOutline)
        }
        makePaintedAmbientPoints(ctx, col, stroke, theme, can, data)
    }

    private fun makeActiveCirclePair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                     can: Canvas, data: ActiveFrameData) {
        if (theme.hasOutline) {
            val circleOutline = Col.createPaint(ctx, PaintType.CIRCLE_OUTLINE, Col.BLACK, stroke)
            makePaintedSlowCircle(ctx, col, stroke, theme, can, data, circleOutline)
            makePaintedFastCircles(ctx, col, stroke, theme, can, data, circleOutline)
        }
        makePaintedSlowCircle(ctx, col, stroke, theme, can, data)
        makePaintedFastCircles(ctx, col, stroke, theme, can, data)
    }

    private fun makeActiveHandPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                   can: Canvas, data: ActiveFrameData) {
        if (theme.hasOutline) {
            val handOutline = Col.createPaint(ctx, PaintType.HAND_OUTLINE, Col.BLACK, stroke)
            makePaintedActiveHandsFirst(ctx, col, stroke, theme, can, data, handOutline)
        }
        makePaintedActiveHandsFirst(ctx, col, stroke, theme, can, data)
    }

    private fun makeActiveCenterPointPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                          can: Canvas, data: ActiveFrameData) {
        if (theme.hasOutline) {
            val pointOutline = Col.createPaint(ctx, PaintType.POINT_OUTLINE, Col.BLACK, stroke)
            makeCenterPoint(ctx, col, stroke, theme, can, data, pointOutline) //center
        }
        makeCenterPoint(ctx, col, stroke, theme, can, data) //center
    }

    private fun makeActiveTrianglesPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                        can: Canvas, data: ActiveFrameData) {
        if (theme.hasOutline) {
            val shapeOutline = Col.createPaint(ctx, PaintType.SHAPE_OUTLINE, Col.BLACK, stroke)
            makePaintedTrianglesFirst(ctx, col, stroke, theme, can, data, shapeOutline)
        }
        makePaintedTrianglesFirst(ctx, col, stroke, theme, can, data)
    }

    private fun makeActivePointsPair(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                     can: Canvas, data: ActiveFrameData) {
        if (theme.hasOutline) {
            val pointOutline = Col.createPaint(ctx, PaintType.POINT_OUTLINE, Col.BLACK, stroke)
            makeSecondsPoint(ctx, col, stroke, theme, can, data, pointOutline)
            makeMinAndHrPoints(ctx, col, stroke, theme, can, data, pointOutline)
        }
        makeSecondsPoint(ctx, col, stroke, theme, can, data)
        makeMinAndHrPoints(ctx, col, stroke, theme, can, data)
    }

    private fun makePaintedActiveHandsFirst(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                        can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.hands.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.HAND, col, stroke)
            drawHands(data.getRef(can), p, data.hour, data.minute, data.second)
        }
    }

    private fun makeCenterPoint(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.center.x, data.center.y, p)
        }
    }

    private fun makeSecondsPoint(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                 can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.sec.x, data.sec.y, p)
        }
    }

    private fun makeMinAndHrPoints(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                   can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.hr.x, data.hr.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            /* foreground center point */
            /*
            if (theme.circles.active) {
                can.drawPoint(data.center.x, data.center.y, p)
            }*/
        }
    }
    private fun makePaintedTrianglesFirst(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                       can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.triangles.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.SHAPE, col, stroke)
            drawTriangle(p, can, data.hour, data.minute, data.second)
        }
    }
    private fun makePaintedFastCircles(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                       can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.circles.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.CIRCLE, col, stroke)
            drawCircleLine(data.getRef(can), p, data.hrRot, data.secRot, data.hr, data.sec)
            drawCircleLine(data.getRef(can), p, data.minRot, data.secRot, data.min, data.sec)
        }
    }
    private fun makePaintedSlowCircle(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                      can: Canvas, data: ActiveFrameData, paint: Paint? = null) {
        if (theme.circles.active) {
            val p = paint ?: Col.createPaint(ctx, PaintType.CIRCLE, col, stroke)
            drawCircleLine(data.getRef(can), p, data.hrRot, data.minRot, data.hr, data.min)
        }
    }

    private fun makePaintedAmbientCircle(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                         can: Canvas, data: AmbientFrameData, paint: Paint? = null) {
        if (theme.circles.ambient) {
            val p = paint ?: Col.createPaint(ctx, PaintType.CIRCLE_AMB, col, stroke)
            can.drawCircle(data.ccCenter.x, data.ccCenter.y, data.ccRadius, p)
        }
    }

    private fun makePaintedAmbientHands(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                        can: Canvas, data: AmbientFrameData, paint: Paint? = null) {
        if (theme.hands.ambient) {
            val p = paint ?: Col.createPaint(ctx, PaintType.SHAPE_AMB, col, stroke)
            drawHand(data.getRef(can), p, data.hour)
            drawHand(data.getRef(can), p, data.minute)
            can.drawLine(data.min.x, data.min.y, data.hr.x, data.hr.y, p)
        }
    }

    private fun makePaintedAmbientPoints(ctx: Context, col: Col, stroke: Stroke, theme: Theme,
                                         can: Canvas, data: AmbientFrameData, paint: Paint? = null) {
        if (theme.points.ambient) {
            val p = paint ?: Col.createPaint(ctx, PaintType.POINT, col, stroke)
            can.drawPoint(data.center.x, data.center.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            can.drawPoint(data.hr.x, data.hr.y, p)
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

    private fun drawTriangle(paint: Paint, can: Canvas, hr: HandData, min: HandData, sec: HandData) {
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

    companion object {
        fun calcDistFromBorder(can: Canvas, stroke: Stroke): Float {
            return can.height / (can.height + 2F * stroke.dim)
        }
        fun calcPosition(rot: Float, length: Float, centerOffset: Float): PointF {
            val x = centerOffset + (Math.sin(rot.toDouble()) * length).toFloat()
            val y = centerOffset + (-Math.cos(rot.toDouble()) * length).toFloat()
            return PointF(x, y)
        }
        fun calcCircumcenter(a: PointF, b: PointF, c: PointF): PointF {
            val dA = a.x * a.x + a.y * a.y
            val dB = b.x * b.x + b.y * b.y
            val dC = c.x * c.x + c.y * c.y
            val divisor = 2F * (a.x * (c.y - b.y) + b.x * (a.y - c.y) + c.x * (b.y - a.y))
            val x = (dA * (c.y - b.y) + dB * (a.y - c.y) + dC * (b.y - a.y)) / divisor
            val y = -(dA * (c.x - b.x) + dB * (a.x - c.x) + dC * (b.x - a.x)) / divisor
            return PointF(x, y)
        }
        fun calcDistance(a: PointF, b: PointF): Float {
            val p = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
            return Math.sqrt(p.toDouble()).toFloat()
        }

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
