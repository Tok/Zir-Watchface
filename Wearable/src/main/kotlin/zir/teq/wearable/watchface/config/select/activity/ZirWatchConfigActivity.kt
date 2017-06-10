package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.adapter.ZirWatchConfigAdapter
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
        ViewHelper.initMainConfigView(mView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PALETTE.code -> {
                if (resultCode == Activity.RESULT_OK) {
                    val ctx = applicationContext
                    val col = Palette.findActive(ctx)
                    val filter = Palette.createFilter(ctx, col)
                    updateItemColor(ctx, R.drawable.icon_color, filter)
                    updateItemColor(ctx, R.drawable.icon_background, filter)
                    updateItemColor(ctx, R.drawable.icon_stroke, filter)
                    updateItemColor(ctx, R.drawable.icon_theme, filter)
                    updateItemColor(ctx, R.drawable.icon_outline, filter)
                    updateItemColor(ctx, R.drawable.icon_growth, filter)
                    updateItemColor(ctx, R.drawable.icon_alpha, filter)
                    updateItemColor(ctx, R.drawable.icon_dim, filter)
                    Log.d(TAG, "Color changed. col: $col")
                }
            }
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
        data class UpdateReq(val code: Int)
        val PALETTE = UpdateReq(1000)
        fun updateItemColor(ctx: Context, id: Int, filter: ColorFilter) {
            val drawable = ctx.resources.getDrawable(id, null)
            drawable.setColorFilter(filter)
            drawable.invalidateSelf()
        }
    }
}