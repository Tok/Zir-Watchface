package zir.teq.wearable.watchface.model.data.frame.data

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import java.util.*

class AmbientWaveData(cal: Calendar, bounds: Rect, can: Canvas) :
        ActiveWaveData(cal, bounds, can, ConfigData.waveAmbientResolution().value)
