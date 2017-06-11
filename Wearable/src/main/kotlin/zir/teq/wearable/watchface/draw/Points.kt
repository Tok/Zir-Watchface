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
        if (theme.hasOutline) {
            val pointOutline = Palette.createPaint(ctx, PaintType.POINT_OUTLINE, theme, stroke)
            makeCenter(ctx, pal, stroke, theme, can, data, pointOutline) //center
        }
        makeCenter(ctx, pal, stroke, theme, can, data) //center
    }

    fun drawActive(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                   can: Canvas, data: DrawUtil.ActiveFrameData) {
        if (theme.hasOutline) {
            val pointOutline = Palette.createPaint(ctx, PaintType.POINT_OUTLINE, theme, stroke)
            makeSeconds(ctx, pal, stroke, theme, can, data, pointOutline)
            makeMinAndHr(ctx, pal, stroke, theme, can, data, pointOutline)
        }
        makeSeconds(ctx, pal, stroke, theme, can, data)
        makeMinAndHr(ctx, pal, stroke, theme, can, data)
    }

    fun drawAmbient(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                    can: Canvas, data: DrawUtil.AmbientFrameData) {
        if (theme.hasOutline) {
            val pointOutline = Palette.createPaint(ctx, PaintType.POINT_OUTLINE, theme, stroke)
            drawAmbientPoints(ctx, pal, stroke, theme, can, data, pointOutline)
        }
        drawAmbientPoints(ctx, pal, stroke, theme, can, data)
    }

    private fun drawAmbientPoints(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                                  can: Canvas, data: DrawUtil.AmbientFrameData, paint: Paint? = null) {
        if (theme.points.ambient) {
            val p = paint ?: Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
            can.drawPoint(data.center.x, data.center.y, p)
            can.drawPoint(data.min.x, data.min.y, p)
            can.drawPoint(data.hr.x, data.hr.y, p)
        }
    }

    private fun makeCenter(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                           can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
            can.drawPoint(data.center.x, data.center.y, p)
        }
    }

    private fun makeSeconds(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                            can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
            can.drawPoint(data.sec.x, data.sec.y, p)
        }
    }

    private fun makeMinAndHr(ctx: Context, pal: Palette, stroke: Stroke, theme: Theme,
                             can: Canvas, data: DrawUtil.ActiveFrameData, paint: Paint? = null) {
        if (theme.points.active) {
            val p = paint ?: Palette.createPaint(ctx, PaintType.POINT, theme, stroke, pal)
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