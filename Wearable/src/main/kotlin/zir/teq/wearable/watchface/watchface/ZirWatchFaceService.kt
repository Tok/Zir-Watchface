package zir.teq.wearable.watchface.watchface

import android.content.*
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.util.Log
import android.view.SurfaceHolder
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.StrokeType
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.watchface.DrawUtil
import java.util.*

class ZirWatchFaceService : CanvasWatchFaceService() {
    override fun onCreateEngine(): Engine {
        return Engine()
    }

    inner class Engine : CanvasWatchFaceService.Engine() {
        private val MSG_UPDATE_TIME = 0

        val ctx = applicationContext
        val drawer = DrawUtil()

        private var mCalendar: Calendar = Calendar.getInstance()
        private var mMuteMode: Boolean = false
        private var mRegisteredTimeZoneReceiver = false


        private var mBackgroundColor: Int = ctx.getColor(R.color.black)
        private var mCol: Col = Col.defaultColor
        private var mStroke: Stroke = Stroke.createStroke(ctx, StrokeType.default.name)
        private var mTheme: Theme = Theme.defaultTheme

        private var mBackgroundPaint: Paint = Col.prep(mBackgroundColor)
        private var mDarkPaint: Paint = Col.prep(mCol.darkId)
        private var mLightPaint: Paint = Col.prep(mCol.lightId)

        private var mAmbient: Boolean = false
        private var mLowBitAmbient: Boolean = false
        private var mBurnInProtection: Boolean = false
        private var mUpdateRateMs = ConfigItem.updateRateMs(mAmbient, mTheme.isFastUpdate)

        internal lateinit var prefs: SharedPreferences
        private val mTimeZoneReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                mCalendar.timeZone = TimeZone.getDefault()
                invalidate()
            }
        }

        internal val mUpdateTimeHandler: Handler = object : Handler() {
            val msgUpdateTime = 0
            override fun handleMessage(message: Message) {
                when (message.what) {
                    msgUpdateTime -> {
                        invalidate()
                        if (shouldTimerBeRunning()) {
                            val timeMs = System.currentTimeMillis()
                            mUpdateRateMs = ConfigItem.updateRateMs(mAmbient, mTheme.isFastUpdate)
                            val delayMs = mUpdateRateMs - timeMs % mUpdateRateMs
                            this.sendEmptyMessageDelayed(msgUpdateTime, delayMs)
                        }
                    }
                }
            }
        }

        override fun onCreate(holder: SurfaceHolder?) {
            super.onCreate(holder)
            val service = this@ZirWatchFaceService
            val style = WatchFaceStyle.Builder(service).setAcceptsTapEvents(true).build()
            setWatchFaceStyle(style)
            loadSavedPreferences()
            initializeStyles()
        }

        private fun loadSavedPreferences() {
            val prefs = ConfigData.prefs(ctx)
            val backgroundColorResourceName = ctx.getString(R.string.saved_background_color)
            mBackgroundColor = prefs.getInt(backgroundColorResourceName, Color.BLACK)

            val mColName = prefs.getString(ctx.getString(R.string.saved_color), Col.WHITE.name)
            mCol = Col.getColorByName(mColName)
            Log.d(TAG, "loaded saved color... mCol: $mCol")

            val mStrokeName = prefs.getString(ctx.getString(R.string.saved_stroke), StrokeType.default.name)
            mStroke = Stroke.createStroke(ctx, mStrokeName)
            Log.d(TAG, "loaded saved stroke... mStroke: $mStroke")

            val savedThemeName = prefs.getString(ctx.getString(R.string.saved_theme), Theme.defaultTheme.name)
            val savedTheme = Theme.getThemeByName(savedThemeName)
            Log.d(TAG, "loaded saved theme... savedTheme: $savedTheme")
            val isFastUpdate = prefs.getBoolean(ctx.getString(R.string.saved_fast_update), savedTheme.isFastUpdate)
            val isHand = Theme.Companion.Setting(
                    prefs.getBoolean(ctx.getString(R.string.saved_hands_act), savedTheme.hands.active),
                    prefs.getBoolean(ctx.getString(R.string.saved_hands_amb), savedTheme.hands.ambient))
            val isTri = Theme.Companion.Setting(
                    prefs.getBoolean(ctx.getString(R.string.saved_triangles_act), savedTheme.triangles.active),
                    prefs.getBoolean(ctx.getString(R.string.saved_triangles_amb), savedTheme.triangles.ambient))
            val isCirc = Theme.Companion.Setting(
                    prefs.getBoolean(ctx.getString(R.string.saved_circles_act), savedTheme.circles.active),
                    prefs.getBoolean(ctx.getString(R.string.saved_circles_amb), savedTheme.circles.ambient))
            val isPoints = Theme.Companion.Setting(
                    prefs.getBoolean(ctx.getString(R.string.saved_points_act), savedTheme.points.active),
                    prefs.getBoolean(ctx.getString(R.string.saved_points_amb), savedTheme.points.ambient))
            val isText = Theme.Companion.Setting(
                    prefs.getBoolean(ctx.getString(R.string.saved_text_act), savedTheme.text.active),
                    prefs.getBoolean(ctx.getString(R.string.saved_text_amb), savedTheme.text.ambient))
            mTheme = Theme(savedTheme.name, savedTheme.iconId, isFastUpdate, isHand, isTri, isCirc, isPoints, isText)

            Log.d(TAG, "theme updated... mTheme: $mTheme")

            updateWatchPaintStyles()
        }

        private fun initializeStyles() {
            Log.d(TAG, "initializeStyles()")
            mBackgroundPaint = Paint()
            mBackgroundPaint.color = mBackgroundColor
            mDarkPaint.color = mCol.darkId
            mDarkPaint.strokeWidth = mStroke.dim
            mLightPaint.color = mCol.lightId
            mLightPaint.strokeWidth = mStroke.dim
        }

        override fun onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            super.onDestroy()
        }

        override fun onPropertiesChanged(properties: Bundle?) {
            super.onPropertiesChanged(properties)
            Log.d(TAG, "onPropertiesChanged: low-bit ambient = " + mLowBitAmbient)
            mLowBitAmbient = properties!!.getBoolean(WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false)
            mBurnInProtection = properties.getBoolean(WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false)
        }

        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            Log.d(TAG, "OnTapCommand()")
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)
            Log.d(TAG, "onAmbientModeChanged: " + inAmbientMode)
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode
                invalidate()
            }
            updateTimer()
        }

        override fun onInterruptionFilterChanged(interruptionFilter: Int) {
            super.onInterruptionFilterChanged(interruptionFilter)
            val inMuteMode = interruptionFilter == WatchFaceService.INTERRUPTION_FILTER_NONE
            val isFastUpdate = true //FIXME
            val rate = ConfigItem.updateRateMs(inMuteMode, isFastUpdate)
            setInteractiveUpdateRateMs(rate)
            if (mMuteMode != inMuteMode) { //dim display in mute mode.
                mMuteMode = inMuteMode
                mDarkPaint.alpha = if (inMuteMode) 100 else 255
                mLightPaint.alpha = if (inMuteMode) 100 else 255
                invalidate()
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                loadSavedPreferences()
                updateWatchPaintStyles()
                registerReceiver()
                mCalendar.timeZone = TimeZone.getDefault()
                invalidate()
            } else {
                unregisterReceiver()
            }
            updateTimer()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect?) {
            mCalendar.timeInMillis = System.currentTimeMillis()
            val makeDarkBackground = mAmbient && (mLowBitAmbient || mBurnInProtection)
            val bgPaint = if (makeDarkBackground) mBackgroundPaint else Col.prep(ctx.getColor(R.color.black))
            drawer.drawBackground(canvas, bgPaint)
            drawer.draw(ctx, mCol, mStroke, mTheme, canvas, bounds!!, mAmbient, mCalendar)
        }

        private fun updateWatchPaintStyles() {
            mBackgroundPaint.color = if (mAmbient) Color.BLACK else mBackgroundColor

            mDarkPaint.color = if (mAmbient) Color.BLACK else mCol.darkId
            mDarkPaint.isAntiAlias = !mAmbient
            mDarkPaint.strokeWidth = mStroke.dim

            mLightPaint.color = if (mAmbient) Color.WHITE else mCol.lightId
            mLightPaint.isAntiAlias = !mAmbient
            mLightPaint.strokeWidth = mStroke.dim

            setShadows(mAmbient)
        }

        private fun setShadows(mAmbient: Boolean) {
            val drawShadows = false //TODO activate?
            if (drawShadows && mAmbient) {
                mDarkPaint.clearShadowLayer()
                mLightPaint.clearShadowLayer()
            } else {
                val shadowRadius = 3F
                val shadowColor = Color.WHITE
                mDarkPaint.setShadowLayer(shadowRadius, 0F, 0F, shadowColor)
            }
        }

        private fun setInteractiveUpdateRateMs(updateRateMs: Long) {
            if (updateRateMs == mUpdateRateMs) {
                return
            }
            mUpdateRateMs = updateRateMs
            if (shouldTimerBeRunning()) {
                updateTimer()
            }
        }

        private fun registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return
            }
            mRegisteredTimeZoneReceiver = true
            val filter = IntentFilter(Intent.ACTION_TIMEZONE_CHANGED)
            this@ZirWatchFaceService.registerReceiver(mTimeZoneReceiver, filter)
        }

        private fun unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return
            }
            mRegisteredTimeZoneReceiver = false
            this@ZirWatchFaceService.unregisterReceiver(mTimeZoneReceiver)
        }

        private fun updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
            }
        }

        private fun shouldTimerBeRunning(): Boolean {
            return isVisible && !mAmbient //when active
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
