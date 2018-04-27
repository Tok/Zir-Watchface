package zir.teq.wearable.watchface.model.data.frame.data

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.data.frame.data.ActiveWaveData
import zir.teq.wearable.watchface.model.data.settings.wave.Resolution
import java.util.*

class AmbientWaveData(cal: Calendar, bounds: Rect, can: Canvas) : ActiveWaveData(cal, bounds, can, Resolution.AMBIENT.value)
