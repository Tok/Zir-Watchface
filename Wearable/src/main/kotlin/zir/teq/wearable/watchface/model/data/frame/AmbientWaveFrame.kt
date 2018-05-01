package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.setting.wave.WaveAmbientResolution
import java.util.*

class AmbientWaveFrame(cal: Calendar, bounds: Rect, can: Canvas) :
        ActiveWaveFrame(cal, bounds, can, WaveAmbientResolution.load().value.toInt())
