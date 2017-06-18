package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.adapter.ZirWatchConfigAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.util.ViewHelper

class ZirWatchConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: ZirWatchConfigAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_config)
        mView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        val ctx = ConfigData.ctx
        mAdapter = ZirWatchConfigAdapter(ctx,
                ConfigData.watchFaceServiceClass,
                ConfigData.getDataToPopulateAdapter(this))
        ViewHelper.initMainConfigView(mView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PALETTE.code -> {
                if (resultCode == Activity.RESULT_OK) {
                    val ctx = mView.context
                    val filter = Palette.createFilter(ConfigData.palette)
                    updateItemColor(ctx, R.drawable.icon_color, filter)
                    updateItemColor(ctx, R.drawable.icon_background, filter)
                    updateItemColor(ctx, R.drawable.icon_stroke, filter)
                    updateItemColor(ctx, R.drawable.icon_theme, filter)
                    updateItemColor(ctx, R.drawable.icon_outline, filter)
                    updateItemColor(ctx, R.drawable.icon_growth, filter)
                    updateItemColor(ctx, R.drawable.icon_alpha, filter)
                    updateItemColor(ctx, R.drawable.icon_dim, filter)
                }
            }
        }
    }

    companion object {
        data class UpdateReq(val code: Int)
        val PALETTE = UpdateReq(1000)
        fun updateItemColor(ctx: Context, id: Int, filter: ColorFilter) {
            val drawable = ctx.resources.getDrawable(id, null)
            drawable.colorFilter = filter
            drawable.invalidateSelf()
        }
    }
}