package zir.teq.wearable.watchface.config

import android.content.Context
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.view.View
import zir.watchface.DrawUtil

class PaletteCirclesLayoutManager(ctx: Context) : CurvedChildLayoutManager(ctx) {
    private var mProgressToCenter: Float = 0F

    override fun updateChild(child: View, parent: WearableRecyclerView) {
        super.updateChild(child, parent)

        val centerOffset = (child.height.toFloat() / 2.0F) / parent.height.toFloat()
        val yRelativeToCenterOffset = (child.y / parent.height) + centerOffset
        mProgressToCenter = Math.abs(0.5F - yRelativeToCenterOffset) * 2F
        //mProgressToCenter = Math.min(mProgressToCenter, MAX_ICON_PROGRESS) // Adjust to the maximum scale
        child.scaleX = 1F - mProgressToCenter
        child.scaleY = 1F - mProgressToCenter
    }

    companion object {
        private val MAX_ICON_PROGRESS = 1F - (1F / DrawUtil.PHI)
    }
}