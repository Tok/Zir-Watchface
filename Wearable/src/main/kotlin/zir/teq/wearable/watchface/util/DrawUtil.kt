package zir.teq.wearable.watchface.util

import android.graphics.*
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v4.graphics.ColorUtils
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.draw.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.data.ActiveData
import zir.teq.wearable.watchface.model.data.frame.data.ActiveWaveData
import zir.teq.wearable.watchface.model.data.frame.data.AmbientData
import zir.teq.wearable.watchface.model.data.frame.data.AmbientWaveData
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.settings.style.Stack
import zir.teq.wearable.watchface.model.data.settings.style.Stroke
import zir.teq.wearable.watchface.model.data.settings.wave.Layer
import zir.teq.wearable.watchface.model.data.settings.wave.Resolution
import zir.teq.wearable.watchface.model.data.settings.wave.Wave
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.data.types.State
import java.nio.IntBuffer
import java.util.*


/**
 * Created by Zir on 03.01.2016.
 * Recreated 20.05.2017
 */
class DrawUtil {
    data class HandData(val p: PointF, val radians: Float, val maybeExtended: PointF)
    data class Ref(val can: Canvas, val unit: Float, val center: PointF)

    fun draw(can: Canvas, bounds: Rect, calendar: Calendar) {
        val wave = ConfigData.waveSpectrum()
        if (wave.isOff) {
            drawBackground(can)
        }
        if (ConfigData.isAmbient) {
            drawAmbient(can, bounds, calendar, wave)
        } else {
            drawActive(can, bounds, calendar, wave)
        }
    }

    private fun drawActive(can: Canvas, bounds: Rect, calendar: Calendar, wave: Wave) {
        val activeData = ActiveData(calendar, bounds, can)
        if (wave.isOn) {
            val waveData = ActiveWaveData(calendar, bounds, can, Resolution.ACTIVE.value)
            drawActiveWave(can, waveData)
        }
        drawActiveFace(can, activeData)
        if (ConfigData.theme().get(Component.TEXT to State.ACTIVE)) {
            Text.draw(can, calendar)
        }
    }

    private fun drawAmbient(can: Canvas, bounds: Rect, calendar: Calendar, wave: Wave) {
        if (wave.isOn) {
            val waveData = AmbientWaveData(calendar, bounds, can)
            drawAmbientWave(can, waveData)
        }
        val data = AmbientData(calendar, bounds, can)
        drawAmbientFace(can, data)
        if (ConfigData.theme().get(Component.TEXT to State.AMBIENT)) {
            Text.draw(can, calendar)
        }
    }

