package zir.teq.wearable.watchface.watchface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import zir.teq.wearable.watchface.Zir
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

        val ctx = Zir.getAppContext()
        val drawer = DrawUtil()

        private var mCalendar: Calendar = Calendar.getInstance()
        private var mRegisteredTimeZoneReceiver = false

        private var mAmbient: Boolean = false
        private var mLowBitAmbient: Boolean = false
        private var mBurnInProtection: Boolean = false
        private var mUpdateRateMs = ConfigItem.updateRateMs(mAmbient) //TODO move elsewhere?

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
                            mUpdateRateMs = ConfigItem.updateRateMs(mAmbient)
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

            drawer.draw(canvas, bounds!!, mCalendar)
        }

        private fun loadSavedPreferences() {
            ConfigData.updateFromSavedPreferences()
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
            val rate = ConfigItem.updateRateMs(inMuteMode)
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
