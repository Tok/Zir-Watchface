package zir.teq.wearable.watchface.config.general.manager

import android.support.v7.widget.RecyclerView
import android.support.wear.widget.WearableLinearLayoutManager
import android.view.View
import zir.teq.wearable.watchface.util.CalcUtil.PHI

class ScalingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {
    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        val centerOffset = child.height.toFloat() * 0.5F / parent.height.toFloat()
        val yRelativeToCenterOffset = (child.y / parent.height) + centerOffset
        val mProgressToCenter = Math.min(Math.abs(0.5F - yRelativeToCenterOffset), 0.5F)
        val scale = 1F - mProgressToCenter
        child.scaleX = scale
        child.scaleY = scale
        child.translationX = child.width * PHI * (mProgressToCenter * mProgressToCenter)
        child.translationY = child.height * -(yRelativeToCenterOffset * yRelativeToCenterOffset)
    }
}
