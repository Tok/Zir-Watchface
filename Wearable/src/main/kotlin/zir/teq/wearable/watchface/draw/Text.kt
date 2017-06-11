package zir.teq.wearable.watchface.draw

import android.content.Context
import android.graphics.Canvas
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Palette
import java.util.*

object Text {
    fun draw(ctx: Context, can: Canvas, pal: Palette, calendar: Calendar) {
        val hh = calendar.get(Calendar.HOUR_OF_DAY)
        val mm = calendar.get(Calendar.MINUTE)
        val text = if (pal.isAmbient) { // Draw HH:MM in ambient mode or HH:MM:SS in interactive mode.
            String.format("%02d:%02d", hh, mm)
        } else {
            val ss = calendar.get(Calendar.SECOND)
            String.format("%02d:%02d:%02d", hh, mm, ss)
        }
        val paint = Palette.createTextPaint(ctx, pal)
        val yOffset = ctx.resources.getDimension(R.dimen.text_y_offset)
        val xOffset = ctx.resources.getDimension(R.dimen.text_x_offset)
        can.drawText(text, xOffset, yOffset, paint)
    }
}