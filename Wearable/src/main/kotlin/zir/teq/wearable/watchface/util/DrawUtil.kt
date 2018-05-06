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
import zir.teq.wearable.watchface.model.data.meta.Meta
import zir.teq.wearable.watchface.model.data.meta.MetaHandlePoints
import zir.teq.wearable.watchface.model.data.meta.MetaPoints
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.component.Component
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.setting.wave.Layer
import zir.teq.wearable.watchface.model.setting.wave.WaveResolution
import zir.teq.wearable.watchface.model.types.Complex
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State
import java.nio.IntBuffer
import java.util.*


/**
 * Created by Zir on 03.01.2016.
 * Recreated 20.05.2017
 */
object DrawUtil {
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
        val pointPaint = Palette.createPaint(PaintType.POINT, Zir.color(R.color.white))
        Metas.drawActive(can, frame)
        when (StyleStack.load()) {
            StyleStack.GROUPED -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE, pal.dark()))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE, pal.half()))
                Points.drawActive(can, frame, pointPaint)
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND, pal.light()))
                Points.drawActiveCenter(can, frame, pointPaint)
            }
            StyleStack.LEGACY -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE))
                Points.drawActive(can, frame, pointPaint)
                Points.drawActiveCenter(can, frame, pointPaint)
            }
            else -> {
                Circles.drawActive(can, frame, Palette.createPaint(PaintType.CIRCLE))
                Shapes.drawActive(can, frame, Palette.createPaint(PaintType.SHAPE, pal.light()))
                Triangles.draw(can, frame, Palette.createPaint(PaintType.SHAPE))
                Hands.drawActive(can, frame, Palette.createPaint(PaintType.HAND))
                Points.drawActive(can, frame, pointPaint)
                Points.drawActiveCenter(can, frame, pointPaint)
            }
        }
    }

    fun drawAmbientFace(can: Canvas, data: AmbientFrame) {
        Metas.drawAmbient(can, data)
        Circles.drawAmbient(can, data)
        Shapes.drawAmbient(can, data)
        Hands.drawAmbient(can, data)
        Points.drawAmbient(can, data)
    }

    fun drawAmbientWave(can: Canvas, data: AmbientWaveFrame) {
        drawActiveWave(can, data, false)
    }

    fun drawActiveWave(can: Canvas, data: ActiveWaveFrame, isActive: Boolean = true) {
        val t = CalcUtil.velocity() * (data.timeStampMs % 60000) / 1000
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

    fun drawMeta(can: Canvas, meta: Meta, p: Paint) {
        val WHITE_PAINT = p
        WHITE_PAINT.strokeWidth = StyleStroke.default.value
        WHITE_PAINT.color = Color.WHITE
        WHITE_PAINT.style = Paint.Style.FILL
        val blobPath = Path()
        with(meta.points) {
            blobPath.moveTo(p1.x, p1.y)
            blobPath.lineTo(p3.x, p3.y)
            blobPath.lineTo(p4.x, p4.y)
            blobPath.lineTo(p2.x, p2.y)
        }
        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), WHITE_PAINT)
        can.drawPath(blobPath, WHITE_PAINT)

        val BLACK_PAINT = p
        BLACK_PAINT.color = Zir.color(ConfigData.background().id)
        WHITE_PAINT.style = Paint.Style.FILL
        BLACK_PAINT.setAntiAlias(true)

        val leftPath = Path()
        leftPath.moveTo(meta.points.p1.x, meta.points.p1.y)
        with(meta.handles) {
            leftPath.cubicTo(h1.x, h1.y, h3.x, h3.y, meta.points.p3.x, meta.points.p3.y)
        }

        val rightPath = Path()
        rightPath.moveTo(meta.points.p2.x, meta.points.p2.y)
        with(meta.handles) {
            rightPath.cubicTo(h2.x, h2.y, h4.x, h4.y, meta.points.p4.x, meta.points.p4.y)
        }

        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), BLACK_PAINT)
        can.drawPath(leftPath, BLACK_PAINT)

        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), BLACK_PAINT)
        can.drawPath(rightPath, BLACK_PAINT)
    }

    private fun drawFromBuffer(can: Canvas, buffer: IntBuffer, data: ActiveWaveFrame) {
        fun rotMatrix() = Matrix().apply { postRotate(90F) }
        val bitmap = Bitmap.createBitmap(data.w, data.h, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer)

        val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, rotMatrix(), true)
        val blurred = gaussianBlur(rotated)
        val scaled = Bitmap.createScaledBitmap(blurred, -can.width, can.height, !ConfigData.waveIsPixelated());
        can.drawBitmap(scaled, 0F, 0F, null)
    }

    private fun gaussianBlur(input: Bitmap): Bitmap {
        fun createBlur(script: RenderScript): ScriptIntrinsicBlur {
            val blurRadius = 1F
            return ScriptIntrinsicBlur.create(script, Element.U8_4(script)).apply { setRadius(blurRadius) }
        }
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

    fun makeOutline(p: Paint) = Paint(p).apply {
        strokeWidth = p.strokeWidth + StyleOutline.load().value
        color = Zir.color(R.color.black)
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
    val OFFSET = 1F - (1F / CalcUtil.PHI) //ratio of the states in which the components stay fully colored.
    val MIN_RATIO = 1F - (1F / CalcUtil.PHI) //cutoff for the blended color. 0F allows full white.
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
