package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrameData
import zir.teq.wearable.watchface.model.data.frame.AmbientFrameData
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.model.data.settings.Stack
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil
import zir.watchface.DrawUtil.HandData
import zir.watchface.DrawUtil.Ref

object Hands {
    data class AmbientFactors(val h: Float, val m: Float, val line: Float)
    data class ActiveFactors(val h: Float, val m: Float, val s: Float)

    val ELASTICITY = 1F / DrawUtil.PHI
    fun drawActive(can: Canvas, data: ActiveFrameData) {
        if (ConfigData.theme.hands.active) {
            with(data) {
                val ref = data.getRef(can)
                val p = Palette.createPaint(PaintType.HAND)
                val hFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, ref.center)
                val mFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, ref.center)
                val sFactor = ELASTICITY * unit / DrawUtil.calcDistance(second.p, ref.center)
                val factors = ActiveFactors(hFactor, mFactor, sFactor)
                when (ConfigData.stack) {
                    Stack.GROUPED, Stack.LEGACY -> stackLegacyActive(ref, data, p, factors)
                    Stack.FAST_TOP -> stackFastTopActive(ref, data, p, factors)
                    Stack.SLOW_TOP -> stackSlowTopActive(ref, data, p, factors)
                    else -> throw IllegalArgumentException("Stack unknown: " + ConfigData.stack)
                }
            }
        }
    }

    fun drawAmbient(can: Canvas, data: AmbientFrameData) {
        if (ConfigData.theme.hands.active) {
            with(data) {
                val ref = data.getRef(can)
                val p = Palette.createPaint(PaintType.SHAPE_AMB) //FIXME why not HAND_AMB...
                val hFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, center)
                val mFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, center)
                val lineFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, hour.p)
                val factors = AmbientFactors(hFactor, mFactor, lineFactor)
                when (ConfigData.stack) {
                    Stack.GROUPED, Stack.LEGACY -> stackLegacyAmbient(ref, data, p, factors)
                    Stack.FAST_TOP -> stackFastTopAmbient(ref, data, p, factors)
                    Stack.SLOW_TOP -> stackSlowTopAmbient(ref, data, p, factors)
                    else -> throw IllegalArgumentException("Stack unknown: " + ConfigData.stack)
                }
            }
        }
    }

    private fun stackLegacyActive(ref: Ref, data: ActiveFrameData, p: Paint, factors: Hands.ActiveFactors) {
        if (ConfigData.theme.hands.ambient) {
            with(data) {
                drawHandOutline(ref, p, second, factors.s)
                drawHandOutline(ref, p, hour, factors.h)
                drawHandOutline(ref, p, minute, factors.m)
                drawHand(ref, p, second, factors.s)
                drawHand(ref, p, hour, factors.h)
                drawHand(ref, p, minute, factors.m)
            }
        }
    }

    private fun stackLegacyAmbient(ref: Ref, data: AmbientFrameData, p: Paint, factors: Hands.AmbientFactors) {
        if (ConfigData.theme.hands.ambient) {
            with(data) {
                drawHandOutline(ref, p, hour, factors.h)
                drawHandOutline(ref, p, minute, factors.m)
                drawLineOutline(ref, p, hr, min, factors.line)
                drawHand(ref, p, hour, factors.h)
                drawHand(ref, p, minute, factors.m)
                drawLine(ref, p, hr, min, factors.line)
            }
        }
    }

    private fun stackFastTopActive(ref: Ref, data: ActiveFrameData, p: Paint, factors: Hands.ActiveFactors) {
        with(data) {
            drawHandAndOutline(ref, p, hour, factors.h)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, second, factors.s)
        }
    }

    private fun stackFastTopAmbient(ref: Ref, data: AmbientFrameData, p: Paint, factors: Hands.AmbientFactors) {
        with(data) {
            drawHandAndOutline(ref, p, hour, factors.h)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawLineAndOutline(ref, p, hr, min, factors.line)
        }
    }

    private fun stackSlowTopActive(ref: Ref, data: ActiveFrameData, p: Paint, factors: Hands.ActiveFactors) {
        with(data) {
            drawHandAndOutline(ref, p, second, factors.s)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, hour, factors.h)
        }
    }

    private fun stackSlowTopAmbient(ref: Ref, data: AmbientFrameData, p: Paint, factors: Hands.AmbientFactors) {
        with(data) {
            drawLineAndOutline(ref, p, hr, min, factors.line)
            drawHandAndOutline(ref, p, minute, factors.m)
            drawHandAndOutline(ref, p, hour, factors.h)
        }
    }

    private fun drawLineAndOutline(ref: Ref, p: Paint, from: PointF, to: PointF, factor: Float) {
        if (ConfigData.hasOutline()) {
            drawLineOutline(ref, p, from, to, factor)
        }
        drawLine(ref, p, from, to, factor)
    }

    private fun drawHandAndOutline(ref: Ref, p: Paint, hand: HandData, factor: Float) {
        if (ConfigData.hasOutline()) {
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