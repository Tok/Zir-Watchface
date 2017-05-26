package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Theme

class ThemeSelectionAdapter(
        private val mSharedPrefString: String?,
        private val mThemeOptionsDataSet: ArrayList<Theme>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        android.util.Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ThemeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.theme_config_list_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(TAG, "onBindViewHolder() Element $position set.")
        val theme = mThemeOptionsDataSet[position]
        val themeViewHolder = viewHolder as ThemeSelectionAdapter.ThemeViewHolder
        val ctx = viewHolder.itemView.context
        themeViewHolder.setIcon(theme)
    }

    override fun getItemCount(): Int {
        return mThemeOptionsDataSet.size
    }

    inner class ThemeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.theme) as CircledImageView
            view.setOnClickListener(this)
        }

        fun setIcon(theme: Theme) {
            mView.setImageResource(theme.iconId)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val theme = mThemeOptionsDataSet[position]
            Log.d(TAG, "Theme: $theme onClick() position: $position sharedPrefString: $mSharedPrefString")
            val activity = view.context as Activity
            if (mSharedPrefString != null && !mSharedPrefString.isEmpty()) {
                val sharedPref = activity.getSharedPreferences(
                        activity.getString(R.string.zir_watch_preference_file_key),
                        Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(mSharedPrefString, theme.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        private val TAG = ThemeSelectionAdapter::class.java.simpleName
    }
}