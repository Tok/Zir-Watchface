package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.types.Component
import zir.teq.wearable.watchface.model.types.State
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI
import zir.teq.wearable.watchface.util.CalcUtil.PI
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*

open class ActiveFrame(cal: Calendar, bounds: Rect, can: Canvas) : Frame(cal, bounds) {
    val ms = cal.get(Calendar.MILLISECOND)
    val secRot = (ss + ms / 1000F) / 30F * PI
    val secLength = unit * CalcUtil.calcDistFromBorder(can, StyleStroke.load() as StyleStroke)
    val minLength = secLength / PHI
    val hrLength = minLength / PHI
    val hr = CalcUtil.calcPosition(hrRot, hrLength, unit)
    val min = CalcUtil.calcPosition(minRot, minLength, unit)
    val sec = CalcUtil.calcPosition(secRot, secLength, unit)
    val circlesActive = ConfigData.isOn(Component.CIRCLE to State.ACTIVE)
    val hrExtended = if (circlesActive) CalcUtil.calcPosition(hrRot, secLength, unit) else hr //why secLength?
    val minExtended = if (circlesActive) CalcUtil.calcPosition(minRot, secLength, unit) else min //why secLength?
    val secExtended = if (circlesActive) CalcUtil.calcPosition(secRot, secLength, unit) else sec
    val hour = DrawUtil.HandData(hr, hrRot, hrExtended)
    val minute = DrawUtil.HandData(min, minRot, minExtended)
    val second = DrawUtil.HandData(sec, secRot, secExtended)
}
