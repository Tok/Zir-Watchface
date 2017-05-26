package zir.teq.wearable.watchface.config.select

import android.support.v7.widget.RecyclerView
import zir.teq.wearable.watchface.model.data.Theme

class ThemeSelectionAdapter(
        private val mSharedPrefString: String?,
        private val mThemeOptionsDataSet: java.util.ArrayList<Theme>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): android.support.v7.widget.RecyclerView.ViewHolder {
        android.util.Log.d(zir.teq.wearable.watchface.config.select.ThemeSelectionAdapter.Companion.TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ThemeViewHolder(
                android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.theme_config_list_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(zir.teq.wearable.watchface.config.select.ThemeSelectionAdapter.Companion.TAG, "onBindViewHolder() Element $position set.")
        val theme = mThemeOptionsDataSet[position]
        val themeViewHolder = viewHolder as zir.teq.wearable.watchface.config.select.ThemeSelectionAdapter.ThemeViewHolder
        val ctx = viewHolder.itemView.context
        themeViewHolder.setItemDisplayColor(ctx.getColor(zir.teq.wearable.watchface.R.color.white))
    }

    override fun getItemCount(): Int {
        return mThemeOptionsDataSet.size
    }

    inner class ThemeViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        private val mView: android.support.wearable.view.CircledImageView
        init {
            mView = view.findViewById(zir.teq.wearable.watchface.R.id.theme) as android.support.wearable.view.CircledImageView
            view.setOnClickListener(this)
        }

        fun setItemDisplayColor(color: Int) {
            mView.setCircleColor(color)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val theme = mThemeOptionsDataSet[position]
            android.util.Log.d(zir.teq.wearable.watchface.config.select.ThemeSelectionAdapter.Companion.TAG, "Theme: $theme onClick() position: $position sharedPrefString: $mSharedPrefString")
            val activity = view.context as android.app.Activity
            if (mSharedPrefString != null && !mSharedPrefString.isEmpty()) {
                val sharedPref = activity.getSharedPreferences(
                        activity.getString(zir.teq.wearable.watchface.R.string.zir_watch_preference_file_key),
                        android.content.Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(mSharedPrefString, theme.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        private val TAG = zir.teq.wearable.watchface.config.select.ThemeSelectionAdapter::class.java.simpleName
    }
}