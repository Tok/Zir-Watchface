package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import zir.watchface.DrawUtil
import java.util.*

open class FrameData(cal: Calendar, bounds: Rect) {
    val hh = cal.get(Calendar.HOUR_OF_DAY)
    val mm = cal.get(Calendar.MINUTE)
    val ss = cal.get(Calendar.SECOND)
    val minRot: Float = (mm + (ss / 60F)) / 30F * DrawUtil.PI
    val hrRot: Float = (hh + (mm / 60F)) / 6F * DrawUtil.PI
    val unit = bounds.width() / 2F
    val center = PointF(unit, unit)
    fun getRef(can: Canvas): DrawUtil.Ref = DrawUtil.Ref(can, unit, center)
}
