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
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity.Companion.EXTRA_SHARED_COLOR
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity.Companion.EXTRA_SHARED_STROKE
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity.Companion.EXTRA_SHARED_THEME
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
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
            TYPE_STROKE_CONFIG -> return StrokePickerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.config_list_stroke_item, parent, false))
            TYPE_THEME_CONFIG -> return ThemePickerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.config_list_theme_item, parent, false))
            else -> return null
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.") //pos = setting option
        val configItemType = mSettingsDataSet[position] //obtain all required data
        when (viewHolder.itemViewType) {
            TYPE_COLOR_CONFIG -> {
                val mViewHolder = viewHolder as ColorPickerViewHolder
                val item = configItemType as ConfigData.ColorConfigItem
                val iconResourceId = item.iconResourceId
                val name = item.name
                val sharedPrefString = item.sharedPrefString
                val activity = item.activityToChoosePreference
                mViewHolder.setIcon(iconResourceId)
                mViewHolder.setName(name)
                mViewHolder.setSharedPrefString(sharedPrefString)
                mViewHolder.setLaunchActivityToSelectColor(activity)
            }
            TYPE_STROKE_CONFIG -> {
                val mViewHolder = viewHolder as StrokePickerViewHolder
                val item = configItemType as ConfigData.StrokeConfigItem
                val iconResourceId = item.iconResourceId
                val name = item.name
                val sharedPrefString = item.sharedPrefString
                val activity = item.activityToChoosePreference
                mViewHolder.setIcon(iconResourceId)
                mViewHolder.setName(name)
                mViewHolder.setSharedPrefString(sharedPrefString)
                mViewHolder.setLaunchActivityToSelectStroke(activity)
            }
            TYPE_THEME_CONFIG -> {
                val mViewHolder = viewHolder as ThemePickerViewHolder
                val item = configItemType as ConfigData.ThemeConfigItem
                val iconResourceId = item.iconResourceId
                val name = item.name
                val sharedPrefString = item.sharedPrefString
                val activity = item.activityToChoosePreference
                mViewHolder.setIcon(iconResourceId)
                mViewHolder.setName(name)
                mViewHolder.setSharedPrefString(sharedPrefString)
                mViewHolder.setLaunchActivityToSelectTheme(activity)
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

    inner class StrokePickerViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {
        private val mAppearanceButton: Button = view.findViewById(R.id.color_picker_button) as Button
        private var mSharedPrefResourceString: String? = null
        private var mLaunchActivityToSelectStroke: Class<StrokeSelectionActivity>? = null
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

        fun setLaunchActivityToSelectStroke(activity: Class<StrokeSelectionActivity>) {
            mLaunchActivityToSelectStroke = activity
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            Log.d(TAG, "onClick() position: " + position)
            if (mLaunchActivityToSelectStroke != null) {
                val launchIntent = Intent(view.context, mLaunchActivityToSelectStroke)
                launchIntent.putExtra(EXTRA_SHARED_STROKE, mSharedPrefResourceString)
                val activity = view.context as Activity
                activity.startActivityForResult(
                        launchIntent,
                        ZirWatchConfigActivity.UPDATE_STROKE_CONFIG_REQUEST_CODE)
            }
        }
    }

    inner class ThemePickerViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {
        private val mAppearanceButton: Button = view.findViewById(R.id.theme_picker_button) as Button
        private var mSharedPrefResourceString: String? = null
        private var mLaunchActivityToSelectTheme: Class<ThemeSelectionActivity>? = null
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

        fun setLaunchActivityToSelectTheme(activity: Class<ThemeSelectionActivity>) {
            mLaunchActivityToSelectTheme = activity
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            Log.d(TAG, "onClick() position: " + position)
            if (mLaunchActivityToSelectTheme != null) {
                val launchIntent = Intent(view.context, mLaunchActivityToSelectTheme)
                launchIntent.putExtra(EXTRA_SHARED_THEME, mSharedPrefResourceString)
                val activity = view.context as Activity
                activity.startActivityForResult(
                        launchIntent,
                        ZirWatchConfigActivity.UPDATE_THEME_CONFIG_REQUEST_CODE)
            }
        }
    }

    companion object {
        private val TAG = ZirWatchConfigAdapter::class.java.simpleName
        val TYPE_COLOR_CONFIG = 2 //FIXME replace by enum or dataclass?
        val TYPE_STROKE_CONFIG = 3
        val TYPE_THEME_CONFIG = 4
    }
}
