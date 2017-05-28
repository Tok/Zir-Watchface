package zir.teq.wearable.watchface.config

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.util.ActivityHelper

class ZirWatchConfigActivity : Activity() {
    private var mView: WearableRecyclerView? = null
    private var mAdapter: ZirWatchConfigAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_watch_config_activity)
        mAdapter = ZirWatchConfigAdapter(applicationContext,
                ConfigData.watchFaceServiceClass,
                ConfigData.getDataToPopulateAdapter(this))
        mView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ActivityHelper.initView(mView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            UPDATE_COLORS_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                val col = Col.findActive(applicationContext) //TODO pass with intent extra?
                val cf = Col.createFilter(applicationContext, col)
                val res = applicationContext.resources
                res.getDrawable(R.drawable.icon_color, null).setColorFilter(cf)
                res.getDrawable(R.drawable.icon_stroke, null).setColorFilter(cf)
                res.getDrawable(R.drawable.icon_theme, null).setColorFilter(cf)
                Log.d(TAG, "Color changed. col: $col")
            }
            //UPDATE_STROKE_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) { doSomething() }
            //UPDATE_THEME_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) { doSomething() }
        }
    }

    companion object {
        private val TAG = ZirWatchConfigActivity::class.java.simpleName
        internal val UPDATE_COLORS_CONFIG_REQUEST_CODE = 1002
        internal val UPDATE_STROKE_CONFIG_REQUEST_CODE = 1003
        internal val UPDATE_THEME_CONFIG_REQUEST_CODE = 1004
        internal val UPDATE_DRAW_CIRCLES_REQUEST_CODE = 1005
        internal val UPDATE_DRAW_ACTIVE_HANDS_REQUEST_CODE = 1006
        internal val UPDATE_DRAW_HANDS_REQUEST_CODE = 1007
        internal val UPDATE_DRAW_TRIANGLE_REQUEST_CODE = 1008
        internal val UPDATE_DRAW_TEXT_REQUEST_CODE = 1009
        internal val UPDATE_DRAW_POINTS_REQUEST_CODE = 1010
    }
}