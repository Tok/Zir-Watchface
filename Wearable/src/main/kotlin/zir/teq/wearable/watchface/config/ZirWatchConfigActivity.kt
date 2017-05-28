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
import zir.teq.wearable.watchface.util.ViewHelper

class ZirWatchConfigActivity : Activity() {
    private var mView: WearableRecyclerView? = null
    private var mAdapter: ZirWatchConfigAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_config)
        mAdapter = ZirWatchConfigAdapter(applicationContext,
                ConfigData.watchFaceServiceClass,
                ConfigData.getDataToPopulateAdapter(this))
        mView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COLORS.code -> {
                if (resultCode == Activity.RESULT_OK) {
                    val col = Col.findActive(applicationContext) //TODO pass with intent extra?
                    val cf = Col.createFilter(applicationContext, col)
                    val res = applicationContext.resources
                    res.getDrawable(R.drawable.icon_color, null).setColorFilter(cf)
                    res.getDrawable(R.drawable.icon_stroke, null).setColorFilter(cf)
                    res.getDrawable(R.drawable.icon_theme, null).setColorFilter(cf)
                    Log.d(TAG, "Color changed. col: $col")
                }
            }
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
        data class UpdateReq(val code: Int)
        val THEME = UpdateReq(1001)
        val COLORS = UpdateReq(1002)
        val STROKE = UpdateReq(1003)
        val DRAW_TRIANGLES = UpdateReq(1004)
        val DRAW_CIRCLES = UpdateReq(1005)
        val DRAW_ACTIVE_HANDS = UpdateReq(1006)
        val DRAW_HANDS = UpdateReq(1007)
        val DRAW_POINTS = UpdateReq(1008)
        val DRAW_TEXT = UpdateReq(1009)
    }
}