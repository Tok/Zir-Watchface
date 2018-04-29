package zir.teq.wearable.watchface.config.general.manager

import android.support.v7.widget.RecyclerView
import android.support.wear.widget.WearableLinearLayoutManager
import android.view.View
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

class ScalingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {
    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        val centerOffset = child.height.toFloat() * 0.5F / parent.height.toFloat()
        val yRelativeToCenterOffset = (child.y / parent.height) + centerOffset
        val mProgressToCenter = Math.min(Math.abs(0.5F - yRelativeToCenterOffset), MAX_ICON_PROGRESS)
        val scale = 1F - mProgressToCenter
        child.scaleX = scale
        child.scaleY = scale
        child.translationX = child.width * PHI * (mProgressToCenter * mProgressToCenter)
        child.translationY = child.height * (0.5F - yRelativeToCenterOffset) * Y_DISTORTION / scale
    }

    companion object {
        private val Y_DISTORTION: Float = 1F / 2F
        private val MAX_ICON_PROGRESS = 0.5F
    }
}
