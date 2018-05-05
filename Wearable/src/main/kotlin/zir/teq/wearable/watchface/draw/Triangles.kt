package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.HandData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.model.types.Component
import zir.teq.wearable.watchface.model.types.State
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI
import zir.teq.wearable.watchface.util.DrawUtil

object Triangles {
    //Triangles only exist in active mode
    data class Factors(val hm: Float, val hs: Float, val ms: Float)

    val ELASTICITY = 1F / PHI
    fun draw(can: Canvas, frame: ActiveFrame, p: Paint) {
        if (ConfigData.isOn(Component.TRIANGLE to State.ACTIVE)) {
            drawTriangle(can, frame, p)
        }
    }

    private fun drawTriangle(can: Canvas, frame: ActiveFrame, p: Paint) {
        with(frame) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            val hmFactor = ELASTICITY * unit / CalcUtil.calcDistance(hour.p, minute.p)
            val hsFactor = ELASTICITY * unit / CalcUtil.calcDistance(hour.p, second.p)
            val msFactor = ELASTICITY * unit / CalcUtil.calcDistance(minute.p, second.p)
            val factors = Factors(hmFactor, hsFactor, msFactor)
            when (StyleStack.load()) {
                StyleStack.GROUPED, StyleStack.LEGACY -> stackLegacy(can, frame, p, factors)
                StyleStack.FAST_TOP -> stackFastTop(can, frame, p, factors)
                StyleStack.SLOW_TOP -> stackSlowTop(can, frame, p, factors)
            }
        }
    }

    private fun stackLegacy(can: Canvas, frame: ActiveFrame, p: Paint, factors: Factors) {
        with(frame) {
            if ((StyleOutline.load() as StyleOutline).isOn) {
                drawLineOutline(can, hour, minute, p, factors.hm)
                drawLineOutline(can, hour, second, p, factors.hs)
                drawLineOutline(can, minute, second, p, factors.ms)
            }
            drawLine(can, hour, minute, p, factors.hm)
            drawLine(can, hour, second, p, factors.hs)
            drawLine(can, minute, second, p, factors.ms)
        }
    }

    private fun stackFastTop(can: Canvas, frame: ActiveFrame, p: Paint, factors: Factors) {
        with(frame) {
            drawLineAndOutline(can, hour, minute, p, factors.hm)
            drawLineAndOutline(can, hour, second, p, factors.hs)
            drawLineAndOutline(can, minute, second, p, factors.ms)
        }
    }

    private fun stackSlowTop(can: Canvas, frame: ActiveFrame, p: Paint, factors: Factors) {
        with(frame) {
            drawLineAndOutline(can, minute, second, p, factors.ms)
            drawLineAndOutline(can, hour, second, p, factors.hs)
            drawLineAndOutline(can, hour, minute, p, factors.hm)
        }
    }

    private fun drawLineAndOutline(can: Canvas, from: HandData, to: HandData, p: Paint, factor: Float) {
        if ((StyleOutline.load() as StyleOutline).isOn) {
            drawLineOutline(can, from, to, p, factor)
        }
        drawLine(can, from, to, p, factor)
    }

    private fun drawLineOutline(can: Canvas, from: HandData, to: HandData, p: Paint, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, true)
        can.drawLine(from.p.x, from.p.y, to.p.x, to.p.y, paint)
    }

    private fun drawLine(can: Canvas, from: HandData, to: HandData, p: Paint, factor: Float) {
        val paint = DrawUtil.applyElasticity(p, factor, false)
        can.drawLine(from.p.x, from.p.y, to.p.x, to.p.y, paint)
    }
}
