package zir.watchface

import android.graphics.*
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v4.graphics.ColorUtils
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.draw.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.model.data.settings.Stack
import zir.teq.wearable.watchface.model.data.settings.Stroke
import zir.teq.wearable.watchface.model.data.settings.Wave
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.util.ColorUtil
import zir.teq.wearable.watchface.util.WaveCalc
import java.nio.IntBuffer
import java.util.*


/**
 * Created by Zir on 03.01.2016.
 * Recreated 20.05.2017
 */
class DrawUtil() {
    data class HandData(val p: PointF, val radians: Float, val maybeExtended: PointF)
    data class Ref(val can: Canvas, val unit: Float, val center: PointF)
    open class FrameData(cal: Calendar, bounds: Rect) {
        val hh = cal.get(Calendar.HOUR_OF_DAY)
        val mm = cal.get(Calendar.MINUTE)
        val ss = cal.get(Calendar.SECOND)
        val minRot: Float = (mm + (ss / 60F)) / 30F * PI
        val hrRot: Float = (hh + (mm / 60F)) / 6F * PI
        val unit = bounds.width() / 2F
        val center = PointF(unit, unit)
        fun getRef(can: Canvas): Ref = Ref(can, unit, center)
    }

    open class AmbientFrameData(cal: Calendar, bounds: Rect, can: Canvas) : FrameData(cal, bounds) {
        val minLength = unit * calcDistFromBorder(can, ConfigData.stroke) / PHI
        val hrLength = minLength / PHI

        val hr = calcPosition(hrRot, hrLength, unit)
        val min = calcPosition(minRot, minLength, unit)
        val hour = HandData(hr, hrRot, center)

        val minute = HandData(min, minRot, center)
        val ccCenter = calcCircumcenter(center, hr, min)
        val ccRadius = calcDistance(min, ccCenter)
    }

    open class ActiveFrameData(cal: Calendar, bounds: Rect, can: Canvas) : FrameData(cal, bounds) {
        val ms = cal.get(Calendar.MILLISECOND)
        val secRot = (ss + ms / 1000F) / 30F * PI
        val secLength = unit * calcDistFromBorder(can, ConfigData.stroke)
        val minLength = secLength / PHI
        val hrLength = minLength / PHI
        val hr = calcPosition(hrRot, hrLength, unit)
        val min = calcPosition(minRot, minLength, unit)
        val sec = calcPosition(secRot, secLength, unit)
        val circlesActive = ConfigData.theme.circles.active
        val hrExtended = if (circlesActive) calcPosition(hrRot, secLength, unit) else hr //why secLength?
        val minExtended = if (circlesActive) calcPosition(minRot, secLength, unit) else min //why secLength?
        val secExtended = if (circlesActive) calcPosition(secRot, secLength, unit) else sec
        val hour = HandData(hr, hrRot, hrExtended)
        val minute = HandData(min, minRot, minExtended)
        val second = HandData(sec, secRot, secExtended)
    }

    class ActiveWaveFrameData(cal: Calendar, bounds: Rect, can: Canvas) : ActiveFrameData(cal, bounds, can) {
        val isUseUneven = false //render an additional line of pixels in the center.
        val timeStamp = cal.timeInMillis
        val res = ConfigData.wave.resolution.value
        val setOff = if (isUseUneven) 1 else 0
        val w = setOff + (bounds.width() / res)
        val h = setOff + (bounds.height() / res)

        val scaledUnit: Float = (bounds.width() - res) / (res * 2F)
        val scaledCenter = PointF(scaledUnit, scaledUnit)

        val waveSecLength = secLength * scaledUnit / unit
        val waveMinLength = minLength * scaledUnit / unit
        val waveHrLength = hrLength * scaledUnit / unit

        val waveHr = calcPosition(hrRot, waveHrLength, scaledUnit)
        val waveMin = calcPosition(minRot, waveMinLength, scaledUnit)
        val waveSec = calcPosition(secRot, waveSecLength, scaledUnit)

        val isProportional = false //TODO
        val centerMass = if (isProportional) Wave.MASS_DEFAULT.value else Wave.MASS_DEFAULT.value
        val hourMass = if (isProportional) Wave.MASS_DEFAULT.value else Wave.MASS_DEFAULT.value
        val minuteMass = if (isProportional) hourMass / PHI else Wave.MASS_DEFAULT.value
        val secondMass = if (isProportional) minuteMass / PHI else Wave.MASS_DEFAULT.value
    }

