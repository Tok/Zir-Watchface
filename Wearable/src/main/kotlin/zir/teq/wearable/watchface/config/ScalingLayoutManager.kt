package zir.teq.wearable.watchface.config

import android.content.Context
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.view.View

class ScalingLayoutManager(ctx: Context) : CurvedChildLayoutManager(ctx) {
    override fun updateChild(child: View, parent: WearableRecyclerView) {
        super.updateChild(child, parent)
        val centerOffset = child.height.toFloat() * 0.5F / parent.height.toFloat()
        val relativeCenterOffsetY = (child.y / parent.height) + centerOffset
        val progress = Math.abs(0.5F - relativeCenterOffsetY) * 2F
        val clippedAbsProgress = Math.min(progress, MAX_ICON_PROGRESS)
        val scale = 1F - progress
        val clippedScale = 1F - clippedAbsProgress
        child.scaleX = scale
        child.scaleY = scale
        child.translationX = child.width * progress * -0.5F
        child.translationY = child.height * (0.5F - relativeCenterOffsetY) * DISTORTION / clippedScale
    }

    companion object {
        private val DISTORTION: Float = 1F / 6F
        private val MAX_ICON_PROGRESS: Float = 0.8F
    }
}