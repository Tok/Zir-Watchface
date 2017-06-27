package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Theme
import zir.teq.wearable.watchface.util.ViewHelper


class ThemeViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, ThemeSelectionActivity.EXTRA) }
    }
}

class ThemeSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: ThemeSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection)
        val sharedThemeName = intent.getStringExtra(EXTRA)
        mAdapter = ThemeSelectionAdapter(sharedThemeName, Theme.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Theme.all.indexOfFirst { it.name.equals(ConfigData.theme.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_THEME"
    }
}

class ThemeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Theme>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ThemeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val theme = mOptions[position]
        val themeViewHolder = vh as ThemeViewHolder
        themeViewHolder.bindTheme(theme)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ThemeViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        //val mCircleView = view.findViewById<View>(R.id.list_item_cirlce) as CircledImageView
        //val mTextView = view.findViewById<View>(R.id.list_item_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindTheme(theme: Theme) {
            //mCircleView.setImageResource(theme.iconId)
            //mTextView.text = theme.name
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
