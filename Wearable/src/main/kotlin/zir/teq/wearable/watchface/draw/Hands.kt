package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Hands {
    fun drawActive(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                   can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(ctx, PaintType.HAND, theme, stroke, pal)
        if (theme.hasOutline) {
            makeActiveFirst(can, data, theme, pal, DrawUtil.makeOutline(ctx, p, theme))
        }
        makeActiveFirst(can, data, theme, pal, p)
    }

    fun drawAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                    can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(ctx, PaintType.SHAPE_AMB, theme, stroke, pal) //not HAND_AMB...
        if (theme.hasOutline) {
            makeAmbient(can, data, theme, DrawUtil.makeOutline(ctx, p, theme))
        }
        makeAmbient(can, data, theme, p)
    }

    private fun makeActiveFirst(can: Canvas, data: DrawUtil.ActiveFrameData,
                                theme: Theme, pal: Palette, p: Paint) {
        if (theme.hands.active) {
            if (pal.isElastic) {
                drawHandsElastic(can, data, p)
            } else {
                drawHands(data.getRef(can), p, data.hour, data.minute, data.second)
            }
        }
    }

    private fun drawHandsElastic(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
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

    private fun makeAmbient(can: Canvas, data: DrawUtil.AmbientFrameData, theme: Theme, p: Paint) {
        if (theme.hands.ambient) {
            drawHand(data.getRef(can), p, data.hour)
            drawHand(data.getRef(can), p, data.minute)
            can.drawLine(data.min.x, data.min.y, data.hr.x, data.hr.y, p)
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