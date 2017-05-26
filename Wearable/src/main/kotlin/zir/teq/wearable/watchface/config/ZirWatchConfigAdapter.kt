package zir.teq.wearable.watchface.config

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ColorSelectionActivity.Companion.EXTRA_SHARED_COLOR
import zir.teq.wearable.watchface.model.ConfigData
import java.util.*

class ZirWatchConfigAdapter(
        private val mContext: Context,
        watchFaceServiceClass: Class<*>,
        private val mSettingsDataSet: ArrayList<ConfigData.ConfigItemType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mWatchFaceComponentName: ComponentName
    internal var mSharedPref: SharedPreferences

    init {
        mWatchFaceComponentName = ComponentName(mContext, watchFaceServiceClass)
        mSharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.zir_watch_preference_file_key),
                Context.MODE_PRIVATE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        when (viewType) { //TODO add more
            TYPE_COLOR_CONFIG -> return ColorPickerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.config_list_color_item, parent, false))
            else -> return null
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.") //pos = setting option
        val configItemType = mSettingsDataSet[position] //obtain all required data
        when (viewHolder.itemViewType) {
            TYPE_COLOR_CONFIG -> {
                val colorPickerViewHolder = viewHolder as ColorPickerViewHolder
                val colorConfigItem = configItemType as ConfigData.ColorConfigItem
                val iconResourceId = colorConfigItem.iconResourceId
                val name = colorConfigItem.name
                val sharedPrefString = colorConfigItem.sharedPrefString
                val activity = colorConfigItem.activityToChoosePreference
                colorPickerViewHolder.setIcon(iconResourceId)
                colorPickerViewHolder.setName(name)
                colorPickerViewHolder.setSharedPrefString(sharedPrefString)
                colorPickerViewHolder.setLaunchActivityToSelectColor(activity)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val configItemType = mSettingsDataSet[position]
        return configItemType.configType
    }

    override fun getItemCount(): Int {
        return mSettingsDataSet.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    inner class ColorPickerViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {
        private val mAppearanceButton: Button = view.findViewById(R.id.color_picker_button) as Button
        private var mSharedPrefResourceString: String? = null
        private var mLaunchActivityToSelectColor: Class<ColorSelectionActivity>? = null
        init {
            view.setOnClickListener(this)
        }

        fun setName(name: String) {
            mAppearanceButton.text = name
        }

        fun setIcon(resourceId: Int) {
            val context = mAppearanceButton.context
            mAppearanceButton.setCompoundDrawablesWithIntrinsicBounds(
                    context.getDrawable(resourceId), null, null, null)
        }

        fun setSharedPrefString(sharedPrefString: String) {
            mSharedPrefResourceString = sharedPrefString
        }

        fun setLaunchActivityToSelectColor(activity: Class<ColorSelectionActivity>) {
            mLaunchActivityToSelectColor = activity
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            Log.d(TAG, "onClick() position: " + position)
            if (mLaunchActivityToSelectColor != null) {
                val launchIntent = Intent(view.context, mLaunchActivityToSelectColor)
                launchIntent.putExtra(EXTRA_SHARED_COLOR, mSharedPrefResourceString)
                val activity = view.context as Activity
                activity.startActivityForResult(
                        launchIntent,
                        ZirWatchConfigActivity.UPDATE_COLORS_CONFIG_REQUEST_CODE)
            }
        }
    }

    companion object {
        private val TAG = ZirWatchConfigAdapter::class.java.simpleName
        val TYPE_COLOR_CONFIG = 2 //FIXME replace by enum or dataclass?
    }
}
