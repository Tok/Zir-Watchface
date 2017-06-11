package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Triangles {
    fun draw(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
             can: Canvas, data: DrawUtil.ActiveFrameData) {
        if (theme.hasOutline) {
            val shapeOutline = Palette.createPaint(ctx, PaintType.SHAPE_OUTLINE, theme, stroke)
            prepareAndDraw(ctx, pal, stroke, theme, can, data, shapeOutline)
        }
        prepareAndDraw(ctx, pal, stroke, theme, can, data)
    }

    private fun prepareAndDraw(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                               can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        if (theme.triangles.active) {
            if (pal.isElastic) {
                drawElastic(ctx, pal, stroke, theme, can, data, paint)
            } else {
                val p = paint ?: Palette.createPaint(ctx, PaintType.SHAPE, theme, stroke, pal)
                drawTriangle(p, can, data.hour, data.minute, data.second)
            }
        }
    }

    private fun drawElastic(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                            can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        val hourMinutePaint = paint ?: Palette.createPaint(ctx, PaintType.SHAPE, theme, stroke, pal)
        val hourSecondPaint = paint ?: Palette.createPaint(ctx, PaintType.SHAPE, theme, stroke, pal)
        val minuteSecondPaint = paint ?: Palette.createPaint(ctx, PaintType.SHAPE, theme, stroke, pal)
        val w: Float = hourMinutePaint.strokeWidth
        with(data) {
            hourMinutePaint.strokeWidth =  w * unit / DrawUtil.calcDistance(hour.p, minute.p)
            hourSecondPaint.strokeWidth = w * unit / DrawUtil.calcDistance(hour.p, second.p)
            minuteSecondPaint.strokeWidth = w * unit / DrawUtil.calcDistance(minute.p, second.p)
            can.drawLine(second.p.x, second.p.y, minute.p.x, minute.p.y, minuteSecondPaint)
            can.drawLine(second.p.x, second.p.y, hour.p.x, hour.p.y, hourSecondPaint)
            can.drawLine(minute.p.x, minute.p.y, hour.p.x, hour.p.y, hourMinutePaint)
        }
    }

    private fun drawTriangle(paint: Paint, can: Canvas, hr: DrawUtil.HandData, min: DrawUtil.HandData, sec: DrawUtil.HandData) {
        can.drawLine(sec.p.x, sec.p.y, min.p.x, min.p.y, paint)
        can.drawLine(sec.p.x, sec.p.y, hr.p.x, hr.p.y, paint)
        can.drawLine(min.p.x, min.p.y, hr.p.x, hr.p.y, paint)
    }
}