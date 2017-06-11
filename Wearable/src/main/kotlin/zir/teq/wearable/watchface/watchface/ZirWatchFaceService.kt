package zir.teq.wearable.watchface.watchface

import android.content.*
import android.graphics.Canvas
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
import zir.teq.wearable.watchface.model.data.*
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
        private var mRegisteredTimeZoneReceiver = false

        private var mPalette: Palette = Palette.default
        private var mStroke: Stroke = Stroke.create(ctx, Stroke.default.name)
        private var mTheme: Theme = Theme.default

        private var mAmbient: Boolean = false
        private var mLowBitAmbient: Boolean = false
        private var mBurnInProtection: Boolean = false
        private var mUpdateRateMs = ConfigItem.updateRateMs(mAmbient, mTheme.isFastUpdate) //TODO move elsewhere?

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
        }

        override fun onDraw(canvas: Canvas, bounds: Rect?) {
            updateWatchPaintStyles()
            mCalendar.timeInMillis = System.currentTimeMillis()
            drawer.drawBackground(canvas, ctx.getColor(ConfigData.background.id))
            drawer.draw(ctx, mPalette, mStroke, mTheme, canvas, bounds!!, mCalendar)
        }

        private fun loadSavedPreferences() {
            val prefs = ConfigData.prefs(ctx)

            val palName = prefs.getString(ctx.getString(R.string.saved_palette), Palette.WHITE.name)
            mPalette = Palette.getByName(palName)

            val strokeName = prefs.getString(ctx.getString(R.string.saved_stroke), Stroke.default.name)
            mStroke = Stroke.create(ctx, strokeName)

            val savedThemeName = prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val savedTheme = Theme.getByName(savedThemeName)

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
            val outlineName = prefs.getString(ctx.getString(R.string.saved_outline), savedTheme.outlineName)
            val growthName = prefs.getString(ctx.getString(R.string.saved_growth), savedTheme.growthName)

            with (prefs) { //TODO change types
                val background = Background.getByName(getString(ctx.getString(R.string.saved_background), Background.default.name))
                val alpha = Alpha.getByName(getString(ctx.getString(R.string.saved_alpha), Alpha.default.name))
                val dim = Dim.getByName(getString(ctx.getString(R.string.saved_dim), Dim.default.name))
                Log.d(TAG, "loaded saved background: $background, alpha: $alpha, dim: $dim")
                ConfigData.background = background
                ConfigData.alpha = alpha
                ConfigData.dim = dim
            }

            mTheme = Theme(savedTheme.name, savedTheme.iconId, isFastUpdate, isHand, isTri, isCirc, isPoints, isText, outlineName, growthName)
            Log.d(TAG, "theme updated... mTheme: $mTheme")
            updateWatchPaintStyles()
        }

        override fun onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            super.onDestroy()
        }

        override fun onPropertiesChanged(properties: Bundle?) {
            super.onPropertiesChanged(properties)
            if (properties != null && !properties.isEmpty) {
                mLowBitAmbient = properties.getBoolean(WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false)
                mBurnInProtection = properties.getBoolean(WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false)
            }
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
            val rate = ConfigItem.updateRateMs(inMuteMode, mTheme.isFastUpdate)
            setInteractiveUpdateRateMs(rate)
            val isDimmed = ConfigData.isMute != inMuteMode
            if (isDimmed) {
                ConfigData.isMute = inMuteMode
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

        private fun updateWatchPaintStyles() {
            ConfigData.isAmbient = mAmbient
            //mPalette.alpha = if (mPalette.isAmbient) Palette.AMBIENT_ALPHA else Palette.FULL_ALPHA
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
