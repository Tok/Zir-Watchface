package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.data.HandData
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI
import zir.teq.wearable.watchface.util.CalcUtil.PI
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
    val hour = HandData(hr, hrRot, hr)
    val minute = HandData(min, minRot, min)
    val second = HandData(sec, secRot, sec)
}
