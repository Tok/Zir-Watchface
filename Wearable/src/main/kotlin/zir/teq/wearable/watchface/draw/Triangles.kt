package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrameData
import zir.teq.wearable.watchface.model.data.settings.Stack
import zir.watchface.DrawUtil
import zir.watchface.DrawUtil.HandData

object Triangles {
    //Triangles only exist in active mode
    data class Factors(val hm: Float, val hs: Float, val ms: Float)
    val ELASTICITY = 1F / DrawUtil.PHI
    fun draw(can: Canvas, data: ActiveFrameData, p: Paint) {
        if (ConfigData.theme.triangles.active) {
            drawTriangle(can, data, p)
        }
    }

    private fun drawTriangle(can: Canvas, data: ActiveFrameData, p: Paint) {
        with(data) {
            can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
            val hmFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, minute.p)
            val hsFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, second.p)
            val msFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, second.p)
            val factors = Factors(hmFactor, hsFactor, msFactor)
            when (ConfigData.stack) {
                Stack.GROUPED, Stack.LEGACY -> stackLegacy(can, data, p, factors)
                Stack.FAST_TOP -> stackFastTop(can, data, p, factors)
                Stack.SLOW_TOP -> stackSlowTop(can, data, p, factors)
                else -> throw IllegalArgumentException("Stack unknown: " + ConfigData.stack)
            }
        }
    }

    private fun stackLegacy(can: Canvas, data: ActiveFrameData, p: Paint, factors: Factors) {
        with(data) {
            if (ConfigData.hasOutline()) {
                drawLineOutline(can, hour, minute, p, factors.hm)
                drawLineOutline(can, hour, second, p, factors.hs)
                drawLineOutline(can, minute, second, p, factors.ms)
            }
            drawLine(can, hour, minute, p, factors.hm)
            drawLine(can, hour, second, p, factors.hs)
            drawLine(can, minute, second, p, factors.ms)
        }
    }

    private fun stackFastTop(can: Canvas, data: ActiveFrameData, p: Paint, factors: Factors) {
        with(data) {
            drawLineAndOutline(can, hour, minute, p, factors.hm)
            drawLineAndOutline(can, hour, second, p, factors.hs)
            drawLineAndOutline(can, minute, second, p, factors.ms)
        }
    }

    private fun stackSlowTop(can: Canvas, data: ActiveFrameData, p: Paint, factors: Factors) {
        with(data) {
            drawLineAndOutline(can, minute, second, p, factors.ms)
            drawLineAndOutline(can, hour, second, p, factors.hs)
            drawLineAndOutline(can, hour, minute, p, factors.hm)
        }
    }

    private fun drawLineAndOutline(can: Canvas, from: HandData, to: HandData, p: Paint, factor: Float) {
        if (ConfigData.hasOutline()) {
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