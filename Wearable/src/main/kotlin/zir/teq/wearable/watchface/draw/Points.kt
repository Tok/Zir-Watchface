package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

object Points {
    fun drawActiveCenter(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                         can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
        if (theme.hasOutline) {
            makeCenter(can, data, theme, DrawUtil.makeOutline(ctx, p, theme)) //center
        }
        makeCenter(can, data, theme, p) //center
    }

    fun drawActive(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                   can: Canvas, data: DrawUtil.ActiveFrameData) {
        val p = Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
        if (theme.hasOutline) {
            val outlineP = DrawUtil.makeOutline(ctx, p, theme)
            makeSeconds(can, data, theme, outlineP)
            makeMinAndHr(can, data, theme, outlineP)
        }
        makeSeconds(can, data, theme, p)
        makeMinAndHr(can, data, theme, p)
    }

    fun drawAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                    can: Canvas, data: DrawUtil.AmbientFrameData) {
        val p = Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
        if (theme.hasOutline) {
            drawAmbientPoints(can, data, theme, DrawUtil.makeOutline(ctx, p, theme))
        }
        drawAmbientPoints(can, data, theme, p)
    }

    private fun drawAmbientPoints(can: Canvas, data: DrawUtil.AmbientFrameData, theme: Theme, p: Paint) {
        if (theme.points.ambient) {
            can.drawPoint(data.center.x, data.center.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            can.drawPoint(data.hr.x, data.hr.y, p)
        }
    }

    private fun makeCenter(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.points.active) {
            can.drawPoint(data.center.x, data.center.y, p)
        }
    }

    private fun makeSeconds(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.points.active) {
            can.drawPoint(data.sec.x, data.sec.y, p)
        }
    }

    private fun makeMinAndHr(can: Canvas, data: DrawUtil.ActiveFrameData, theme: Theme, p: Paint) {
        if (theme.points.active) {
            can.drawPoint(data.hr.x, data.hr.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            /* foreground center point */
            /*
            if (theme.circles.active) {
                can.drawPoint(data.center.x, data.center.y, p)
            }*/
        }
    }
}