    fun drawActiveFace(can: Canvas, data: ActiveData) {
        val pal = ConfigData.palette()
        when (ConfigData.style().stack) {
            Stack.GROUPED -> {
                Circles.drawActive(can, data, Palette.createPaint(PaintType.CIRCLE, pal.dark()))
                Shapes.drawActive(can, data, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, data, Palette.createPaint(PaintType.SHAPE, pal.half()))
                Points.drawActive(can, data, Palette.createPaint(PaintType.POINT, Zir.color(R.color.white)))
                Hands.drawActive(can, data, Palette.createPaint(PaintType.HAND, pal.light()))
                Points.drawActiveCenter(can, data, Palette.createPaint(PaintType.POINT, Zir.color(R.color.white)))
            }
            Stack.LEGACY -> {
                Circles.drawActive(can, data, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, data, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Hands.drawActive(can, data, Palette.createPaint(PaintType.HAND))
                Triangles.draw(can, data, Palette.createPaint(PaintType.SHAPE))
                Points.drawActive(can, data, Palette.createPaint(PaintType.POINT))
                Points.drawActiveCenter(can, data, Palette.createPaint(PaintType.POINT))
            }
            else -> {
                Circles.drawActive(can, data, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, data, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, data, Palette.createPaint(PaintType.SHAPE))
                Hands.drawActive(can, data, Palette.createPaint(PaintType.HAND))
                Points.drawActive(can, data, Palette.createPaint(PaintType.POINT))
                Points.drawActiveCenter(can, data, Palette.createPaint(PaintType.POINT))
            }
        }
    }

    fun drawAmbientFace(can: Canvas, data: AmbientData) {
        Circles.drawAmbient(can, data)
        Shapes.drawAmbient(can, data)
        Hands.drawAmbient(can, data)
        Points.drawAmbient(can, data)
    }

    fun drawAmbientWave(can: Canvas, data: AmbientWaveData) {
        drawActiveWave(can, data, false)
    }

    fun drawActiveWave(can: Canvas, data: ActiveWaveData, isActive: Boolean = true) {
        val t = ConfigData.waveSpectrum().velocity() * (data.timeStampMs % 60000) / 1000
        val buffer = IntBuffer.allocate(data.w * data.h)
        data.keys.forEach { key: Point ->
            val complexPixel: Complex = Layer.fromData(data, key, t, isActive).get()
            val color: Int = ColorUtil.getColor(complexPixel)
            buffer.put(color)
        }
        buffer.rewind()
        drawFromBuffer(can, buffer, data)
    }

    private fun drawBackground(can: Canvas) {
        val color = Zir.color(ConfigData.background().id)
        //can.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        can.drawColor(color, PorterDuff.Mode.CLEAR)
        val paint = Palette.createPaint(PaintType.BACKGROUND, color)
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), paint)
    }

    private fun drawFromBuffer(can: Canvas, buffer: IntBuffer, data: ActiveWaveData) {
        val bitmap = Bitmap.createBitmap(data.w, data.h, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer)

        val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, rotMatrix(), true)
        val blurred = gaussianBlur(rotated)
        val scaled = Bitmap.createScaledBitmap(blurred, -can.width, can.height, !ConfigData.waveIsPixelated());
        can.drawBitmap(scaled, 0F, 0F, null)
    }

    private fun rotMatrix() = Matrix().apply { postRotate(90F) }
    private fun gaussianBlur(input: Bitmap): Bitmap {
        val isBlur = !ConfigData.waveIsPixelated()
        if (isBlur) {
            val output = Bitmap.createBitmap(input)
            val script = RenderScript.create(Zir.ctx())
            val blur = createBlur(script)
            val tmpIn = Allocation.createFromBitmap(script, input)
            blur.setInput(tmpIn)
            val tmpOut = Allocation.createFromBitmap(script, output)
            blur.forEach(tmpOut)
            tmpOut.copyTo(output)
            return output
        } else {
            return input
        }
    }

    private fun createBlur(script: RenderScript): ScriptIntrinsicBlur {
        val blurRadius = Resolution.get(!ConfigData.isAmbient).blurRadius
        return ScriptIntrinsicBlur.create(script, Element.U8_4(script)).apply { setRadius(blurRadius) }
    }

    companion object {
        val PHI = 1.618033988F
        val PI = Math.PI.toFloat()
        val TAU = PI * 2F
        val ONE_MINUTE_AS_RAD = PI / 30F
        val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F

        fun makeOutline(p: Paint) = Paint(p).apply {
            strokeWidth = p.strokeWidth + ConfigData.style().outline.dim
            color = Zir.color(R.color.black)
        }

        fun calcDistFromBorder(can: Canvas, stroke: Stroke) = calcDistFromBorder(can.height, stroke.dim)
        fun calcDistFromBorder(height: Int, dim: Float): Float {
            val assertedOutlineDimension = 8 //TODO use exact?
            val totalSetoff = 4F * (dim + assertedOutlineDimension)
            return height / (height + totalSetoff)
        }

        fun calcPosition(rot: Float, length: Float, centerOffset: Float): PointF {
            val x = centerOffset + (Math.sin(rot.toDouble()) * length).toFloat()
            val y = centerOffset + (-Math.cos(rot.toDouble()) * length).toFloat()
            return PointF(x, y)
        }

        fun calcCircumcenter(a: PointF, b: PointF, c: PointF): PointF {
            val dA = a.x * a.x + a.y * a.y
            val dB = b.x * b.x + b.y * b.y
            val dC = c.x * c.x + c.y * c.y
            val divisor = 2F * (a.x * (c.y - b.y) + b.x * (a.y - c.y) + c.x * (b.y - a.y))
            val x = (dA * (c.y - b.y) + dB * (a.y - c.y) + dC * (b.y - a.y)) / divisor
            val y = -(dA * (c.x - b.x) + dB * (a.x - c.x) + dC * (b.x - a.x)) / divisor
            return PointF(x, y)
        }

        fun calcDistance(a: PointF, b: PointF): Float {
            val p = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
            return Math.sqrt(p.toDouble()).toFloat()
        }

        private fun maybeAddOutline(isOutline: Boolean) = if (isOutline) ConfigData.style().outline.dim else 0F
        private fun applyStretch(isAdd: Boolean, w: Float, f: Float) = if (isAdd) w + (w * f) else (w * f)
        private fun calcStrokeWidth(p: Paint, factor: Float, isOutline: Boolean, isAdd: Boolean): Float {
            val w = p.strokeWidth
            if (ConfigData.isElastic()) {
                if (ConfigData.isElasticOutline) {
                    return applyStretch(isAdd, w + maybeAddOutline(isOutline), factor)
                } else {
                    return applyStretch(isAdd, w, factor) + maybeAddOutline(isOutline)
                }
            } else {
                return w + maybeAddOutline(isOutline)
            }
        }

        val MAX_FACTOR = 2.895378F //max() of all factors. FIXME may require adjustment if points are moved.
        val OFFSET = 1F - (1F / PHI) //ratio of the states in which the components stay fully colored.
        val MIN_RATIO = 1F - (1F / PHI) //cutoff for the blended color. 0F allows full white.
        private fun handleColor(p: Paint, factor: Float, isOutline: Boolean): Int {
            if (isOutline) {
                return Zir.color(R.color.black)
            } else {
                if (factor > MAX_FACTOR) Log.w(TAG, "MAX_FACTOR: $MAX_FACTOR factor: $factor")
                if (ConfigData.isElasticColor) {
                    val ratio = maxOf(MIN_RATIO, minOf(1F, (factor / MAX_FACTOR) + OFFSET))
                    return ColorUtils.blendARGB(Zir.color(R.color.white), p.color, ratio)
                } else {
                    return p.color
                }
            }
        }

        fun applyElasticity(p: Paint, factor: Float, isOutline: Boolean) = applyElasticity(p, factor, isOutline, false)
        fun applyElasticity(p: Paint, factor: Float, isOutline: Boolean, isAdd: Boolean) = Paint(p).apply {
            strokeWidth = calcStrokeWidth(p, factor, isOutline, isAdd)
            color = handleColor(p, factor, isOutline)
        }

        private val TAG = this::class.java.simpleName
    }
}
