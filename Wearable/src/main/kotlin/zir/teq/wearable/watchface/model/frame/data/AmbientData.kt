package zir.teq.wearable.watchface.model.frame.data

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.frame.Frame
import zir.teq.wearable.watchface.model.setting.StyleStroke
import zir.teq.wearable.watchface.model.setting.WaveVelocity
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*

open class AmbientData(cal: Calendar, bounds: Rect, can: Canvas) : Frame(cal, bounds) {
    val minLength = unit * DrawUtil.calcDistFromBorder(can, StyleStroke.load()) / DrawUtil.PHI
    val hrLength = minLength / DrawUtil.PHI

    val hr = DrawUtil.calcPosition(hrRot, hrLength, unit)
    val min = DrawUtil.calcPosition(minRot, minLength, unit)
    val hour = DrawUtil.HandData(hr, hrRot, center)

    val minute = DrawUtil.HandData(min, minRot, center)
    val ccCenter = DrawUtil.calcCircumcenter(center, hr, min)
    val ccRadius = DrawUtil.calcDistance(min, ccCenter)
}