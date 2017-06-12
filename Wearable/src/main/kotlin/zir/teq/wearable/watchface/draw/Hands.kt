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
            if (ConfigData.isElastic) {
                drawElastic(can, data, p)
            } else {
                drawHands(data.getRef(can), p, data.hour, data.minute, data.second)
            }
        }
    }

    private fun makeAmbient(can: Canvas, data: DrawUtil.AmbientFrameData, p: Paint) {
        if (ConfigData.theme.hands.ambient) {
            if (ConfigData.isElastic) {
                drawAmbientElastic(can, data, p)
            } else {
                drawHand(data.getRef(can), p, data.hour)
                drawHand(data.getRef(can), p, data.minute)
                can.drawLine(data.min.x, data.min.y, data.hr.x, data.hr.y, p)
            }
        }
    }

    private fun drawElastic(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        val hourPaint = Paint(p)
        val minutePaint = Paint(p)
        val secondPaint = Paint(p)
        val w: Float = p.strokeWidth
        with(data) {
            hourPaint.strokeWidth = w * unit / DrawUtil.calcDistance(hour.p, center)
            minutePaint.strokeWidth = w * unit / DrawUtil.calcDistance(minute.p, center)
            secondPaint.strokeWidth = w * unit / DrawUtil.calcDistance(second.p, center)
            drawHand(getRef(can), secondPaint, second)
            drawHand(getRef(can), hourPaint, hour)
            drawHand(getRef(can), minutePaint, minute)
        }
    }

    private fun drawAmbientElastic(can: Canvas, data: DrawUtil.AmbientFrameData, p: Paint) {
        val hourPaint = Paint(p)
        val minutePaint = Paint(p)
        val linePaint = Paint(p)
        val w: Float = p.strokeWidth
        with(data) {
            hourPaint.strokeWidth = w * unit / DrawUtil.calcDistance(hour.p, center)
            minutePaint.strokeWidth = w * unit / DrawUtil.calcDistance(minute.p, center)
            linePaint.strokeWidth = w * unit / DrawUtil.calcDistance(minute.p, hour.p)
            drawHand(getRef(can), hourPaint, data.hour)
            drawHand(getRef(can), minutePaint, data.minute)
            can.drawLine(min.x, min.y, hr.x, hr.y, linePaint)
        }
    }

    private fun drawHands(ref: DrawUtil.Ref, paint: Paint, hour: DrawUtil.HandData, minute: DrawUtil.HandData, second: DrawUtil.HandData) {
        drawHand(ref, paint, second)
        drawHand(ref, paint, hour)
        drawHand(ref, paint, minute)
    }

    private fun drawHand(ref: DrawUtil.Ref, paint: Paint, hand: DrawUtil.HandData) {
        ref.can.drawLine(ref.center.x, ref.center.y, hand.p.x, hand.p.y, paint)
    }
}