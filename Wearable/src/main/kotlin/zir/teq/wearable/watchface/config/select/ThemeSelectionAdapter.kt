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
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ViewHelper

class ThemeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Theme>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        android.util.Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ThemeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_theme, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(TAG, "onBindViewHolder() Element $position set.")
        val theme = mOptions[position]
        val themeViewHolder = viewHolder as ThemeSelectionAdapter.ThemeViewHolder
        ViewHelper.bindCircleColor(themeViewHolder.mView)
        themeViewHolder.setTheme(theme)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ThemeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.theme) as CircledImageView
            view.setOnClickListener(this)
        }

        fun setTheme(theme: Theme) {
            mView.setImageResource(theme.iconId)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val theme: Theme = mOptions[position]
            Log.d(TAG, "Theme: $theme onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValues(view.context, theme)
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    fun updateSavedValues(ctx: Context, theme: Theme) {
        val editor = ConfigData.prefs(ctx).edit()
        with(editor) {
            putString(mPrefString, theme.name)
            putBoolean(ctx.getString(R.string.saved_hands_act), theme.hands.active)
            putBoolean(ctx.getString(R.string.saved_hands_amb), theme.hands.ambient)
            putBoolean(ctx.getString(R.string.saved_triangles_act), theme.triangles.active)
            putBoolean(ctx.getString(R.string.saved_triangles_amb), theme.triangles.ambient)
            putBoolean(ctx.getString(R.string.saved_circles_act), theme.circles.active)
            putBoolean(ctx.getString(R.string.saved_circles_amb), theme.circles.ambient)
            putBoolean(ctx.getString(R.string.saved_points_act), theme.points.active)
            putBoolean(ctx.getString(R.string.saved_points_amb), theme.points.ambient)
            putBoolean(ctx.getString(R.string.saved_text_act), theme.text.active)
            putBoolean(ctx.getString(R.string.saved_text_amb), theme.text.ambient)
            commit()
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}