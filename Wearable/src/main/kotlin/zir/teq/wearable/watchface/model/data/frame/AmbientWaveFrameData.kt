package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import java.util.*

class AmbientWaveFrameData(cal: Calendar, bounds: Rect, can: Canvas) : ActiveWaveFrameData(cal, bounds, can, ConfigData.wave.ambientRes.value)
