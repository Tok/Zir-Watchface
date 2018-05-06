package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import zir.teq.wearable.watchface.util.CalcUtil.PI
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*

open class Frame(cal: Calendar, bounds: Rect) {
    val hh = cal.get(Calendar.HOUR_OF_DAY)
    val mm = cal.get(Calendar.MINUTE)
    val ss = cal.get(Calendar.SECOND)
    val minRot: Float = (mm + (ss / 60F)) / 30F * PI
    val hrRot: Float = (hh + (mm / 60F)) / 6F * PI
    val unit = bounds.width() / 2F
    val center = PointF(unit, unit)
    fun getRef(can: Canvas): DrawUtil.Ref = DrawUtil.Ref(can, unit, center)

    val centerMass = Mass.DEFAULT.value
    val hourMass = Mass.HEAVIER.value
    val minuteMass = Mass.HEAVY.value
    val secondMass = Mass.DEFAULT.value
}
