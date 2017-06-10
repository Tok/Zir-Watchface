package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView

class ThemeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<zir.teq.wearable.watchface.model.data.Theme>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            ThemeViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_theme, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val theme = mOptions[position]
        val themeViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.ThemeSelectionAdapter.ThemeViewHolder
        themeViewHolder.bindTheme(theme)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ThemeViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView = view as android.widget.LinearLayout
        val mCircleView = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_theme_circle) as android.support.wearable.view.CircledImageView
        val mTextView = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_theme_text) as android.widget.TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindTheme(theme: zir.teq.wearable.watchface.model.data.Theme) {
            mCircleView.setImageResource(theme.iconId)
            mTextView.text = theme.name
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val theme: zir.teq.wearable.watchface.model.data.Theme = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValues(view.context, theme)
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValues(ctx: android.content.Context, theme: zir.teq.wearable.watchface.model.data.Theme) {
        val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).edit()
        with(editor) {
            putString(mPrefString, theme.name)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_hands_act), theme.hands.active)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_hands_amb), theme.hands.ambient)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_triangles_act), theme.triangles.active)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_triangles_amb), theme.triangles.ambient)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_circles_act), theme.circles.active)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_circles_amb), theme.circles.ambient)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_points_act), theme.points.active)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_points_amb), theme.points.ambient)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_text_act), theme.text.active)
            putBoolean(ctx.getString(zir.teq.wearable.watchface.R.string.saved_text_amb), theme.text.ambient)
            commit()
        }
    }
}