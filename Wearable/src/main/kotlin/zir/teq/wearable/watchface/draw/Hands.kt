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
        if (theme.hasOutline) {
            val handOutline = Palette.createPaint(ctx, PaintType.HAND_OUTLINE, theme, stroke)
            makeActiveFirst(ctx, pal, stroke, theme, can, data, handOutline)
        }
        makeActiveFirst(ctx, pal, stroke, theme, can, data)
    }

    fun drawAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                    can: Canvas, data: DrawUtil.AmbientFrameData) {
        if (theme.hasOutline) {
            val shapeOutline = Palette.createPaint(ctx, PaintType.SHAPE_OUTLINE, theme, stroke)
            makeAmbient(ctx, pal, stroke, theme, can, data, shapeOutline)
        }
        makeAmbient(ctx, pal, stroke, theme, can, data)
    }

    private fun makeActiveFirst(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                                can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        if (theme.hands.active) {
            if (pal.isElastic) {
                makeActiveFirstElastic(ctx, pal, stroke, theme, can, data, paint)
            } else {
                val p = paint ?: Palette.createPaint(ctx, PaintType.HAND, theme, stroke, pal)
                drawHands(data.getRef(can), p, data.hour, data.minute, data.second)
            }
        }
    }

    private fun makeActiveFirstElastic(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                                       can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        val hourPaint = paint ?: Palette.createPaint(ctx, PaintType.HAND, theme, stroke, pal)
        val minutePaint = paint ?: Palette.createPaint(ctx, PaintType.HAND, theme, stroke, pal)
        val secondPaint = paint ?: Palette.createPaint(ctx, PaintType.HAND, theme, stroke, pal)
        val w: Float = hourPaint.strokeWidth
        with(data) {
            hourPaint.strokeWidth = w * unit / DrawUtil.calcDistance(hour.p, center)
            minutePaint.strokeWidth = w * unit / DrawUtil.calcDistance(minute.p, center)
            secondPaint.strokeWidth = w * unit / DrawUtil.calcDistance(second.p, center)
            drawHand(getRef(can), secondPaint, second)
            drawHand(getRef(can), hourPaint, hour)
            drawHand(getRef(can), minutePaint, minute)
        }
    }

    private fun makeAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                            can: Canvas, data: DrawUtil.AmbientFrameData, paint: Paint? = null) {
        if (theme.hands.ambient) {
            val p = paint ?: Palette.createPaint(ctx, PaintType.SHAPE_AMB, theme, stroke, pal)
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