package zir.teq.wearable.watchface.model.frame.data

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.setting.wave.WaveAmbientResolution
import java.util.*

class AmbientWaveData(cal: Calendar, bounds: Rect, can: Canvas) :
        ActiveWaveData(cal, bounds, can, WaveAmbientResolution.load().value.toInt())
