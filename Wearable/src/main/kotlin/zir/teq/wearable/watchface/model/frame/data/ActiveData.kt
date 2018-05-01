package zir.teq.wearable.watchface.model.frame.data

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.frame.Frame
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.model.data.types.State
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*


open class ActiveData(cal: Calendar, bounds: Rect, can: Canvas) : Frame(cal, bounds) {
    val ms = cal.get(Calendar.MILLISECOND)
    val secRot = (ss + ms / 1000F) / 30F * DrawUtil.PI
    val secLength = unit * DrawUtil.calcDistFromBorder(can, ConfigData.style().stroke)
    val minLength = secLength / DrawUtil.PHI
    val hrLength = minLength / DrawUtil.PHI
    val hr = DrawUtil.calcPosition(hrRot, hrLength, unit)
    val min = DrawUtil.calcPosition(minRot, minLength, unit)
    val sec = DrawUtil.calcPosition(secRot, secLength, unit)
    val circlesActive = ConfigData.isOn(Component.CIRCLE to State.ACTIVE)
    val hrExtended = if (circlesActive) DrawUtil.calcPosition(hrRot, secLength, unit) else hr //why secLength?
    val minExtended = if (circlesActive) DrawUtil.calcPosition(minRot, secLength, unit) else min //why secLength?
    val secExtended = if (circlesActive) DrawUtil.calcPosition(secRot, secLength, unit) else sec
    val hour = DrawUtil.HandData(hr, hrRot, hrExtended)
    val minute = DrawUtil.HandData(min, minRot, minExtended)
    val second = DrawUtil.HandData(sec, secRot, secExtended)
}
