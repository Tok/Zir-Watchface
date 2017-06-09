package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.PaletteSelectionActivity
import zir.teq.wearable.watchface.model.data.Palette

class ColorPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<PaletteSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_color))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            val col = Palette.findActive(view.context)
            launchIntent.putExtra("color", col.name)
            launchIntent.putExtra(PaletteSelectionActivity.EXTRA_SHARED_COLOR, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.COLORS.code)
        }
    }

    fun setActivity(activity: Class<PaletteSelectionActivity>) {
        mActivity = activity
    }
}
