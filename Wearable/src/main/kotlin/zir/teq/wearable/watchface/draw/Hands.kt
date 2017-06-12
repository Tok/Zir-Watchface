package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Hands {
    val ELASTICITY = 1F / DrawUtil.PHI
    fun drawActive(can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(PaintType.HAND)
        if (ConfigData.hasOutline()) {
            makeActive(can, data, p, true)
        }
        makeActive(can, data, p)
    }

    fun drawAmbient(can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(PaintType.SHAPE_AMB) //not HAND_AMB...
        if (ConfigData.hasOutline()) {
            makeAmbient(can, data, p, true)
        }
        makeAmbient(can, data, p)
    }

    private fun makeActive(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint, isOutline: Boolean = false) {
        if (ConfigData.theme.hands.active) {
            with(data) {
                val ref = data.getRef(can)
                val hFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, ref.center)
                val mFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, ref.center)
                val sFactor = ELASTICITY * unit / DrawUtil.calcDistance(second.p, ref.center)
                val hP = DrawUtil.applyElasticity(p, hFactor, isOutline)
                val mP = DrawUtil.applyElasticity(p, mFactor, isOutline)
                val sP = DrawUtil.applyElasticity(p, sFactor, isOutline)
                drawHand(ref, sP, second)
                drawHand(ref, hP, hour)
                drawHand(ref, mP, minute)
            }
        }
    }

    private fun makeAmbient(can: Canvas, data: DrawUtil.AmbientFrameData, p: Paint, isOutline: Boolean = false) {
        if (ConfigData.theme.hands.ambient) {
            with(data) {
                val hFactor = ELASTICITY * unit / DrawUtil.calcDistance(hour.p, center)
                val mFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, center)
                val lineFactor = ELASTICITY * unit / DrawUtil.calcDistance(minute.p, hour.p)
                val hP = DrawUtil.applyElasticity(p, hFactor, isOutline)
                val mP = DrawUtil.applyElasticity(p, mFactor, isOutline)
                val lineP = DrawUtil.applyElasticity(p, lineFactor, isOutline)
                drawHand(getRef(can), hP, hour)
                drawHand(getRef(can), mP, minute)
                can.drawLine(min.x, min.y, hr.x, hr.y, lineP)
            }
        }
    }

    private fun drawHand(ref: DrawUtil.Ref, paint: Paint, hand: DrawUtil.HandData) {
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }
}