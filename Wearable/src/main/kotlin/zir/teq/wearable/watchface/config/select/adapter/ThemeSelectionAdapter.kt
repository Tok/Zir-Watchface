package config.select.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Theme

class ThemeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Theme>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ThemeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_theme, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val theme = mOptions[position]
        val themeViewHolder = vh as ThemeViewHolder
        themeViewHolder.bindTheme(theme)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ThemeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircleView = view.findViewById(R.id.list_item_theme_circle) as CircledImageView
        val mTextView = view.findViewById(R.id.list_item_theme_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindTheme(theme: Theme) {
            mCircleView.setImageResource(theme.iconId)
            mTextView.text = theme.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val theme: Theme = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                loadTheme(view.context, theme)
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    private fun loadTheme(ctx: Context, theme: Theme) {
        //ConfigData.theme = theme
        val editor = ConfigData.prefs.edit()
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
}