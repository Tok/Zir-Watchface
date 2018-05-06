package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.CIRCLE
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.SHAPE
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI
import zir.teq.wearable.watchface.util.CalcUtil.PI
import zir.teq.wearable.watchface.util.CalcUtil.TAU
import zir.teq.wearable.watchface.util.DrawUtil

object Circles {
    val ELASTICITY = 1F / PHI
    fun drawActive(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(CIRCLE to ACTIVE)) {
            if (ConfigData.isOn(SHAPE to ACTIVE)) {
                p.style = Paint.Style.FILL_AND_STROKE
                p.alpha = (p.alpha * Shapes.ALPHA_FACTOR).toInt()
                //TODO gradients?
            }
            val stack: StyleStack = StyleStack.load() as StyleStack
            val outline: StyleOutline = StyleOutline.load() as StyleOutline
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            when (stack) {
                StyleStack.GROUPED, StyleStack.LEGACY -> {
                    if (outline.isOn) {
                        makeSlow(can, frame, p, true)
                        makeFast(can, frame, p, true)
                    }
                    makeSlow(can, frame, p)
                    makeFast(can, frame, p)
                }
                StyleStack.FAST_TOP -> {
                    if (outline.isOn) {
                        makeSlow(can, frame, p, true)
                    }
                    makeSlow(can, frame, p)
                    if (outline.isOn) {
                        makeFast(can, frame, p, true)
                    }
                    makeFast(can, frame, p)
                }
                StyleStack.SLOW_TOP -> {
                    if (outline.isOn) {
                        makeFast(can, frame, p, true)
                    }
                    makeFast(can, frame, p)
                    if (outline.isOn) {
                        makeSlow(can, frame, p, true)
                    }
                    makeSlow(can, frame, p)
                }
            }
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrame) {
        //No stacking required...
        val outline: StyleOutline = StyleOutline.load() as StyleOutline
        if (ConfigData.isOn(CIRCLE to ACTIVE)) {
            val p = Palette.createPaint(PaintType.CIRCLE_AMB)
            if (ConfigData.isOn(SHAPE to ACTIVE)) {
                p.style = Paint.Style.FILL_AND_STROKE
                p.alpha = (p.alpha * Shapes.ALPHA_FACTOR).toInt()
                //TODO gradients?
            }
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            if (outline.isOn) {
                makeAmbient(can, data, p, true)
            }
            makeAmbient(can, data, p)
        }
    }

    private fun makeFast(can: Canvas, frame: ActiveFrame, p: Paint, isOutline: Boolean = false) {
        with(frame) {
            drawLine(getRef(can), p, hrRot, secRot, hr, sec, isOutline)
            drawLine(getRef(can), p, minRot, secRot, min, sec, isOutline)
        }
    }

    private fun makeSlow(can: Canvas, frame: ActiveFrame, p: Paint, isOutline: Boolean = false) {
        with(frame) {
            drawLine(getRef(can), p, hrRot, minRot, hr, min, isOutline)
        }
    }

    private fun makeAmbient(can: Canvas, data: AmbientFrame, p: Paint, isOutline: Boolean = false) {
        with(data) {
            val factor = ELASTICITY * unit / data.ccRadius
            val stretched = DrawUtil.applyElasticity(p, factor, isOutline, true)
            can.drawCircle(data.ccCenter.x, data.ccCenter.y, data.ccRadius, stretched)
        }
    }

    private fun drawLine(ref: DrawUtil.Ref, paint: Paint, hrRot: Float, secRot: Float, hr: PointF, sec: PointF, isOutline: Boolean) {
        val ccCenter = CalcUtil.calcCircumcenter(ref.center, hr, sec)
        val ccRadius = CalcUtil.calcDistance(ref.center, ccCenter)
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
        return absDiff < CalcUtil.HALF_MINUTE_AS_RAD
    }

    private fun calcOuterPosition(rot: Float, unit: Float): PointF {
        val x = unit + (Math.sin(rot.toDouble()) * unit).toFloat()
        val y = unit + (-Math.cos(rot.toDouble()) * unit).toFloat()
        return PointF(x, y)
    }

    private fun fixRot(value: Float, compare: Float): Float {
        return if (arePointsAligned(value, compare)) clipRad(value + PI) else value
    }

    private fun clipRad(rad: Float): Float {
        return if (rad < 0F) rad + TAU else if (rad > TAU) rad - TAU else rad
    }

    private fun arePointsAligned(firstRad: Float, secondRad: Float): Boolean {
        return Math.abs(firstRad - secondRad) < 15F * CalcUtil.ONE_MINUTE_AS_RAD
    }
}
