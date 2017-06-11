package zir.watchface

import android.content.Context
import android.graphics.*
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.draw.*
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
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

    fun drawBackground(can: Canvas, color: Int) {
        //can.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        can.drawColor(color, PorterDuff.Mode.CLEAR)
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), Palette.prep(color))
    }

    fun draw(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
             can: Canvas, bounds: Rect, calendar: Calendar) {
        if (pal.isAmbient) {
            val data = AmbientFrameData(calendar, bounds, can, stroke)
            drawAmbientFace(ctx, pal, stroke, theme, can, data)
            if (theme.text.ambient) {
                Text.draw(ctx, can, pal, calendar)
            }
        } else {
            val data = ActiveFrameData(calendar, bounds, can, stroke, theme)
            drawActiveFace(ctx, pal, stroke, theme, can, data)
            if (theme.text.active) {
                Text.draw(ctx, can, pal, calendar)
            }
        }
    }

    fun drawActiveFace(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                       can: Canvas, data: ActiveFrameData) {
        Circles.drawActive(ctx, pal, stroke, theme, can, data)
        Hands.drawActive(ctx, pal, stroke, theme, can, data)
        Points.drawActiveCenter(ctx, pal, stroke, theme, can, data)
        Triangles.draw(ctx, pal, stroke, theme, can, data)
        Points.drawActive(ctx, pal, stroke, theme, can, data)
    }

    fun drawAmbientFace(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                        can: Canvas, data: AmbientFrameData) {
        Circles.drawAmbient(ctx, pal, stroke, theme, can, data)
        Hands.drawAmbient(ctx, pal, stroke, theme, can, data)
        Points.drawAmbient(ctx, pal, stroke, theme, can, data)
    }

    companion object {
        fun makeOutline(ctx: Context, p: Paint, theme: Theme): Paint {
            val outLine = Paint(p)
            outLine.strokeWidth = p.strokeWidth + Outline.create(ctx, theme.outlineName).dim
            outLine.color = ctx.getColor(R.color.black)
            return outLine
        }
        fun calcDistFromBorder(can: Canvas, stroke: Stroke): Float {
            val assertedOutlineDimension = 8 //TODO use exact?
            val totalSetoff = 4F * (stroke.dim + assertedOutlineDimension)
            return can.height / (can.height + totalSetoff)
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