    fun draw(can: Canvas, bounds: Rect, calendar: Calendar) {
        if (ConfigData.isAmbient || ConfigData.wave != Wave.OFF) {
            drawBackground(can)
        }
        if (ConfigData.isAmbient) {
            if (ConfigData.wave != Wave.OFF) {
                val waveData = ActiveWaveFrameData(calendar, bounds, can)
                drawAmbientWave(can, waveData)
            }
            val data = AmbientFrameData(calendar, bounds, can)
            drawAmbientFace(can, data)
            if (ConfigData.theme.text.ambient) {
                Text.draw(can, calendar)
            }
        } else {
            val activeData = ActiveFrameData(calendar, bounds, can)
            if (ConfigData.wave != Wave.OFF) {
                val waveData = ActiveWaveFrameData(calendar, bounds, can)
                drawActiveWave(can, waveData)
            }
            drawActiveFace(can, activeData)
            if (ConfigData.theme.text.active) {
                Text.draw(can, calendar)
            }
        }
    }

    fun drawActiveFace(can: Canvas, data: ActiveFrameData) {
        when (ConfigData.stack) {
            Stack.GROUPED -> {
                Circles.drawActive(can, data)
                Hands.drawActive(can, data)
                Points.drawActiveCenter(can, data)
                Triangles.draw(can, data)
                Points.drawActive(can, data)
            }
            else -> {
                Circles.drawActive(can, data)
                Triangles.draw(can, data)
                Hands.drawActive(can, data)
                Points.drawActive(can, data)
                Points.drawActiveCenter(can, data)
            }
        }
    }

    fun drawAmbientFace(can: Canvas, data: AmbientFrameData) {
        Circles.drawAmbient(can, data)
        Hands.drawAmbient(can, data)
        Points.drawAmbient(can, data)
    }

    fun drawAmbientWave(can: Canvas, data: ActiveWaveFrameData) {
        drawActiveWave(can, data, false)
    }

    val lastFrame = mutableMapOf<Pair<Int, Int>, Complex>()
    fun drawActiveWave(can: Canvas, data: ActiveWaveFrameData, isActive: Boolean = true) {
        val wave = ConfigData.wave
        val t = data.timeStamp * ConfigData.wave.velocity
        val buffer = IntBuffer.allocate(data.w * data.h)
        val xRange = 0..(data.h - 1)
        val yRange = 0..(data.w - 1)
        xRange.flatMap { xInt ->
            yRange.map { yInt ->
                with(data) {
                    val point = PointF(xInt.toFloat(), yInt.toFloat())
                    val terms = prepareTerms(data, point, t, isActive)
                    val all: Complex = when (ConfigData.wave.op) {
                        Operator.MULTIPLY -> terms.fold(terms.first()) { total, next -> total.multiply(next) }
                        Operator.ADD -> terms.fold(terms.first()) { total, next -> total.add(next) }
                        else -> throw IllegalArgumentException("Unknown operator: " + ConfigData.wave.op)
                    }
                    if (wave.isKeepState) {
                        val key = Pair(xInt, yInt)
                        val last = lastFrame.get(key) ?: all
                        val newMagnitude = (all.magnitude + (wave.lastWeight * last.magnitude)) / (wave.lastWeight + 1)
                        val newPhase = (all.phase + (wave.lastWeight * last.phase)) / (wave.lastWeight + 1)
                        val new = Complex.fromMagnitudeAndPhase(newMagnitude, newPhase)
                        lastFrame.put(key, new)
                        val col = ColorUtil.getColor(new)
                        buffer.put(col)
                    } else {
                        val col = ColorUtil.getColor(all)
                        buffer.put(col)
                    }
                }
            }
        }
        buffer.rewind()
        drawFromBuffer(can, buffer, data)
    }

    private fun drawBackground(can: Canvas) {
        val color = ConfigData.ctx.getColor(ConfigData.background.id)
        //can.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        can.drawColor(color, PorterDuff.Mode.CLEAR)
        can.drawRect(0F, 0F, can.width.toFloat(), can.height.toFloat(), Palette.prep(color))
    }

    private fun prepareTerms(data: ActiveWaveFrameData, point: PointF, t: Double, isActive: Boolean): List<Complex> {
        with(data) {
            val terms = mutableListOf<Complex>()
            val wave = ConfigData.wave
            if (wave.hasCenter) terms.add(WaveCalc.calc(point, scaledCenter, t, centerMass))
            if (wave.hasHours) terms.add(WaveCalc.calc(point, waveHr, t, hourMass))
            if (wave.hasMinutes) terms.add(WaveCalc.calc(point, waveMin, t, minuteMass))
            if (isActive && wave.hasSeconds) terms.add(WaveCalc.calc(point, waveSec, t, secondMass))
            if (terms.isEmpty()) throw IllegalStateException("Missing terms.")
            return terms
        }
    }

