package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Hands {
    fun drawActive(can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(PaintType.HAND)
        if (ConfigData.hasOutline()) {
            makeActive(can, data, DrawUtil.makeOutline(p))
        }
        makeActive(can, data, p)
    }

    fun drawAmbient(can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(PaintType.SHAPE_AMB) //not HAND_AMB...
        if (ConfigData.hasOutline()) {
            makeAmbient(can, data, DrawUtil.makeOutline(p))
        }
        makeAmbient(can, data, p)
    }

    private fun makeActive(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        if (ConfigData.theme.hands.active) {
            with(data) {
                val ref = data.getRef(can)
                val hP = DrawUtil.applyElasticity(p, ref.unit / DrawUtil.calcDistance(hour.p, ref.center))
                val mP = DrawUtil.applyElasticity(p, ref.unit / DrawUtil.calcDistance(minute.p, ref.center))
                val sP = DrawUtil.applyElasticity(p, ref.unit / DrawUtil.calcDistance(second.p, ref.center))
                drawHand(ref, sP, second)
                drawHand(ref, hP, hour)
                drawHand(ref, mP, minute)
            }
        }
    }

    private fun makeAmbient(can: Canvas, data: DrawUtil.AmbientFrameData, p: Paint) {
        if (ConfigData.theme.hands.ambient) {
            with(data) {
                val hP = DrawUtil.applyElasticity(p,  unit / DrawUtil.calcDistance(hour.p, center))
                val mP = DrawUtil.applyElasticity(p,  unit / DrawUtil.calcDistance(minute.p, center))
                val lineP = DrawUtil.applyElasticity(p,  unit / DrawUtil.calcDistance(minute.p, hour.p))
                drawHand(data.getRef(can), hP, data.hour)
                drawHand(data.getRef(can), mP, data.minute)
                can.drawLine(data.min.x, data.min.y, data.hr.x, data.hr.y, lineP)
            }
        }
    }

    private fun drawHand(ref: DrawUtil.Ref, paint: Paint, hand: DrawUtil.HandData) {
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }
}