package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.data.ActiveData
import zir.teq.wearable.watchface.model.data.frame.data.AmbientData
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.settings.style.Stack
import zir.teq.wearable.watchface.model.data.types.Component.Companion.CIRCLE
import zir.teq.wearable.watchface.model.data.types.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.data.types.State.ACTIVE
import zir.teq.wearable.watchface.util.DrawUtil

object Circles {
    val ELASTICITY = 1F / DrawUtil.PHI
    fun drawActive(can: Canvas, data: ActiveData, p: Paint) {
        if (ConfigData.isOn(CIRCLE to ACTIVE)) {
            if (ConfigData.isOn(SHAPE to ACTIVE)) {
                p.style = Paint.Style.FILL_AND_STROKE
                p.alpha = (p.alpha * Shapes.ALPHA_FACTOR).toInt()
                //TODO gradients?
            }
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            when (ConfigData.style().stack) {
                Stack.GROUPED, Stack.LEGACY -> {
                    if (ConfigData.style().outline.isOn) {
                        makeSlow(can, data, p, true)
                        makeFast(can, data, p, true)
                    }
                    makeSlow(can, data, p)
                    makeFast(can, data, p)
                }
                Stack.FAST_TOP -> {
                    if (ConfigData.style().outline.isOn) {
                        makeSlow(can, data, p, true)
                    }
                    makeSlow(can, data, p)
                    if (ConfigData.style().outline.isOn) {
                        makeFast(can, data, p, true)
                    }
                    makeFast(can, data, p)
                }
                Stack.SLOW_TOP -> {
                    if (ConfigData.style().outline.isOn) {
                        makeFast(can, data, p, true)
                    }
                    makeFast(can, data, p)
                    if (ConfigData.style().outline.isOn) {
                        makeSlow(can, data, p, true)
                    }
                    makeSlow(can, data, p)
                }
                else -> throw IllegalArgumentException("Stack unknown: " + ConfigData.style().stack)
            }
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientData) {
        //No stacking required...
        if (ConfigData.isOn(CIRCLE to ACTIVE)) {
            val p = Palette.createPaint(PaintType.CIRCLE_AMB)
            if (ConfigData.isOn(SHAPE to ACTIVE)) {
                p.style = Paint.Style.FILL_AND_STROKE
                p.alpha = (p.alpha * Shapes.ALPHA_FACTOR).toInt()
                //TODO gradients?
            }
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (ConfigData.style().outline.isOn) {
                makeAmbient(can, data, p, true)
            }
            makeAmbient(can, data, p)
        }
    }

    private fun makeFast(can: Canvas, data: ActiveData, p: Paint, isOutline: Boolean = false) {
        with(data) {
            drawLine(getRef(can), p, hrRot, secRot, hr, sec, isOutline)
            drawLine(getRef(can), p, minRot, secRot, min, sec, isOutline)
        }
    }

    private fun makeSlow(can: Canvas, data: ActiveData, p: Paint, isOutline: Boolean = false) {
        with(data) {
            drawLine(getRef(can), p, hrRot, minRot, hr, min, isOutline)
        }
    }

    private fun makeAmbient(can: Canvas, data: AmbientData, p: Paint, isOutline: Boolean = false) {
        with(data) {
            val factor = ELASTICITY * unit / data.ccRadius
            val stretched = DrawUtil.applyElasticity(p, factor, isOutline, true)
            can.drawCircle(data.ccCenter.x, data.ccCenter.y, data.ccRadius, stretched)
        }
    }

    private fun drawLine(ref: DrawUtil.Ref, paint: Paint, hrRot: Float, secRot: Float, hr: PointF, sec: PointF, isOutline: Boolean) {
        val ccCenter = DrawUtil.calcCircumcenter(ref.center, hr, sec)
        val ccRadius = DrawUtil.calcDistance(ref.center, ccCenter)
        val factor = ELASTICITY * ref.unit / ccRadius
        val stretched = DrawUtil.applyElasticity(paint, factor, isOutline, true)
        if (!areHandsAlmostCollinear(hrRot, secRot)) {
            ref.can.drawCircle(ccCenter.x, ccCenter.y, ccRadius, stretched)
        } else {
            drawFullLine(ref.can, stretched, hrRot, secRot, ref.unit)
        }
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