    private fun drawFromBuffer(can: Canvas, buffer: IntBuffer, data: ActiveWaveFrameData) {
        val bitmap = Bitmap.createBitmap(data.w, data.h, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer)

        val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, rotMatrix(), true)
        val scaled = Bitmap.createScaledBitmap(rotated, -can.width, can.height, true);
        val blurred = gaussianBlur(scaled)
        can.drawBitmap(blurred, 0F, 0F, null)
    }

    private fun rotMatrix(): Matrix {
        val matrix = Matrix()
        matrix.postRotate(90F)
        return matrix
    }

    private fun gaussianBlur(input: Bitmap): Bitmap {
        if (!ConfigData.wave.isBlur) {
            return input
        } else {
            val output = Bitmap.createBitmap(input)
            val script = RenderScript.create(ConfigData.ctx)
            val blur = createBlur(script)
            val tmpIn = Allocation.createFromBitmap(script, input)
            blur.setInput(tmpIn)
            val tmpOut = Allocation.createFromBitmap(script, output)
            blur.forEach(tmpOut)
            tmpOut.copyTo(output)
            return output
        }
    }

    private fun createBlur(script: RenderScript): ScriptIntrinsicBlur {
        val intrinsicBlur = ScriptIntrinsicBlur.create(script, Element.U8_4(script))
        val blurRadius = ConfigData.wave.resolution.value.toFloat()
        intrinsicBlur.setRadius(blurRadius)
        return intrinsicBlur
    }

    companion object {
        val PHI = 1.618033988F //TODO change all the Floats to Double?
        val PI = Math.PI.toFloat() //180 Degree
        val TAU = PI * 2F //180 Degree

        val ONE_MINUTE_AS_RAD = PI / 30F
        val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F
        val yOffset: Float = 98F
        var xOffset: Float = 0.toFloat()

        fun makeOutline(p: Paint): Paint {
            val outLine = Paint(p)
            outLine.strokeWidth = p.strokeWidth + ConfigData.outline.dim
            outLine.color = ConfigData.ctx.getColor(R.color.black)
            return outLine
        }

        fun calcDistFromBorder(can: Canvas, stroke: Stroke): Float {
            return calcDistFromBorder(can.height, stroke.dim)
        }

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

        private fun maybeAddOutline(isOutline: Boolean) = if (isOutline) {
            ConfigData.outline.dim
        } else {
            0F
        }

        private fun applyStretch(isAdd: Boolean, w: Float, f: Float) = if (isAdd) w + (w * f) else (w * f)
        private fun calcStrokeWidth(p: Paint, factor: Float, isOutline: Boolean, isAdd: Boolean): Float {
            val w = p.strokeWidth
            if (ConfigData.isElastic) {
                if (ConfigData.isElasticOutline) {
                    return applyStretch(isAdd, w + maybeAddOutline(isOutline), factor)
                } else {
                    return applyStretch(isAdd, w, factor) + maybeAddOutline(isOutline)
                }
            } else {
                return w + maybeAddOutline(isOutline)
            }
        }

        val MAX_FACTOR = 2.6404555F //max() of all factors. FIXME may require adjustment if points are moved.
        //WA finds no meaningful closed forms https://www.wolframalpha.com/input/?i=2.6404555
        val OFFSET = 1F - (1F / PHI) //ratio of the states in which the components stay fully colored.
        val MIN_RATIO = 1F - (1F / PHI) //cutoff for the blended color. 0F allows full white.
        private fun handleColor(p: Paint, factor: Float, isOutline: Boolean): Int {
            if (isOutline) {
                return ConfigData.ctx.getColor(R.color.black)
            } else {
                if (factor > MAX_FACTOR) Log.w(TAG, "MAX_FACTOR: $MAX_FACTOR factor: $factor")
                if (ConfigData.isElasticColor) {
                    val ratio = maxOf(MIN_RATIO, minOf(1F, (factor / MAX_FACTOR) + OFFSET))
                    return ColorUtils.blendARGB(ConfigData.ctx.getColor(R.color.white), p.color, ratio)
                } else {
                    return p.color
                }
            }
        }

        fun applyElasticity(p: Paint, factor: Float, isOutline: Boolean) = applyElasticity(p, factor, isOutline, false)
        fun applyElasticity(p: Paint, factor: Float, isOutline: Boolean, isAdd: Boolean): Paint {
            //Log.d(TAG, "applyElasticity factor: $factor")
            val result = Paint(p)
            result.strokeWidth = calcStrokeWidth(p, factor, isOutline, isAdd)
            result.color = handleColor(p, factor, isOutline)
            return result
        }

        private val TAG = this::class.java.simpleName
    }
}
