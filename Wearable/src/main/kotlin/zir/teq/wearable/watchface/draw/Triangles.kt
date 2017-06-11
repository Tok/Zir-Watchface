package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Triangles {
    fun draw(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
             can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(ctx, PaintType.SHAPE, theme, stroke, pal)
        if (theme.hasOutline) {
            prepareAndDraw(can, data, theme, DrawUtil.makeOutline(ctx, p, theme))
        }
        prepareAndDraw(can, data, theme, p)
    }

    private fun prepareAndDraw(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.triangles.active) {
            if (ConfigData.isElastic) {
                drawElastic(can, data, p)
            } else {
                drawTriangle(can, data, p)
            }
        }
    }

    private fun drawElastic(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        val hourMinutePaint = Paint(p)
        val hourSecondPaint = Paint(p)
        val minuteSecondPaint = Paint(p)
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

    private fun drawTriangle(can: Canvas, data: DrawUtil.ActiveFrameData, p: Paint) {
        with (data) {
            can.drawLine(second.p.x, second.p.y, minute.p.x, minute.p.y, p)
            can.drawLine(second.p.x, second.p.y, hour.p.x, hour.p.y, p)
            can.drawLine(minute.p.x, minute.p.y, hour.p.x, hour.p.y, p)
        }
    }
}