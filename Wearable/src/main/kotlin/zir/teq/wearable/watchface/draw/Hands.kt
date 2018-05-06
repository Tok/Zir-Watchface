package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.HandData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.model.setting.component.Component.Companion.HAND
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.DrawUtil
import zir.teq.wearable.watchface.util.DrawUtil.Ref

object Hands {
    data class AmbientFactors(val h: Float, val m: Float, val line: Float)
    data class ActiveFactors(val h: Float, val m: Float, val s: Float)

    val ELASTICITY = 1F / CalcUtil.PHI
    fun drawActive(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(HAND to ACTIVE)) {
            val stack = StyleStack.load()
            with(frame) {
                can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
                val ref = frame.getRef(can)
                val hFactor = ELASTICITY * unit / CalcUtil.calcDistance(hour.p, ref.center)
                val mFactor = ELASTICITY * unit / CalcUtil.calcDistance(minute.p, ref.center)
                val sFactor = ELASTICITY * unit / CalcUtil.calcDistance(second.p, ref.center)
                val factors = ActiveFactors(hFactor, mFactor, sFactor)
                when (stack) {
                    StyleStack.GROUPED, StyleStack.LEGACY -> stackLegacyActive(ref, frame, p, factors)
                    StyleStack.FAST_TOP -> stackFastTopActive(ref, frame, p, factors)
                    StyleStack.SLOW_TOP -> stackSlowTopActive(ref, frame, p, factors)
                }
            }
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrame) {
        if (ConfigData.isOn(HAND to AMBIENT)) {
            val stack = StyleStack.load()
            with(data) {
                val ref = data.getRef(can)
                val p = Palette.createPaint(PaintType.HAND_AMB)
                can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
                val hFactor = ELASTICITY * unit / CalcUtil.calcDistance(hour.p, center)
                val mFactor = ELASTICITY * unit / CalcUtil.calcDistance(minute.p, center)
                val lineFactor = ELASTICITY * unit / CalcUtil.calcDistance(minute.p, hour.p)
                val factors = AmbientFactors(hFactor, mFactor, lineFactor)
                when (stack) {
                    StyleStack.GROUPED, StyleStack.LEGACY -> stackLegacyAmbient(ref, data, p, factors)
                    StyleStack.FAST_TOP -> stackFastTopAmbient(ref, data, p, factors)
                    StyleStack.SLOW_TOP -> stackSlowTopAmbient(ref, data, p, factors)
                }
            }
        }
    }

    private fun stackLegacyActive(ref: Ref, frame: ActiveFrame, p: Paint, factors: Hands.ActiveFactors) {
        with(frame) {
            drawHandOutline(ref, p, second, factors.s)
            drawHandOutline(ref, p, hour, factors.h)
            drawHandOutline(ref, p, minute, factors.m)
            drawHand(ref, p, second, factors.s)
            drawHand(ref, p, hour, factors.h)
            drawHand(ref, p, minute, factors.m)
        }
    }

    private fun stackLegacyAmbient(ref: Ref, data: AmbientFrame, p: Paint, factors: Hands.AmbientFactors) {
        with(data) {
            drawHandOutline(ref, p, hour, factors.h)
            drawHandOutline(ref, p, minute, factors.m)
            drawLineOutline(ref, p, hr, min, factors.line)
            drawHand(ref, p, hour, factors.h)
            drawHand(ref, p, minute, factors.m)
            drawLine(ref, p, hr, min, factors.line)
        }
    }

    private fun stackFastTopActive(ref: Ref, frame: ActiveFrame, p: Paint, factors: Hands.ActiveFactors) {
        with(frame) {
            drawHandAndOutline(ref, p, hour, factors.h)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, second, factors.s)
        }
    }

    private fun stackFastTopAmbient(ref: Ref, data: AmbientFrame, p: Paint, factors: Hands.AmbientFactors) {
        with(data) {
            drawHandAndOutline(ref, p, hour, factors.h)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawLineAndOutline(ref, p, hr, min, factors.line)
        }
    }

    private fun stackSlowTopActive(ref: Ref, frame: ActiveFrame, p: Paint, factors: Hands.ActiveFactors) {
        with(frame) {
            drawHandAndOutline(ref, p, second, factors.s)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, hour, factors.h)
        }
    }

    private fun stackSlowTopAmbient(ref: Ref, data: AmbientFrame, p: Paint, factors: Hands.AmbientFactors) {
        with(data) {
            drawLineAndOutline(ref, p, hr, min, factors.line)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, hour, factors.h)
        }
    }

    private fun drawLineAndOutline(ref: Ref, p: Paint, from: PointF, to: PointF, factor: Float) {
        if ((StyleOutline.load() as StyleOutline).isOn) {
            drawLineOutline(ref, p, from, to, factor)
        }
        drawLine(ref, p, from, to, factor)
    }

    private fun drawHandAndOutline(ref: Ref, p: Paint, hand: HandData, factor: Float) {
        if ((StyleOutline.load() as StyleOutline).isOn) {
            drawHandOutline(ref, p, hand, factor)
        }
        drawHand(ref, p, hand, factor)
    }

    private fun drawLineOutline(ref: Ref, p: Paint, from: PointF, to: PointF, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, true)
        ref.can.drawLine(from.x, from.y, to.x, to.y, paint)
    }

    private fun drawLine(ref: Ref, p: Paint, from: PointF, to: PointF, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, false)
        ref.can.drawLine(from.x, from.y, to.x, to.y, paint)
    }

    private fun drawHandOutline(ref: Ref, p: Paint, hand: HandData, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, true)
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }

    private fun drawHand(ref: Ref, p: Paint, hand: HandData, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, false)
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }
}
