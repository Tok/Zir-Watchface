package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.data.settings.wave.Resolution
import java.util.*

class AmbientWaveFrameData(cal: Calendar, bounds: Rect, can: Canvas) : ActiveWaveFrameData(cal, bounds, can, Resolution.AMBIENT.value)
