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
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.ActiveWaveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientWaveFrame
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.setting.wave.Layer
import zir.teq.wearable.watchface.model.setting.wave.WaveResolution
import zir.teq.wearable.watchface.model.setting.wave.WaveVelocity
import zir.teq.wearable.watchface.model.types.Complex
import zir.teq.wearable.watchface.model.types.Component
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State
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
        if (ConfigData.waveIsOff()) {
            drawBackground(can)
        }
        when {
            ConfigData.isAmbient -> drawAmbient(can, bounds, calendar)
            else -> drawActive(can, bounds, calendar)
        }
    }

    private fun drawActive(can: Canvas, bounds: Rect, calendar: Calendar) {
        val activeData = ActiveFrame(calendar, bounds, can)
        if (!ConfigData.waveIsOff()) {
            val waveData = ActiveWaveFrame(calendar, bounds, can, WaveResolution.load().value.toInt())
            drawActiveWave(can, waveData)
        }
        drawActiveFace(can, activeData)
        if (ConfigData.isOn(Component.TEXT to State.ACTIVE)) {
            Text.draw(can, calendar)
        }
    }

    private fun drawAmbient(can: Canvas, bounds: Rect, calendar: Calendar) {
        if (!ConfigData.waveIsOff()) {
            val waveData = AmbientWaveFrame(calendar, bounds, can)
            drawAmbientWave(can, waveData)
        }
        val data = AmbientFrame(calendar, bounds, can)
        drawAmbientFace(can, data)
        if (ConfigData.isOn(Component.TEXT to State.AMBIENT)) {
            Text.draw(can, calendar)
        }
    }

    fun drawActiveFace(can: Canvas, frame: ActiveFrame) {
        val pal = ConfigData.palette()
        when (StyleStack.load()) {
            StyleStack.GROUPED -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE, pal.dark()))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE, pal.half()))
                Points.drawActive(can, frame, Palette.createPaint(PaintType.POINT, Zir.color(R.color.white)))
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND, pal.light()))
                Points.drawActiveCenter(can, frame, Palette.createPaint(PaintType.POINT, Zir.color(R.color.white)))
            }
            StyleStack.LEGACY -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE))
                Points.drawActive(can, frame, Palette.createPaint(PaintType.POINT))
                Points.drawActiveCenter(can, frame, Palette.createPaint(PaintType.POINT))
            }
            else -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE))
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND))
                Points.drawActive(can, frame, Palette.createPaint(PaintType.POINT))
                Points.drawActiveCenter(can, frame, Palette.createPaint(PaintType.POINT))
            }
        }
    }

    fun drawAmbientFace(can: Canvas, data: AmbientFrame) {
        Circles.drawAmbient(can, data)
        Shapes.drawAmbient(can, data)
        Hands.drawAmbient(can, data)
        Points.drawAmbient(can, data)
    }

    fun drawAmbientWave(can: Canvas, data: AmbientWaveFrame) {
        drawActiveWave(can, data, false)
    }

    private fun velocity() = when {
        ConfigData.waveIsStanding() -> 0F
        ConfigData.waveIsInward() -> WaveVelocity.load().value * -1
        else -> WaveVelocity.load().value
    }

    fun drawActiveWave(can: Canvas, data: ActiveWaveFrame, isActive: Boolean = true) {
        val t = velocity() * (data.timeStampMs % 60000) / 1000
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

    private fun drawFromBuffer(can: Canvas, buffer: IntBuffer, data: ActiveWaveFrame) {
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
        val blurRadius = 1F
        return ScriptIntrinsicBlur.create(script, Element.U8_4(script)).apply { setRadius(blurRadius) }
    }

    companion object {
        val PHI = 1.618033988F
        val PI = Math.PI.toFloat()
        val TAU = PI * 2F
        val ONE_MINUTE_AS_RAD = PI / 30F
        val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F

        fun makeOutline(p: Paint) = Paint(p).apply {
            strokeWidth = p.strokeWidth + StyleOutline.load().value
            color = Zir.color(R.color.black)
        }

        fun calcDistFromBorder(can: Canvas, stroke: StyleStroke) = calcDistFromBorder(can.height, stroke.value)
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

        private fun maybeAddOutline(isOutline: Boolean) = if (isOutline) StyleOutline.load().value else 0F
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
