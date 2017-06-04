package zir.teq.wearable.watchface.config

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
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
                    val ctx = applicationContext
                    val col = Palette.findActive(ctx)
                    val filter = Palette.createFilter(ctx, col)
                    val res = applicationContext.resources
                    res.getDrawable(R.drawable.icon_color, null).setColorFilter(filter)
                    res.getDrawable(R.drawable.icon_stroke, null).setColorFilter(filter)
                    res.getDrawable(R.drawable.icon_theme, null).setColorFilter(filter)
                    res.getDrawable(R.drawable.icon_outline, null).setColorFilter(filter)
                    res.getDrawable(R.drawable.icon_growth, null).setColorFilter(filter)
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
        val OUTLINE = UpdateReq(1004)
        val GROWTH = UpdateReq(1005)
    }
}