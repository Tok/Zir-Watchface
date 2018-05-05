package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*


open class AmbientFrame(cal: Calendar, bounds: Rect, can: Canvas) : Frame(cal, bounds) {
    val minLength = unit * CalcUtil.calcDistFromBorder(can, StyleStroke.load() as StyleStroke) / PHI
    val hrLength = minLength / PHI

    val hr = CalcUtil.calcPosition(hrRot, hrLength, unit)
    val min = CalcUtil.calcPosition(minRot, minLength, unit)
    val hour = DrawUtil.HandData(hr, hrRot, center)

    val minute = DrawUtil.HandData(min, minRot, center)
    val ccCenter = CalcUtil.calcCircumcenter(center, hr, min)
    val ccRadius = CalcUtil.calcDistance(min, ccCenter)
}
